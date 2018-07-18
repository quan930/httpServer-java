import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//无需更改
public class HttpServer {
    private int prot;//端口号
    private String context;//环境
    private HttpExchange httpExchange;//当前线程
    private Response response;

    public HttpServer (int prot,String context){
        this.prot = prot;
        this.context = context;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(prot);
        Socket socket=null;//客户端套接字
        while(true){
            socket = serverSocket.accept();
            httpExchange = new HttpExchange(socket,context,response);
            new Thread(httpExchange).start();
        }
    };
    public void setResponse(Response response){//实现响应接口
        this.response = response;
    }
}