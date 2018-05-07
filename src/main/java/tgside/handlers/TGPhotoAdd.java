package tgside.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import starter.Starter;
import tgside.LapmBot;
import tgside.ents.PhotoResponse;
import vkside.handlers.VKPhotoAdd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TGPhotoAdd extends TGHandler{

    public TGPhotoAdd(Message msg, LapmBot bot) {
        super(msg, bot);
    }

    public void handlIt() {
        int sizeAlbum = msg.getPhoto().size();
        String postfix = "";
        for (int i = sizeAlbum - 1; i >= 0; i--) {
//            String mock = "/Users/mhlv/Documents/VLvYcIOpkuA.jpg";
            String fileId = msg.getPhoto().get(i).getFileId();
            String newPostfix = fileId.substring(20, 25);
            if (postfix.equals(newPostfix)) continue;
            postfix = newPostfix;
            String pathId = "https://api.telegram.org/bot"+ bot.getBotToken() +"/getFile?file_id=" + fileId;
            String directLink = "https://api.telegram.org/file/bot"+ bot.getBotToken() + "/" + getPhotoPath(pathId);
            new VKPhotoAdd(this).addPhoto(directLink);
        }
    }

    @Override
    public void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("delete last"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("score"));


        // Добавляем клавиатуру в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private String getPhotoPath(String url) {
        HttpClient client = HttpClientBuilder.create().setProxy(Starter.getProxy()).build();
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
    public Message getMsg() {
        return msg;
    }

}