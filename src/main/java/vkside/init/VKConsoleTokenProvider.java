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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VKConsoleTokenProvider implements VKTokenProvider {
    VKMain main = Starter.getVkMain();

    @Override
    public Integer getVkAppId() {
        System.out.print("Input VK appId:  ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Integer getVkGroupId() {
        System.out.print("Input VK groupId:  ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String getVkAppSecretCode() {
        System.out.print("Input VK appSecretCode:  ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error appSecretCode";
    }

    @Override
    public String getVkAccesToken() {
        String code = getCode();
        return getAccesToken(code);

    }

    @Override
    public Integer getVkUserId() {
        System.out.print("Input VK userId:  ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
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
}
