package com.example.pointofsalef.utils;

import com.itextpdf.text.Jpeg;
import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import java.util.ArrayList;
import org.apache.poi.hssf.record.UnknownRecord;
import org.apache.poi.hssf.usermodel.HSSFShapeTypes;

/* loaded from: classes2.dex */
public class WCharMapperCT42 {
    private int getCellNO(String s, int ix) {
        char c = s.charAt(ix);
        if (c < '0' || c > '9') {
            if (c < 1632 || c > 1641) {
                if (c < 1776 || c > 1785) {
                    if (c >= 0 && c <= 127) {
                        return c;
                    }
                    boolean postConn = false;
                    boolean preConn = (ix == 0 || BidiStringBreaker.isSpecialChar(s.charAt(ix + (-1))) || !connectToNext(s.charAt(ix + (-1)))) ? false : true;
                    if (ix != s.length() - 1 && !BidiStringBreaker.isSpecialChar(s.charAt(ix + 1)) && connectToNext(c)) {
                        postConn = true;
                    }
                    return (preConn || postConn) ? (preConn || !postConn) ? (preConn && postConn) ? medialForm(c) : finalForm(c) : initialForm(c) : isolatedForm(c);
                }
                return getCellNOArabNO2(c);
            }
            return getCellNOArabNO1(c);
        }
        return getCellNONum(c);
    }

    private int getCellNOArabNO2(char c) {
        return c - 1728;
    }

    private int getCellNOArabNO1(char c) {
        return c - 1584;
    }

    private int getCellNONum(char c) {
        return c;
    }

    protected int isolatedForm(char c) {
        if (c == 1575 || c == 1570) {
            return 128;
        }
        if (c == 1576 || c == 1662) {
            return HSSFShapeTypes.DoubleWave;
        }
        if (c == 1578) {
            return HSSFShapeTypes.ActionButtonBlank;
        }
        if (c == 1579) {
            return HSSFShapeTypes.ActionButtonHome;
        }
        if (c == 1580 || c == 1670) {
            return HSSFShapeTypes.ActionButtonHelp;
        }
        if (c == 1581) {
            return HSSFShapeTypes.ActionButtonInformation;
        }
        if (c == 1582) {
            return HSSFShapeTypes.ActionButtonForwardNext;
        }
        if (c == 1583) {
            return 180;
        }
        if (c == 1584) {
            return 181;
        }
        if (c == 1585) {
            return 182;
        }
        if (c == 1586 || c == 1688) {
            return 183;
        }
        if (c == 1587) {
            return HSSFShapeTypes.ActionButtonBackPrevious;
        }
        if (c == 1588) {
            return HSSFShapeTypes.ActionButtonEnd;
        }
        if (c == 1589) {
            return HSSFShapeTypes.ActionButtonBeginning;
        }
        if (c == 1590) {
            return HSSFShapeTypes.ActionButtonReturn;
        }
        if (c == 1591) {
            return HSSFShapeTypes.ActionButtonDocument;
        }
        if (c == 1592) {
            return HSSFShapeTypes.ActionButtonSound;
        }
        if (c == 1593) {
            return 164;
        }
        if (c == 1594) {
            return 165;
        }
        if (c == 1601) {
            return 166;
        }
        if (c == 1602) {
            return 167;
        }
        if (c == 1705 || c == 1603) {
            return 168;
        }
        if (c == 1711) {
            return 215;
        }
        if (c == 1604) {
            return 169;
        }
        if (c == 1605) {
            return 170;
        }
        if (c == 1606) {
            return 171;
        }
        if (c == 1608) {
            return 229;
        }
        if (c == 1607) {
            return 213;
        }
        if (c == 1740 || c == 1610) {
            return 173;
        }
        return otherChars(c);
    }

    protected int initialForm(char c) {
        if (c == 1575 || c == 1570) {
            return 128;
        }
        if (c == 1576) {
            return 174;
        }
        if (c == 1662) {
            return 217;
        }
        if (c == 1578) {
            return 175;
        }
        if (c == 1579) {
            return 176;
        }
        if (c == 1580 || c == 1670) {
            return 177;
        }
        if (c == 1581) {
            return 178;
        }
        if (c == 1582) {
            return 179;
        }
        if (c == 1583) {
            return 180;
        }
        if (c == 1584) {
            return 181;
        }
        if (c == 1585) {
            return 182;
        }
        if (c == 1586 || c == 1688) {
            return 183;
        }
        if (c == 1587) {
            return 133;
        }
        if (c == 1588) {
            return 134;
        }
        if (c == 1589) {
            return 135;
        }
        if (c == 1590) {
            return 136;
        }
        if (c == 1591) {
            return 137;
        }
        if (c == 1592) {
            return 138;
        }
        if (c == 1593) {
            return 211;
        }
        if (c == 1594) {
            return 221;
        }
        if (c == 1601) {
            return 222;
        }
        if (c == 1602) {
            return 223;
        }
        if (c == 1705 || c == 1603) {
            return 224;
        }
        if (c == 1711) {
            return 215;
        }
        if (c == 1604) {
            return 225;
        }
        if (c == 1605) {
            return Jpeg.M_APP2;
        }
        if (c == 1606) {
            return 227;
        }
        if (c == 1608) {
            return 229;
        }
        if (c == 1607) {
            return 228;
        }
        if (c == 1740 || c == 1610) {
            return 230;
        }
        return otherChars(c);
    }

    protected int otherChars(char c) {
        return c == 1548 ? 248 : 95;
    }

    protected int medialForm(char c) {
        if (c == 1575 || c == 1570) {
            return 187;
        }
        if (c == 1576) {
            return UnknownRecord.BITMAP_00E9;
        }
        if (c == 1662) {
            return 151;
        }
        if (c == 1578) {
            return 234;
        }
        if (c == 1579) {
            return MetaDo.META_CREATEPALETTE;
        }
        if (c == 1580 || c == 1670) {
            return 177;
        }
        if (c == 1581) {
            return 178;
        }
        if (c == 1582) {
            return 179;
        }
        if (c == 1583) {
            return 180;
        }
        if (c == 1584) {
            return 181;
        }
        if (c == 1585) {
            return 182;
        }
        if (c == 1586 || c == 1688) {
            return 183;
        }
        if (c == 1587) {
            return 133;
        }
        if (c == 1588) {
            return 134;
        }
        if (c == 1589) {
            return 135;
        }
        if (c == 1590) {
            return 136;
        }
        if (c == 1591) {
            return 137;
        }
        if (c == 1592) {
            return 138;
        }
        if (c == 1593) {
            return 139;
        }
        if (c == 1594) {
            return 140;
        }
        if (c == 1601) {
            return 141;
        }
        if (c == 1602) {
            return 142;
        }
        if (c == 1705 || c == 1603) {
            return 143;
        }
        if (c == 1711) {
            return 150;
        }
        if (c == 1604) {
            return 144;
        }
        if (c == 1605) {
            return 145;
        }
        if (c == 1606) {
            return 146;
        }
        if (c == 1608) {
            return 229;
        }
        if (c == 1607) {
            return 147;
        }
        if (c == 1740 || c == 1610) {
            return 149;
        }
        return otherChars(c);
    }

    protected int finalForm(char c) {
        if (c == 1575 || c == 1570) {
            return 187;
        }
        if (c == 1576 || c == 1662) {
            return HSSFShapeTypes.DoubleWave;
        }
        if (c == 1578) {
            return HSSFShapeTypes.ActionButtonBlank;
        }
        if (c == 1579) {
            return HSSFShapeTypes.ActionButtonHome;
        }
        if (c == 1580 || c == 1670) {
            return HSSFShapeTypes.ActionButtonHelp;
        }
        if (c == 1581) {
            return HSSFShapeTypes.ActionButtonInformation;
        }
        if (c == 1582) {
            return HSSFShapeTypes.ActionButtonForwardNext;
        }
        if (c == 1583) {
            return 180;
        }
        if (c == 1584) {
            return 181;
        }
        if (c == 1585) {
            return 182;
        }
        if (c == 1586 || c == 1688) {
            return 183;
        }
        if (c == 1587) {
            return HSSFShapeTypes.ActionButtonBackPrevious;
        }
        if (c == 1588) {
            return HSSFShapeTypes.ActionButtonEnd;
        }
        if (c == 1589) {
            return HSSFShapeTypes.ActionButtonBeginning;
        }
        if (c == 1590) {
            return HSSFShapeTypes.ActionButtonReturn;
        }
        if (c == 1591) {
            return HSSFShapeTypes.ActionButtonDocument;
        }
        if (c == 1592) {
            return HSSFShapeTypes.ActionButtonSound;
        }
        if (c == 1593) {
            return 201;
        }
        if (c == 1594) {
            return 202;
        }
        if (c == 1601) {
            return 166;
        }
        if (c == 1602) {
            return 167;
        }
        if (c == 1705 || c == 1603) {
            return 143;
        }
        if (c == 1711) {
            return 150;
        }
        if (c == 1604) {
            return 209;
        }
        if (c == 1605) {
            return 170;
        }
        if (c == 1606) {
            return 171;
        }
        if (c == 1608) {
            return 229;
        }
        if (c == 1607) {
            return 172;
        }
        if (c == 1740 || c == 1610) {
            return 220;
        }
        return otherChars(c);
    }

    private boolean connectToNext(char preChar) {
        return (preChar == 1570 || preChar == 1575 || preChar == 1583 || preChar == 1584 || preChar == 1585 || preChar == 1586 || preChar == 1688 || preChar == 1608) ? false : true;
    }

    public ArrayList<Integer> getCodes(String string) {
        BidiStringBreaker bidiBreaker = new BidiStringBreaker();
        ArrayList<String> brokenDown = bidiBreaker.breakDown(string);
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = brokenDown.size() - 1; i >= 0; i--) {
            String s = brokenDown.get(i);
            if (bidiBreaker.getKind(s, 0) == 2) {
                res.addAll(getSubCodesFarsi(s));
            } else {
                res.addAll(getSubCodesEng_Num(s));
            }
        }
        return res;
    }

    private ArrayList<Integer> getSubCodesFarsi(String s) {
        ArrayList<Integer> wCodes = new ArrayList<>();
        for (int i = s.length() - 1; i >= 0; i--) {
            wCodes.add(Integer.valueOf(getCellNO(s, i)));
        }
        return wCodes;
    }

    private ArrayList<Integer> getSubCodesEng_Num(String s) {
        ArrayList<Integer> wCodes = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            wCodes.add(Integer.valueOf(getCellNO(s, i)));
        }
        return wCodes;
    }
}
