package starter.init;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HerokuConfigInitializer implements ConfigInitializer {
   HashMap<String,String> config;

    public HerokuConfigInitializer() {
        HttpGet hg = new HttpGet("https://api.heroku.com/apps/tg-bot-1/config-vars");
        hg.setHeader("Accept", "application/vnd.heroku+json; version=3");
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(hg)) {
            config = new HashMap<String, String>((Map<String, String>) response.getEntity());
            System.out.println(config);
        } catch (IOException e) {
            System.out.println("Error with heroku config upload");
        }
    }

    @Override
    public boolean getProxyStatus() {
        return Boolean.parseBoolean(config.get("startWithProxy"));
    }

    @Override
    public String getProxyAddress() {
        return config.get("proxyAddress");
    }

    @Override
    public Integer getProxyPort() {
        return Integer.parseInt(config.get("proxyPort"));
    }
}
