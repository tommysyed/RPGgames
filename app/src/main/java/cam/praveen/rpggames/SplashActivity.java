package cam.praveen.rpggames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences =getSharedPreferences(MainActivity.PREFS_NAME,0);
                boolean hasLogged =sharedPreferences.getBoolean("hasLoggedIn",false);
                if(hasLogged){
                    Intent intent =new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent =new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);
    }
}