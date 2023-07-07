package com.example.pointofsalef.settings.order_type;

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
public class AddOrderTypeActivity extends BaseActivity {
    EditText etxtOrderType;
    TextView txtAddOrderType;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_type);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_order_type);
        this.etxtOrderType = (EditText) findViewById(R.id.etxt_order_type);
        TextView textView = (TextView) findViewById(R.id.txt_add_order_type);
        this.txtAddOrderType = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.order_type.AddOrderTypeActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String orderTypeName = AddOrderTypeActivity.this.etxtOrderType.getText().toString().trim();
                if (orderTypeName.isEmpty()) {
                    AddOrderTypeActivity.this.etxtOrderType.setError(AddOrderTypeActivity.this.getString(R.string.order_type_name));
                    AddOrderTypeActivity.this.etxtOrderType.requestFocus();
                    return;
                }
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddOrderTypeActivity.this);
                databaseAccess.open();
                boolean check = databaseAccess.addOrderType(orderTypeName);
                if (check) {
                    Toasty.success(AddOrderTypeActivity.this, (int) R.string.successfully_added, 0).show();
                    Intent intent = new Intent(AddOrderTypeActivity.this, OrderTypeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    AddOrderTypeActivity.this.startActivity(intent);
                    AddOrderTypeActivity.this.finish();
                    return;
                }
                Toasty.error(AddOrderTypeActivity.this, (int) R.string.failed, 0).show();
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
