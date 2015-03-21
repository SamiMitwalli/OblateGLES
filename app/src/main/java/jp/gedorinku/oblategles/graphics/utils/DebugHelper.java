package jp.gedorinku.oblategles.graphics.utils;

import android.opengl.GLES20;

import jp.gedorinku.oblategles.BuildConfig;
import jp.gedorinku.oblategles.graphics.GLESError;

public final class DebugHelper {

    public static void checkGLError() {
        if (!BuildConfig.DEBUG) {
            return;
        }

        int errorCode = GLES20.glGetError();

        if (errorCode != GLES20.GL_NO_ERROR) {
            throw new GLESError(getGLErrorString(errorCode));
        }
    }

    public static String getGLErrorString(int errorCode) {
        switch (errorCode) {
            case GLES20.GL_NO_ERROR:
                return "no error";

            case GLES20.GL_INVALID_ENUM:
                return "invalid enum";

            case GLES20.GL_INVALID_VALUE:
                return "invalid value";

            case GLES20.GL_INVALID_OPERATION:
                return "invalid operation";

            case GLES20.GL_OUT_OF_MEMORY:
                return "out of memory";

            default:
                return "unknown error : illegal argument";
        }
    }
}
