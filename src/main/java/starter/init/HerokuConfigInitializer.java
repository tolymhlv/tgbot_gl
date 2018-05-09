package starter.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import starter.Starter;
import utils.GsonUtil;
import utils.JacksonUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class HerokuConfigInitializer implements ConfigInitializer {

    public Config getConfig(String herokuToken) {
        Config config = new Config();
        HttpGet get = new HttpGet("https://api.heroku.com/apps/tg-bot-1/config-vars");
        get.setHeader("Accept", "application/vnd.heroku+json; version=3");
        get.setHeader("Authorization", "Bearer " + herokuToken);
        try (CloseableHttpClient client = HttpClientBuilder.create().build();
             CloseableHttpResponse response = client.execute(get)) {
//            config = GsonUtil.parseJson(response.getEntity().getContent(), config.getClass());
            config = JacksonUtil.parseJson(response.getEntity().getContent(), config.getClass());
            System.out.println(config);
        } catch (IOException e) {
            System.out.println("Error with Heroku config initializer");
            e.printStackTrace();
        }
        return config;
    }

}
