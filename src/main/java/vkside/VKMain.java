package vkside;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import starter.Starter;
import tgside.handlers.ents.PhotoResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class VKMain extends Thread {
    private String GROUP_TOKEN = "68fbff47122d82c80df25aa5082c4fe7e99b14bda4df69387ca4fcec22928b605d67969e0341b314919e8";
    private String appSecretCode =  "qsPNy30f45dqTrHh1vML";
    private String userToken = createUserToken();
    private final Integer USER_ID = 597538;
    private final Integer GROUP_ID = 165014414;
    private final Integer APP_ID = 6261709;
    private VkApiClient vk;
    private GroupActor groupActor;
    private UserActor actor;

    public void run() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        groupActor = new GroupActor(GROUP_ID, GROUP_TOKEN);
        actor = new UserActor(USER_ID, userToken);
        System.out.println("VK actor has been initialized");

    }

    public VkApiClient getVk() {
        return vk;
    }

    public Integer getUserId() {
        return USER_ID;
    }

    public Integer getGroupId() {
        return GROUP_ID;
    }

    public GroupActor getGroupActor() {
        return groupActor;
    }

    public UserActor getActor() {
        return actor;
    }
    public String createUserToken(){
        HttpClient client = HttpClientBuilder.create().setProxy(Starter.proxy).build();
        String link1 = "https://oauth.vk.com/authorize?client_id=" + APP_ID + "&display=page&redirect_uri=&scope=photos,groups&response_type=code&v=5.74";
        HttpPost post1 = new HttpPost(link1);
        StringBuffer result = new StringBuffer();
        try {
            HttpResponse response = client.execute(post1);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpGet get1 = new HttpGet();
        try {
            HttpResponse response = client.execute(get1);
            response.
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        PhotoResponse pr = gson.fromJson(result.toString(), PhotoResponse.class);
        return pr.getResult().getFilePath();
    }
}
