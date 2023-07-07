package com.example.pointofsalef.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.app.smartpos.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes2.dex */
public class Utils {
    public static final byte[] UNICODE_TEXT = {35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35};
    private static String hexStr = "0123456789ABCDEF";
    private static String[] binaryArray = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", NativeContentAd.ASSET_HEADLINE, "1010", "1011", "1100", "1101", "1110", "1111"};

    public void interstitialAdsShow(Context context) {
        final InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.admob_interstitial_ads_id));
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);
        interstitialAd.setAdListener(new AdListener() { // from class: com.app.smartpos.utils.Utils.1
            @Override // com.google.android.gms.ads.AdListener
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });
    }

    public static byte[] decodeBitmap(Bitmap bmp) {
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        List<String> list = new ArrayList<>();
        int i = bmpWidth / 8;
        int zeroCount = bmpWidth % 8;
        String zeroStr = "";
        if (zeroCount > 0) {
            int bitLen = (bmpWidth / 8) + 1;
            for (int i2 = 0; i2 < 8 - zeroCount; i2++) {
                zeroStr = zeroStr + "0";
            }
        }
        for (int i3 = 0; i3 < bmpHeight; i3++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < bmpWidth; j++) {
                int color = bmp.getPixel(j, i3);
                int r = (color >> 16) & 255;
                int g = (color >> 8) & 255;
                int b = color & 255;
                if (r > 160 && g > 160 && b > 160) {
                    sb.append("0");
                } else {
                    sb.append("1");
                }
            }
            if (zeroCount > 0) {
                sb.append(zeroStr);
            }
            list.add(sb.toString());
        }
        Collection<? extends String> bmpHexList = binaryListToHexStringList(list);
        String widthHexString = Integer.toHexString(bmpWidth % 8 == 0 ? bmpWidth / 8 : (bmpWidth / 8) + 1);
        if (widthHexString.length() > 2) {
            Log.e("decodeBitmap error", " width is too large");
            return null;
        }
        if (widthHexString.length() == 1) {
            widthHexString = "0" + widthHexString;
        }
        String widthHexString2 = widthHexString + "00";
        String heightHexString = Integer.toHexString(bmpHeight);
        if (heightHexString.length() > 2) {
            Log.e("decodeBitmap error", " height is too large");
            return null;
        }
        if (heightHexString.length() == 1) {
            heightHexString = "0" + heightHexString;
        }
        String heightHexString2 = heightHexString + "00";
        List<String> commandList = new ArrayList<>();
        commandList.add("1D763000" + widthHexString2 + heightHexString2);
        commandList.addAll(bmpHexList);
        return hexList2Byte(commandList);
    }

    public static List<String> binaryListToHexStringList(List<String> list) {
        List<String> hexList = new ArrayList<>();
        for (String binaryStr : list) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < binaryStr.length(); i += 8) {
                String str = binaryStr.substring(i, i + 8);
                String hexString = myBinaryStrToHexString(str);
                sb.append(hexString);
            }
            hexList.add(sb.toString());
        }
        return hexList;
    }

    public static String myBinaryStrToHexString(String binaryStr) {
        String hex = "";
        String f4 = binaryStr.substring(0, 4);
        String b4 = binaryStr.substring(4, 8);
        int i = 0;
        while (true) {
            String[] strArr = binaryArray;
            if (i >= strArr.length) {
                break;
            }
            if (f4.equals(strArr[i])) {
                hex = hex + hexStr.substring(i, i + 1);
            }
            i++;
        }
        int i2 = 0;
        while (true) {
            String[] strArr2 = binaryArray;
            if (i2 < strArr2.length) {
                if (b4.equals(strArr2[i2])) {
                    hex = hex + hexStr.substring(i2, i2 + 1);
                }
                i2++;
            } else {
                return hex;
            }
        }
    }

    public static byte[] hexList2Byte(List<String> list) {
        List<byte[]> commandList = new ArrayList<>();
        for (String hexStr2 : list) {
            commandList.add(hexStringToBytes(hexStr2));
        }
        byte[] bytes = sysCopy(commandList);
        return bytes;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        String hexString2 = hexString.toUpperCase();
        int length = hexString2.length() / 2;
        char[] hexChars = hexString2.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte[] sysCopy(List<byte[]> srcArrays) {
        int len = 0;
        for (byte[] srcArray : srcArrays) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray2 : srcArrays) {
            System.arraycopy(srcArray2, 0, destArray, destLen, srcArray2.length);
            destLen += srcArray2.length;
        }
        return destArray;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
