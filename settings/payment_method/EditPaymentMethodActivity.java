package com.example.pointofsalef.settings.payment_method;

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
public class EditPaymentMethodActivity extends BaseActivity {
    EditText etxtPaymentMethodName;
    TextView txtEdit;
    TextView txtUpdatePaymentMethod;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment_method);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.update_payment_method);
        this.txtEdit = (TextView) findViewById(R.id.txt_edit);
        this.txtUpdatePaymentMethod = (TextView) findViewById(R.id.txt_update_payment_method);
        this.etxtPaymentMethodName = (EditText) findViewById(R.id.etxt_payment_method_name);
        final String payment_method_id = getIntent().getExtras().getString("payment_method_id");
        String payment_method_name = getIntent().getExtras().getString("payment_method_name");
        this.etxtPaymentMethodName.setText(payment_method_name);
        this.etxtPaymentMethodName.setEnabled(false);
        this.txtUpdatePaymentMethod.setVisibility(View.INVISIBLE);
        this.txtEdit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.payment_method.EditPaymentMethodActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditPaymentMethodActivity.this.etxtPaymentMethodName.setEnabled(true);
                EditPaymentMethodActivity.this.txtUpdatePaymentMethod.setVisibility(View.VISIBLE);
//                EditPaymentMethodActivity.this.etxtPaymentMethodName.setTextColor(SupportMenu.CATEGORY_MASK);
            }
        });
        this.txtUpdatePaymentMethod.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.payment_method.EditPaymentMethodActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String payment_method_name2 = EditPaymentMethodActivity.this.etxtPaymentMethodName.getText().toString().trim();
                if (payment_method_name2.isEmpty()) {
                    EditPaymentMethodActivity.this.etxtPaymentMethodName.setError(EditPaymentMethodActivity.this.getString(R.string.payment_method_name));
                    EditPaymentMethodActivity.this.etxtPaymentMethodName.requestFocus();
                    return;
                }
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(EditPaymentMethodActivity.this);
                databaseAccess.open();
                boolean check = databaseAccess.updatePaymentMethod(payment_method_id, payment_method_name2);
                if (check) {
                    Toasty.success(EditPaymentMethodActivity.this, (int) R.string.successfully_updated, 0).show();
                    Intent intent = new Intent(EditPaymentMethodActivity.this, PaymentMethodActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    EditPaymentMethodActivity.this.startActivity(intent);
                    return;
                }
                Toasty.error(EditPaymentMethodActivity.this, (int) R.string.failed, 0).show();
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
