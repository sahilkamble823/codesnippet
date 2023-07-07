package com.example.pointofsalef.settings.payment_method;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;

import com.example.pointofsalef.adapter.PaymentMethodAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class PaymentMethodActivity extends BaseActivity {
    EditText etxtSearch;
    FloatingActionButton fabAdd;
    ImageView imgNoProduct;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_payment_method);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.etxtSearch = (EditText) findViewById(R.id.etxt_search);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> paymentMethodData = databaseAccess.getPaymentMethod();
        Log.d("data", "" + paymentMethodData.size());
        if (paymentMethodData.size() <= 0) {
            Toasty.info(this, (int) R.string.no_data_found, 0).show();
            this.imgNoProduct.setImageResource(R.drawable.no_data);
        } else {
            this.imgNoProduct.setVisibility(View.GONE);
            PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(this, paymentMethodData);
            this.recyclerView.setAdapter(paymentMethodAdapter);
        }
        this.fabAdd.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.payment_method.PaymentMethodActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethodActivity.this, AddPaymentMethodActivity.class);
                PaymentMethodActivity.this.startActivity(intent);
            }
        });
        this.etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.settings.payment_method.PaymentMethodActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(PaymentMethodActivity.this);
                databaseAccess2.open();
                List<HashMap<String, String>> searchPaymentMethodList = databaseAccess2.searchPaymentMethod(s.toString());
                if (searchPaymentMethodList.size() <= 0) {
                    PaymentMethodActivity.this.recyclerView.setVisibility(View.GONE);
                    PaymentMethodActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    PaymentMethodActivity.this.imgNoProduct.setImageResource(R.drawable.no_data);
                    return;
                }
                PaymentMethodActivity.this.recyclerView.setVisibility(View.VISIBLE);
                PaymentMethodActivity.this.imgNoProduct.setVisibility(View.GONE);
                PaymentMethodAdapter paymentMethodAdapter2 = new PaymentMethodAdapter(PaymentMethodActivity.this, searchPaymentMethodList);
                PaymentMethodActivity.this.recyclerView.setAdapter(paymentMethodAdapter2);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
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
