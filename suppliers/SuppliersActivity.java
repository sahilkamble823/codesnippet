package com.example.pointofsalef.suppliers;

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

import com.example.pointofsalef.adapter.SupplierAdapter;
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
public class SuppliersActivity extends BaseActivity {
    EditText etxtSearch;
    FloatingActionButton fabAdd;
    ImageView imgNoProduct;
    ProgressDialog loading;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_suppliers);
        this.recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.etxtSearch = (EditText) findViewById(R.id.etxt_supplier_search);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add);
        this.fabAdd = floatingActionButton;
        floatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.suppliers.SuppliersActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(SuppliersActivity.this, AddSuppliersActivity.class);
                SuppliersActivity.this.startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> suppliersData = databaseAccess.getSuppliers();
        Log.d("data", "" + suppliersData.size());
        if (suppliersData.size() <= 0) {
            Toasty.info(this, (int) R.string.no_suppliers_found, 0).show();
            this.imgNoProduct.setImageResource(R.drawable.no_data);
        } else {
            this.imgNoProduct.setVisibility(View.GONE);
            SupplierAdapter supplierAdapter = new SupplierAdapter(this, suppliersData);
            this.recyclerView.setAdapter(supplierAdapter);
        }
        this.etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.suppliers.SuppliersActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(SuppliersActivity.this);
                databaseAccess2.open();
                List<HashMap<String, String>> searchSupplier = databaseAccess2.searchSuppliers(s.toString());
                if (searchSupplier.size() <= 0) {
                    SuppliersActivity.this.recyclerView.setVisibility(View.GONE);
                    SuppliersActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    SuppliersActivity.this.imgNoProduct.setImageResource(R.drawable.no_data);
                    return;
                }
                SuppliersActivity.this.recyclerView.setVisibility(View.VISIBLE);
                SuppliersActivity.this.imgNoProduct.setVisibility(View.GONE);
                SupplierAdapter supplierAdapter2 = new SupplierAdapter(SuppliersActivity.this, searchSupplier);
                SuppliersActivity.this.recyclerView.setAdapter(supplierAdapter2);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.export_suppliers_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_export_supplier) {
            folderChooser();
        } else if (id == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void folderChooser() {
        new ChooserDialog((Activity) this).displayPath(true).withFilter(true, false, new String[0]).withChosenListener(new ChooserDialog.Result() { // from class: com.app.smartpos.suppliers.SuppliersActivity.3
            @Override // com.obsez.android.lib.filechooser.ChooserDialog.Result
            public void onChoosePath(String path, File pathFile) {
                SuppliersActivity.this.onExport(path);
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
        sqliteToExcel.exportSingleTable("suppliers", "suppliers.xls", new SQLiteToExcel.ExportListener() { // from class: com.app.smartpos.suppliers.SuppliersActivity.4
            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onStart() {
                SuppliersActivity.this.loading = new ProgressDialog(SuppliersActivity.this);
                SuppliersActivity.this.loading.setMessage(SuppliersActivity.this.getString(R.string.data_exporting_please_wait));
                SuppliersActivity.this.loading.setCancelable(false);
                SuppliersActivity.this.loading.show();
            }

            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onCompleted(String filePath) {
                Handler mHand = new Handler();
                mHand.postDelayed(new Runnable() { // from class: com.app.smartpos.suppliers.SuppliersActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SuppliersActivity.this.loading.dismiss();
                        Toasty.success(SuppliersActivity.this, (int) R.string.data_successfully_exported, 0).show();
                    }
                }, 5000L);
            }

            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onError(Exception e) {
                SuppliersActivity.this.loading.dismiss();
                Toasty.error(SuppliersActivity.this, (int) R.string.data_export_fail, 0).show();
            }
        });
    }
}
