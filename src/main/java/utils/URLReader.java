package utils;

import org.apache.http.HttpHost;
import starter.Starter;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Properties;

public class URLReader {
    private static final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Starter.getProxy().getHostName(), Starter.getProxy().getPort()));

    public static File copyURLToFile(String url, File file) {
        InputStream input = null;
        try (FileOutputStream output = new FileOutputStream(file)){
            if (Starter.isStartWithProxy()) {
                input = URI.create(url).toURL().openConnection(proxy).getInputStream();
            } else {
                input = URI.create(url).toURL().openConnection().getInputStream();
            }
            if (file.exists()) {
                if (file.isDirectory())
                    throw new IOException("File '" + file + "' is a directory");

                if (!file.canWrite())
                    throw new IOException("File '" + file + "' cannot be written");
            } else {
                File parent = file.getParentFile();
                if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }

            input.close();
            output.close();

            System.out.println("File '" + file.getName() + "' was saved to the storage!");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException ei) {
                System.out.println("Error with file. InputStream was not been init");
            }
        }
        return file;
    }

}
