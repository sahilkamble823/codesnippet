package com.example.pointofsalef.pdf_report;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class BarCodeEncoder {
    private static final int BLACK = -16777216;
    private static final int WHITE = -1;

    public Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int imgWidth, int imgHeight) throws WriterException {
        Map<EncodeHintType, Object> hints;
        if (contents == null) {
            return null;
        }
        Object encoding = guessAppropriateEncoding(contents);
        if (encoding == null) {
            hints = null;
        } else {
            Map<EncodeHintType, Object> hints2 = new EnumMap<>(EncodeHintType.class);
            hints2.put(EncodeHintType.CHARACTER_SET, encoding);
            hints = hints2;
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix result = writer.encode(contents, format, imgWidth, imgHeight, hints);
            int width = result.getWidth();
            int height = result.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? -16777216 : -1;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 255) {
                return "UTF-8";
            }
        }
        return null;
    }
}
