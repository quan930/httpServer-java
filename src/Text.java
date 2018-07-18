import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Text {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(7010,"/books");
        httpServer.setResponse(new Response() {
            @Override
            public void response(HttpExchange httpExchange) throws IOException {
                System.out.println(httpExchange.getRequestMethod());
                System.out.println(httpExchange.getRequestContext());
                System.out.println(httpExchange.getRequestMessageBody());

//                if ("{\"user\":\"quan\",\"password\":\"quan123\"}".equals(httpExchange.getRequestMessageBody())){
//                    httpExchange.sendResponseHeaders("Content-Length", String.valueOf("Password is correct".getBytes().length));
//                    httpExchange.sendResponseBody("Password is correct".getBytes());
//                }else {
//                    httpExchange.sendResponseHeaders("Content-Length", String.valueOf("Password mistake".getBytes().length));
//                    httpExchange.sendResponseBody("Password mistake".getBytes());
//                }
//
                File file = new File("/Users/daquan/IdeaProjects/httpserver/quan.html");
                httpExchange.sendResponseHeaders("Content-Length", String.valueOf(file.length()));
                httpExchange.sendResponseBody(file);
            }
        });
        httpServer.start();
    }
}
