package com.example.pointofsalef.customers;

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
import com.example.pointofsalef.adapter.CustomerAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.database.DatabaseOpenHelper;
import com.example.pointofsalef.utils.BaseActivity;
import com.example.pointofsalef.utils.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.obsez.android.lib.filechooser.ChooserDialog;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class CustomersActivity extends BaseActivity {
    EditText etxtSearch;
    FloatingActionButton fabAdd;
    ImageView imgNoProduct;
    ProgressDialog loading;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_customer);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.etxtSearch = (EditText) findViewById(R.id.etxt_customer_search);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> customerData = databaseAccess.getCustomers();
        Log.d("data", "" + customerData.size());
        if (customerData.size() <= 0) {
            Toasty.info(this, (int) R.string.no_customer_found, 0).show();
            this.imgNoProduct.setImageResource(R.drawable.no_data);
        } else {
            this.imgNoProduct.setVisibility(View.GONE);
            CustomerAdapter customerAdapter = new CustomerAdapter(this, customerData);
            this.recyclerView.setAdapter(customerAdapter);
        }
        this.fabAdd.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.customers.CustomersActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(CustomersActivity.this, AddCustomersActivity.class);
                CustomersActivity.this.startActivity(intent);
            }
        });
        this.etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.customers.CustomersActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(CustomersActivity.this);
                databaseAccess2.open();
                List<HashMap<String, String>> searchCustomerList = databaseAccess2.searchCustomers(s.toString());
                if (searchCustomerList.size() <= 0) {
                    CustomersActivity.this.recyclerView.setVisibility(View.GONE);
                    CustomersActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    CustomersActivity.this.imgNoProduct.setImageResource(R.drawable.no_data);
                    return;
                }
                CustomersActivity.this.recyclerView.setVisibility(View.VISIBLE);
                CustomersActivity.this.imgNoProduct.setVisibility(View.GONE);
                CustomerAdapter customerAdapter2 = new CustomerAdapter(CustomersActivity.this, searchCustomerList);
                CustomersActivity.this.recyclerView.setAdapter(customerAdapter2);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_customer_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_export_customer) {
            folderChooser();
            return true;
        } else if (id == 16908332) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void folderChooser() {
        new ChooserDialog((Activity) this).displayPath(true).withFilter(true, false, new String[0]).withChosenListener(new ChooserDialog.Result() { // from class: com.app.smartpos.customers.CustomersActivity.3
            @Override // com.obsez.android.lib.filechooser.ChooserDialog.Result
            public void onChoosePath(String path, File pathFile) {
                CustomersActivity.this.onExport(path);
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
        sqliteToExcel.exportSingleTable("customers", "customers.xls", new SQLiteToExcel.ExportListener() { // from class: com.app.smartpos.customers.CustomersActivity.4
            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onStart() {
                CustomersActivity.this.loading = new ProgressDialog(CustomersActivity.this);
                CustomersActivity.this.loading.setMessage(CustomersActivity.this.getString(R.string.data_exporting_please_wait));
                CustomersActivity.this.loading.setCancelable(false);
                CustomersActivity.this.loading.show();
            }

            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onCompleted(String filePath) {
                Handler mHand = new Handler();
                mHand.postDelayed(new Runnable() { // from class: com.app.smartpos.customers.CustomersActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CustomersActivity.this.loading.dismiss();
                        Toasty.success(CustomersActivity.this, (int) R.string.data_successfully_exported, 0).show();
                    }
                }, 5000L);
            }

            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onError(Exception e) {
                CustomersActivity.this.loading.dismiss();
                Toasty.error(CustomersActivity.this, (int) R.string.data_export_fail, 0).show();
                Log.d("Error", e.toString());
            }
        });
    }
}
