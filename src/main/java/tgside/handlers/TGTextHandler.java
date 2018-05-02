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

public class TGTextHandler extends TGHandler {
    public TGTextHandler(Message msg, LapmBot bot) {
        super(msg, bot);
    }


    @Override
    public void handlIt() {
        String text = msg.getText();
        switch (text) {
            case "/start":
                sendMessage("Just drop a new photo of a growlamp");
                break;
            case "SCORE":
                sendMessage("score-table generator is not ready yet :(");
                break;
            case "DELETE":
                new TGPhotoDel(msg, bot).handlIt();
                break;
            case "\uD83D\uDD34 delete":
                if (new TGPhotoDel(msg, bot).deleteLastPhoto())
                    sendMessage("Photo has been deleted");
                else
                    sendMessage("Delete error %(");
                break;
            default:
                sendMessage("Just drop a new photo of a growlamp");
        }
    }
}
