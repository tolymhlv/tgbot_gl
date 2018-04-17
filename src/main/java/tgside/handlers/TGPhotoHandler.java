package tgside.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.ResponseParameters;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import vkside.VKAdd;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TGPhotoHandler extends TGHandler{
    private Message msg;

    public TGPhotoHandler(Message msg) {
        this.msg = msg;
    }

    public void handlIt() {
        int sizeAlbum = msg.getPhoto().size();
        for (int i = 2; i < sizeAlbum; i++) {
//            String mock = "/Users/mhlv/Documents/VLvYcIOpkuA.jpg";
            String fileId = msg.getPhoto().get(i).getFileId();
            String pathId = "https://api.telegram.org/bot"+ getBotToken() +"/getFile?file_id=" + fileId;
//            нужно отпрвить запрос pathId и доставть из  него path http apache
//            System.out.println(pathId);
            System.out.println(sendPost(pathId));
//            new VKAdd().addPhoto("", this);

        }
    }
    @Override
    public void sendMessage(String text) {
        SendMessage sms = new SendMessage();
        sms.enableMarkdown(true);
        sms.setChatId(msg.getChatId().toString());
        sms.setText(text);
        try {
            setButtons(sms);
            execute(sms);
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
//        String url = "https://selfsolve.apple.com/wcResults.do";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        StringBuffer result = new StringBuffer();
        // add header
//        post.setHeader("User-Agent", USER_AGENT);
        try {
//            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//            urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
//            urlParameters.add(new BasicNameValuePair("cn", ""));
//            urlParameters.add(new BasicNameValuePair("locale", ""));
//            urlParameters.add(new BasicNameValuePair("caller", ""));
//            urlParameters.add(new BasicNameValuePair("num", "12345"));
//
//            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = client.execute(post);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

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