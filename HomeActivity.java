package com.example.pointofsalef;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.pointofsalef.customers.CustomersActivity;
import com.example.pointofsalef.expense.ExpenseActivity;
import com.example.pointofsalef.orders.OrdersActivity;
import com.example.pointofsalef.pos.PosActivity;
import com.example.pointofsalef.product.ProductActivity;
import com.example.pointofsalef.report.ReportActivity;
import com.example.pointofsalef.settings.SettingsActivity;
import com.example.pointofsalef.suppliers.SuppliersActivity;
import com.example.pointofsalef.utils.BaseActivity;
import com.example.pointofsalef.utils.LocaleManager;
import com.example.pointofsalef.R;
import com.example.pointofsalef.orders.OrdersActivity;
import com.example.pointofsalef.pos.PosActivity;
import com.example.pointofsalef.product.ProductActivity;
import com.example.pointofsalef.utils.BaseActivity;
import com.example.pointofsalef.utils.LocaleManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//import com.example.dmoral.toasty.Toasty;
import java.util.List;

import es.dmoral.toasty.Toasty;

/* loaded from: classes2.dex */
public class HomeActivity extends BaseActivity {
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    CardView cardCustomers;
    CardView cardExpense;
    CardView cardOrderList;
    CardView cardPos;
    CardView cardProducts;
    CardView cardReport;
    CardView cardSettings;
    CardView cardSupplier;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setElevation(0.0f);
        this.cardCustomers = (CardView) findViewById(R.id.card_customers);
        this.cardSupplier = (CardView) findViewById(R.id.card_suppliers);
        this.cardProducts = (CardView) findViewById(R.id.card_products);
        this.cardPos = (CardView) findViewById(R.id.card_pos);
        this.cardOrderList = (CardView) findViewById(R.id.card_order_list);
        this.cardReport = (CardView) findViewById(R.id.card_report);
        this.cardSettings = (CardView) findViewById(R.id.card_settings);
        this.cardExpense = (CardView) findViewById(R.id.card_expense);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        }
//        MobileAds.initialize(this, new OnInitializationCompleteListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda0
//            @Override // com.google.android.gms.ads.initialization.OnInitializationCompleteListener
//            public final void onInitializationComplete(InitializationStatus initializationStatus) {
//                HomeActivity.lambda$onCreate$0(initializationStatus);
//            }
//        });
//        AdView adView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//        this.cardCustomers.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda1
//            @Override // android.view.View.OnClickListener
//            public final void onClick(View view) {
//                HomeActivity.this.m40lambda$onCreate$1$comappsmartposHomeActivity(view);
//            }
//        });
        this.cardSupplier.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.this.m41lambda$onCreate$2$comappsmartposHomeActivity(view);
            }
        });
        this.cardProducts.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.this.m42lambda$onCreate$3$comappsmartposHomeActivity(view);
            }
        });
        this.cardPos.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.this.m43lambda$onCreate$4$comappsmartposHomeActivity(view);
            }
        });
        this.cardOrderList.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.this.m44lambda$onCreate$5$comappsmartposHomeActivity(view);
            }
        });
        this.cardReport.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.this.m45lambda$onCreate$6$comappsmartposHomeActivity(view);
            }
        });
        this.cardExpense.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.this.m46lambda$onCreate$7$comappsmartposHomeActivity(view);
            }
        });
        this.cardSettings.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.HomeActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });
    }

//    /* JADX INFO: Access modifiers changed from: package-private */
//    public static /* synthetic */ void lambda$onCreate$0(InitializationStatus initializationStatus) {
//    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$1$com-app-smartpos-HomeActivity  reason: not valid java name */
    public /* synthetic */ void m40lambda$onCreate$1$comappsmartposHomeActivity(View v) {
        Intent intent = new Intent(this, CustomersActivity.class);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$2$com-app-smartpos-HomeActivity  reason: not valid java name */
    public /* synthetic */ void m41lambda$onCreate$2$comappsmartposHomeActivity(View v) {
        Intent intent = new Intent(this, SuppliersActivity.class);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$3$com-app-smartpos-HomeActivity  reason: not valid java name */
    public /* synthetic */ void m42lambda$onCreate$3$comappsmartposHomeActivity(View v) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$4$com-app-smartpos-HomeActivity  reason: not valid java name */
    public /* synthetic */ void m43lambda$onCreate$4$comappsmartposHomeActivity(View v) {
        Intent intent = new Intent(this, PosActivity.class);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$5$com-app-smartpos-HomeActivity  reason: not valid java name */
    public /* synthetic */ void m44lambda$onCreate$5$comappsmartposHomeActivity(View v) {
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$6$com-app-smartpos-HomeActivity  reason: not valid java name */
    public /* synthetic */ void m45lambda$onCreate$6$comappsmartposHomeActivity(View v) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$7$com-app-smartpos-HomeActivity  reason: not valid java name */
    public /* synthetic */ void m46lambda$onCreate$7$comappsmartposHomeActivity(View v) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.language_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.local_bangla /* 2131296616 */:
                setNewLocale(this, LocaleManager.BANGLA);
                return true;
            case R.id.local_english /* 2131296617 */:
                setNewLocale(this, LocaleManager.ENGLISH);
                return true;
            case R.id.local_french /* 2131296618 */:
                setNewLocale(this, LocaleManager.FRENCH);
                return true;
            case R.id.local_hindi /* 2131296619 */:
                setNewLocale(this, LocaleManager.HINDI);
                return true;
            case R.id.local_spanish /* 2131296620 */:
                setNewLocale(this, LocaleManager.SPANISH);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("WrongConstant")
    private void setNewLocale(AppCompatActivity mContext, String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(268468224));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toasty.info(this, (int) R.string.press_once_again_to_exit, 0).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    private void requestPermission() {
        Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() { // from class: com.app.smartpos.HomeActivity.2
            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                report.areAllPermissionsGranted();
                report.isAnyPermissionPermanentlyDenied();
            }

            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() { // from class: com.app.smartpos.HomeActivity$$ExternalSyntheticLambda8
            @Override // com.karumi.dexter.listener.PermissionRequestErrorListener
            public final void onError(DexterError dexterError) {
                HomeActivity.this.m47lambda$requestPermission$8$comappsmartposHomeActivity(dexterError);
            }
        }).onSameThread().check();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$requestPermission$8$com-app-smartpos-HomeActivity  reason: not valid java name */
    public /* synthetic */ void m47lambda$requestPermission$8$comappsmartposHomeActivity(DexterError error) {
        Toast.makeText(getApplicationContext(), (int) R.string.error, Toast.LENGTH_SHORT).show();
    }
}
