package tgside.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import tgside.TGMain;

public abstract class TGHandler extends TGMain {

    public void sendMessage(String text) {
        SendMessage sms = new SendMessage();
        sms.enableMarkdown(true);
        sms.setText(text);
        try {
            execute(sms);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public abstract void handlIt();

    public abstract void setButtons(SendMessage sendMessage);
}
