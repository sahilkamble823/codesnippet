package com.example.pointofsalef.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes2.dex */
public class PrefMng {
    private static final String PREF_DEV_ADDR = "PrefMng.PREF_DEVADDR";
    private static final String PREF_NAME = "org.kasabeh.androidprint";
    private static final String PREF_PRINTER = "PrefMng.PREF_PRINTER";
    public static final int PRN_BIXOLON_SELECTED = 2;
    public static final int PRN_OTHER_PRINTERS_SELECTED = 3;
    public static final int PRN_RONGTA_SELECTED = 4;
    public static final int PRN_WOOSIM_SELECTED = 1;

    public static int getActivePrinter(Context c) {
        SharedPreferences pref = c.getSharedPreferences(PREF_NAME, 0);
        return pref.getInt(PREF_PRINTER, 1);
    }

    public static void saveActivePrinter(Context context, int printerName) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_PRINTER, printerName);
        editor.commit();
    }

    public static void saveDeviceAddr(Context context, String newAddr) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_DEV_ADDR, newAddr);
        editor.commit();
    }

    public static String getDeviceAddr(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        return pref.getString(PREF_DEV_ADDR, "");
    }

    public static boolean getBoldPrinting(Context contx) {
        return false;
    }

    public static int getWoosimCodeTbl() {
        return 42;
    }
}
