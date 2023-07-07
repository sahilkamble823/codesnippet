package com.example.pointofsalef.customers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ajts.androidmads.library.ExcelToSQLite;
import com.example.pointofsalef.HomeActivity;
import com.app.smartpos.R;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.database.DatabaseOpenHelper;
import com.example.pointofsalef.utils.BaseActivity;
//import com.itextpdf.text.io.PagedChannelRandomAccessSource;
import com.itextpdf.io.*;
import com.obsez.android.lib.filechooser.ChooserDialog;
import es.dmoral.toasty.Toasty;
import java.io.File;

/* loaded from: classes2.dex */
public class AddCustomersActivity extends BaseActivity {
    EditText etxtAddress;
    EditText etxtCustomerCell;
    EditText etxtCustomerEmail;
    EditText etxtCustomerName;
    ProgressDialog loading;
    TextView txtAddCustomer;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_customer);
        this.etxtCustomerName = (EditText) findViewById(R.id.etxt_customer_name);
        this.etxtCustomerCell = (EditText) findViewById(R.id.etxt_customer_cell);
        this.etxtCustomerEmail = (EditText) findViewById(R.id.etxt_email);
        this.etxtAddress = (EditText) findViewById(R.id.etxt_address);
        TextView textView = (TextView) findViewById(R.id.txt_add_customer);
        this.txtAddCustomer = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.customers.AddCustomersActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String customer_name = AddCustomersActivity.this.etxtCustomerName.getText().toString().trim();
                String customer_cell = AddCustomersActivity.this.etxtCustomerCell.getText().toString().trim();
                String customer_email = AddCustomersActivity.this.etxtCustomerEmail.getText().toString().trim();
                String customer_address = AddCustomersActivity.this.etxtAddress.getText().toString().trim();
                if (customer_name.isEmpty()) {
                    AddCustomersActivity.this.etxtCustomerName.setError(AddCustomersActivity.this.getString(R.string.enter_customer_name));
                    AddCustomersActivity.this.etxtCustomerName.requestFocus();
                } else if (customer_cell.isEmpty()) {
                    AddCustomersActivity.this.etxtCustomerCell.setError(AddCustomersActivity.this.getString(R.string.enter_customer_cell));
                    AddCustomersActivity.this.etxtCustomerCell.requestFocus();
                } else if (customer_email.isEmpty() || !customer_email.contains("@") || !customer_email.contains(".")) {
                    AddCustomersActivity.this.etxtCustomerEmail.setError(AddCustomersActivity.this.getString(R.string.enter_valid_email));
                    AddCustomersActivity.this.etxtCustomerEmail.requestFocus();
                } else if (customer_address.isEmpty()) {
                    AddCustomersActivity.this.etxtAddress.setError(AddCustomersActivity.this.getString(R.string.enter_customer_address));
                    AddCustomersActivity.this.etxtAddress.requestFocus();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddCustomersActivity.this);
                    databaseAccess.open();
                    boolean check = databaseAccess.addCustomer(customer_name, customer_cell, customer_email, customer_address);
                    if (check) {
                        Toasty.success(AddCustomersActivity.this, (int) R.string.customer_successfully_added, 0).show();
                        Intent intent = new Intent(AddCustomersActivity.this, CustomersActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        AddCustomersActivity.this.startActivity(intent);
                        return;
                    }
                    Toasty.error(AddCustomersActivity.this, (int) R.string.failed, 0).show();
                }
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.menu_import /* 2131296653 */:
                fileChooser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onImport(String path) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, (int) R.string.no_file_found, Toast.LENGTH_SHORT).show();
            return;
        }
        ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), DatabaseOpenHelper.DATABASE_NAME, false);
        excelToSQLite.importFromFile(path, new ExcelToSQLite.ImportListener() { // from class: com.app.smartpos.customers.AddCustomersActivity.2
            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onStart() {
                AddCustomersActivity.this.loading = new ProgressDialog(AddCustomersActivity.this);
                AddCustomersActivity.this.loading.setMessage(AddCustomersActivity.this.getString(R.string.data_importing_please_wait));
                AddCustomersActivity.this.loading.setCancelable(false);
                AddCustomersActivity.this.loading.show();
            }

            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onCompleted(String dbName) {
                Handler mHand = new Handler();
                mHand.postDelayed(new Runnable() { // from class: com.app.smartpos.customers.AddCustomersActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AddCustomersActivity.this.loading.dismiss();
                        Toasty.success(AddCustomersActivity.this, (int) R.string.data_successfully_imported, 0).show();
                        Intent intent = new Intent(AddCustomersActivity.this, HomeActivity.class);
                        AddCustomersActivity.this.startActivity(intent);
                        AddCustomersActivity.this.finish();
                    }
                }, 5000L);
            }

            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onError(Exception e) {
                AddCustomersActivity.this.loading.dismiss();
                Log.d("Error : ", "" + e.getMessage());
                Toasty.error(AddCustomersActivity.this, (int) R.string.data_import_fail, 0).show();
            }
        });
    }

    public void fileChooser() {
        new ChooserDialog((Activity) this).displayPath(true).withFilter(false, false, "xls").withChosenListener(new ChooserDialog.Result() { // from class: com.app.smartpos.customers.AddCustomersActivity.4
            @Override // com.obsez.android.lib.filechooser.ChooserDialog.Result
            public void onChoosePath(String path, File pathFile) {
                AddCustomersActivity.this.onImport(path);
            }
        }).withOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.app.smartpos.customers.AddCustomersActivity.3
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                Log.d("CANCEL", "CANCEL");
                dialog.cancel();
            }
        }).build().show();
    }
}
