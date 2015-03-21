package jp.gedorinku.oblategles.test;

import android.opengl.GLES20;
import android.view.MotionEvent;

import java.nio.FloatBuffer;

import jp.gedorinku.oblategles.graphics.sprite.PositionConverter;
import jp.gedorinku.oblategles.graphics.sprite.SpriteSheet;
import jp.gedorinku.oblategles.graphics.utils.BufferConverter;

public class TestEntityFireEffect {

    private final int deathTime = 40;

    private SpriteSheet spriteSheet;
    private PositionConverter positionConverter;
    private int posX;
    private int posY;
    private int shaderPosLocation;
    private int shaderUVLocation;
    private int age;
    private boolean isHolding;
    private boolean isDeath;

    public TestEntityFireEffect(int posX, int posY, SpriteSheet spriteSheet,
                                int shaderPosLocation, int shaderUVLocation,
                                PositionConverter positionConverter) {
        this.posX = posX;
        this.posY = posY - 200;
        this.spriteSheet = spriteSheet;
        this.positionConverter = positionConverter;
        this.shaderPosLocation = shaderPosLocation;
        this.shaderUVLocation = shaderUVLocation;

        isHolding = true;
    }

    public void update(int lastTouchAction, int touchPosX, int touchPosY) {
        age++;

        if (isHolding &&
                (lastTouchAction != MotionEvent.ACTION_DOWN) &&
                (lastTouchAction != MotionEvent.ACTION_MOVE)) {
            isHolding = false;
        }

        if (isHolding) {
            if (28 < age) {
                age = 12;
            }

            posX = touchPosX;
            posY = touchPosY - 200;
        } else if (deathTime <= age) {
            isDeath = true;
            return;
        }
    }

    public void draw() {
        if (isDeath) {
            return;
        }

        GLES20.glUniform2f(shaderPosLocation, positionConverter.toWindowPositionX(posX),
                positionConverter.toWindowPositionY(posY));
        {
            FloatBuffer floatBuffer = BufferConverter.convertToFloatBuffer(spriteSheet.getSprite(age / 4).getUV());
            GLES20.glVertexAttribPointer(shaderUVLocation, 2, GLES20.GL_FLOAT, false, 0, floatBuffer);
        }
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isDeath() {
        return isDeath;
    }
}
