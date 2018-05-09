package starter;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import starter.init.Config;
import starter.init.HerokuConfigInitializer;
import tgside.LapmBot;
import tgside.init.TGConfigTokenProvider;
import tgside.init.TGPropertiesTokenProvider;
import tgside.init.TGTokenProvider;
import utils.URLReader;
import vkside.VKMain;

import java.io.*;
import java.util.Properties;

public class Starter {
    private static final VKMain vkMain= new VKMain();
    private static Config config;
    private static Boolean startWithProxy;
    private static HttpHost proxy;


    public static void main(String[] args) {
        config = new HerokuConfigInitializer().getConfig(args[0]);
        startWithProxy = config.isStartWithProxy();
//        startWithProxy = true;
        if (startWithProxy) proxy = getProxy();
        Starter starter = new Starter();
        starter.tgInit();
        starter.vkInit();
    }

    public void tgInit() {
        final TGTokenProvider tokenProvider = new TGConfigTokenProvider();
        RequestConfig requestConfig = null;
        if (isStartWithProxy()) {
            requestConfig = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
        } else {
            requestConfig = RequestConfig.custom().build();
        }
        final DefaultBotOptions options = new DefaultBotOptions();
        options.setRequestConfig(requestConfig);

        ApiContextInitializer.init();
        final TelegramLongPollingBot bot = new LapmBot(tokenProvider.getBotToken(), options);
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

    public static boolean isStartWithProxy() {
        return startWithProxy;
    }

    public static VKMain getVkMain() {
        return vkMain;
    }

    public static HttpHost getProxy() {
        if (proxy == null && startWithProxy) {
            proxy = new HttpHost(config.getProxyAddress(), config.getProxyPort());
        }
        return proxy;
    }

    public static Config getConfig() {
        return config;
    }
}