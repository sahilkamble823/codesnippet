package com.example.pointofsalef.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.cardview.widget.CardView;
import com.app.smartpos.R;

import com.example.pointofsalef.settings.categories.CategoriesActivity;
import com.example.pointofsalef.settings.order_type.OrderTypeActivity;
import com.example.pointofsalef.settings.payment_method.PaymentMethodActivity;
import com.example.pointofsalef.settings.shop.ShopInformationActivity;
import com.example.pointofsalef.settings.unit.UnitActivity;
import com.example.pointofsalef.utils.BaseActivity;

/* loaded from: classes2.dex */
public class SettingsActivity extends BaseActivity {
    CardView cardBackup;
    CardView cardCategory;
    CardView cardOrderType;
    CardView cardPaymentMethod;
    CardView cardShopInfo;
    CardView cardUnit;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_settings);
        this.cardShopInfo = (CardView) findViewById(R.id.card_shop_info);
        this.cardBackup = (CardView) findViewById(R.id.card_backup);
        this.cardCategory = (CardView) findViewById(R.id.card_category);
        this.cardPaymentMethod = (CardView) findViewById(R.id.card_payment_method);
        this.cardOrderType = (CardView) findViewById(R.id.card_order_type);
        this.cardUnit = (CardView) findViewById(R.id.card_unit);
        this.cardShopInfo.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.SettingsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ShopInformationActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
        this.cardCategory.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.SettingsActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, CategoriesActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
        this.cardOrderType.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.SettingsActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, OrderTypeActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
        this.cardUnit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.SettingsActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, UnitActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
        this.cardPaymentMethod.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.SettingsActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PaymentMethodActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
//        this.cardBackup.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.SettingsActivity.6
//            @Override // android.view.View.OnClickListener
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingsActivity.this, BackupActivity.class);
//                SettingsActivity.this.startActivity(intent);
//            }
//        });
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
