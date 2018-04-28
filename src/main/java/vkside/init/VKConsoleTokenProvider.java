package vkside.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import starter.Starter;
import tgside.handlers.ents.PhotoResponse;
import vkside.VKMain;
import vkside.ents.AccessToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VKConsoleTokenProvider implements VKTokenProvider {
    VKMain main = Starter.vkMain;

    @Override
    public String getToken() {
//        String code = getCode();
//        return getAccesToken(code);
        String mock = "b51f2e629217e07c4b1a93d946a062767534a3b978a14912eebdd7e89a76d43cfa02d9deae3fa8ec11c63";
        return mock;
    }

    public String getCode() {
        String link1 = "https://oauth.vk.com/authorize?client_id=" + main.getAppID() + "&display=page&redirect_uri=&scope=photos,groups&response_type=code&v=5.74";
        System.out.println(link1);
        System.out.print("Follow the link and copy the code from browser. Code:  ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ErrorCode";

    }

    private String getAccesToken(String code) {
        String link = "https://oauth.vk.com/access_token?client_id=" + main.getAppID() + "&client_secret=" + main.getAppSecretCode() + "&redirect_uri=&code=" + code;
        HttpClient client = HttpClientBuilder.create().setProxy(Starter.proxy).build();
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
}
