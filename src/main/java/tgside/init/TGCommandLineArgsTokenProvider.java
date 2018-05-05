package tgside.init;

public class TGCommandLineArgsTokenProvider implements TGTokenProvider {
    private final String token;
    /**
     * @param args command line args. First args must contain bot token.
     */
    public TGCommandLineArgsTokenProvider(final String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Args must contain at least one value");
        }
        this.token = args[0];
    }

    public String getBotToken() {
        return token;
    }
}
