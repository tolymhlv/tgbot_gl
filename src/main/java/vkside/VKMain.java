package vkside;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;


public class VKMain extends Thread{
    private static final String GROUP_TOKEN = "68fbff47122d82c80df25aa5082c4fe7e99b14bda4df69387ca4fcec22928b605d67969e0341b314919e8";
    private static final String USER_TOKEN = "416c4e34a80fd658e417478c99a71485c1f4c190e5c71a3eea3bd51041ee9c995fb7923cb782f4ad58909";
    private static final Integer USER_ID = 597538;
    private static final Integer GROUP_ID = 165014414;
    private static VkApiClient vk;
    private static GroupActor groupActor;
    private static UserActor actor;

    public void run() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        groupActor = new GroupActor(GROUP_ID, GROUP_TOKEN);
        actor = new UserActor(USER_ID, USER_TOKEN);

    }

    public static VkApiClient getVk() {
        return vk;
    }

    public static Integer getUserId() {
        return USER_ID;
    }

    public static Integer getGroupId() {
        return GROUP_ID;
    }

    public static GroupActor getGroupActor() {
        return groupActor;
    }

    public static UserActor getActor() {
        return actor;
    }
}
