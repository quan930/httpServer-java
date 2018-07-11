import java.io.IOException;
import java.net.Socket;

public class MainTwo {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(7009,"/books");
        httpServer.response(new Response() {
            @Override
            public void response(HttpExchange httpExchange) {
                System.out.println(httpExchange.request.getMethod());
                System.out.println(httpExchange.request.getContext());
                System.out.println(httpExchange.request.getHeaders().get("Accept"));
            }
        });
        httpServer.start();
    }
}