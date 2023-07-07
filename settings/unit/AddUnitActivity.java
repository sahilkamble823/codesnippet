package com.example.pointofsalef.settings.unit;

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
public class AddUnitActivity extends BaseActivity {
    EditText etxtUnit;
    TextView txtAddUnit;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_unit);
        this.etxtUnit = (EditText) findViewById(R.id.etxt_unit);
        TextView textView = (TextView) findViewById(R.id.txt_add_unit);
        this.txtAddUnit = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.unit.AddUnitActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String unitName = AddUnitActivity.this.etxtUnit.getText().toString().trim();
                if (unitName.isEmpty()) {
                    AddUnitActivity.this.etxtUnit.setError(AddUnitActivity.this.getString(R.string.add_unit));
                    AddUnitActivity.this.etxtUnit.requestFocus();
                    return;
                }
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddUnitActivity.this);
                databaseAccess.open();
                boolean check = databaseAccess.addUnit(unitName);
                if (check) {
                    Toasty.success(AddUnitActivity.this, (int) R.string.successfully_added, 0).show();
                    Intent intent = new Intent(AddUnitActivity.this, UnitActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    AddUnitActivity.this.startActivity(intent);
                    AddUnitActivity.this.finish();
                    return;
                }
                Toasty.error(AddUnitActivity.this, (int) R.string.failed, 0).show();
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
