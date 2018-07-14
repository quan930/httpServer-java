import java.io.IOException;

public interface Response {//响应接口
    void response(HttpExchange httpExchange) throws IOException;
}
