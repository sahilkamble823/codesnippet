package com.example.pointofsalef.settings.order_type;

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
import com.example.pointofsalef.adapter.OrderTypeAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class OrderTypeActivity extends BaseActivity {
    EditText etxtSearch;
    FloatingActionButton fabAdd;
    ImageView imgNoProduct;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_type);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.order_type);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.etxtSearch = (EditText) findViewById(R.id.etxt_search);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> orderTypeData = databaseAccess.getOrderType();
        Log.d("data", "" + orderTypeData.size());
        if (orderTypeData.size() <= 0) {
            Toasty.info(this, (int) R.string.no_data_found, 0).show();
            this.imgNoProduct.setImageResource(R.drawable.no_data);
        } else {
            this.imgNoProduct.setVisibility(View.GONE);
            OrderTypeAdapter orderTypeAdapter = new OrderTypeAdapter(this, orderTypeData);
            this.recyclerView.setAdapter(orderTypeAdapter);
        }
        this.fabAdd.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.order_type.OrderTypeActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(OrderTypeActivity.this, AddOrderTypeActivity.class);
                OrderTypeActivity.this.startActivity(intent);
            }
        });
        this.etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.settings.order_type.OrderTypeActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(OrderTypeActivity.this);
                databaseAccess2.open();
                List<HashMap<String, String>> searchOrderTypeList = databaseAccess2.searchOrderType(s.toString());
                if (searchOrderTypeList.size() <= 0) {
                    OrderTypeActivity.this.recyclerView.setVisibility(View.GONE);
                    OrderTypeActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    OrderTypeActivity.this.imgNoProduct.setImageResource(R.drawable.no_data);
                    return;
                }
                OrderTypeActivity.this.recyclerView.setVisibility(View.VISIBLE);
                OrderTypeActivity.this.imgNoProduct.setVisibility(View.GONE);
                OrderTypeAdapter orderTypeAdapter2 = new OrderTypeAdapter(OrderTypeActivity.this, searchOrderTypeList);
                OrderTypeActivity.this.recyclerView.setAdapter(orderTypeAdapter2);
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
