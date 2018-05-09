package starter.init;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Config {
    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("vkAppId")
    @SerializedName("vkAppId")
    private Integer vkAppId;

    @JsonProperty("vkAppSecretCode")
    @SerializedName("vkAppSecretCode")
    private String vkAppSecretCode;

    @JsonProperty("vkGroupId")
    @SerializedName("vkGroupId")
    private Integer vkGroupId;

    @JsonProperty("vkAccessToken")
    @SerializedName("vkAccessToken")
    private String vkAccessToken;

    @JsonProperty("vkUserId")
    @SerializedName("vkUserId")
    private Integer vkUserId;

    @JsonProperty("startWithProxy")
    @SerializedName("startWithProxy")
    private Boolean startWithProxy;

    @JsonProperty("proxyAddress")
    @SerializedName("proxyAddress")
    private String proxyAddress;

    @JsonProperty("proxyPort")
    @SerializedName("proxyPort")
    private Integer proxyPort;


    @JsonProperty("tgBotToken")
    @SerializedName("tgBotToken")
    private String tgBotToken;

    public Integer getVkAppId() {
        return vkAppId;
    }

    public String getVkAppSecretCode() {
        return vkAppSecretCode;
    }

    public Integer getVkGroupId() {
        return vkGroupId;
    }

    public String getVkAccessToken() {
        return vkAccessToken;
    }

    public Integer getVkUserId() {
        return vkUserId;
    }

    public Boolean isStartWithProxy() {
        return startWithProxy;
    }

    public String getProxyAddress() {
        return proxyAddress;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public String getTgBotToken() {
        return tgBotToken;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", vkAppId=" + vkAppId +
                ", vkAppSecretCode='" + vkAppSecretCode + '\'' +
                ", vkGroupId=" + vkGroupId +
                ", vkAccessToken='" + vkAccessToken + '\'' +
                ", vkUserId=" + vkUserId +
                ", startWithProxy=" + startWithProxy +
                ", proxyAddress='" + proxyAddress + '\'' +
                ", proxyPort=" + proxyPort +
                ", tgBotToken='" + tgBotToken + '\'' +
                '}';
    }
}
