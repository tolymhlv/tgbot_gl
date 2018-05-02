package tgside.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import tgside.LapmBot;

public abstract class TGHandler {
    protected Message msg;
    protected LapmBot bot;

    public TGHandler(Message msg, LapmBot bot) {
        this.bot = bot;
        this.msg = msg;
    }

    public void sendMessage(String text) {
        SendMessage sms = new SendMessage();
        sms.enableMarkdown(true);
        sms.setText(text);
        try {
            bot.execute(sms);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public abstract void handlIt();

    public abstract void setButtons(SendMessage sendMessage);

    public Message getMsg() {
        return msg;
    }
}
