package starter;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import starter.init.PropertiesConfigInitializer;
import tgside.LapmBot;
import tgside.init.TGPropertiesTokenProvider;
import tgside.init.TGTokenProvider;
import utils.URLReader;
import vkside.VKMain;

import java.io.*;
import java.util.Properties;

public class Starter {
    private static boolean startWithProxy;
    private static HttpHost proxy;
    private static final VKMain vkMain = new VKMain();
    private static String propertiesPath = "/secret_keys.properties";

    public static void main(String[] args) {
        Starter starter = new Starter();
        starter.proxySwitcher();
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

//    public static void initConfig(String[] args) {
//        propertiesPath = "/secret_keys.properties";
//        if (propertiesPath == null) {
//            propertiesPath = "./secret_keys.properties";
//            String propertiesUrl = null;
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//                System.out.println("Input url to properties file: ");
////                propertiesUrl = br.readLine();
//                propertiesUrl = "https://www.dropbox.com/s/v81uwqdmcx0ic9r/secret_keys.properties?dl=1";
//                System.out.println();
//            } catch (IOException ignored) {
//            }
//            URLReader.copyURLToFile(propertiesUrl, new File(propertiesPath));
//        }
//    }

    public static boolean isStartWithProxy() {
        return startWithProxy;
    }

    private void proxySwitcher() {

//        startWithProxy = tokenProvider.getProxyStatus();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            startWithProxy = Boolean.parseBoolean(br.readLine());
        } catch (IOException e) {
            System.out.println("pr");
        }
        PropertiesConfigInitializer tokenProvider = new PropertiesConfigInitializer(propertiesPath);
        if (startWithProxy) {
            proxy = new HttpHost(tokenProvider.getProxyAddress(), tokenProvider.getProxyPort());
            System.out.println("PROXY ON");

        } else {
            System.out.println("PROXY OFF");
        }
    }

}