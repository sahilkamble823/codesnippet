package com.example.pointofsalef.pos;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.app.smartpos.R;
import com.example.pointofsalef.utils.BaseActivity;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import es.dmoral.toasty.Toasty;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


/* loaded from: classes2.dex */
public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    int currentApiVersion = Build.VERSION.SDK_INT;
    private ZXingScannerView scannerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.qr_barcode_scanner);
        if (this.currentApiVersion >= 23) {
            requestCameraPermission();
        }
        ZXingScannerView zXingScannerView = new ZXingScannerView(this);
        this.scannerView = zXingScannerView;
        setContentView(zXingScannerView);
        this.scannerView.startCamera();
        this.scannerView.setResultHandler(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.scannerView.setResultHandler(this);
        this.scannerView.startCamera();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.scannerView.stopCamera();
    }

    @Override // me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler
    public void handleResult(Result result) {
        String myResult = result.getText();
        PosActivity.etxtSearch.setText(myResult);
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());
        onBackPressed();
    }

    private void requestCameraPermission() {
        Dexter.withActivity(this).withPermission("android.permission.CAMERA").withListener(new PermissionListener() { // from class: com.app.smartpos.pos.ScannerActivity.1
            @Override // com.karumi.dexter.listener.single.PermissionListener
            public void onPermissionGranted(PermissionGrantedResponse response) {
                ScannerActivity.this.scannerView = new ZXingScannerView(ScannerActivity.this);
                ScannerActivity scannerActivity = ScannerActivity.this;
                scannerActivity.setContentView(scannerActivity.scannerView);
                ScannerActivity.this.scannerView.startCamera();
                ScannerActivity.this.scannerView.setResultHandler(ScannerActivity.this);
            }

            @Override // com.karumi.dexter.listener.single.PermissionListener
            public void onPermissionDenied(PermissionDeniedResponse response) {
                if (response.isPermanentlyDenied()) {
                    Toasty.info(ScannerActivity.this, (int) R.string.camera_permission, 0).show();
                }
            }

            @Override // com.karumi.dexter.listener.single.PermissionListener
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
