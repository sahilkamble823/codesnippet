package com.example.pointofsalef.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;
import com.app.smartpos.R;
import com.google.android.gms.ads.formats.NativeContentAd;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.poi.ss.formula.ptg.BoolPtg;

/* loaded from: classes2.dex */
public class BixolonPrnMng extends WoosimPrnMng {
    public BixolonPrnMng(Context c, String deviceAddr, IPrintToPrinter prnToWoosim) {
        super(c, deviceAddr, prnToWoosim);
    }

    @Override // com.app.smartpos.utils.WoosimPrnMng
    protected void simplePrintStr(boolean mEmphasis, boolean mUnderline, int mCharsize, int mJustification, String strLine) {
        WCharMapperCT42 wm = printerFactory.getActiveCharMapper(this.contx);
        ArrayList<Integer> wCodes = wm.getCodes(strLine);
        ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
        if (mEmphasis) {
            byte[] cmd_emphasis = {27, 69, 1};
            buffer.append(cmd_emphasis, 0, cmd_emphasis.length);
        }
        int mCharsize2 = mCharsize - 1;
        if (mCharsize2 != 0) {
            byte charSize = (byte) ((mCharsize2 << 4) | mCharsize2);
            byte[] cmd_size = {BoolPtg.sid, 33, charSize};
            buffer.append(cmd_size, 0, cmd_size.length);
        } else {
            byte[] cmd_size2 = {BoolPtg.sid, 33, 0};
            buffer.append(cmd_size2, 0, cmd_size2.length);
        }
        byte[] cmd_justification = {27, 97, (byte) mJustification};
        buffer.append(cmd_justification, 0, cmd_justification.length);
        Iterator<Integer> it = wCodes.iterator();
        while (it.hasNext()) {
            Integer i = it.next();
            buffer.append(i.intValue());
        }
        buffer.append(10);
        sendData(buffer.toByteArray());
    }

    @Override // com.app.smartpos.utils.WoosimPrnMng
    public void printBitmap(String picPath, int maxWidth) {
        Bitmap bmp = ImageGallaryMng.getBitmap(picPath);
        if (bmp == null) {
            Toast.makeText(this.contx, (int) R.string.pic_load_error, 1).show();
            return;
        }
        byte[] command = Utils.decodeBitmap(bmp);
        byte[] cmd_justification = {27, 97, 1};
        sendData(cmd_justification);
        sendData(command);
    }

    /* loaded from: classes2.dex */
    public static class Utils {
        private static String hexStr = "0123456789ABCDEF";
        private static String[] binaryArray = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", NativeContentAd.ASSET_HEADLINE, "1010", "1011", "1100", "1101", "1110", "1111"};

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
}
