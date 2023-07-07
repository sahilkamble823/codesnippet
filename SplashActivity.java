package com.example.pointofsalef;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pointofsalef.HomeActivity;
import com.example.pointofsalef.R;

/* loaded from: classes2.dex */
public class SplashActivity extends AppCompatActivity {
    public static int splashTimeOut = 2000;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        new Handler().postDelayed(new Runnable() { // from class: com.app.smartpos.SplashActivity.1
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, splashTimeOut);
    }
}
