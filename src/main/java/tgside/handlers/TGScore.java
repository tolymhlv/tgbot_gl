package tgside.handlers;

import org.telegram.telegrambots.api.objects.Message;
import tgside.LapmBot;
import vkside.handlers.VKGallery;

import java.util.HashMap;
import java.util.Map;

public class TGScore extends TGHandler {

    public TGScore(Message msg, LapmBot bot) {
        super(msg, bot);
    }

    @Override
    public void handlIt() {
        Map<String, Integer> albums = new HashMap<>(new VKGallery().getAlbumsMap());
        StringBuffer sb = new StringBuffer();
        sb.append( bot.getBotUsername() + " score:\n");
        albums.forEach((x, y) -> sb.append(x + ": " + y + " pic\n"));
        sendMessage(sb.toString());
    }
}
