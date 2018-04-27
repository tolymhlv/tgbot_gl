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
    private final Display display = new Display(this);

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
        // Форк на тип запроса
        if(update.hasMessage()) new TGMsgHandler(update.getMessage(), this).run();
//        else new QueryThread().answerCallbackQuery(update.getCallbackQuery());
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;

    }

    public Display getDisplay() {
        return display;
    }
    //    public class QueryThread extends Thread {
//        public synchronized void answerCallbackQuery(CallbackQuery cq) {
//            AnswerCallbackQuery answer = new AnswerCallbackQuery();
//            answer.setCallbackQueryId(cq.getId());
//            answer.setText(cq.getMessage().toString());
//            answer.setShowAlert(true);
//            try {
//                setInline();
//                execute(answer);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//        private void setInline() {
//            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//            List<InlineKeyboardButton> buttons1 = new ArrayList<>();
//            buttons1.add(new InlineKeyboardButton().setText("FRM KAT").setCallbackData("11"));
//            buttons1.add(new InlineKeyboardButton().setText("FRM TOL").setCallbackData("12"));
//            List<InlineKeyboardButton> buttons2 = new ArrayList<>();
//            buttons2.add(new InlineKeyboardButton().setText("FRM KAT").setCallbackData("21"));
//            buttons2.add(new InlineKeyboardButton().setText("FRM TOL").setCallbackData("22"));
//
//            buttons.add(buttons1);
//
//            InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
//            markupKeyboard.setKeyboard(buttons);
//        }
//    }



}
