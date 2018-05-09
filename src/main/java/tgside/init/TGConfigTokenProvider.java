package tgside.init;

import starter.Starter;

public class TGConfigTokenProvider implements TGTokenProvider{
    @Override
    public String getBotToken() {
        return Starter.getConfig().getTgBotToken();
    }
}
