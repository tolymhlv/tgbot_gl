package tgside.handlers;

import exceptions.PhotoListIsEmptyException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import tgside.LapmBot;

import java.util.ArrayList;
import java.util.List;

public class TGTextHandler extends TGHandler {
    public TGTextHandler(Message msg, LapmBot bot) {
        super(msg, bot);
    }


    @Override
    public void handlIt() {
        String text = msg.getText();
        if (text.equals("/start")) {
            sendMessage("Just drop a new photo of a growlamp");

        } else if (text.equals("score")) {
            sendMessage("score-table generator is not ready yet :(");

        } else if (text.equals("delete last")) {
            new TGPhotoDel(msg, bot).handlIt();

        } else if (text.equals("\uD83D\uDD34 delete")) {
            boolean wasDelet = false;
            try {
                wasDelet = new TGPhotoDel(msg, bot).deleteLastPhoto();
                sendMessage("Photo has been deleted");
            } catch (PhotoListIsEmptyException e) {
                sendMessage("delete error %(. photoalbum is empty");
                e.printStackTrace();
            }

        } else if (text.equals("gallery")) {
            new TGGallery(msg, bot).show();
        } else if (text.matches("^@.*")) {
            new TGGallery(msg, bot).handlIt(text.substring(1));
        } else {
            sendMessage("Just drop a new photo of a growlamp");
        }
    }
}
