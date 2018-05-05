package tgside;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import tgside.handlers.TGMsgHandler;
import vkside.VKMain;

import java.util.ArrayList;
import java.util.List;

public class LapmBot extends TelegramLongPollingBot {
    private String BOT_TOKEN;

    public LapmBot() {

    }

    public LapmBot(final String token, final DefaultBotOptions options) {
        super(options);
        BOT_TOKEN = token;
    }
    public LapmBot(final String token) {
        BOT_TOKEN = token;
    }

    @Override
    public String getBotUsername() {
        return "growlightbot";
        //возвращаем юзера
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) new TGMsgHandler(update.getMessage(), this).run();
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;

    }

}
