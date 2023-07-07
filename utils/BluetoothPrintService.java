package com.example.pointofsalef.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.app.smartpos.R;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

/* loaded from: classes2.dex */
public class BluetoothPrintService {
    private static final boolean D = true;
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    private static final String TAG = "BluetoothPrintService";
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private final Handler mHandler;
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private int mState = 0;

    public BluetoothPrintService(Context context, Handler handler) {
        this.mHandler = handler;
    }

    private synchronized void setState(int state) {
        Log.d(TAG, "setState() " + this.mState + " -> " + state);
        this.mState = state;
    }

    public synchronized int getState() {
        return this.mState;
    }

    public synchronized void start() {
        Log.d(TAG, "start");
        ConnectThread connectThread = this.mConnectThread;
        if (connectThread != null) {
            connectThread.cancel();
            this.mConnectThread = null;
        }
        ConnectedThread connectedThread = this.mConnectedThread;
        if (connectedThread != null) {
            connectedThread.cancel();
            this.mConnectedThread = null;
        }
        setState(1);
    }

    public synchronized void connect(BluetoothDevice device, boolean secure) {
        ConnectThread connectThread;
        Log.d(TAG, "connect to: " + device);
        if (this.mState == 2 && (connectThread = this.mConnectThread) != null) {
            connectThread.cancel();
            this.mConnectThread = null;
        }
        ConnectedThread connectedThread = this.mConnectedThread;
        if (connectedThread != null) {
            connectedThread.cancel();
            this.mConnectedThread = null;
        }
        ConnectThread connectThread2 = new ConnectThread(device, secure);
        this.mConnectThread = connectThread2;
        connectThread2.start();
        setState(2);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, String socketType) {
        Log.d(TAG, "connected, Socket Type:" + socketType);
        ConnectThread connectThread = this.mConnectThread;
        if (connectThread != null) {
            connectThread.cancel();
            this.mConnectThread = null;
        }
        ConnectedThread connectedThread = this.mConnectedThread;
        if (connectedThread != null) {
            connectedThread.cancel();
            this.mConnectedThread = null;
        }
        ConnectedThread connectedThread2 = new ConnectedThread(socket, socketType);
        this.mConnectedThread = connectedThread2;
        connectedThread2.start();
        Message msg = this.mHandler.obtainMessage(1);
        Bundle bundle = new Bundle();
        bundle.putString(WoosimPrnMng.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
        setState(3);
    }

    public synchronized void stop() {
        Log.d(TAG, "stop");
        ConnectThread connectThread = this.mConnectThread;
        if (connectThread != null) {
            connectThread.cancel();
            this.mConnectThread = null;
        }
        ConnectedThread connectedThread = this.mConnectedThread;
        if (connectedThread != null) {
            connectedThread.cancel();
            this.mConnectedThread = null;
        }
        setState(0);
    }

    public void write(byte[] out) {
        synchronized (this) {
            if (this.mState != 3) {
                return;
            }
            ConnectedThread r = this.mConnectedThread;
            r.write(out);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectionFailed() {
        if (this.mState == 0) {
            return;
        }
        Message msg = this.mHandler.obtainMessage(2);
        Bundle bundle = new Bundle();
        bundle.putInt(WoosimPrnMng.TOAST, R.string.connect_fail);
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
        start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectionLost() {
        if (this.mState == 0) {
            return;
        }
        Message msg = this.mHandler.obtainMessage(2);
        Bundle bundle = new Bundle();
        bundle.putInt(WoosimPrnMng.TOAST, R.string.connect_lost);
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
        start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ConnectThread extends Thread {
        private String mSocketType;
        private final BluetoothDevice mmDevice;
        private final BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            this.mmDevice = device;
            BluetoothSocket tmp = null;
            this.mSocketType = secure ? "Secure" : "Insecure";
            try {
                tmp = secure ? device.createRfcommSocketToServiceRecord(BluetoothPrintService.SPP_UUID) : device.createInsecureRfcommSocketToServiceRecord(BluetoothPrintService.SPP_UUID);
            } catch (IOException e) {
                Log.e(BluetoothPrintService.TAG, "Socket Type: " + this.mSocketType + "create() failed", e);
            }
            this.mmSocket = tmp;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.i(BluetoothPrintService.TAG, "BEGIN mConnectThread SocketType:" + this.mSocketType);
            setName("ConnectThread" + this.mSocketType);
            BluetoothPrintService.this.mAdapter.cancelDiscovery();
            try {
                this.mmSocket.connect();
                synchronized (BluetoothPrintService.this) {
                    BluetoothPrintService.this.mConnectThread = null;
                }
                BluetoothPrintService.this.connected(this.mmSocket, this.mmDevice, this.mSocketType);
            } catch (IOException e) {
                try {
                    this.mmSocket.close();
                } catch (IOException e2) {
                    Log.e(BluetoothPrintService.TAG, "unable to close() " + this.mSocketType + " socket during connection failure", e2);
                }
                Log.e(BluetoothPrintService.TAG, "Connection Failed", e);
                BluetoothPrintService.this.connectionFailed();
            }
        }

        public void cancel() {
            try {
                this.mmSocket.close();
            } catch (IOException e) {
                Log.e(BluetoothPrintService.TAG, "close() of connect " + this.mSocketType + " socket failed", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            Log.d(BluetoothPrintService.TAG, "create ConnectedThread: " + socketType);
            this.mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(BluetoothPrintService.TAG, "temp sockets not created", e);
            }
            this.mmInStream = tmpIn;
            this.mmOutStream = tmpOut;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.i(BluetoothPrintService.TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            while (true) {
                try {
                    int bytes = this.mmInStream.read(buffer);
                    byte[] bArr = new byte[bytes];
                    byte[] rcvData = Arrays.copyOf(buffer, bytes);
                    BluetoothPrintService.this.mHandler.obtainMessage(3, bytes, -1, rcvData).sendToTarget();
                } catch (IOException e) {
                    Log.e(BluetoothPrintService.TAG, "Connection Lost", e);
                    BluetoothPrintService.this.connectionLost();
                    return;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                this.mmOutStream.write(buffer);
            } catch (IOException e) {
                Log.e(BluetoothPrintService.TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                this.mmInStream.close();
                this.mmOutStream.close();
                this.mmSocket.close();
            } catch (IOException e) {
                Log.e(BluetoothPrintService.TAG, "close() of connect socket failed", e);
            }
        }
    }
}
