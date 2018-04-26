package vkside;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import vkside.init.VKConsoleTokenProvider;


public class VKMain extends Thread {
    private final String appSecretCode =  "qsPNy30f45dqTrHh1vML";
    private final Integer appID = 6261709;
    private final Integer userID = 597538;
    private final Integer groupID = 165014414;
    private VkApiClient vk;
    private UserActor actor;

    public void run() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        actor = new UserActor(userID, new VKConsoleTokenProvider().getToken());
        System.out.println("VK actor has been initialized");
    }

    public VkApiClient getVk() {
        return vk;
    }


    public UserActor getActor() {
        return actor;
    }

    public Integer getAppID() {
        return appID;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public String getAppSecretCode() {
        return appSecretCode;
    }
}
