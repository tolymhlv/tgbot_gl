package starter.init;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class StarterPropertiesTokenProvider implements StarterTokenProvder {
    private Properties properties;

    public StarterPropertiesTokenProvider(String propertiesPath) {
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
    public String getProxyAdress() {
        return properties.getProperty("proxyAdress");
    }

    @Override
    public Integer getProxyPort() {
        return Integer.parseInt(properties.getProperty("proxyPort"));
    }
}
