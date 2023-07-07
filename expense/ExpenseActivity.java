package com.example.pointofsalef.expense;

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
import com.example.pointofsalef.adapter.ExpenseAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ExpenseActivity extends BaseActivity {
    EditText etxtSearch;
    FloatingActionButton fabAdd;
    ImageView imgNoProduct;
    ExpenseAdapter productAdapter;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        this.etxtSearch = (EditText) findViewById(R.id.etxt_search);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_expense);
        this.recyclerView = (RecyclerView) findViewById(R.id.product_recyclerview);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.fabAdd.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.expense.ExpenseActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseActivity.this, AddExpenseActivity.class);
                ExpenseActivity.this.startActivity(intent);
            }
        });
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> productData = databaseAccess.getAllExpense();
        Log.d("data", "" + productData.size());
        if (productData.size() <= 0) {
            Toasty.info(this, (int) R.string.no_data_found, 0).show();
            this.imgNoProduct.setImageResource(R.drawable.no_data);
        } else {
            this.imgNoProduct.setVisibility(View.GONE);
            ExpenseAdapter expenseAdapter = new ExpenseAdapter(this, productData);
            this.productAdapter = expenseAdapter;
            this.recyclerView.setAdapter(expenseAdapter);
        }
        this.etxtSearch.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.expense.ExpenseActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(ExpenseActivity.this);
                databaseAccess2.open();
                List<HashMap<String, String>> searchExpenseList = databaseAccess2.searchExpense(s.toString());
                if (searchExpenseList.size() <= 0) {
                    ExpenseActivity.this.recyclerView.setVisibility(View.GONE);
                    ExpenseActivity.this.imgNoProduct.setVisibility(View.VISIBLE);
                    ExpenseActivity.this.imgNoProduct.setImageResource(R.drawable.no_data);
                    return;
                }
                ExpenseActivity.this.recyclerView.setVisibility(View.VISIBLE);
                ExpenseActivity.this.imgNoProduct.setVisibility(View.GONE);
                ExpenseActivity.this.productAdapter = new ExpenseAdapter(ExpenseActivity.this, searchExpenseList);
                ExpenseActivity.this.recyclerView.setAdapter(ExpenseActivity.this.productAdapter);
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
