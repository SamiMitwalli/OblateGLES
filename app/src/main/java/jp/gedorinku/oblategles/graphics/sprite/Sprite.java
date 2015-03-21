package jp.gedorinku.oblategles.graphics.sprite;

public class Sprite {

    private SpriteSheet baseSheet;
    private String name;
    private int posSheetX;
    private int posSheetY;
    private float posSheetXUV;
    private float posSheetYUV;
    private int width;
    private int height;
    private float widthUV;
    private float heightUV;
    private float[] uv;

    public Sprite(SpriteSheet baseSheet, String name, int posSheetX, int posSheetY,
                  int width, int height) {
        this.baseSheet = baseSheet;
        this.name = name;
        this.posSheetX = posSheetX;
        this.posSheetY = posSheetY;
        this.width = width;
        this.height = height;

        System.out.println("name: " + name + " x: " + posSheetX + " y: " + posSheetY + " w:" + width + " h: " + height);

        PositionConverter positionConverter = this.baseSheet.getPositionConverter();
        widthUV = positionConverter.toUVPositionX(this.width);
        heightUV = positionConverter.toUVPositionY(this.height);
        posSheetXUV = positionConverter.toUVPositionX(this.posSheetX);
        posSheetYUV = positionConverter.toUVPositionY(this.posSheetY);

        uv = new float[]{
                posSheetXUV, posSheetYUV + heightUV,
                posSheetXUV, posSheetYUV,
                posSheetXUV + widthUV, posSheetYUV + heightUV,
                posSheetXUV + widthUV, posSheetYUV
        };
    }

    public float[] getUV() {
        return uv;
    }

    public String getName() {
        return name;
    }

    public int getPosSheetX() {
        return posSheetX;
    }

    public int getPosSheetY() {
        return posSheetY;
    }

    public float getPosSheetXUV() {
        return posSheetXUV;
    }

    public float getPosSheetYUV() {
        return posSheetYUV;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getWidthUV() {
        return widthUV;
    }

    public float getHeightUV() {
        return heightUV;
    }
}
