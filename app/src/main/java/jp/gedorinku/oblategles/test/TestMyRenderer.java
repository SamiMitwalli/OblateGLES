package jp.gedorinku.oblategles.test;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jp.gedorinku.oblategles.R;
import jp.gedorinku.oblategles.graphics.GLESException;
import jp.gedorinku.oblategles.graphics.shader.ShaderProgram;
import jp.gedorinku.oblategles.graphics.shader.ShaderProgramGenerator;
import jp.gedorinku.oblategles.graphics.sprite.SpriteSheet;
import jp.gedorinku.oblategles.graphics.utils.BufferConverter;
import jp.gedorinku.oblategles.graphics.utils.DebugHelper;
import jp.gedorinku.oblategles.graphics.sprite.PositionConverter;

public class TestMyRenderer implements GLSurfaceView.Renderer {

    public View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ((lastTouchAction = event.getAction()) != MotionEvent.ACTION_DOWN) {
                touchPosX = (int) event.getX();
                touchPosY = (int) event.getY();
                return true;
            }
            effects.add(new TestEntityFireEffect((int) event.getX(), (int) event.getY(),
                    spriteSheet, movePosLocation, attrUvLocation, positionConverter));
            return true;
        }
    };

    private static final float[] postion = {
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, 0.5f,
            0.5f, -0.5f
    };

    private static final float[] uv = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
    };

    private static FloatBuffer postionBuffer;
    private static FloatBuffer uvBuffer;

    private ShaderProgram program;
    private Context context;
    private PositionConverter positionConverter;
    private SpriteSheet spriteSheet;
    private int viewWidth;
    private int viewHeigh;
    private int attrPosLocation;
    private int attrUvLocation;
    private int movePosLocation;
    private int textureLocation;
    private float[] movePosBuffer = new float[2];
    private float[] viewAndProjectionMatrix = new float[16];
    private long frameCount;
    private int animeFrame;
    private int lastTouchAction;
    private int touchPosX;
    private int touchPosY;

    private List<TestEntityFireEffect> effects;

    public TestMyRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //GLES20.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        DebugHelper.checkGLError();

        try {
            program = ShaderProgramGenerator.generateShaderProgram("shaders/vertex.txt", "shaders/fragment.txt", context.getAssets());
            program.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GLESException e) {
            e.printStackTrace();
        }
        program.use();

        attrPosLocation = program.getAttribLocation("attr_pos");
        attrUvLocation = program.getAttribLocation("attr_uv");
        movePosLocation = program.getUniformLocation("movePos");
        textureLocation = program.getUniformLocation("texture");
        GLES20.glUniform1i(textureLocation, 0);

        spriteSheet = new SpriteSheet(context.getResources(), R.drawable.fire, "fire.json");
        try {
            spriteSheet.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        postionBuffer = BufferConverter.convertToFloatBuffer(postion);
        uvBuffer = BufferConverter.convertToFloatBuffer(spriteSheet.getSprite(4).getUV());

        effects = new ArrayList<>();

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        //GLES20.glUniform1i(textureLocation, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        DebugHelper.checkGLError();
        viewWidth = width;
        viewHeigh = height;
        if (positionConverter == null) {
            positionConverter = new PositionConverter(width, height);
        } else {
            positionConverter.setWidth(width);
            positionConverter.setHeight(height);
        }

        GLES20.glVertexAttribPointer(attrPosLocation, 2, GLES20.GL_FLOAT, false, 0, postionBuffer);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        if ((frameCount % 4) == 0) {
            if (9 < ++animeFrame) {
                animeFrame = 0;
            }
            uvBuffer = BufferConverter.convertToFloatBuffer(spriteSheet.getSprite(animeFrame).getUV());
        }

        GLES20.glEnableVertexAttribArray(attrPosLocation);
        GLES20.glEnableVertexAttribArray(attrUvLocation);

        //GLES20.glUniform1i(textureLocation, 0);

        for (int i = 0; i < effects.size(); ++i) {
            TestEntityFireEffect effect;

            if ((effect = effects.get(i)).isDeath()) {
                effects.remove(i);
                continue;
            }

            //GLES20.glVertexAttribPointer(attrPosLocation, 2, GLES20.GL_FLOAT, false, 0, postionBuffer);
            effect.update(lastTouchAction, touchPosX, touchPosY);
            effect.draw();
        }

        DebugHelper.checkGLError();
        frameCount++;
    }

    public void destroy() {
        program.destroy();
    }
}

