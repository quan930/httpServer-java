import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) throws IOException {
//        char m = '\n';
//        System.out.println((int)m);
//        char n = '\r';
//
//        ServerSocket serverSocket= new ServerSocket(7002);
//        Socket server = serverSocket.accept();
//        InputStream in = server.getInputStream();
//        int i;
//        while((i=in.read())!=-1){
//            System.out.print((char)i);
//        }
//        String string = String.join(" / ","a","b","c");
//        System.out.println(string);

        String a = "hello";
        String b = "hello";
        String aa = new String("hello");
        String bb = new String("hello");

        System.out.println(a.equals(b));
        System.out.println(a==b);
        System.out.println(aa.equals(bb));
        System.out.println(aa==bb);
        System.out.println(aa==a);

        System.out.println(new Date().toString());
    }
}
