package jp.gedorinku.oblategles.graphics.shader;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.gedorinku.oblategles.graphics.utils.AssetUtil;

public final class ShaderProgramGenerator {

    public static ShaderProgram generateShaderProgram(String vertexShaderSourcePath, String fragmentShaderSourcePath,
                                                      AssetManager assets) throws IOException {
        String vertexShaderSource = AssetUtil.readTexFileAll(assets.open(vertexShaderSourcePath));
        String fragmentShaderSource = AssetUtil.readTexFileAll(assets.open(fragmentShaderSourcePath));

        ShaderProgram shaderProgram = new ShaderProgram(vertexShaderSource, fragmentShaderSource);

        return shaderProgram;
    }
}
