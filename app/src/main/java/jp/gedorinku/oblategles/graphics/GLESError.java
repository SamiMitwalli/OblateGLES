package jp.gedorinku.oblategles.graphics;

public class GLESError extends Error {

    public GLESError(String message) {
        super(message);
    }

    public GLESError(String message, Throwable cause) {
        super(message, cause);
    }
}
