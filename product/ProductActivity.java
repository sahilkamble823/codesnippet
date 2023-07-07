package com.example.pointofsalef.product;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.app.smartpos.R;

import com.example.pointofsalef.adapter.ProductAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.database.DatabaseOpenHelper;
import com.example.pointofsalef.utils.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.obsez.android.lib.filechooser.ChooserDialog;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ProductActivity extends BaseActivity {
    EditText etxtSearch;
    FloatingActionButton fabAdd;
    ImageView imgNoProduct;
    ProgressDialog loading;
    ProductAdapter productAdapter;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        this.etxtSearch = (EditText) findViewById(R.id.etxt_search);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_product);
        this.recyclerView = (RecyclerView) findViewById(R.id.product_recyclerview);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.fabAdd.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.ProductActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, AddProductActivity.class);
                ProductActivity.this.startActivity(intent);
            }
        });
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> productData = databaseAccess.getProducts();
        Log.d("data", "" + productData.size());
        if (productData.size() <= 0) {
            Toasty.info(this, (int) R.string.no_product_found, 0).show();
            this.imgNoProduct.setImageResource(R.drawable.no_data);
        } else {
            this.imgNoProduct.setVisibility(View.GONE);
            ProductAdapter productAdapter = new ProductAdapter(this, productData);
            this.productAdapter = productAdapter;
            this.recyclerView.setAdapter(productAdapter);
        }
        this.etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.product.ProductActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(ProductActivity.this);
                databaseAccess2.open();
                List<HashMap<String, String>> searchProductList = databaseAccess2.getSearchProducts(s.toString());
                if (searchProductList.size() <= 0) {
                    ProductActivity.this.recyclerView.setVisibility(View.GONE);
                    ProductActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    ProductActivity.this.imgNoProduct.setImageResource(R.drawable.no_data);
                    return;
                }
                ProductActivity.this.recyclerView.setVisibility(View.VISIBLE);
                ProductActivity.this.imgNoProduct.setVisibility(View.GONE);
                ProductActivity.this.productAdapter = new ProductAdapter(ProductActivity.this, searchProductList);
                ProductActivity.this.recyclerView.setAdapter(ProductActivity.this.productAdapter);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_product_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.menu_export /* 2131296649 */:
                folderChooser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void folderChooser() {
        new ChooserDialog((Activity) this).displayPath(true).withFilter(true, false, new String[0]).withChosenListener(new ChooserDialog.Result() { // from class: com.app.smartpos.product.ProductActivity.3
            @Override // com.obsez.android.lib.filechooser.ChooserDialog.Result
            public void onChoosePath(String path, File pathFile) {
                ProductActivity.this.onExport(path);
                Log.d("path", path);
            }
        }).build().show();
    }

    public void onExport(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DatabaseOpenHelper.DATABASE_NAME, path);
        sqliteToExcel.exportSingleTable("products", "products.xls", new SQLiteToExcel.ExportListener() { // from class: com.app.smartpos.product.ProductActivity.4
            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onStart() {
                ProductActivity.this.loading = new ProgressDialog(ProductActivity.this);
                ProductActivity.this.loading.setMessage(ProductActivity.this.getString(R.string.data_exporting_please_wait));
                ProductActivity.this.loading.setCancelable(false);
                ProductActivity.this.loading.show();
            }

            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onCompleted(String filePath) {
                Handler mHand = new Handler();
                mHand.postDelayed(new Runnable() { // from class: com.app.smartpos.product.ProductActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ProductActivity.this.loading.dismiss();
                        Toasty.success(ProductActivity.this, (int) R.string.data_successfully_exported, 0).show();
                    }
                }, 5000L);
            }

            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onError(Exception e) {
                ProductActivity.this.loading.dismiss();
                Toasty.error(ProductActivity.this, (int) R.string.data_export_fail, 0).show();
            }
        });
    }
}
