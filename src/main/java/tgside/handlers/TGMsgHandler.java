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
        handlIt();
    }

    public void handlIt() {
        if (msg.hasPhoto()) {
            new TGPhotoAdd(msg, bot).handlIt();
        } else if (msg.hasText()){ ;
            new TGTextHandler(msg, bot).handlIt();
        }
    }
}
