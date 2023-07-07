package com.example.pointofsalef.utils;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class BidiStringBreaker {
    private static final int CBreaker = 5;
    private static final char CCharForceBreaker = 31;
    public static final int CEnglish_Number = 3;
    public static final int CFarsi = 2;
    public static final int CUndef = 4;

    public ArrayList<String> breakDown(String s) {
        if (s == null || s.length() == 0) {
            return errArray();
        }
        ArrayList<String> res = new ArrayList<>();
        int ix = 0;
        int kind = getKind(s, 0);
        int i = 1;
        while (i < s.length()) {
            int newKind = getKind(s, i);
            if (newKind != kind) {
                res.add(s.substring(ix, i));
                if (newKind == 5) {
                    i++;
                    newKind = getKind(s, i);
                }
                ix = i;
                kind = newKind;
            }
            i++;
        }
        res.add(s.substring(ix, i));
        return res;
    }

    private ArrayList<String> errArray() {
        ArrayList<String> res = new ArrayList<>();
        res.add(" ");
        return res;
    }

    public int getKind(String s, int i) {
        char c = s.charAt(i);
        if (isSpecialChar(c)) {
            int j = i - 1;
            while (j >= 0 && isSpecialChar(s.charAt(j))) {
                j--;
            }
            int kindPre = j == -1 ? 4 : getKind(s, j);
            int j2 = i + 1;
            while (j2 < s.length() && isSpecialChar(s.charAt(j2))) {
                j2++;
            }
            int kindNext = j2 == s.length() ? 4 : getKind(s, j2);
            if (kindPre == kindNext) {
                return kindPre;
            }
            if (kindPre == 4 && kindNext == 4) {
                return 3;
            }
            return kindPre == 4 ? kindNext : kindNext == 4 ? kindPre : getKindSpecialChar(c);
        } else if (c == 31) {
            return 5;
        } else {
            if (Character.isDigit(c)) {
                return 3;
            }
            return (c <= 0 || c > 127) ? 2 : 3;
        }
    }

    private int getKindSpecialChar(char c) {
        return c;
    }

    public static boolean isSpecialChar(char c) {
        return c == ' ' || c == '-' || c == '.' || c == '_' || c == '*' || c == '#' || c == '?' || c == ':' || c == 1548;
    }

    public static char getForceBreaker() {
        return CCharForceBreaker;
    }
}
