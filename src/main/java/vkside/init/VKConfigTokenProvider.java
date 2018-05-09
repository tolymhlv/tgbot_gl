package vkside.init;

import starter.Starter;

public class VKConfigTokenProvider implements VKTokenProvider {

    @Override
    public Integer getVkAppId() {
        return Starter.getConfig().getVkAppId();
    }

    @Override
    public Integer getVkGroupId() {
        return Starter.getConfig().getVkGroupId();
    }

    @Override
    public String getVkAppSecretCode() {
        return Starter.getConfig().getVkAppSecretCode();
    }

    @Override
    public String getVkAccessToken() {
        return Starter.getConfig().getVkAccessToken();
    }

    @Override
    public Integer getVkUserId() {
        return Starter.getConfig().getVkUserId();
    }
}
