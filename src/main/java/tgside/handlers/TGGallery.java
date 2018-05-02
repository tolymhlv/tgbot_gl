package tgside.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import tgside.LapmBot;
import vkside.VKGallery;
import vkside.exceptions.NoSuchAlbumException;

import java.util.*;

public class TGGallery extends TGHandler {
    private VKGallery vkGallery;

    public TGGallery(Message msg, LapmBot bot) {
        super(msg, bot);
        vkGallery = new VKGallery(this);
    }

    @Override
    public void handlIt() {

    }

    public void handlIt(String albumName) {
        sendMessage("follow the link");
        String link = "invalid link";
        try {
            link = vkGallery.getAlbumLink(albumName);
            sendMessage(link);
        } catch (NoSuchAlbumException e) {
            sendMessage("there is not album of user '" + albumName + "' %(");
            e.printStackTrace();
        }
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
        keyboardFirstRow.add(new KeyboardButton("delete last"));
        //Вторая строчка
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        HashMap map = new HashMap(vkGallery.getAlbumsMap());
        for (int i = 0; i < map.keySet().size(); i++ ) {
            keyboardSecondRow.add(new KeyboardButton("@" + map.get(i)));
        }
        //Трутья строчка
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("\uD83D\uDC9A back"));
        // Добавляем клавиатуру в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void show() {
        sendMessage("choose player");
    }
}
