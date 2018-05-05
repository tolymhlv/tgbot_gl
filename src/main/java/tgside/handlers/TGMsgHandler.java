package tgside.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import tgside.LapmBot;

public class TGMsgHandler extends TGHandler implements Runnable {

    public TGMsgHandler(Message msg, LapmBot bot) {
        super(msg, bot);
    }

    @Override
    public void run() {
        if (userNameCheker()) handlIt();
    }

    public void handlIt() {
        if (msg.hasPhoto()) {
            new TGPhotoAdd(msg, bot).handlIt();
        } else if (msg.hasText()){
            new TGTextHandler(msg, bot).handlIt();
        }
    }

    private boolean userNameCheker() {
        String userName =  msg.getChat().getUserName();
        if (userName == null) {
            sendMessage("go to the settings and set unique 'user name'");
            sendMessage("later click /start again");
            return false;
        }
        return true;
    }

    @Override
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
        keyboardFirstRow.add(new KeyboardButton("/start"));
        // Добавляем клавиатуру в список
        keyboard.add(keyboardFirstRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
