package com.example.pointofsalef.settings.order_type;

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
public class EditOrderTypeActivity extends BaseActivity {
    EditText etxtOrderTypeName;
    TextView txtEdit;
    TextView txtUpdate;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_type);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.update_order_type);
        this.txtEdit = (TextView) findViewById(R.id.txt_edit);
        this.txtUpdate = (TextView) findViewById(R.id.txt_update);
        this.etxtOrderTypeName = (EditText) findViewById(R.id.etxt_order_type);
        final String order_type_id = getIntent().getExtras().getString("order_type_id");
        String order_type_name = getIntent().getExtras().getString("order_type_name");
        this.etxtOrderTypeName.setText(order_type_name);
        this.etxtOrderTypeName.setEnabled(false);
        this.txtUpdate.setVisibility(View.INVISIBLE);
        this.txtEdit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.order_type.EditOrderTypeActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditOrderTypeActivity.this.etxtOrderTypeName.setEnabled(true);
                EditOrderTypeActivity.this.txtUpdate.setVisibility(View.VISIBLE);
//                EditOrderTypeActivity.this.etxtOrderTypeName.setTextColor(SupportMenu.CATEGORY_MASK);
            }
        });
        this.txtUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.order_type.EditOrderTypeActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String orderTypeName = EditOrderTypeActivity.this.etxtOrderTypeName.getText().toString().trim();
                if (orderTypeName.isEmpty()) {
                    EditOrderTypeActivity.this.etxtOrderTypeName.setError(EditOrderTypeActivity.this.getString(R.string.order_type_name));
                    EditOrderTypeActivity.this.etxtOrderTypeName.requestFocus();
                    return;
                }
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(EditOrderTypeActivity.this);
                databaseAccess.open();
                boolean check = databaseAccess.updateOrderType(order_type_id, orderTypeName);
                if (check) {
                    Toasty.success(EditOrderTypeActivity.this, (int) R.string.successfully_updated, 0).show();
                    Intent intent = new Intent(EditOrderTypeActivity.this, OrderTypeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    EditOrderTypeActivity.this.startActivity(intent);
                    EditOrderTypeActivity.this.finish();
                    return;
                }
                Toasty.error(EditOrderTypeActivity.this, (int) R.string.failed, 0).show();
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
