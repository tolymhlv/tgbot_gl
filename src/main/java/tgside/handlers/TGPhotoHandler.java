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
import org.telegram.telegrambots.exceptions.TelegramApiException;
import starter.Starter;
import tgside.LapmBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TGPhotoHandler extends TGHandler{

    public TGPhotoHandler(Message msg, LapmBot bot) {
        super(msg, bot);
    }

    public void handlIt() {
        int sizeAlbum = msg.getPhoto().size();
        String postfix = "";
        for (int i = sizeAlbum - 1; i >= 0; i--) {
//            String mock = "/Users/mhlv/Documents/VLvYcIOpkuA.jpg";
            String fileId = msg.getPhoto().get(i).getFileId();
            String newPostfix = fileId.substring(fileId.length() - 5, fileId.length());
            if (postfix.equals(newPostfix)) continue;
            postfix = newPostfix;
            String pathId = "https://api.telegram.org/bot"+ bot.getBotToken() +"/getFile?file_id=" + fileId;
//            нужно отпрвить запрос pathId и доставть из  него path http apache
//            System.out.println(pathId);
            System.out.println(sendPost(pathId));
//            new VKAdd().addPhoto("", this);
        }
    }
    public void sendMessage(String text) {
        SendMessage sms = new SendMessage();
        sms.enableMarkdown(true);
        sms.setChatId(msg.getChatId().toString());
        sms.setText(text);
        try {
            setButtons(sms);
            bot.execute(sms);
        } catch (TelegramApiException e) {
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

        // Первая строчка
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("DELETE"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("SCORE"));


        // Добавляем клавиатуру в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public String sendPost(String url) {
        HttpClient client = HttpClientBuilder.create().setProxy(Starter.proxy).build();
        HttpPost post = new HttpPost(url);
        StringBuffer result = new StringBuffer();
        try {
            HttpResponse response = client.execute(post);
//            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();

        return result.toString();
    }
    public Message getMsg() {
        return msg;
    }

}