package com.example.pointofsalef.customers;

import android.annotation.SuppressLint;
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
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;
//import com.itextpdf.io.PagedChannelRandomAccessSource;
import es.dmoral.toasty.Toasty;

/* loaded from: classes2.dex */
public class EditCustomersActivity extends BaseActivity {
    EditText etxtAddress;
    EditText etxtCustomerCell;
    EditText etxtCustomerEmail;
    EditText etxtCustomerName;
    String get_customer_address;
    String get_customer_cell;
    String get_customer_email;
    String get_customer_id;
    String get_customer_name;
    TextView txtEditCustomer;
    TextView txtUpdateInformation;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customers);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.edit_customer);
        this.etxtCustomerName = (EditText) findViewById(R.id.etxt_customer_name);
        this.etxtCustomerCell = (EditText) findViewById(R.id.etxt_customer_cell);
        this.etxtCustomerEmail = (EditText) findViewById(R.id.etxt_email);
        this.etxtAddress = (EditText) findViewById(R.id.etxt_address);
        this.txtEditCustomer = (TextView) findViewById(R.id.txt_edit_customer);
        this.txtUpdateInformation = (TextView) findViewById(R.id.txt_update_customer);
        this.get_customer_id = getIntent().getExtras().getString("customer_id");
        this.get_customer_name = getIntent().getExtras().getString("customer_name");
        this.get_customer_cell = getIntent().getExtras().getString("customer_cell");
        this.get_customer_email = getIntent().getExtras().getString("customer_email");
        this.get_customer_address = getIntent().getExtras().getString("customer_address");
        this.etxtCustomerName.setText(this.get_customer_name);
        this.etxtCustomerCell.setText(this.get_customer_cell);
        this.etxtCustomerEmail.setText(this.get_customer_email);
        this.etxtAddress.setText(this.get_customer_address);
        this.etxtCustomerName.setEnabled(false);
        this.etxtCustomerCell.setEnabled(false);
        this.etxtCustomerEmail.setEnabled(false);
        this.etxtAddress.setEnabled(false);
        this.txtUpdateInformation.setVisibility(View.INVISIBLE);
        this.txtEditCustomer.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.customers.EditCustomersActivity.1
            @SuppressLint("RestrictedApi")
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditCustomersActivity.this.etxtCustomerName.setEnabled(true);
                EditCustomersActivity.this.etxtCustomerCell.setEnabled(true);
                EditCustomersActivity.this.etxtCustomerEmail.setEnabled(true);
                EditCustomersActivity.this.etxtAddress.setEnabled(true);
                EditCustomersActivity.this.etxtCustomerName.setTextColor(SupportMenu.CATEGORY_MASK);
                EditCustomersActivity.this.etxtCustomerCell.setTextColor(SupportMenu.CATEGORY_MASK);
                EditCustomersActivity.this.etxtCustomerEmail.setTextColor(SupportMenu.CATEGORY_MASK);
                EditCustomersActivity.this.etxtAddress.setTextColor(SupportMenu.CATEGORY_MASK);
                EditCustomersActivity.this.txtUpdateInformation.setVisibility(View.VISIBLE);
            }
        });
        this.txtUpdateInformation.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.customers.EditCustomersActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String customer_name = EditCustomersActivity.this.etxtCustomerName.getText().toString().trim();
                String customer_cell = EditCustomersActivity.this.etxtCustomerCell.getText().toString().trim();
                String customer_email = EditCustomersActivity.this.etxtCustomerEmail.getText().toString().trim();
                String customer_address = EditCustomersActivity.this.etxtAddress.getText().toString().trim();
                if (customer_name.isEmpty()) {
                    EditCustomersActivity.this.etxtCustomerName.setError(EditCustomersActivity.this.getString(R.string.enter_customer_name));
                    EditCustomersActivity.this.etxtCustomerName.requestFocus();
                } else if (customer_cell.isEmpty()) {
                    EditCustomersActivity.this.etxtCustomerCell.setError(EditCustomersActivity.this.getString(R.string.enter_customer_cell));
                    EditCustomersActivity.this.etxtCustomerCell.requestFocus();
                } else if (customer_email.isEmpty() || !customer_email.contains("@") || !customer_email.contains(".")) {
                    EditCustomersActivity.this.etxtCustomerEmail.setError(EditCustomersActivity.this.getString(R.string.enter_valid_email));
                    EditCustomersActivity.this.etxtCustomerEmail.requestFocus();
                } else if (customer_address.isEmpty()) {
                    EditCustomersActivity.this.etxtAddress.setError(EditCustomersActivity.this.getString(R.string.enter_customer_address));
                    EditCustomersActivity.this.etxtAddress.requestFocus();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(EditCustomersActivity.this);
                    databaseAccess.open();
                    boolean check = databaseAccess.updateCustomer(EditCustomersActivity.this.get_customer_id, customer_name, customer_cell, customer_email, customer_address);
                    if (check) {
                        Toasty.success(EditCustomersActivity.this, (int) R.string.update_successfully, 0).show();
                        Intent intent = new Intent(EditCustomersActivity.this, CustomersActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        EditCustomersActivity.this.startActivity(intent);
                        return;
                    }
                    Toasty.error(EditCustomersActivity.this, (int) R.string.failed, 0).show();
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
