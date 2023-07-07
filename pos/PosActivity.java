package com.example.pointofsalef.pos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartpos.R;

import com.example.pointofsalef.HomeActivity;
import com.example.pointofsalef.adapter.PosProductAdapter;
import com.example.pointofsalef.adapter.ProductCategoryAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class PosActivity extends BaseActivity {
    public static EditText etxtSearch;
    public static TextView txtCount;
    ProductCategoryAdapter categoryAdapter;
    DatabaseAccess databaseAccess;
    ImageView imgBack;
    ImageView imgCart;
    ImageView imgNoProduct;
    ImageView imgScanner;
    PosProductAdapter productAdapter;
    private RecyclerView recyclerView;
    int spanCount = 2;
    TextView txtNoProducts;
    TextView txtReset;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_product);
        getSupportActionBar().hide();
        etxtSearch = (EditText) findViewById(R.id.etxt_search);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.txtNoProducts = (TextView) findViewById(R.id.txt_no_products);
        this.imgScanner = (ImageView) findViewById(R.id.img_scanner);
        this.txtReset = (TextView) findViewById(R.id.txt_reset);
        txtCount = (TextView) findViewById(R.id.txt_count);
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.imgCart = (ImageView) findViewById(R.id.img_cart);
        RecyclerView categoryRecyclerView = (RecyclerView) findViewById(R.id.category_recyclerview);
        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.imgScanner.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.PosActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(PosActivity.this, ScannerActivity.class);
                PosActivity.this.startActivity(intent);
            }
        });
        this.imgNoProduct.setVisibility(View.GONE);
        this.txtNoProducts.setVisibility(View.GONE);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            this.spanCount = 4;
        } else if ((getResources().getConfiguration().screenLayout & 15) == 2) {
            this.spanCount = 2;
        } else if ((getResources().getConfiguration().screenLayout & 15) == 1) {
            this.spanCount = 2;
        } else {
            this.spanCount = 4;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.txtReset.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.PosActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PosActivity.this.databaseAccess.open();
                List<HashMap<String, String>> productList = PosActivity.this.databaseAccess.getProducts();
                if (productList.isEmpty()) {
                    PosActivity.this.recyclerView.setVisibility(View.GONE);
                    PosActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    PosActivity.this.imgNoProduct.setImageResource(R.drawable.not_found);
                    PosActivity.this.txtNoProducts.setVisibility(View.VISIBLE);
                    return;
                }
                PosActivity.this.recyclerView.setVisibility(View.VISIBLE);
                PosActivity.this.imgNoProduct.setVisibility(View.GONE);
                PosActivity.this.txtNoProducts.setVisibility(View.GONE);
                PosActivity.this.productAdapter = new PosProductAdapter(PosActivity.this, productList);
                PosActivity.this.recyclerView.setAdapter(PosActivity.this.productAdapter);
            }
        });
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(linerLayoutManager);
        categoryRecyclerView.setHasFixedSize(true);
        this.databaseAccess.open();
        List<HashMap<String, String>> categoryData = this.databaseAccess.getProductCategory();
        Log.d("data", "" + categoryData.size());
        if (categoryData.isEmpty()) {
            Toasty.info(this, (int) R.string.no_data_found, 0).show();
        } else {
            ProductCategoryAdapter productCategoryAdapter = new ProductCategoryAdapter(this, categoryData, this.recyclerView, this.imgNoProduct, this.txtNoProducts);
            this.categoryAdapter = productCategoryAdapter;
            categoryRecyclerView.setAdapter(productCategoryAdapter);
        }
        this.databaseAccess.open();
        int count = this.databaseAccess.getCartItemCount();
        if (count == 0) {
            txtCount.setVisibility(View.INVISIBLE);
        } else {
            txtCount.setVisibility(View.VISIBLE);
            txtCount.setText(String.valueOf(count));
        }
        this.imgCart.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.PosActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(PosActivity.this, ProductCart.class);
                PosActivity.this.startActivity(intent);
            }
        });
        this.imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.PosActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(PosActivity.this, HomeActivity.class);
                PosActivity.this.startActivity(intent);
                PosActivity.this.finish();
            }
        });
        this.databaseAccess.open();
        List<HashMap<String, String>> productList = this.databaseAccess.getProducts();
        if (productList.isEmpty()) {
            this.recyclerView.setVisibility(View.GONE);
            this.imgNoProduct.setVisibility(View.VISIBLE);
            this.imgNoProduct.setImageResource(R.drawable.not_found);
            this.txtNoProducts.setVisibility(View.VISIBLE);
        } else {
            this.recyclerView.setVisibility(View.VISIBLE);
            this.imgNoProduct.setVisibility(View.GONE);
            this.txtNoProducts.setVisibility(View.GONE);
            PosProductAdapter posProductAdapter = new PosProductAdapter(this, productList);
            this.productAdapter = posProductAdapter;
            this.recyclerView.setAdapter(posProductAdapter);
        }
        etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.pos.PosActivity.5
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count2, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count2) {
                PosActivity.this.databaseAccess.open();
                List<HashMap<String, String>> searchProductList = PosActivity.this.databaseAccess.getSearchProducts(s.toString());
                if (searchProductList.size() <= 0) {
                    PosActivity.this.recyclerView.setVisibility(View.GONE);
                    PosActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    PosActivity.this.imgNoProduct.setImageResource(R.drawable.not_found);
                    PosActivity.this.txtNoProducts.setVisibility(View.VISIBLE);
                    return;
                }
                PosActivity.this.recyclerView.setVisibility(View.VISIBLE);
                PosActivity.this.imgNoProduct.setVisibility(View.GONE);
                PosActivity.this.txtNoProducts.setVisibility(View.GONE);
                PosActivity.this.productAdapter = new PosProductAdapter(PosActivity.this, searchProductList);
                PosActivity.this.recyclerView.setAdapter(PosActivity.this.productAdapter);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.menu_cart_button /* 2131296647 */:
                Intent intent = new Intent(this, ProductCart.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.databaseAccess.open();
        int count = this.databaseAccess.getCartItemCount();
        if (count == 0) {
            txtCount.setVisibility(View.INVISIBLE);
            return;
        }
        txtCount.setVisibility(View.VISIBLE);
        txtCount.setText(String.valueOf(count));
    }
}
