package vkside.init;

import java.io.*;
import java.util.Properties;

public class VKPropertiesTokenProvider implements VKTokenProvider {
    private Properties properties;

    public VKPropertiesTokenProvider(String propertiesPath) {
        properties = new Properties();
        try (InputStream fis = this.getClass().getResourceAsStream(propertiesPath)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            System.err.println("Error with properties files of secret keys");
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
    public String getVkAccessToken() {
        return (String) properties.getProperty("vkAccessToken");
    }

    @Override
    public Integer getVkUserId() {
        return Integer.parseInt(properties.getProperty("vkUserId"));
    }
}
