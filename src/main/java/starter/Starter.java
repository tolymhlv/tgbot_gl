package starter;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import starter.init.StarterPropertiesTokenProvider;
import starter.init.StarterTokenProvder;
import tgside.LapmBot;
import tgside.init.TGCommandLineArgsTokenProvider;
import tgside.init.TGPropertiesTokenProvider;
import tgside.init.TGTokenProvider;
import vkside.VKMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Starter {
    private static HttpHost proxy;
    private static final VKMain vkMain = new VKMain();
    private static String propertiesPath;


    public static void main(String[] args) {
        propertiesPath = getPropertiesPath();
        StarterPropertiesTokenProvider tokenProvider = new StarterPropertiesTokenProvider(propertiesPath);
        proxy = new HttpHost(tokenProvider.getProxyAdress(), tokenProvider.getProxyPort());
        Starter starter = new Starter();
        starter.tgInit(args);
        starter.vkInit();
    }
    public void tgInit(String[] args) {
        final TGTokenProvider TGTokenProvider = new TGPropertiesTokenProvider(propertiesPath);
        final RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(new HttpHost(proxy))
                .build();
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
        propertiesPath = "/Users/mhlv/IdeaProjects/bot2/src/main/resourses/secret_keys.properties";
//        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//            System.out.println("Input path to properties file: ");
//            propertiesPath = br.readLine();
//        } catch (IOException ignored) {
//            ignored.printStackTrace();
//        }
        return propertiesPath;
    }
}