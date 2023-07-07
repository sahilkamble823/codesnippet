package com.example.pointofsalef.settings.categories;

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
public class EditCategoryActivity extends BaseActivity {
    EditText etxtCategoryName;
    TextView txtEdit;
    TextView txtUpdateCategory;

    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.edit_category);
        this.txtEdit = (TextView) findViewById(R.id.txt_edit_category);
        this.txtUpdateCategory = (TextView) findViewById(R.id.txt_update_category);
        this.etxtCategoryName = (EditText) findViewById(R.id.etxt_category_name);
        final String category_id = getIntent().getExtras().getString("category_id");
        String category_name = getIntent().getExtras().getString("category_name");
        this.etxtCategoryName.setText(category_name);
        this.etxtCategoryName.setEnabled(false);
        this.txtUpdateCategory.setVisibility(View.INVISIBLE);
        this.txtEdit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.categories.EditCategoryActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditCategoryActivity.this.etxtCategoryName.setEnabled(true);
                EditCategoryActivity.this.txtUpdateCategory.setVisibility(View.VISIBLE);
//               0 EditCategoryActivity.this.etxtCategoryName.setTextColor(SupportMenu.CATEGORY_MASK);
            }
        });
        this.txtUpdateCategory.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.categories.EditCategoryActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String category_name2 = EditCategoryActivity.this.etxtCategoryName.getText().toString().trim();
                if (category_name2.isEmpty()) {
                    EditCategoryActivity.this.etxtCategoryName.setError(EditCategoryActivity.this.getString(R.string.enter_category_name));
                    EditCategoryActivity.this.etxtCategoryName.requestFocus();
                    return;
                }
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(EditCategoryActivity.this);
                databaseAccess.open();
                boolean check = databaseAccess.updateCategory(category_id, category_name2);
                if (check) {
                    Toasty.success(EditCategoryActivity.this, (int) R.string.category_updated, 0).show();
                    Intent intent = new Intent(EditCategoryActivity.this, CategoriesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    EditCategoryActivity.this.startActivity(intent);
                    return;
                }
                Toasty.error(EditCategoryActivity.this, (int) R.string.failed, 0).show();
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
