package com.example.pointofsalef.utils;

import android.content.Context;

/* loaded from: classes2.dex */
public class printerFactory {
    public static WCharMapperCT42 getActiveCharMapper(Context context) {
        if (PrefMng.getActivePrinter(context) == 1) {
            return new WCharMapperCT42();
        }
        if (PrefMng.getActivePrinter(context) == 4) {
            return new WCharMapperRongtaDef();
        }
        return new WCharMapperBixolonDef();
    }

    public static WoosimPrnMng createPrnMng(Context c, String deviceAddr, IPrintToPrinter prnToWoosim) {
        if (PrefMng.getActivePrinter(c) == 1) {
            return new WoosimPrnMng(c, deviceAddr, prnToWoosim);
        }
        if (PrefMng.getActivePrinter(c) == 4) {
            return new RongtaPrnMng(c, deviceAddr, prnToWoosim);
        }
        return new BixolonPrnMng(c, deviceAddr, prnToWoosim);
    }

    public static printerWordMng createPaperMng(Context c) {
        if (PrefMng.getActivePrinter(c) == 1) {
            return new printerWordMng(24);
        }
        return new printerWordMng(30);
    }
}
