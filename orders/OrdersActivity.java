package com.example.pointofsalef.orders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartpos.R;

import com.example.pointofsalef.HomeActivity;
import com.example.pointofsalef.adapter.OrderAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class OrdersActivity extends BaseActivity {
    EditText etxtSearch;
    ImageView imgNoProduct;
    private OrderAdapter orderAdapter;
    private RecyclerView recyclerView;
    TextView txtNoProducts;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.txtNoProducts = (TextView) findViewById(R.id.txt_no_products);
        this.etxtSearch = (EditText) findViewById(R.id.etxt_search_order);
        this.imgNoProduct.setVisibility(View.GONE);
        this.txtNoProducts.setVisibility(View.GONE);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.order_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> orderList = databaseAccess.getOrderList();
        if (orderList.size() <= 0) {
            Toasty.info(this, (int) R.string.no_order_found, 0).show();
            this.recyclerView.setVisibility(View.GONE);
            this.imgNoProduct.setVisibility(View.VISIBLE);
            this.imgNoProduct.setImageResource(R.drawable.not_found);
            this.txtNoProducts.setVisibility(View.VISIBLE);
        } else {
            OrderAdapter orderAdapter = new OrderAdapter(this, orderList);
            this.orderAdapter = orderAdapter;
            this.recyclerView.setAdapter(orderAdapter);
        }
        this.etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.orders.OrdersActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(OrdersActivity.this);
                databaseAccess2.open();
                List<HashMap<String, String>> searchOrder = databaseAccess2.searchOrderList(s.toString());
                if (searchOrder.size() <= 0) {
                    OrdersActivity.this.recyclerView.setVisibility(View.GONE);
                    OrdersActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    OrdersActivity.this.imgNoProduct.setImageResource(R.drawable.no_data);
                    return;
                }
                OrdersActivity.this.recyclerView.setVisibility(View.VISIBLE);
                OrdersActivity.this.imgNoProduct.setVisibility(View.GONE);
                OrderAdapter supplierAdapter = new OrderAdapter(OrdersActivity.this, searchOrder);
                OrdersActivity.this.recyclerView.setAdapter(supplierAdapter);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
