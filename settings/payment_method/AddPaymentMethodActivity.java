package com.example.pointofsalef.settings.payment_method;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;

import es.dmoral.toasty.Toasty;

/* loaded from: classes2.dex */
public class AddPaymentMethodActivity extends BaseActivity {
    EditText etxtPaymentMethod;
    TextView txtAddPaymentMethod;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_method);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_payment_method);
        this.etxtPaymentMethod = (EditText) findViewById(R.id.etxt_payment_method_name);
        TextView textView = (TextView) findViewById(R.id.txt_add_payment_method);
        this.txtAddPaymentMethod = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.payment_method.AddPaymentMethodActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String payment_method_name = AddPaymentMethodActivity.this.etxtPaymentMethod.getText().toString().trim();
                if (payment_method_name.isEmpty()) {
                    AddPaymentMethodActivity.this.etxtPaymentMethod.setError(AddPaymentMethodActivity.this.getString(R.string.enter_payment_method_name));
                    AddPaymentMethodActivity.this.etxtPaymentMethod.requestFocus();
                    return;
                }
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddPaymentMethodActivity.this);
                databaseAccess.open();
                boolean check = databaseAccess.addPaymentMethod(payment_method_name);
                if (check) {
                    Toasty.success(AddPaymentMethodActivity.this, (int) R.string.successfully_added, 0).show();
                    Intent intent = new Intent(AddPaymentMethodActivity.this, PaymentMethodActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    AddPaymentMethodActivity.this.startActivity(intent);
                    AddPaymentMethodActivity.this.finish();
                    return;
                }
                Toasty.error(AddPaymentMethodActivity.this, (int) R.string.failed, 0).show();
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
