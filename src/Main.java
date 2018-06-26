import com.sun.corba.se.spi.activation.Server;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        InetSocketAddress addr=new InetSocketAddress(7001);
        HttpServer server=HttpServer.create(addr,1);
        server.createContext("/books").setHandler(new HttpHandler() {
            @Override
            public void handle(HttpExchange arg0) throws IOException {
                String method=arg0.getRequestMethod();
                System.out.println(method);
                arg0.sendResponseHeaders(200, "Hello world!!!".length());
                arg0.getResponseBody().write("Hello world!!!".getBytes());
                arg0.getResponseBody().flush();
                arg0.getResponseBody().close();
                System.out.println("books.....");

            }
        });
        System.out.println(server.getClass().getName());
        server.start();
    }
}
