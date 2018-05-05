package tgside.handlers;

import org.telegram.telegrambots.api.objects.Message;
import tgside.LapmBot;
import vkside.exceptions.NoSuchPhotoException;

public class TGTextHandler extends TGHandler {
    public TGTextHandler(Message msg, LapmBot bot) {
        super(msg, bot);
    }


    @Override
    public void handlIt() {
        String text = msg.getText();
        if (text.equals("/start")) {
            sendMessage("just drop a new photo of a growlamp");
        } else if (text.equals("score")) {
            new TGScore(msg, bot).handlIt();
        } else if (text.equals("delete last")) {
            new TGPhotoDel(msg, bot).handlIt();

        } else if (text.equals("\uD83D\uDD34 delete")) {
            try {
                boolean wasDeleted = new TGPhotoDel(msg, bot).deleteLastPhoto();
                if (wasDeleted) sendMessage("Photo has been deleted");
            } catch (NoSuchPhotoException e) {
                sendMessage("delete error %(. maybe album is empty");
                e.printStackTrace();
            }
        } else if (text.equals("gallery")) {
            new TGGallery(msg, bot).show();
        } else if (text.matches("^@.*")) {
            new TGGallery(msg, bot).handlIt(text.substring(1));
        } else {
            sendMessage("just drop a new photo");
        }
    }
}
