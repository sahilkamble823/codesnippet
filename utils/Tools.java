package com.example.pointofsalef.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.app.smartpos.R;

/* loaded from: classes2.dex */
public class Tools {
    public static boolean isBlueToothOn(Context c) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(c, c.getString(R.string.bluetooth_not_available), 1).show();
            return false;
        } else if (mBluetoothAdapter.isEnabled()) {
            return true;
        } else {
            Toast.makeText(c, c.getString(R.string.turnon_bluetooth), 1).show();
            Intent enableIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
            c.startActivity(enableIntent);
            return false;
        }
    }
}
