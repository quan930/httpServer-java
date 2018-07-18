import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Request {//请求类
    private InputStream in;
    private String method;//请求方式
    private String context;//请求上下文
    private String queryString;//查找字符串
    private Map<String,String> requestHeaders;//请求头
    private String requestMessageBody;

    public Request(InputStream in){//构造器
        this.in = in;
    }

    public void init() throws IOException {//请求报文首部
        requestLine();
        requestHeader();
        requestBody();//请求报文主体
    }

    private void requestLine() throws IOException {//请求行
        StringBuilder requestLine = new StringBuilder();
        int i;
        while ((i = in.read())!=13){//读取请求行
            requestLine.append((char)i);
        }
        in.read();

        String line[] = requestLine.toString().split(" ");
        method = line[0];
        int mm = line[1].indexOf("?");
        if(mm!=-1) {//切割
            context = line[1].substring(0,mm);
        }else{
            context = line[1];
        }
    }

    private void requestHeader() throws IOException {//请求头
        StringBuilder requestHeader = new StringBuilder();
        int m;
        byte [] bytes = new byte[3];
        while ((m = in.read())!=-1){//请求头
            if(m==13){
                requestHeader.append((char)m);
                in.read(bytes);
                if(new String(bytes).equals("\n\r\n")){
                    requestHeader.append('\n');
                    break;
                }else{
                    requestHeader.append(new String(bytes));
                    continue;
                }
            }else{
                requestHeader.append((char)m);
            }
        }

        requestHeaders = new HashMap<>();
        String headerString[] = requestHeader.toString().split("\r\n");
        for(int i = 0;i < headerString.length;i++){
//            System.out.println(headerString[i]);
            String headerOne[] = headerString[i].split(": ");
            requestHeaders.put(headerOne[0],headerOne[1]);
        }
    }

    private void requestBody() throws IOException {//请求报文主体
        //!!!!!!编码格式！！！！！！！！
        if("GET".equals(method)){
        }else{
            StringBuilder messageBody = new StringBuilder();
//            System.out.println("post请求");
            int lenght = Integer.valueOf(requestHeaders.get("Content-Length"));
            for (int i = 0;i < lenght;i++){
                messageBody.append((char)in.read());
            }
            requestMessageBody = messageBody.toString();
//            requestMessageBody = new String(messageBody.toString().getBytes("UTF-8"));
        }
    }

    public String getRequestContext() {//请求上下文
        return context;
    }

    public String getMethod() {//请求方式
        return method;
    }

    public Map<String, String> getRequestHeaders() {//请求头
        return requestHeaders;
    }

    public String getRequestMessageBody() {
        return requestMessageBody;
    }
}
