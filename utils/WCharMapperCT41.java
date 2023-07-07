package com.example.pointofsalef.utils;

import com.itextpdf.xmp.XMPError;
import org.apache.poi.hssf.usermodel.HSSFShapeTypes;

/* loaded from: classes2.dex */
public class WCharMapperCT41 extends WCharMapperCT42 {
    @Override // com.app.smartpos.utils.WCharMapperCT42
    protected int isolatedForm(char c) {
        if (c == 1575) {
            return 129;
        }
        if (c == 1570) {
            return 128;
        }
        if (c == 1576) {
            return 130;
        }
        if (c == 1662) {
            return 131;
        }
        if (c == 1578) {
            return 132;
        }
        if (c == 1579) {
            return 133;
        }
        if (c == 1580) {
            return 134;
        }
        if (c == 1670) {
            return 135;
        }
        if (c == 1581) {
            return 136;
        }
        if (c == 1582) {
            return 137;
        }
        if (c == 1583) {
            return 138;
        }
        if (c == 1584) {
            return 139;
        }
        if (c == 1585) {
            return 140;
        }
        if (c == 1586) {
            return 141;
        }
        if (c == 1688) {
            return 142;
        }
        if (c == 1587) {
            return 143;
        }
        if (c == 1588) {
            return 144;
        }
        if (c == 1589) {
            return 145;
        }
        if (c == 1590) {
            return 146;
        }
        if (c == 1591) {
            return 147;
        }
        if (c == 1592) {
            return 148;
        }
        if (c == 1593) {
            return 149;
        }
        if (c == 1594) {
            return 150;
        }
        if (c == 1601) {
            return 151;
        }
        if (c == 1602) {
            return 152;
        }
        if (c == 1705 || c == 1603) {
            return 153;
        }
        if (c == 1711) {
            return 154;
        }
        if (c == 1604) {
            return 155;
        }
        if (c == 1605) {
            return 156;
        }
        if (c == 1606) {
            return 157;
        }
        if (c == 1608) {
            return 158;
        }
        if (c == 1607) {
            return 159;
        }
        if (c == 1740 || c == 1610) {
            return 160;
        }
        return otherChars(c);
    }

    @Override // com.app.smartpos.utils.WCharMapperCT42
    protected int initialForm(char c) {
        if (c == 1575) {
            return 128;
        }
        if (c == 1570) {
            return 129;
        }
        if (c == 1576) {
            return 161;
        }
        if (c == 1662) {
            return 162;
        }
        if (c == 1578) {
            return 163;
        }
        if (c == 1579) {
            return 164;
        }
        if (c == 1580) {
            return 165;
        }
        if (c == 1670) {
            return 166;
        }
        if (c == 1581) {
            return 167;
        }
        if (c == 1582) {
            return 168;
        }
        if (c == 1583) {
            return 138;
        }
        if (c == 1584) {
            return 139;
        }
        if (c == 1585) {
            return 140;
        }
        if (c == 1586) {
            return 141;
        }
        if (c == 1688) {
            return 142;
        }
        if (c == 1587) {
            return 169;
        }
        if (c == 1588) {
            return 170;
        }
        if (c == 1589) {
            return 171;
        }
        if (c == 1590) {
            return 172;
        }
        if (c == 1591) {
            return 147;
        }
        if (c == 1592) {
            return 148;
        }
        if (c == 1593) {
            return 173;
        }
        if (c == 1594) {
            return 174;
        }
        if (c == 1601) {
            return 175;
        }
        if (c == 1602) {
            return 176;
        }
        if (c == 1705 || c == 1603) {
            return 177;
        }
        if (c == 1711) {
            return 178;
        }
        if (c == 1604) {
            return 179;
        }
        if (c == 1605) {
            return 180;
        }
        if (c == 1606) {
            return 181;
        }
        if (c == 1608) {
            return 158;
        }
        if (c == 1607) {
            return 182;
        }
        if (c == 1740 || c == 1610) {
            return 183;
        }
        return otherChars(c);
    }

    @Override // com.app.smartpos.utils.WCharMapperCT42
    protected int otherChars(char c) {
        return c == 1548 ? 221 : 244;
    }

    @Override // com.app.smartpos.utils.WCharMapperCT42
    protected int medialForm(char c) {
        if (c == 1575 || c == 1570) {
            return XMPError.BADXMP;
        }
        if (c == 1576) {
            return 184;
        }
        if (c == 1662) {
            return 185;
        }
        if (c == 1578) {
            return 186;
        }
        if (c == 1579) {
            return 187;
        }
        if (c == 1580) {
            return HSSFShapeTypes.DoubleWave;
        }
        if (c == 1670) {
            return HSSFShapeTypes.ActionButtonBlank;
        }
        if (c == 1581) {
            return HSSFShapeTypes.ActionButtonHome;
        }
        if (c == 1582) {
            return HSSFShapeTypes.ActionButtonHelp;
        }
        if (c == 1583) {
            return XMPError.BADSTREAM;
        }
        if (c == 1584) {
            return 205;
        }
        if (c == 1585) {
            return 206;
        }
        if (c == 1586) {
            return 207;
        }
        if (c == 1688) {
            return 208;
        }
        if (c == 1587) {
            return 169;
        }
        if (c == 1588) {
            return 170;
        }
        if (c == 1589) {
            return 171;
        }
        if (c == 1590) {
            return 172;
        }
        if (c == 1591) {
            return 147;
        }
        if (c == 1592) {
            return 148;
        }
        if (c == 1593) {
            return HSSFShapeTypes.ActionButtonInformation;
        }
        if (c == 1594) {
            return HSSFShapeTypes.ActionButtonForwardNext;
        }
        if (c == 1601) {
            return HSSFShapeTypes.ActionButtonBackPrevious;
        }
        if (c == 1602) {
            return HSSFShapeTypes.ActionButtonEnd;
        }
        if (c == 1705 || c == 1603) {
            return HSSFShapeTypes.ActionButtonBeginning;
        }
        if (c == 1711) {
            return HSSFShapeTypes.ActionButtonReturn;
        }
        if (c == 1604) {
            return HSSFShapeTypes.ActionButtonDocument;
        }
        if (c == 1605) {
            return HSSFShapeTypes.ActionButtonSound;
        }
        if (c == 1606) {
            return 200;
        }
        if (c == 1608) {
            return 214;
        }
        if (c == 1607) {
            return 201;
        }
        if (c == 1740 || c == 1610) {
            return 202;
        }
        return otherChars(c);
    }

    @Override // com.app.smartpos.utils.WCharMapperCT42
    protected int finalForm(char c) {
        if (c == 1575 || c == 1570) {
            return XMPError.BADXMP;
        }
        if (c == 1576) {
            return 130;
        }
        if (c == 1662) {
            return 131;
        }
        if (c == 1578) {
            return 132;
        }
        if (c == 1579) {
            return 133;
        }
        if (c == 1580) {
            return 134;
        }
        if (c == 1670) {
            return 135;
        }
        if (c == 1581) {
            return 136;
        }
        if (c == 1582) {
            return 137;
        }
        if (c == 1583) {
            return XMPError.BADSTREAM;
        }
        if (c == 1584) {
            return 205;
        }
        if (c == 1585) {
            return 206;
        }
        if (c == 1586) {
            return 207;
        }
        if (c == 1688) {
            return 208;
        }
        if (c == 1587) {
            return 143;
        }
        if (c == 1588) {
            return 144;
        }
        if (c == 1589) {
            return 145;
        }
        if (c == 1590) {
            return 146;
        }
        if (c == 1591) {
            return 147;
        }
        if (c == 1592) {
            return 148;
        }
        if (c == 1593) {
            return 209;
        }
        if (c == 1594) {
            return 210;
        }
        if (c == 1601) {
            return 151;
        }
        if (c == 1602) {
            return 211;
        }
        if (c == 1705 || c == 1603) {
            return 153;
        }
        if (c == 1711) {
            return 154;
        }
        if (c == 1604) {
            return 212;
        }
        if (c == 1605) {
            return 156;
        }
        if (c == 1606) {
            return 213;
        }
        if (c == 1608) {
            return 214;
        }
        if (c == 1607) {
            return 215;
        }
        if (c == 1740 || c == 1610) {
            return 216;
        }
        return otherChars(c);
    }
}
