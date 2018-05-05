package vkside.init;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class VKPropertiesTokenProvider implements VKTokenProvider {
    private Properties properties;

    public VKPropertiesTokenProvider(String propertiesPath) {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            System.err.println("Error with properies files of secret keys");
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
    public String getVkAccesToken() {
        return (String) properties.getProperty("vkAccesToken");
    }

    @Override
    public Integer getVkUserId() {
        return Integer.parseInt(properties.getProperty("vkUserId"));
    }
}
