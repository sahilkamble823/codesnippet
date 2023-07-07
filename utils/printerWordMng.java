package com.example.pointofsalef.utils;

/* loaded from: classes2.dex */
public class printerWordMng {
    private int maxChars;

    public printerWordMng(int maxChars) {
        this.maxChars = maxChars;
    }

    public boolean isFittable(String s) {
        return s.length() <= this.maxChars;
    }

    private String trim(String s) {
        return s.substring(0, this.maxChars);
    }

    public String autoTrim(String s) {
        return isFittable(s) ? s : trim(s);
    }

    private String wrap(String inputStr) {
        String nl = WoosimPrnMng.getSysLineSeparator();
        String res = "";
        String[] arrParts = inputStr.split(nl);
        for (String s : arrParts) {
            int ix = 0;
            for (int i = 1; i < s.length(); i++) {
                if (i % this.maxChars == 0) {
                    res = res + s.substring(ix, i) + nl;
                    ix = i;
                }
            }
            res = res + s.substring(ix) + nl;
        }
        return res.substring(0, res.length() - 1);
    }

    public String autoWrap(String s) {
        return isFittable(s) ? s : wrap(s);
    }

    private String wordWrap(String inputStr) {
        String nl = WoosimPrnMng.getSysLineSeparator();
        String res = "";
        String[] arrParts = inputStr.split(nl);
        int length = arrParts.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            String parts = arrParts[i2];
            int len = 0;
            String[] arrWords = parts.split(" ");
            int length2 = arrWords.length;
            int i3 = 0;
            while (i3 < length2) {
                String s = arrWords[i3];
                int len2 = len + s.length();
                if (len2 <= this.maxChars) {
                    res = res + s + " ";
                    len = len2 + 1;
                } else {
                    if (res.length() <= 0) {
                        res = autoTrim(s) + " ";
                    } else {
                        res = res.substring(i, res.length() - 1) + nl + autoTrim(s) + " ";
                    }
                    len = s.length();
                }
                i3++;
                i = 0;
            }
            res = res.substring(0, res.length() - 1) + nl;
            i2++;
            i = 0;
        }
        return res.substring(0, res.length() - 1);
    }

    public String autoWordWrap(String s) {
        return isFittable(s) ? s : wordWrap(s);
    }

    public String getHorizontalUnderline() {
        String s = "";
        for (int i = 1; i <= this.maxChars - 2; i++) {
            s = s + "_";
        }
        return s;
    }

    public int getWidth() {
        return this.maxChars * 16;
    }
}
