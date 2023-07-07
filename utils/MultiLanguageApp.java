package com.example.pointofsalef.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/* loaded from: classes.dex */
public class MultiLanguageApp extends Application {
    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}
