package rilma.example.com.sweetculinary.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rilma.example.com.sweetculinary.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final int SPLASH_TIME_OUT = 2500;

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent login = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(login);
                finish();
            }
        },SPLASH_TIME_OUT);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
