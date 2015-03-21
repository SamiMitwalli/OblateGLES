package jp.gedorinku.oblategles.graphics.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import jp.gedorinku.oblategles.graphics.utils.DebugHelper;

public final class TextureUtil {

    public static int loadTexture(Resources resources, int resourceId) {
        Bitmap bp;
        int textures[] = new int[1];
        bp = BitmapFactory.decodeResource(resources, resourceId);
        GLES20.glGenTextures(1, textures, 0);
        DebugHelper.checkGLError();
        GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
        DebugHelper.checkGLError();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        DebugHelper.checkGLError();
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bp, 0);

        bp.recycle();
        return textures[0];
    }
}
