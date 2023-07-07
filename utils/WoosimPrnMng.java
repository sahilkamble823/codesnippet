package com.example.pointofsalef.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.app.smartpos.R;
import com.woosim.printer.WoosimCmd;
import com.woosim.printer.WoosimImage;
import com.woosim.printer.WoosimService;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.util.ByteArrayBuffer;

/* loaded from: classes2.dex */
public class WoosimPrnMng {
    public static final String DEVICE_NAME = "device_name";
    public static final int MESSAGE_DEVICE_NAME = 1;
    public static final int MESSAGE_READ = 3;
    public static final int MESSAGE_TOAST = 2;
    public static final String TOAST = "toast";
    private static BluetoothPrintService mPrintService = null;
    private static WoosimService mWoosim = null;
    protected Context contx;
    private BluetoothDevice device;
    private String mDeviceAddr;
    private final Handler mHandler;
    private IPrintToPrinter mPrintToPrinter;

    public void releaseAllocatoins() {
        if (mPrintService != null) {
            new Thread(new Runnable() { // from class: com.app.smartpos.utils.WoosimPrnMng.1
                @Override // java.lang.Runnable
                public void run() {
                    WoosimPrnMng.mPrintService.stop();
                    do {
                    } while (WoosimPrnMng.mPrintService.getState() != 0);
                    BluetoothPrintService unused = WoosimPrnMng.mPrintService = null;
                }
            }).run();
        }
        mWoosim = null;
    }

    public WoosimPrnMng(Context c, String deviceAddr, IPrintToPrinter prnToWoosim) {
        this.mDeviceAddr = "";
        Handler handler = new Handler() { // from class: com.app.smartpos.utils.WoosimPrnMng.2
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        String mConnectedDeviceName = msg.getData().getString(WoosimPrnMng.DEVICE_NAME);
                        Toast.makeText(WoosimPrnMng.this.contx, WoosimPrnMng.this.contx.getString(R.string.connected) + mConnectedDeviceName, 0).show();
                        WoosimPrnMng.this.printInfo();
                        return;
                    case 2:
                        Toast.makeText(WoosimPrnMng.this.contx, msg.getData().getInt(WoosimPrnMng.TOAST), 0).show();
                        return;
                    case 3:
                        WoosimPrnMng.mWoosim.processRcvData((byte[]) msg.obj, msg.arg1);
                        return;
                    case 100:
                        Toast.makeText(WoosimPrnMng.this.contx, "MSR message", 0).show();
                        return;
                    default:
                        return;
                }
            }
        };
        this.mHandler = handler;
        this.mDeviceAddr = deviceAddr;
        this.contx = c;
        this.mPrintToPrinter = prnToWoosim;
        PrefMng.saveDeviceAddr(c, "");
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.device = mBluetoothAdapter.getRemoteDevice(this.mDeviceAddr);
        if (mWoosim == null) {
            mWoosim = new WoosimService(handler);
        }
        if (mPrintService == null) {
            mPrintService = new BluetoothPrintService(this.contx, handler);
        }
        if (mPrintService.getState() == 0) {
            mPrintService.start();
        }
        if (mPrintService.getState() == 1) {
            mPrintService.connect(this.device, false);
        } else if (mPrintService.getState() == 3) {
            printInfo();
        }
    }

    public void printStr(String string) {
        printStr(string, PrefMng.getBoldPrinting(this.contx), false, 1, 2);
    }

    public void printStr(String string, int mCharsize, int mJustification) {
        printStr(string, PrefMng.getBoldPrinting(this.contx), false, mCharsize, mJustification);
    }

    public void printStr(String string, boolean mEmphasis, boolean mUnderline, int mCharsize, int mJustification) {
        String[] arrLines = string.split(getSysLineSeparator());
        for (String strLine : arrLines) {
            simplePrintStr(mEmphasis, mUnderline, mCharsize, mJustification, strLine);
        }
    }

    protected void simplePrintStr(boolean mEmphasis, boolean mUnderline, int mCharsize, int mJustification, String strLine) {
        WCharMapperCT42 wm = printerFactory.getActiveCharMapper(this.contx);
        ArrayList<Integer> wCodes = wm.getCodes(strLine);
        ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
        byte[] cmd_style = WoosimCmd.setTextStyle(mEmphasis, mUnderline, false, mCharsize, mCharsize);
        byte[] cmd_justification = WoosimCmd.setTextAlign(mJustification);
        byte[] cmd_CodeTable = WoosimCmd.setCodeTable(3, PrefMng.getWoosimCodeTbl(), 0);
        byte[] cmd_default_CT = WoosimCmd.setCodeTable(3, 0, 0);
        byte[] cmd_prn = WoosimCmd.printData();
        buffer.append(cmd_CodeTable, 0, cmd_CodeTable.length);
        buffer.append(cmd_justification, 0, cmd_justification.length);
        buffer.append(cmd_style, 0, cmd_style.length);
        Iterator<Integer> it = wCodes.iterator();
        while (it.hasNext()) {
            Integer i = it.next();
            buffer.append(i.intValue());
        }
        buffer.append(cmd_default_CT, 0, cmd_default_CT.length);
        buffer.append(cmd_prn, 0, cmd_prn.length);
        sendData(WoosimCmd.initPrinter());
        sendData(buffer.toByteArray());
    }

    public void leftRightAlign(String str1, String str2) {
        String ans = str1 + str2;
        if (ans.length() < 32) {
            int n = (32 - str1.length()) + str2.length();
            ans = str1 + new String(new char[n]).replace("\u0000", " ") + str2;
        }
        printStr(ans, 1, 2);
    }

    public void printNewLine() {
        simplePrintStr(false, false, 1, 2, "\n");
    }

    public static String getSysLineSeparator() {
        return System.getProperty("line.separator");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void sendData(byte[] data) {
        if (mPrintService.getState() != 3) {
            Toast.makeText(this.contx, (int) R.string.not_connected, 1).show();
        } else if (data.length > 0) {
            mPrintService.write(data);
        }
    }

    public boolean printSucc() {
        return mPrintService.getState() == 3;
    }

    public String getDeviceAddr() {
        return this.mDeviceAddr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printInfo() {
        this.mPrintToPrinter.printContent(this);
    }

    public void printTable(ByteArrayBuffer buffer) {
        sendData(WoosimCmd.initPrinter());
        sendData(buffer.toByteArray());
    }

    public void printBitmap(String picPath, int maxWidth) {
        Bitmap bmp = ImageGallaryMng.getBitmap(picPath);
        if (bmp == null) {
            Toast.makeText(this.contx, (int) R.string.pic_load_error, 1).show();
            return;
        }
        int w = bmp.getWidth();
        if (w > maxWidth) {
            w = maxWidth;
        }
        int x = (maxWidth - w) / 2;
        byte[] data = WoosimImage.printBitmap(x, 5, w, bmp.getHeight(), bmp);
        bmp.recycle();
        sendData(WoosimCmd.setPageMode());
        sendData(data);
        sendData(WoosimCmd.PM_setStdMode());
    }

    public void printPhoto(Bitmap img) {
        try {
            if (img == null) {
                Log.e("Print Photo error", "the file isn't exists");
            } else {
                byte[] command = Utils.decodeBitmap(img);
                mPrintService.write(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }
}
