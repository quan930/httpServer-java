import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Request {//响应类
    private InputStream in;
    private String method;//请求方式
    private String context;
    private Map<String,String> headers;
    public Request(InputStream in){
        this.in = in;
    }

    public String getContext() {
        return context;
    }

    public String getMethod() {
        return method;
    }

    public void readRequest() throws IOException {
        StringBuilder requestLine = new StringBuilder();
        StringBuilder requestHeader = new StringBuilder();
        int i;
        while ((i = in.read())!=13){//读取请求行
            requestLine.append((char)i);
        }
        requestLine(requestLine);

        in.read();
        byte [] bytes = new byte[3];
        while ((i = in.read())!=-1){
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
    private void requestLine(StringBuilder requestLine){
        //请求行
        String line[] = requestLine.toString().split(" ");
        method = line[0];
        int i = line[1].indexOf("?");
        if(i!=-1) {
            context = line[1].substring(0,i);
        }else{
            context = line[1];
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    private void requestHeader(StringBuilder requestHeader){
        //请求行
        headers = new HashMap<>();
        String headerString[] = requestHeader.toString().split("\r\n");
        for(int i = 0;i < headerString.length;i++){
            String headerOne[] = headerString[i].split(": ");
            headers.put(headerOne[0],headerOne[1]);
        }
    }
}
