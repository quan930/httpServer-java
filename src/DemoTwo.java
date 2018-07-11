import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DemoTwo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket= new ServerSocket(7002);
        Socket server = serverSocket.accept();

        StringBuilder requestLine = new StringBuilder();
        StringBuilder requestHeader = new StringBuilder();
        StringBuilder message = new StringBuilder();//编码？？？？

        InputStream in  = server.getInputStream();

        int i;
        while (true){//读取请求行
            i = in.read();
            if(i==13){
                in.read();
//                i = in.read();//请求行最后一次读
                break;
            }else{
                requestLine.append((char) i);
                continue;
            }
        }
        System.out.println("请求行读取完毕");
        System.out.println(requestLine.toString());
        byte [] bytes = new byte[3];
        while ((i = in.read())!=-1){//读取请求头////////判断结束！！！！！！！！！
//            i = in.read();
            if(i==13){
                requestHeader.append((char)i);
                in.read(bytes);
                if(new String(bytes).equals("\n\r\n")){
                    System.out.println("情求头完毕");
                    break;
                }else{
                    requestHeader.append(new String(bytes));
                    continue;
                }
            }else{
                requestHeader.append((char)i);
            }
        }
        System.out.println("请求头读取完毕");
        System.out.println(requestHeader.toString());
        System.out.println("请求头读取完毕");

        sendMessage(server.getOutputStream());
//        if ((in.read())!=-1){
//            System.out.println("报文有");
//            while ((i=in.read())!=(-1)){//报文
//                message.append((char)i);
//                System.out.print((char)i);
//            }
//        }else{
//
//        }
//        while ((i=in.read())!=(-1)){//报文
//            message.append((char)i);
//            System.out.print((char)i);
//        }
        System.out.println("报文");
        System.out.println(message.toString());
        in.close();
        server.close();
        serverSocket.close();
    }
    private static void sendMessage(OutputStream out) throws IOException {
        Date date = new Date();
        File file = new File("/Users/daquan/Desktop/quan.html");
        out.write("HTTP/1.0 200 OK\r\n".getBytes());
//        writer.println("HTTP/1.0 200 OK");
//        writer.println("Date:" +date.toString() );
        String aa = "Content-Encoding:" + "UTF-8"+"\r\n";
        out.write(aa.getBytes());
        String ab = "Content-Type:" + "text/html"+"\r\n";
//        String ab = "Content-type"+","+"textml;charset=UTF-8";
        out.write(ab.getBytes());
        String a = "Date:" +date.toString()+"\r\n";
        out.write(a.getBytes());
//        writer.println("Content-Length:"+file.length()+"\r\n");
        String b = "Content-Length:"+file.length()+"\r\n\r\n";
        out.write(b.getBytes());
//        writer.println(file);
//        writer.close();
        InputStream fileIn = new FileInputStream(file);
        int n=-1;
        while ((n=fileIn.read())!=-1){
            out.write(n);
        }
        out.flush();
        out.close();
        fileIn.close();
    }
}
