import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import tgside.TGMain;
import tgside.init.CommandLineArgsTokenProvider;
import tgside.init.TokenProvider;
import vkside.VKMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.tgInit(args);
        main.vkInit();
    }
    public void tgInit(String... args) {
        final TokenProvider tokenProvider = new CommandLineArgsTokenProvider(args);
        final RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(new HttpHost("78.57.227.227", 64569))
                .build();
        final DefaultBotOptions options = new DefaultBotOptions();
        options.setRequestConfig(requestConfig);

        ApiContextInitializer.init();
        final TelegramLongPollingBot bot = new TGMain(tokenProvider.getToken(), options);
        final TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        System.out.println("Bot '" + bot.getBotUsername() + "' registered");
    }

    public void vkInit() {
        new VKMain().start();
    }

    public void waiting(long ms) {
        try {
            this.wait(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}