package vkside.exceptions;

public class NoSuchPhotoException extends Throwable {
    String message;

    public NoSuchPhotoException(){};

    public NoSuchPhotoException(String message) {
        super(message);
    }
}
