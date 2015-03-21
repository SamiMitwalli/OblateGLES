package jp.gedorinku.oblategles.graphics.sprite;

public final class PositionConverter {

    private int width;
    private int height;

    public PositionConverter(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public float[] toWindowPosition(float[] spritePosition) {
        float[] windowPosition = new float[]{
                toWindowPositionX(spritePosition[0]),
                toWindowPositionY(spritePosition[1])
        };

        return windowPosition;
    }

    public float[] toWindowPosition(float spriteX, float sptiteY) {
        return toWindowPosition(new float[]{spriteX, sptiteY});
    }

    public float toWindowPositionX(float spriteX) {
        return spriteX / width * 2.0f - 1.0f;
    }

    public float toWindowPositionY(float spriteY) {
        return (height - spriteY) / height * 2.0f - 1.0f;
    }


    public float[] toSpritePosition(float[] windowPosition) {
        float[] spritePosition = new float[]{
                toSpritePositionX(windowPosition[0]),
                toSpritePositionY(windowPosition[1])
        };

        return spritePosition;
    }

    public float[] toSpritePosition(float windowX, float windowY) {
        return toSpritePosition(new float[]{windowX, windowY});
    }

    public float toSpritePositionX(float windowX) {
        return (float) width / 2 * (windowX + 1);
    }

    public float toSpritePositionY(float windowY) {
        return (height - (height * windowY)) / 2;
    }


    public float[] toUVPosition(float[] spritePosition) {
        float[] uvPosition = new float[]{
                toUVPositionX(spritePosition[0]),
                toUVPositionY(spritePosition[1])
        };

        return uvPosition;
    }

    public float[] toUVPposition(float spriteX, float spriteY) {
        return toUVPosition(new float[]{spriteX, spriteY});
    }

    public float toUVPositionX(float spriteX) {
        return spriteX / width;
    }

    public float toUVPositionY(float spriteY) {
        return spriteY / height;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
