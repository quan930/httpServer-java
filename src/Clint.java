import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Clint {
    public static void main(String[] args) throws Exception {
        URL u1 = new URL("http://47.94.13.255");

        //创建到web服务器的http链接
        HttpURLConnection con1 = (HttpURLConnection) u1.openConnection();
        //真正的连接到web服务器
        con1.connect();

        //从服务器读取内容，实际上没有必要，只需要读取http头信息就可以了。
        BufferedReader br = new BufferedReader(new InputStreamReader(con1.getInputStream(), "GBK"));
        String s = null;
        while ((s = br.readLine()) != null) {
			System.out.println(s);
        }
    }
}
