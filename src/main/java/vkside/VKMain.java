package vkside;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import starter.Starter;
import utils.RateLimiterHttpTransportClient;
import vkside.init.*;


public class VKMain extends Thread {
    private VkApiClient vk;
    private UserActor actor;
    private VKTokenProvider tokenProvider;

    public void run() {
        TransportClient transportClient = new RateLimiterHttpTransportClient(HttpTransportClient.getInstance(), 2.5);
        vk = new VkApiClient(transportClient);
        tokenProvider = new VKConfigTokenProvider();
        actor = new UserActor(getVkUserId(), tokenProvider.getVkAccessToken());
        System.out.println("VK actor has been initialized");
    }

    public VkApiClient getVk() {
        return vk;
    }

    public UserActor getActor() {
        return actor;
    }

    public Integer getVkAppId() {
        return tokenProvider.getVkAppId();
    }

    public Integer getVkUserId() {
        return tokenProvider.getVkUserId();
    }

    public Integer getVkGroupId() {
        return tokenProvider.getVkGroupId();
    }

    public String getVkAppSecretCode() {
        return tokenProvider.getVkAppSecretCode();
    }
}
