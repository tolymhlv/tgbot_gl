package tgside;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Display {
    private LapmBot bot;

    public Display(LapmBot bot) {
        this.bot = bot;
    }

    public void sendMessage(String text) {
    }
}
