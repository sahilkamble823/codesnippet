package com.example.pointofsalef.settings.categories;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;
import com.example.pointofsalef.adapter.CategoryAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class CategoriesActivity extends BaseActivity {
    EditText etxtSearch;
    FloatingActionButton fabAdd;
    ImageView imgNoProduct;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.categories);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.etxtSearch = (EditText) findViewById(R.id.etxt_customer_search);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> categoryData = databaseAccess.getProductCategory();
        Log.d("data", "" + categoryData.size());
        if (categoryData.size() <= 0) {
            Toasty.info(this, (int) R.string.no_data_found, 0).show();
            this.imgNoProduct.setImageResource(R.drawable.no_data);
        } else {
            this.imgNoProduct.setVisibility(View.GONE);
            CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryData);
            this.recyclerView.setAdapter(categoryAdapter);
        }
        this.fabAdd.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.settings.categories.CategoriesActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, AddCategoryActivity.class);
                CategoriesActivity.this.startActivity(intent);
            }
        });
        this.etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.settings.categories.CategoriesActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(CategoriesActivity.this);
                databaseAccess2.open();
                List<HashMap<String, String>> searchCategoryList = databaseAccess2.searchProductCategory(s.toString());
                if (searchCategoryList.size() <= 0) {
                    CategoriesActivity.this.recyclerView.setVisibility(View.GONE);
                    CategoriesActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    CategoriesActivity.this.imgNoProduct.setImageResource(R.drawable.no_data);
                    return;
                }
                CategoriesActivity.this.recyclerView.setVisibility(View.VISIBLE);
                CategoriesActivity.this.imgNoProduct.setVisibility(View.GONE);
                CategoryAdapter categoryAdapter2 = new CategoryAdapter(CategoriesActivity.this, searchCategoryList);
                CategoriesActivity.this.recyclerView.setAdapter(categoryAdapter2);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
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
