package exceptions;

import java.nio.file.NoSuchFileException;

public class PhotoListIsEmptyException extends NoSuchFileException {

    public PhotoListIsEmptyException(String file) {
        super(file);
    }

    public PhotoListIsEmptyException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
