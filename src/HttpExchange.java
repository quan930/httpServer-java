import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpExchange implements Runnable{
    private Socket socket;
    private String context;
    private Response response;
    public Request request;
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
    public HttpExchange (Socket socket,String context,Response response){
        this.socket = socket;
        this.context = context;
        this.response = response;
    }
    private void request() throws IOException {
        request = new Request(socket.getInputStream());
        request.readRequest();
        //请求行请求头读取完毕
        response.response(this);
    }
}
