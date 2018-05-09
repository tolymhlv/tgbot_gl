package vkside.init;

import vkside.VKMain;

public interface VKTokenProvider {

    Integer getVkAppId();

    Integer getVkGroupId();

    String getVkAppSecretCode();

    String getVkAccessToken();

    Integer getVkUserId();
}
