package tgside.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.PhotoListIsEmptyException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import starter.Starter;
import tgside.LapmBot;
import tgside.handlers.ents.PhotoResponse;
import vkside.VKPhotoDel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TGPhotoDel extends TGHandler{

    public TGPhotoDel(Message msg, LapmBot bot) {
        super(msg, bot);
    }

    public void handlIt() {
        sendLastPhoto();

    }
    public void sendLastPhoto() {
        File lastPhoto = new VKPhotoDel(this).getLastPhoto();
        SendPhoto sp = new SendPhoto();
        sp.setChatId(msg.getChatId().toString());
        sp.setNewPhoto(lastPhoto);
        try {
            bot.sendPhoto(sp);
            sendMessage("This photo will be delete. Are you sure?");
        } catch (TelegramApiException e) {
            sendMessage("Delete error :(. Maybe there is no photo in album.");
        }
    }

    public boolean deleteLastPhoto() throws PhotoListIsEmptyException{
        return new VKPhotoDel(this).deleteLastPhoto();

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

        // Первая строчка
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("\uD83D\uDD34 delete"));
        keyboardFirstRow.add(new KeyboardButton("\uD83D\uDC9A back"));

        // Добавляем клавиатуру в список
        keyboard.add(keyboardFirstRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private String getPhotoPath(String url) {
        HttpClient client = HttpClientBuilder.create().setProxy(Starter.proxy).build();
        HttpPost post = new HttpPost(url);
        StringBuffer result = new StringBuffer();
        try {
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        PhotoResponse pr = gson.fromJson(result.toString(), PhotoResponse.class);
        return pr.getResult().getFilePath();
    }
}