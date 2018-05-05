package vkside.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import starter.Starter;
import vkside.VKMain;
import vkside.ents.AccessToken;

import java.io.*;
import java.util.Properties;

public class VKMockTokenProvider implements VKTokenProvider {
    VKMain main = Starter.getVkMain();
    private Properties properties;

    public VKMockTokenProvider(String propertiesPath) {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            System.err.println("Error with properies files of secret keys");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer getVkAppId() {
        return Integer.parseInt(properties.getProperty("vkAppId"));
    }

    @Override
    public Integer getVkGroupId() {
        return Integer.parseInt(properties.getProperty("vkGroupId"));
    }

    @Override
    public String getVkAppSecretCode() {
        return (String) properties.getProperty("vkAppSecretCode");
    }

    @Override
    public String getVkAccesToken() {
        String code = getCode();
        return getAccesToken(code);

    }
    public String getCode() {
        String link1 = "https://oauth.vk.com/authorize?client_id=" + main.getVkAppId() + "&display=page&redirect_uri=&scope=photos,groups,offline&response_type=code&v=5.74";
        System.out.println(link1);
        System.out.print("Follow the link and copy the code from browser. Code:  ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error code";

    }

    private String getAccesToken(String code) {
        String link = "https://oauth.vk.com/access_token?client_id=" + main.getVkAppId() + "&client_secret=" + main.getVkAppSecretCode() + "&redirect_uri=&code=" + code;
        HttpClient client = HttpClientBuilder.create().setProxy(Starter.getProxy()).build();
        HttpPost post = new HttpPost(link);
        StringBuffer result = new StringBuffer();
        try {
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        AccessToken response = gson.fromJson(result.toString(), AccessToken.class);
        String token = response.getAccessToken();
        System.out.println(token);
        return token;
    }

    @Override
    public Integer getVkUserId() {
        return Integer.parseInt(properties.getProperty("vkUserId"));
    }
}
