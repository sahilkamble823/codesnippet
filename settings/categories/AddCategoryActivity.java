package com.example.pointofsalef.settings.categories;

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
public class AddCategoryActivity extends BaseActivity {
    EditText etxtCategoryName;
    TextView txtAddCategory;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_category);
        this.etxtCategoryName = (EditText) findViewById(R.id.etxt_category_name);
        TextView textView = (TextView) findViewById(R.id.txt_add_category);
        this.txtAddCategory = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.categories.AddCategoryActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String category_name = AddCategoryActivity.this.etxtCategoryName.getText().toString().trim();
                if (category_name.isEmpty()) {
                    AddCategoryActivity.this.etxtCategoryName.setError(AddCategoryActivity.this.getString(R.string.enter_category_name));
                    AddCategoryActivity.this.etxtCategoryName.requestFocus();
                    return;
                }
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddCategoryActivity.this);
                databaseAccess.open();
                boolean check = databaseAccess.addCategory(category_name);
                if (check) {
                    Toasty.success(AddCategoryActivity.this, (int) R.string.category_added_successfully, 0).show();
                    Intent intent = new Intent(AddCategoryActivity.this, CategoriesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    AddCategoryActivity.this.startActivity(intent);
                    return;
                }
                Toasty.error(AddCategoryActivity.this, (int) R.string.failed, 0).show();
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
