package com.example.pointofsalef.settings.unit;

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
public class EditUnitActivity extends BaseActivity {
    EditText etxtWeightUnit;
    TextView txtEdit;
    TextView txtUpdate;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_unit);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.update_unit);
        this.txtEdit = (TextView) findViewById(R.id.txt_edit);
        this.txtUpdate = (TextView) findViewById(R.id.txt_update);
        this.etxtWeightUnit = (EditText) findViewById(R.id.etxt_weight_unit);
        final String weightId = getIntent().getExtras().getString("weight_id");
        String weightUnit = getIntent().getExtras().getString("weight_unit");
        this.etxtWeightUnit.setText(weightUnit);
        this.etxtWeightUnit.setEnabled(false);
        this.txtUpdate.setVisibility(View.INVISIBLE);
        this.txtEdit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.unit.EditUnitActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditUnitActivity.this.etxtWeightUnit.setEnabled(true);
                EditUnitActivity.this.txtUpdate.setVisibility(View.VISIBLE);
//                EditUnitActivity.this.etxtWeightUnit.setTextColor(SupportMenu.CATEGORY_MASK);
            }
        });
        this.txtUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.unit.EditUnitActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String weightUnit2 = EditUnitActivity.this.etxtWeightUnit.getText().toString().trim();
                if (weightUnit2.isEmpty()) {
                    EditUnitActivity.this.etxtWeightUnit.setError(EditUnitActivity.this.getString(R.string.weight_unit_name));
                    EditUnitActivity.this.etxtWeightUnit.requestFocus();
                    return;
                }
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(EditUnitActivity.this);
                databaseAccess.open();
                boolean check = databaseAccess.updateWeightUnit(weightId, weightUnit2);
                if (check) {
                    Toasty.success(EditUnitActivity.this, (int) R.string.successfully_updated, 0).show();
                    Intent intent = new Intent(EditUnitActivity.this, UnitActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    EditUnitActivity.this.startActivity(intent);
                    EditUnitActivity.this.finish();
                    return;
                }
                Toasty.error(EditUnitActivity.this, (int) R.string.failed, 0).show();
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
