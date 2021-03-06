package tgside.init;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TGPropertiesTokenProvider implements TGTokenProvider {
    private Properties properties;

    public TGPropertiesTokenProvider(String propertiesPath) {
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
    public String getBotToken() {
        return properties.getProperty("tgBotToken");
    }
}
