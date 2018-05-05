package tgside.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import tgside.LapmBot;
import vkside.handlers.VKGallery;
import vkside.exceptions.NoSuchAlbumException;

import java.util.*;

public class TGGallery extends TGHandler {
    private VKGallery vkGallery = new VKGallery();

    public TGGallery(Message msg, LapmBot bot) {
        super(msg, bot);
    }

    @Override
    public void handlIt() {
    }

    public void handlIt(String albumName) {
        sendMessage("follow the link " + albumName);
        String link = "invalid link";
        try {
            link = vkGallery.getAlbumLink(albumName);
            System.out.println(link);
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
            keyboardSecondRow.add(new KeyboardButton("@" + map.keySet().toArray()[i]));
        }
        //Трутья строчка
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("\uD83D\uDC9A back"));
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
