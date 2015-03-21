package jp.gedorinku.oblategles.graphics.shader;

import android.opengl.GLES20;

import jp.gedorinku.oblategles.graphics.GLESError;
import jp.gedorinku.oblategles.graphics.GLESException;
import jp.gedorinku.oblategles.graphics.utils.DebugHelper;

public class ShaderProgram {

    private int location;
    private int vertexShaderLocation;
    private String vertexShaderSource;
    private int fragmentShaderLocation;
    private String fragmentShaderSource;
    private boolean initialized;

    public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
        this.vertexShaderSource = vertexShaderSource;
        this.fragmentShaderSource = fragmentShaderSource;
        initialized = false;
    }

    public void initialize() throws GLESException {
        vertexShaderLocation = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        DebugHelper.checkGLError();
        GLES20.glShaderSource(vertexShaderLocation, vertexShaderSource);
        GLES20.glCompileShader(vertexShaderLocation);

        fragmentShaderLocation = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        DebugHelper.checkGLError();
        GLES20.glShaderSource(fragmentShaderLocation, fragmentShaderSource);
        GLES20.glCompileShader(fragmentShaderLocation);

        if (!getShaderCompileStatus(vertexShaderLocation) ||
                !getShaderCompileStatus(fragmentShaderLocation)) {
            String vertexLog = GLES20.glGetShaderInfoLog(vertexShaderLocation);
            String fragmentLog = GLES20.glGetShaderInfoLog(fragmentShaderLocation);
            throw new GLESException("compile failed : vertex shader log :" + vertexLog +
                    "fragment shader log :" + fragmentLog);
        }


        location = GLES20.glCreateProgram();
        DebugHelper.checkGLError();
        GLES20.glAttachShader(location, vertexShaderLocation);
        DebugHelper.checkGLError();
        GLES20.glAttachShader(location, fragmentShaderLocation);
        DebugHelper.checkGLError();
        GLES20.glLinkProgram(location);

        if (!getProgramLinkStatus(location)) {
            String log = GLES20.glGetProgramInfoLog(location);
            throw new GLESException("link failed : " + log);
        }

        use();

        initialized = true;
    }

    public void use() {
        GLES20.glUseProgram(location);
        DebugHelper.checkGLError();
    }

    public int getAttribLocation(String name) {
        int attribLocation = GLES20.glGetAttribLocation(location, name);
        if (attribLocation == -1) {
            throw new GLESError("could not get attribute location : " + name);
        }

        return attribLocation;
    }

    public int getUniformLocation(String name) {
        int uniformLocation = GLES20.glGetUniformLocation(location, name);
        if (uniformLocation == -1) {
            throw new GLESError("could not get uniform location : " + name);
        }

        return uniformLocation;
    }

    public int getLocation() {
        return location;
    }

    public int getVertexShaderLocation() {
        return vertexShaderLocation;
    }

    public int getFragmentShaderLocation() {
        return fragmentShaderLocation;
    }

    public boolean getIsInitialized() {
        return initialized;
    }

    public void destroy() {
        useNoProgram();
        GLES20.glDeleteProgram(location);
        DebugHelper.checkGLError();

        GLES20.glDeleteShader(vertexShaderLocation);
        DebugHelper.checkGLError();
        GLES20.glDeleteShader(fragmentShaderLocation);
        DebugHelper.checkGLError();
        initialized = false;
    }

    @Override
    protected void finalize() {
        if (initialized) {
            destroy();
        }
    }


    public static void useNoProgram() {
        GLES20.glUseProgram(0);
        DebugHelper.checkGLError();
    }

    private static boolean getShaderCompileStatus(int shaderLoacation) {
        int[] result = new int[1];
        GLES20.glGetShaderiv(shaderLoacation, GLES20.GL_COMPILE_STATUS, result, 0);
        return result[0] == GLES20.GL_TRUE;
    }

    private static boolean getProgramLinkStatus(int programLocation) {
        int[] result = new int[1];
        GLES20.glGetProgramiv(programLocation, GLES20.GL_LINK_STATUS, result, 0);
        return result[0] == GLES20.GL_TRUE;
    }
}
