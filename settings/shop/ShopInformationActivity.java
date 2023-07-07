package com.example.pointofsalef.settings.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.settings.SettingsActivity;
import com.example.pointofsalef.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ShopInformationActivity extends BaseActivity {
    EditText etxtShopAddress;
    EditText etxtShopContact;
    EditText etxtShopCurrency;
    EditText etxtShopEmail;
    EditText etxtShopName;
    EditText etxtTax;
    TextView txtShopEdit;
    TextView txtUpdate;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.shop_information);
        this.etxtShopName = (EditText) findViewById(R.id.etxt_shop_name);
        this.etxtShopContact = (EditText) findViewById(R.id.etxt_shop_contact);
        this.etxtShopEmail = (EditText) findViewById(R.id.etxt_shop_email);
        this.etxtShopAddress = (EditText) findViewById(R.id.etxt_shop_address);
        this.etxtShopCurrency = (EditText) findViewById(R.id.etxt_shop_currency);
        this.txtUpdate = (TextView) findViewById(R.id.txt_update);
        this.txtShopEdit = (TextView) findViewById(R.id.txt_shop_edit);
        this.txtShopEdit = (TextView) findViewById(R.id.txt_shop_edit);
        this.etxtTax = (EditText) findViewById(R.id.etxt_tax);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> shopData = databaseAccess.getShopInformation();
        String shop_name = shopData.get(0).get("shop_name");
        String shop_contact = shopData.get(0).get("shop_contact");
        String shop_email = shopData.get(0).get("shop_email");
        String shop_address = shopData.get(0).get("shop_address");
        String shop_currency = shopData.get(0).get("shop_currency");
        String tax = shopData.get(0).get("tax");
        this.etxtShopName.setText(shop_name);
        this.etxtShopContact.setText(shop_contact);
        this.etxtShopEmail.setText(shop_email);
        this.etxtShopAddress.setText(shop_address);
        this.etxtShopCurrency.setText(shop_currency);
        this.etxtTax.setText(tax);
        this.etxtShopName.setEnabled(false);
        this.etxtShopContact.setEnabled(false);
        this.etxtShopEmail.setEnabled(false);
        this.etxtShopAddress.setEnabled(false);
        this.etxtShopCurrency.setEnabled(false);
        this.etxtTax.setEnabled(false);
        this.txtUpdate.setVisibility(View.GONE);
        this.txtShopEdit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.shop.ShopInformationActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ShopInformationActivity.this.etxtShopName.setEnabled(true);
                ShopInformationActivity.this.etxtShopContact.setEnabled(true);
                ShopInformationActivity.this.etxtShopEmail.setEnabled(true);
                ShopInformationActivity.this.etxtShopAddress.setEnabled(true);
                ShopInformationActivity.this.etxtShopCurrency.setEnabled(true);
                ShopInformationActivity.this.etxtTax.setEnabled(true);
//                ShopInformationActivity.this.etxtShopName.setTextColor(SupportMenu.CATEGORY_MASK);
//                ShopInformationActivity.this.etxtShopContact.setTextColor(SupportMenu.CATEGORY_MASK);
//                ShopInformationActivity.this.etxtShopEmail.setTextColor(SupportMenu.CATEGORY_MASK);
//                ShopInformationActivity.this.etxtShopAddress.setTextColor(SupportMenu.CATEGORY_MASK);
//                ShopInformationActivity.this.etxtShopCurrency.setTextColor(SupportMenu.CATEGORY_MASK);
//                ShopInformationActivity.this.etxtTax.setTextColor(SupportMenu.CATEGORY_MASK);
                ShopInformationActivity.this.txtUpdate.setVisibility(View.VISIBLE);
                ShopInformationActivity.this.txtShopEdit.setVisibility(View.GONE);
            }
        });
        this.txtUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.shop.ShopInformationActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String shop_name2 = ShopInformationActivity.this.etxtShopName.getText().toString().trim();
                String shop_contact2 = ShopInformationActivity.this.etxtShopContact.getText().toString().trim();
                String shop_email2 = ShopInformationActivity.this.etxtShopEmail.getText().toString().trim();
                String shop_address2 = ShopInformationActivity.this.etxtShopAddress.getText().toString().trim();
                String shop_currency2 = ShopInformationActivity.this.etxtShopCurrency.getText().toString().trim();
                String tax2 = ShopInformationActivity.this.etxtTax.getText().toString().trim();
                if (shop_name2.isEmpty()) {
                    ShopInformationActivity.this.etxtShopName.setError(ShopInformationActivity.this.getString(R.string.shop_name_cannot_be_empty));
                    ShopInformationActivity.this.etxtShopName.requestFocus();
                } else if (shop_contact2.isEmpty()) {
                    ShopInformationActivity.this.etxtShopContact.setError(ShopInformationActivity.this.getString(R.string.shop_contact_cannot_be_empty));
                    ShopInformationActivity.this.etxtShopContact.requestFocus();
                } else if (shop_email2.isEmpty() || !shop_email2.contains("@") || !shop_email2.contains(".")) {
                    ShopInformationActivity.this.etxtShopEmail.setError(ShopInformationActivity.this.getString(R.string.enter_valid_email));
                    ShopInformationActivity.this.etxtShopEmail.requestFocus();
                } else if (shop_address2.isEmpty()) {
                    ShopInformationActivity.this.etxtShopAddress.setError(ShopInformationActivity.this.getString(R.string.shop_address_cannot_be_empty));
                    ShopInformationActivity.this.etxtShopAddress.requestFocus();
                } else if (shop_currency2.isEmpty()) {
                    ShopInformationActivity.this.etxtShopCurrency.setError(ShopInformationActivity.this.getString(R.string.shop_currency_cannot_be_empty));
                    ShopInformationActivity.this.etxtShopCurrency.requestFocus();
                } else if (tax2.isEmpty()) {
                    ShopInformationActivity.this.etxtTax.setError(ShopInformationActivity.this.getString(R.string.tax_in_percentage));
                    ShopInformationActivity.this.etxtTax.requestFocus();
                } else {
                    DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(ShopInformationActivity.this);
                    databaseAccess2.open();
                    boolean check = databaseAccess2.updateShopInformation(shop_name2, shop_contact2, shop_email2, shop_address2, shop_currency2, tax2);
                    if (check) {
                        Toasty.success(ShopInformationActivity.this, (int) R.string.shop_information_updated_successfully, 0).show();
                        Intent intent = new Intent(ShopInformationActivity.this, SettingsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        ShopInformationActivity.this.startActivity(intent);
                        return;
                    }
                    Toasty.error(ShopInformationActivity.this, (int) R.string.failed, 0).show();
                }
            }
        });
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
