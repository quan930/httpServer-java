import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

public class HttpExchange implements Runnable{
    private Socket socket;
    private String context;//上下文
    private Response response;//响应接口实现
    private Request request;
    private byte body[];
    private StringBuilder responseHeaders = new StringBuilder();//响应头
    private File file;

    public HttpExchange (Socket socket, String context, Response response){
        this.socket = socket;
        this.context = context;//所设置的上下文
        this.response = response;
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
        request = new Request(socket.getInputStream());
        request.init();//请求报文
        response.response(this);//接口方法
        responseMessage();//响应报文
    }

    private void responseMessage() throws IOException {//响应报文
        ResponseMessage responseMessage = new ResponseMessage(socket.getOutputStream());
        File fileo = new File(new File("").getAbsolutePath()+request.getRequestContext());
        boolean fileYOrN = responseMessage.responseFile(fileo);
        if(fileYOrN){
            return;
        }

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

    public String getRequestMessageBody() {
        return request.getRequestMessageBody();
    }
}
