package jp.gedorinku.oblategles.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class TestMainActivity extends Activity {

    private TestGLView testGlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        testGlView = new TestGLView(this);
        setContentView(testGlView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        testGlView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        testGlView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        testGlView.destroy();
    }
}
