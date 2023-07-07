package com.example.pointofsalef.suppliers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;

import es.dmoral.toasty.Toasty;

/* loaded from: classes2.dex */
public class EditSuppliersActivity extends BaseActivity {
    EditText etxtSuppliersAddress;
    EditText etxtSuppliersCell;
    EditText etxtSuppliersContactPerson;
    EditText etxtSuppliersEmail;
    EditText etxtSuppliersName;
    TextView txtEditSuppliers;
    TextView txtUpdateSuppliers;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_suppliers);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.edit_suppliers);
        this.etxtSuppliersName = (EditText) findViewById(R.id.etxt_supplier_name);
        this.etxtSuppliersContactPerson = (EditText) findViewById(R.id.etxt_supplier_contact_name);
        this.etxtSuppliersCell = (EditText) findViewById(R.id.etxt_supplier_cell);
        this.etxtSuppliersEmail = (EditText) findViewById(R.id.etxt_supplier_email);
        this.etxtSuppliersAddress = (EditText) findViewById(R.id.etxt_supplier_address);
        this.txtUpdateSuppliers = (TextView) findViewById(R.id.txt_update_suppliers);
        this.txtEditSuppliers = (TextView) findViewById(R.id.txt_edit_suppliers);
        final String get_suppliers_id = getIntent().getExtras().getString("suppliers_id");
        String get_suppliers_name = getIntent().getExtras().getString("suppliers_name");
        String get_suppliers_contact_person = getIntent().getExtras().getString("suppliers_contact_person");
        String get_suppliers_cell = getIntent().getExtras().getString("suppliers_cell");
        String get_suppliers_email = getIntent().getExtras().getString("suppliers_email");
        String get_suppliers_address = getIntent().getExtras().getString("suppliers_address");
        this.etxtSuppliersName.setText(get_suppliers_name);
        this.etxtSuppliersContactPerson.setText(get_suppliers_contact_person);
        this.etxtSuppliersCell.setText(get_suppliers_cell);
        this.etxtSuppliersEmail.setText(get_suppliers_email);
        this.etxtSuppliersAddress.setText(get_suppliers_address);
        this.etxtSuppliersName.setEnabled(false);
        this.etxtSuppliersContactPerson.setEnabled(false);
        this.etxtSuppliersCell.setEnabled(false);
        this.etxtSuppliersEmail.setEnabled(false);
        this.etxtSuppliersAddress.setEnabled(false);
        this.txtUpdateSuppliers.setVisibility(View.INVISIBLE);
        this.txtEditSuppliers.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.suppliers.EditSuppliersActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditSuppliersActivity.this.etxtSuppliersName.setEnabled(true);
                EditSuppliersActivity.this.etxtSuppliersContactPerson.setEnabled(true);
                EditSuppliersActivity.this.etxtSuppliersCell.setEnabled(true);
                EditSuppliersActivity.this.etxtSuppliersEmail.setEnabled(true);
                EditSuppliersActivity.this.etxtSuppliersAddress.setEnabled(true);
//                EditSuppliersActivity.this.etxtSuppliersName.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditSuppliersActivity.this.etxtSuppliersContactPerson.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditSuppliersActivity.this.etxtSuppliersCell.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditSuppliersActivity.this.etxtSuppliersEmail.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditSuppliersActivity.this.etxtSuppliersAddress.setTextColor(SupportMenu.CATEGORY_MASK);
                EditSuppliersActivity.this.txtUpdateSuppliers.setVisibility(View.VISIBLE);
                EditSuppliersActivity.this.txtEditSuppliers.setVisibility(View.GONE);
            }
        });
        this.txtUpdateSuppliers.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.suppliers.EditSuppliersActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String suppliers_name = EditSuppliersActivity.this.etxtSuppliersName.getText().toString().trim();
                String suppliers_contact_person = EditSuppliersActivity.this.etxtSuppliersContactPerson.getText().toString().trim();
                String suppliers_cell = EditSuppliersActivity.this.etxtSuppliersCell.getText().toString().trim();
                String suppliers_email = EditSuppliersActivity.this.etxtSuppliersEmail.getText().toString().trim();
                String suppliers_address = EditSuppliersActivity.this.etxtSuppliersAddress.getText().toString().trim();
                if (suppliers_name.isEmpty()) {
                    EditSuppliersActivity.this.etxtSuppliersName.setError(EditSuppliersActivity.this.getString(R.string.enter_suppliers_name));
                    EditSuppliersActivity.this.etxtSuppliersName.requestFocus();
                } else if (suppliers_contact_person.isEmpty()) {
                    EditSuppliersActivity.this.etxtSuppliersContactPerson.setError(EditSuppliersActivity.this.getString(R.string.enter_suppliers_contact_person_name));
                    EditSuppliersActivity.this.etxtSuppliersContactPerson.requestFocus();
                } else if (suppliers_cell.isEmpty()) {
                    EditSuppliersActivity.this.etxtSuppliersCell.setError(EditSuppliersActivity.this.getString(R.string.enter_suppliers_cell));
                    EditSuppliersActivity.this.etxtSuppliersCell.requestFocus();
                } else if (suppliers_email.isEmpty() || !suppliers_email.contains("@") || !suppliers_email.contains(".")) {
                    EditSuppliersActivity.this.etxtSuppliersEmail.setError(EditSuppliersActivity.this.getString(R.string.enter_valid_email));
                    EditSuppliersActivity.this.etxtSuppliersEmail.requestFocus();
                } else if (suppliers_address.isEmpty()) {
                    EditSuppliersActivity.this.etxtSuppliersAddress.setError(EditSuppliersActivity.this.getString(R.string.enter_suppliers_address));
                    EditSuppliersActivity.this.etxtSuppliersAddress.requestFocus();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(EditSuppliersActivity.this);
                    databaseAccess.open();
                    boolean check = databaseAccess.updateSuppliers(get_suppliers_id, suppliers_name, suppliers_contact_person, suppliers_cell, suppliers_email, suppliers_address);
                    if (check) {
                        Toasty.success(EditSuppliersActivity.this, (int) R.string.update_successfully, 0).show();
                        Intent intent = new Intent(EditSuppliersActivity.this, SuppliersActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        EditSuppliersActivity.this.startActivity(intent);
                        return;
                    }
                    Toasty.error(EditSuppliersActivity.this, (int) R.string.failed, 0).show();
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
