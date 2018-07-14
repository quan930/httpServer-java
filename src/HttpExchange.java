import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

public class HttpExchange implements Runnable{
    private Socket socket;
    private String context;//上下文
    private Response response;//响应接口实现
    private RequestMessageBody requestMessageBody;//请求报文接口实现
    private Request request;
    private byte body[];
    private StringBuilder responseHeaders = new StringBuilder();
    private File file;

    public HttpExchange (Socket socket,String context,Response response,RequestMessageBody requestMessageBody){
        this.socket = socket;
        this.context = context;//所设置的上下文
        this.response = response;
        this.requestMessageBody = requestMessageBody;
    }

    public void sendResponseHeaders(String fieldName,String fieldValue){//发送响应字段
        responseHeaders.append(fieldName+": "+fieldValue+"\r\n");
    }

    public void sendResponseBody(byte b[]) throws IOException {//响应报文主体
        body = b;
    }

    public void sendResponseBody(File file) throws IOException {//响应报文主体
        this.file = file;
    }

    //单个线程类
    //接口
    @Override
    public void run() {
        try {
            request();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void request() throws IOException {
        request = new Request(socket.getInputStream(),requestMessageBody);
        request.readRequest();
        //请求行请求头读取完毕
        response.response(this);
        responseMessage();
        request.requestBody();//请求报文主体
    }

    private void responseMessage() throws IOException {//响应报文
        File fileo = new File(request.getRequestContext());
        if(fileo.isFile()){
//            System.out.println("有文件");
            socket.getOutputStream().write("HTTP/1.0 200 OK\r\n".getBytes());
            socket.getOutputStream().write("Content-Type: image/png".getBytes());
            String s = "Content-Length:"+fileo.length()+"\r\n\r\n";
            socket.getOutputStream().write(s.getBytes());

            InputStream fileIn = new FileInputStream(fileo);
            int n = -1;
            while ((n=fileIn.read())!=-1){
                socket.getOutputStream().write(n);
            }
            socket.getOutputStream().flush();
            return;
        }
        ResponseMessage responseMessage = new ResponseMessage(socket.getOutputStream());
        if (this.context.equals(request.getRequestContext())){//200
            if (file!=null){
                responseMessage.responseOK(responseHeaders.toString(),file);
            }else {
                responseMessage.responseOK(responseHeaders.toString(),body);
            }
        }else {
            responseMessage.responseNotFound();
        }
    }

    public String getRequestMethod(){
        return request.getMethod();
    }

    public Map<String, String> getRequestHeaders() {
        return request.getRequestHeaders();
    }

    public String getRequestContext(){
        return request.getRequestContext();
    }
}
