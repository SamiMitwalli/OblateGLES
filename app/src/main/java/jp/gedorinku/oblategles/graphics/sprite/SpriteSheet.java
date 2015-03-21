package jp.gedorinku.oblategles.graphics.sprite;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SpriteSheet {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private Sprite[] sprites;
    private PositionConverter positionConverter;
    private Resources resources;
    private String jsonAssetPath;
    private int sheetResourceId;
    private int sheetTextureId;
    private int width;
    private int height;

    public SpriteSheet(Resources resources, int sheetResourceId, String jsonAssetPath) {
        this.sheetResourceId = sheetResourceId;
        this.resources = resources;
        this.jsonAssetPath = jsonAssetPath;
    }

    public void load() throws IOException {
        sheetTextureId = TextureUtil.loadTexture(resources, sheetResourceId);

        {
            JsonNode root = objectMapper.readTree(resources.getAssets().open(jsonAssetPath));

            {
                JsonNode size = root.get("meta").get("size");
                width = size.get("w").asInt();
                height = size.get("h").asInt();

                positionConverter = new PositionConverter(width, height);
            }

            List<Sprite> spriteList = new ArrayList<>();
            for (JsonNode node : root.get("frames")) {
                String name = node.get("name").asText();

                JsonNode frame = node.get("frame");
                int x = frame.get("x").asInt();
                int y = frame.get("y").asInt();
                int w = frame.get("w").asInt();
                int h = frame.get("h").asInt();

                spriteList.add(new Sprite(this, name, x, y, w, h));
            }

            sprites = new Sprite[0];
            sprites = spriteList.toArray(sprites);
        }
    }

    public PositionConverter getPositionConverter() {
        return positionConverter;
    }

    public Sprite getSprite(int index) {
        return sprites[index];
    }

    public Sprite getSprite(String name) {
        for (Sprite sprite : sprites) {
            if (sprite.getName().equals(name)) {
                return sprite;
            }
        }

        Log.e("OblateGLES", "sprite not found : " + name);
        return null;
    }

    public int getSheetTextureId() {
        return sheetTextureId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void destroy() {
        GLES20.glDeleteTextures(1, new int[]{sheetTextureId}, 0);
    }

    @Override
    protected void finalize() {
        destroy();
    }
}
