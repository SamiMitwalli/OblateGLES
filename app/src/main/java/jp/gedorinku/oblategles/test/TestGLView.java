package jp.gedorinku.oblategles.test;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class TestGLView extends GLSurfaceView {

    private static final int OPENGL_ES_VERSION = 3;
    private TestMyRenderer testMyRenderer;

    public TestGLView(Context context) {
        super(context);

        testMyRenderer = new TestMyRenderer(context);
        setEGLContextClientVersion(OPENGL_ES_VERSION);
        setRenderer(testMyRenderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
        setOnTouchListener(testMyRenderer.onTouchListener);
    }

    public void destroy() {
        testMyRenderer.destroy();
    }
}
