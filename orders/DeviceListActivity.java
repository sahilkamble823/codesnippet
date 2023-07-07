package com.example.pointofsalef.orders;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.app.smartpos.R;
import java.util.Set;

/* loaded from: classes2.dex */
public class DeviceListActivity extends AppCompatActivity {
    private static final boolean D = true;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private static final String TAG = "DeviceListActivity";
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.orders.DeviceListActivity.2
        @SuppressLint("MissingPermission")
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            DeviceListActivity.this.mBtAdapter.cancelDiscovery();
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            Intent intent = new Intent();
            intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, address);
            DeviceListActivity.this.setResult(-1, intent);
            DeviceListActivity.this.finish();
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.app.smartpos.orders.DeviceListActivity.3
        @SuppressLint("MissingPermission")
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (device.getBondState() != 12) {
                    DeviceListActivity.this.mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                DeviceListActivity.this.findViewById(R.id.title_new_devices).setVisibility(View.GONE);
                DeviceListActivity.this.findViewById(R.id.pgBar).setVisibility(View.GONE);
                DeviceListActivity.this.setTitle(R.string.select_device);
                if (DeviceListActivity.this.mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = DeviceListActivity.this.getResources().getText(R.string.none_found).toString();
                    DeviceListActivity.this.mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @SuppressLint("MissingPermission")
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        setContentView(R.layout.activity_device_list);
        ActionBar br = getSupportActionBar();
        if (br != null) {
            br.setDisplayHomeAsUpEnabled(true);
            br.setHomeAsUpIndicator(R.drawable.back);
        }
        setResult(0);
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.orders.DeviceListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DeviceListActivity.this.doDiscovery();
                v.setVisibility(View.GONE);
            }
        });
        this.mPairedDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        this.mNewDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_name);
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter((ListAdapter) this.mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(this.mDeviceClickListener);
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter((ListAdapter) this.mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(this.mDeviceClickListener);
        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.FOUND");
        registerReceiver(this.mReceiver, filter);
        IntentFilter filter2 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        registerReceiver(this.mReceiver, filter2);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBtAdapter = defaultAdapter;
        @SuppressLint("MissingPermission") Set<BluetoothDevice> pairedDevices = defaultAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                this.mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            return;
        }
        String noDevices = getResources().getText(R.string.none_paired).toString();
        this.mPairedDevicesArrayAdapter.add(noDevices);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @SuppressLint("MissingPermission")
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }
        unregisterReceiver(this.mReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint("MissingPermission")
    public void doDiscovery() {
        Log.d(TAG, "doDiscovery()");
        findViewById(R.id.button_scan).setVisibility(View.GONE);
        findViewById(R.id.pgBar).setVisibility(View.VISIBLE);
        setTitle(R.string.scanning);
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
        if (this.mBtAdapter.isDiscovering()) {
            this.mBtAdapter.cancelDiscovery();
        }
        this.mBtAdapter.startDiscovery();
    }
}
