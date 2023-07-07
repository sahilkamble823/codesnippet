package com.example.pointofsalef.utils;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.itextpdf.text.Jpeg;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import org.apache.poi.hssf.record.UnknownRecord;

/* loaded from: classes2.dex */
public class WCharMapperRongtaDef extends WCharMapperCT41 {
    @Override // com.app.smartpos.utils.WCharMapperCT41, com.app.smartpos.utils.WCharMapperCT42
    protected int isolatedForm(char c) {
        if (c == 1575) {
            return 144;
        }
        if (c == 1570) {
            return 141;
        }
        if (c == 1576) {
            return 146;
        }
        if (c == 1662) {
            return 148;
        }
        if (c == 1578) {
            return 150;
        }
        if (c == 1579) {
            return 152;
        }
        if (c == 1580) {
            return 154;
        }
        if (c == 1670) {
            return 156;
        }
        if (c == 1581) {
            return 158;
        }
        if (c == 1582) {
            return 160;
        }
        if (c == 1583) {
            return 162;
        }
        if (c == 1584) {
            return 163;
        }
        if (c == 1585) {
            return 164;
        }
        if (c == 1586) {
            return 165;
        }
        if (c == 1688) {
            return 166;
        }
        if (c == 1587) {
            return 167;
        }
        if (c == 1588) {
            return 169;
        }
        if (c == 1589) {
            return 171;
        }
        if (c == 1590) {
            return 173;
        }
        if (c == 1591) {
            return 175;
        }
        if (c == 1592) {
            return 224;
        }
        if (c == 1593) {
            return 225;
        }
        if (c == 1594) {
            return 229;
        }
        if (c == 1601) {
            return UnknownRecord.BITMAP_00E9;
        }
        if (c == 1602) {
            return 235;
        }
        if (c == 1705 || c == 1603) {
            return Jpeg.M_APPD;
        }
        if (c == 1711) {
            return UnknownRecord.PHONETICPR_00EF;
        }
        if (c == 1604) {
            return 241;
        }
        if (c == 1605) {
            return 244;
        }
        if (c == 1606) {
            return 246;
        }
        if (c == 1608) {
            return 248;
        }
        if (c == 1607) {
            return 249;
        }
        if (c == 1740 || c == 1610) {
            return 253;
        }
        return otherChars(c);
    }

    @Override // com.app.smartpos.utils.WCharMapperCT41, com.app.smartpos.utils.WCharMapperCT42
    protected int initialForm(char c) {
        if (c == 1575) {
            return 144;
        }
        if (c == 1570) {
            return 141;
        }
        if (c == 1576) {
            return 147;
        }
        if (c == 1662) {
            return 149;
        }
        if (c == 1578) {
            return 151;
        }
        if (c == 1579) {
            return 153;
        }
        if (c == 1580) {
            return 155;
        }
        if (c == 1670) {
            return 157;
        }
        if (c == 1581) {
            return 159;
        }
        if (c == 1582) {
            return 161;
        }
        if (c == 1583) {
            return 162;
        }
        if (c == 1584) {
            return 163;
        }
        if (c == 1585) {
            return 164;
        }
        if (c == 1586) {
            return 165;
        }
        if (c == 1688) {
            return 166;
        }
        if (c == 1587) {
            return 168;
        }
        if (c == 1588) {
            return 170;
        }
        if (c == 1589) {
            return 172;
        }
        if (c == 1590) {
            return 174;
        }
        if (c == 1591) {
            return 175;
        }
        if (c == 1592) {
            return 224;
        }
        if (c == 1593) {
            return 228;
        }
        if (c == 1594) {
            return 232;
        }
        if (c == 1601) {
            return 234;
        }
        if (c == 1602) {
            return 236;
        }
        if (c == 1705 || c == 1603) {
            return Jpeg.M_APPE;
        }
        if (c == 1711) {
            return 240;
        }
        if (c == 1604) {
            return 243;
        }
        if (c == 1605) {
            return 245;
        }
        if (c == 1606) {
            return MetaDo.META_CREATEPALETTE;
        }
        if (c == 1608) {
            return 248;
        }
        if (c == 1607) {
            return 251;
        }
        return (c == 1740 || c == 1610) ? TIFFConstants.TIFFTAG_SUBFILETYPE : otherChars(c);
    }

    @Override // com.app.smartpos.utils.WCharMapperCT41, com.app.smartpos.utils.WCharMapperCT42
    protected int otherChars(char c) {
        return c == 1548 ? 138 : 220;
    }

    @Override // com.app.smartpos.utils.WCharMapperCT41, com.app.smartpos.utils.WCharMapperCT42
    protected int medialForm(char c) {
        if (c == 1575 || c == 1570) {
            return 145;
        }
        if (c == 1576) {
            return 147;
        }
        if (c == 1662) {
            return 149;
        }
        if (c == 1578) {
            return 151;
        }
        if (c == 1579) {
            return 153;
        }
        if (c == 1580) {
            return 155;
        }
        if (c == 1670) {
            return 157;
        }
        if (c == 1581) {
            return 159;
        }
        if (c == 1582) {
            return 161;
        }
        if (c == 1583) {
            return 162;
        }
        if (c == 1584) {
            return 163;
        }
        if (c == 1585) {
            return 164;
        }
        if (c == 1586) {
            return 165;
        }
        if (c == 1688) {
            return 166;
        }
        if (c == 1587) {
            return 168;
        }
        if (c == 1588) {
            return 170;
        }
        if (c == 1589) {
            return 172;
        }
        if (c == 1590) {
            return 174;
        }
        if (c == 1591) {
            return 175;
        }
        if (c == 1592) {
            return 224;
        }
        if (c == 1593) {
            return 227;
        }
        if (c == 1594) {
            return 231;
        }
        if (c == 1601) {
            return 234;
        }
        if (c == 1602) {
            return 236;
        }
        if (c == 1705 || c == 1603) {
            return Jpeg.M_APPE;
        }
        if (c == 1711) {
            return 240;
        }
        if (c == 1604) {
            return 243;
        }
        if (c == 1605) {
            return 245;
        }
        if (c == 1606) {
            return MetaDo.META_CREATEPALETTE;
        }
        if (c == 1608) {
            return 248;
        }
        return c == 1607 ? ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION : (c == 1740 || c == 1610) ? TIFFConstants.TIFFTAG_SUBFILETYPE : otherChars(c);
    }

    @Override // com.app.smartpos.utils.WCharMapperCT41, com.app.smartpos.utils.WCharMapperCT42
    protected int finalForm(char c) {
        if (c == 1575 || c == 1570) {
            return 145;
        }
        if (c == 1576) {
            return 146;
        }
        if (c == 1662) {
            return 148;
        }
        if (c == 1578) {
            return 150;
        }
        if (c == 1579) {
            return 152;
        }
        if (c == 1580) {
            return 154;
        }
        if (c == 1670) {
            return 156;
        }
        if (c == 1581) {
            return 158;
        }
        if (c == 1582) {
            return 160;
        }
        if (c == 1583) {
            return 162;
        }
        if (c == 1584) {
            return 163;
        }
        if (c == 1585) {
            return 164;
        }
        if (c == 1586) {
            return 165;
        }
        if (c == 1688) {
            return 166;
        }
        if (c == 1587) {
            return 167;
        }
        if (c == 1588) {
            return 169;
        }
        if (c == 1589) {
            return 171;
        }
        if (c == 1590) {
            return 173;
        }
        if (c == 1591) {
            return 175;
        }
        if (c == 1592) {
            return 224;
        }
        if (c == 1593) {
            return Jpeg.M_APP2;
        }
        if (c == 1594) {
            return 230;
        }
        if (c == 1601) {
            return UnknownRecord.BITMAP_00E9;
        }
        if (c == 1602) {
            return 235;
        }
        if (c == 1705 || c == 1603) {
            return Jpeg.M_APPD;
        }
        if (c == 1711) {
            return UnknownRecord.PHONETICPR_00EF;
        }
        if (c == 1604) {
            return 241;
        }
        if (c == 1605) {
            return 244;
        }
        if (c == 1606) {
            return 246;
        }
        if (c == 1608) {
            return 248;
        }
        if (c == 1607) {
            return 249;
        }
        if (c == 1740 || c == 1610) {
            return 252;
        }
        return otherChars(c);
    }
}
