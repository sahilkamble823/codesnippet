package com.example.pointofsalef.suppliers;

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

import com.app.smartpos.R;

import com.example.pointofsalef.HomeActivity;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.database.DatabaseOpenHelper;
import com.example.pointofsalef.utils.BaseActivity;

import com.obsez.android.lib.filechooser.ChooserDialog;
import es.dmoral.toasty.Toasty;
import java.io.File;

/* loaded from: classes2.dex */
public class AddSuppliersActivity extends BaseActivity {
    EditText etxtSuppliersAddress;
    EditText etxtSuppliersCell;
    EditText etxtSuppliersContactPerson;
    EditText etxtSuppliersEmail;
    EditText etxtSuppliersName;
    ProgressDialog loading;
    TextView txtAddSuppliers;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suppliers);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_suppliers);
        this.etxtSuppliersName = (EditText) findViewById(R.id.etxt_supplier_name);
        this.etxtSuppliersContactPerson = (EditText) findViewById(R.id.etxt_supplier_contact_name);
        this.etxtSuppliersCell = (EditText) findViewById(R.id.etxt_supplier_cell);
        this.etxtSuppliersEmail = (EditText) findViewById(R.id.etxt_supplier_email);
        this.etxtSuppliersAddress = (EditText) findViewById(R.id.etxt_supplier_address);
        TextView textView = (TextView) findViewById(R.id.txt_add_supplier);
        this.txtAddSuppliers = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.suppliers.AddSuppliersActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String suppliers_name = AddSuppliersActivity.this.etxtSuppliersName.getText().toString().trim();
                String suppliers_contact_person = AddSuppliersActivity.this.etxtSuppliersContactPerson.getText().toString().trim();
                String suppliers_cell = AddSuppliersActivity.this.etxtSuppliersCell.getText().toString().trim();
                String suppliers_email = AddSuppliersActivity.this.etxtSuppliersEmail.getText().toString().trim();
                String suppliers_address = AddSuppliersActivity.this.etxtSuppliersAddress.getText().toString().trim();
                if (suppliers_name.isEmpty()) {
                    AddSuppliersActivity.this.etxtSuppliersName.setError(AddSuppliersActivity.this.getString(R.string.enter_suppliers_name));
                    AddSuppliersActivity.this.etxtSuppliersName.requestFocus();
                } else if (suppliers_contact_person.isEmpty()) {
                    AddSuppliersActivity.this.etxtSuppliersContactPerson.setError(AddSuppliersActivity.this.getString(R.string.enter_suppliers_contact_person_name));
                    AddSuppliersActivity.this.etxtSuppliersContactPerson.requestFocus();
                } else if (suppliers_cell.isEmpty()) {
                    AddSuppliersActivity.this.etxtSuppliersCell.setError(AddSuppliersActivity.this.getString(R.string.enter_suppliers_cell));
                    AddSuppliersActivity.this.etxtSuppliersCell.requestFocus();
                } else if (suppliers_email.isEmpty() || !suppliers_email.contains("@") || !suppliers_email.contains(".")) {
                    AddSuppliersActivity.this.etxtSuppliersEmail.setError(AddSuppliersActivity.this.getString(R.string.enter_valid_email));
                    AddSuppliersActivity.this.etxtSuppliersEmail.requestFocus();
                } else if (suppliers_address.isEmpty()) {
                    AddSuppliersActivity.this.etxtSuppliersAddress.setError(AddSuppliersActivity.this.getString(R.string.enter_suppliers_address));
                    AddSuppliersActivity.this.etxtSuppliersAddress.requestFocus();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddSuppliersActivity.this);
                    databaseAccess.open();
                    boolean check = databaseAccess.addSuppliers(suppliers_name, suppliers_contact_person, suppliers_cell, suppliers_email, suppliers_address);
                    if (check) {
                        Toasty.success(AddSuppliersActivity.this, (int) R.string.suppliers_successfully_added, 0).show();
                        Intent intent = new Intent(AddSuppliersActivity.this, SuppliersActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        AddSuppliersActivity.this.startActivity(intent);
                        return;
                    }
                    Toasty.error(AddSuppliersActivity.this, (int) R.string.failed, 0).show();
                }
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_supplier_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.menu_import_supplier /* 2131296654 */:
                fileChooser();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        excelToSQLite.importFromFile(path, new ExcelToSQLite.ImportListener() { // from class: com.app.smartpos.suppliers.AddSuppliersActivity.2
            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onStart() {
                AddSuppliersActivity.this.loading = new ProgressDialog(AddSuppliersActivity.this);
                AddSuppliersActivity.this.loading.setMessage(AddSuppliersActivity.this.getString(R.string.data_importing_please_wait));
                AddSuppliersActivity.this.loading.setCancelable(false);
                AddSuppliersActivity.this.loading.show();
            }

            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onCompleted(String dbName) {
                Handler mHand = new Handler();
                mHand.postDelayed(new Runnable() { // from class: com.app.smartpos.suppliers.AddSuppliersActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AddSuppliersActivity.this.loading.dismiss();
                        Toasty.success(AddSuppliersActivity.this, (int) R.string.data_successfully_imported, 0).show();
                        Intent intent = new Intent(AddSuppliersActivity.this, HomeActivity.class);
                        AddSuppliersActivity.this.startActivity(intent);
                        AddSuppliersActivity.this.finish();
                    }
                }, 5000L);
            }

            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onError(Exception e) {
                AddSuppliersActivity.this.loading.dismiss();
                Log.d("Error : ", "" + e.getMessage());
                Toasty.error(AddSuppliersActivity.this, (int) R.string.data_import_fail, 0).show();
            }
        });
    }

    public void fileChooser() {
        new ChooserDialog((Activity) this).displayPath(true).withFilter(false, false, "xls").withChosenListener(new ChooserDialog.Result() { // from class: com.app.smartpos.suppliers.AddSuppliersActivity.4
            @Override // com.obsez.android.lib.filechooser.ChooserDialog.Result
            public void onChoosePath(String path, File pathFile) {
                AddSuppliersActivity.this.onImport(path);
            }
        }).withOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.app.smartpos.suppliers.AddSuppliersActivity.3
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                Log.d("CANCEL", "CANCEL");
                dialog.cancel();
            }
        }).build().show();
    }
}
