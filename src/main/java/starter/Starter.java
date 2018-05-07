package starter;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import starter.init.StarterPropertiesTokenProvider;
import tgside.LapmBot;
import tgside.init.TGPropertiesTokenProvider;
import tgside.init.TGTokenProvider;
import utils.URLReader;
import vkside.VKMain;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Starter {
    private static boolean startWithProxy = true;
    private static HttpHost proxy = new HttpHost("89.236.17.106", 3128);
    private static final VKMain vkMain = new VKMain();
    private static String propertiesPath;

    public static void main(String[] args) {
        initConfig(args);
        proxyTurnOner();
        StarterPropertiesTokenProvider tokenProvider = new StarterPropertiesTokenProvider(propertiesPath);
        proxy = new HttpHost(tokenProvider.getProxyAdress(), tokenProvider.getProxyPort());
        Starter starter = new Starter();
        starter.tgInit(args);
        starter.vkInit();
    }

    public void tgInit(String[] args) {
        final TGTokenProvider TGTokenProvider = new TGPropertiesTokenProvider(propertiesPath);
        RequestConfig requestConfig = null;
        if (isStartWithProxy()) {
            requestConfig = RequestConfig.custom()
                    .setProxy(new HttpHost(proxy))
                    .build();
        } else {
            requestConfig = RequestConfig.custom().build();
        }
        final DefaultBotOptions options = new DefaultBotOptions();
        options.setRequestConfig(requestConfig);

        ApiContextInitializer.init();
        final TelegramLongPollingBot bot = new LapmBot(TGTokenProvider.getBotToken(), options);
        final TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        System.out.println("TG getBotToken '" + bot.getBotUsername() + "' registered");
    }

    public void vkInit() {
        vkMain.start();
    }

    public static VKMain getVkMain() {
        return vkMain;
    }

    public static HttpHost getProxy() {
        return proxy;
    }

    public static String getPropertiesPath() {
        return propertiesPath;
    }

    public static void initConfig(String[] args) {
        if (propertiesPath == null) {
            propertiesPath = "./src/main/resources/secret_keys.properties";
            String propertiesUrl = null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Input url to properties file: ");
//                propertiesUrl = br.readLine();
                propertiesUrl = "https://www.dropbox.com/s/v81uwqdmcx0ic9r/secret_keys.properties?dl=1";
                System.out.println();
            } catch (IOException ignored) {
            }
            URLReader.copyURLToFile(propertiesUrl, new File(propertiesPath));
        }
    }

    public static boolean isStartWithProxy() {
        return startWithProxy;
    }

    private static void proxyTurnOner() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            properties.load(fis);
            startWithProxy = Boolean.parseBoolean(properties.getProperty("startWithProxy"));
        } catch (FileNotFoundException e) {
            System.err.println("Error with propertieies files of secret keys");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}