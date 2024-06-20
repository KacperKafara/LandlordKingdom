package pl.lodz.p.it.ssbd2024.exceptions;

public class ImageFormatNotSupported extends ApplicationBaseException {
    public ImageFormatNotSupported(String message, String code) {
        super(message, code);
    }

    public ImageFormatNotSupported(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}