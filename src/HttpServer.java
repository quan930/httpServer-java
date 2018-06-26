import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket= new ServerSocket(7002);
        Socket server = serverSocket.accept();
        String message = null;

        Scanner in = new Scanner(server.getInputStream());

        while ((message=in.nextLine())!=null){
            System.out.println(message);
        }
        System.out.printf("登录");
        in.close();
        server.close();
        serverSocket.close();
    }
}