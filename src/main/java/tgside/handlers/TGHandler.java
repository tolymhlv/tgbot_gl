package tgside.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import tgside.LapmBot;

import java.util.ArrayList;
import java.util.List;

public abstract class TGHandler {
    protected Message msg;
    protected LapmBot bot;

    public TGHandler(Message msg, LapmBot bot) {
        this.bot = bot;
        this.msg = msg;
    }

    public void sendMessage(String text) {
        SendMessage sms = new SendMessage();
//        sms.enableMarkdown(true);
        sms.setChatId(msg.getChatId());
        sms.setText(text);
        try {
            setButtons(sms);
            bot.execute(sms);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public abstract void handlIt();

    public void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая сторчка
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("score"));
        //Вторая строчка
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("gallery"));
        // Добавляем клавиатуру в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public Message getMsg() {
        return msg;
    }
}
