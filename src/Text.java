import java.io.File;
import java.io.IOException;

public class Text {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(7010,"/books");
        httpServer.setResponse(new Response() {
            @Override
            public void response(HttpExchange httpExchange) throws IOException {
                System.out.println(httpExchange.getRequestMethod());
                System.out.println(httpExchange.getRequestHeaders().get("Accept"));
                System.out.println(httpExchange.getRequestContext());

//                httpExchange.sendResponseHeaders("Content-Length", String.valueOf("hello world".getBytes().length));
//                httpExchange.sendResponseBody("hello world".getBytes());

                File file = new File("/Users/daquan/IdeaProjects/httpserver/quan.html");
                httpExchange.sendResponseHeaders("Content-Length", String.valueOf(file.length()));
                httpExchange.sendResponseBody(file);
            }
        });
        httpServer.setRequestMessageBody(new RequestMessageBody() {
            @Override
            public void requestMessageBody(String requestMessageBody) {
                System.out.println("RequestMessageBody: "+requestMessageBody);
            }
        });
        httpServer.start();
    }
}
