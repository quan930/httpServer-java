import java.io.*;
import java.util.Date;

public class ResponseMessage {//发送响应报文
    private OutputStream outputStream;
    public ResponseMessage(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public boolean responseFile(File file) throws IOException {//请求文件，true为成功
        if(file.isFile()){
//            System.out.println("有文件");
//            System.out.println(file.getName().substring(file.getName().lastIndexOf(".") + 1));
            outputStream.write("HTTP/1.0 200 OK\r\n".getBytes());
            String a = "Content-Type: "+file.getName().substring(file.getName().lastIndexOf(".") + 1);
            outputStream.write(a.getBytes());
            String s = "Content-Length:"+file.length()+"\r\n\r\n";
            outputStream.write(s.getBytes());

            int n = -1;
            InputStream fileIn = new BufferedInputStream(new FileInputStream(file));
            while((n=fileIn.read())!=-1){
                outputStream.write(n);
            }

            outputStream.flush();
            outputStream.close();
            return true;
        }
        return false;
    }


    public void responseOK(String responseHeaders,byte body[]){
        try {
            outputStream.write("HTTP/1.0 200 OK\r\n".getBytes());
            outputStream.write(responseHeaders.getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(body);
            outputStream.flush();
//            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void responseOK(String responseHeaders,File file){
        try {
            outputStream.write("HTTP/1.0 200 OK\r\n".getBytes());
            outputStream.write(responseHeaders.getBytes());
            outputStream.write("\r\n".getBytes());
            InputStream fileIn = new FileInputStream(file);
            int n = -1;
            while ((n=fileIn.read())!=-1){
                outputStream.write(n);
            }
            outputStream.flush();
//            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void responseNotFound(){//404错误
        try {
            outputStream.write("HTTP/1.0 404 Not Found\r\n".getBytes());
            Date date = new Date();
            String a = "Date:" +date.toString()+"\r\n";
            outputStream.write(a.getBytes());
            String b = "Content-Length:"+"<h1>404 Not Found</h1>No context found for request".length()+"\r\n\r\n";
            outputStream.write(b.getBytes());
            outputStream.write("<h1>404 Not Found</h1>No context found for request".getBytes());
            outputStream.flush();
//            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
