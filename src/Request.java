import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Request {//请求类
    private InputStream in;
    private String method;//请求方式
    private String context;//请求上下文
    private Map<String,String> headers;//请求头
    private RequestMessageBody requestMessageBody;///请求报文主体

    public Request(InputStream in,RequestMessageBody requestMessageBody){
        this.in = in;
        this.requestMessageBody = requestMessageBody;
    }

    public void readRequest() throws IOException {//请求报文首部
        StringBuilder requestLine = new StringBuilder();
        StringBuilder requestHeader = new StringBuilder();
        int i;
        while ((i = in.read())!=13){//读取请求行
            requestLine.append((char)i);
        }
        requestLine(requestLine);

        in.read();
        byte [] bytes = new byte[3];
        while ((i = in.read())!=-1){//请求头
            if(i==13){
                requestHeader.append((char)i);
                in.read(bytes);
                if(new String(bytes).equals("\n\r\n")){
                    requestHeader.append('\n');
                    break;
                }else{
                    requestHeader.append(new String(bytes));
                    continue;
                }
            }else{
                requestHeader.append((char)i);
            }
        }
        requestHeader(requestHeader);
    }

    public void requestBody() throws IOException {//请求报文主体
        //!!!!!!编码格式！！！！！！！！
        int i;
        StringBuilder requestBody = new StringBuilder();
        while ((i=in.read())!=-1){
            requestBody.append((char)i);
        }
        requestMessageBody.requestMessageBody(requestBody.toString());
    }

    private void requestLine(StringBuilder requestLine){//请求行
//        System.out.println(requestLine.toString());
        String line[] = requestLine.toString().split(" ");
        method = line[0];
        int i = line[1].indexOf("?");
        if(i!=-1) {//切割
            context = line[1].substring(0,i);
        }else{
            context = line[1];
        }
    }

    private void requestHeader(StringBuilder requestHeader){//请求头
        headers = new HashMap<>();
        String headerString[] = requestHeader.toString().split("\r\n");
        for(int i = 0;i < headerString.length;i++){
            String headerOne[] = headerString[i].split(": ");
            headers.put(headerOne[0],headerOne[1]);
        }
    }

    public String getRequestContext() {//请求上下文
        return context;
    }

    public String getMethod() {//请求方式
        return method;
    }

    public Map<String, String> getRequestHeaders() {//请求头
        return headers;
    }
}
