package utils;

import org.apache.http.HttpHost;
import starter.Starter;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class URLReader {
    public static final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Starter.proxy.getHostName(), Starter.proxy.getPort()));

    public static void copyURLToFile(String url, File file) {
        try {
            InputStream input = URI.create(url).toURL().openConnection(proxy).getInputStream();
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

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }

            input.close();
            output.close();

            System.out.println("Photo '" + file.getName() + "' was saved on server!");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}
