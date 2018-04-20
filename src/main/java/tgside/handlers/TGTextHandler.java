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
    public void sendMessage(String text) {
        SendMessage sms = new SendMessage();
        sms.enableMarkdown(true);
        sms.setChatId(msg.getChatId());
        sms.setText(text);
        try {
            setButtons(sms);
            bot.execute(sms);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
            default:
                sendMessage(msg.getChat().getUserName() + ", photos has been deleted.");
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

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        // Добавляем кнопки в строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("SCORE"));

        // Добавляем клавиатуру в список
        keyboard.add(keyboardFirstRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
