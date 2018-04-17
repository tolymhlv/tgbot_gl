package tgside.init;

public class CommandLineArgsTokenProvider implements TokenProvider {
    private final String token;
    /**
     * @param args command line args. First args must contain bot token.
     */
    public CommandLineArgsTokenProvider(final String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Args must contain at least one value");
        }
        this.token = args[0];
    }

    public String getToken() {
        return token;
    }
}
