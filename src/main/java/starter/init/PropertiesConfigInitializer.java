package starter.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfigInitializer implements ConfigInitializer {
    private Properties properties;

    public PropertiesConfigInitializer(String propertiesPath) {
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
    public boolean getProxyStatus() {
        return Boolean.parseBoolean(properties.getProperty("startWithProxy"));
    }

    @Override
    public String getProxyAddress() {
        return properties.getProperty("proxyAddress");
    }

    @Override
    public Integer getProxyPort() {
        return Integer.parseInt(properties.getProperty("proxyPort"));
    }
}
