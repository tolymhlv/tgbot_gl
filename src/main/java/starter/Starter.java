package starter;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import tgside.LapmBot;
import tgside.init.TGCommandLineArgsTokenProvider;
import tgside.init.TGTokenProvider;
import vkside.VKMain;

public class Starter {
    public static final HttpHost proxy = new HttpHost("192.116.142.153", 8080);
    public static final VKMain vkMain = new VKMain();

    public static void main(String[] args) {
        Starter starter = new Starter();
        starter.tgInit(args);
        starter.vkInit();
    }
    public void tgInit(String[] args) {
        final TGTokenProvider TGTokenProvider = new TGCommandLineArgsTokenProvider(args);
        final RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(new HttpHost(proxy))
                .build();
        final DefaultBotOptions options = new DefaultBotOptions();
        options.setRequestConfig(requestConfig);

        ApiContextInitializer.init();
        final TelegramLongPollingBot bot = new LapmBot(TGTokenProvider.getToken(), options);
        final TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        System.out.println("TG Bot '" + bot.getBotUsername() + "' registered");
    }

    public void vkInit() {
        vkMain.start();
    }

    public void waiting(long ms) {
        try {
            this.wait(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}