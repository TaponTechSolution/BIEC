package com.tapontech.biec.src.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

import com.tapontech.biec.R;
import com.tapontech.biec.src.helpers.AppConfigMgr;

/**
 * Created by Sanjay on 03-02-2016.
 */
public class AppSplashScreen extends Activity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Load the application configuration data
        AppConfigMgr appConfigMgr = AppConfigMgr.getInstance();
        appConfigMgr.init(getApplicationContext(), "appConfig.json");

        /* New Handler to start the Menu-Activity
		 * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(AppSplashScreen.this, HomeGridView.class);
                AppSplashScreen.this.startActivity(mainIntent);
                AppSplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
