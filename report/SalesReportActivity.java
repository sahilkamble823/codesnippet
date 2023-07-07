package com.example.pointofsalef.report;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.app.smartpos.R;

import com.example.pointofsalef.adapter.SalesReportAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.database.DatabaseOpenHelper;
import com.example.pointofsalef.utils.BaseActivity;
import com.obsez.android.lib.filechooser.ChooserDialog;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class SalesReportActivity extends BaseActivity {
    DecimalFormat f;
    ImageView imgNoProduct;
    ProgressDialog loading;
    private SalesReportAdapter orderDetailsAdapter;
    List<HashMap<String, String>> orderDetailsList;
    private RecyclerView recyclerView;
    TextView txtNetSales;
    TextView txtNoProducts;
    TextView txtTotalDiscount;
    TextView txtTotalPrice;
    TextView txtTotalTax;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.txtNoProducts = (TextView) findViewById(R.id.txt_no_products);
        this.txtTotalPrice = (TextView) findViewById(R.id.txt_total_price);
        this.txtTotalTax = (TextView) findViewById(R.id.txt_total_tax);
        this.txtTotalDiscount = (TextView) findViewById(R.id.txt_total_discount);
        this.txtNetSales = (TextView) findViewById(R.id.txt_net_sales);
        this.f = new DecimalFormat("#0.00");
        this.imgNoProduct.setVisibility(View.GONE);
        this.txtNoProducts.setVisibility(View.GONE);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.all_sales);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        ArrayList<HashMap<String, String>> allSalesItems = databaseAccess.getAllSalesItems();
        this.orderDetailsList = allSalesItems;
        if (allSalesItems.size() <= 0) {
            Toasty.info(this, (int) R.string.no_data_found, 0).show();
            this.recyclerView.setVisibility(View.GONE);
            this.imgNoProduct.setVisibility(View.VISIBLE);
            this.imgNoProduct.setImageResource(R.drawable.not_found);
            this.txtNoProducts.setVisibility(View.VISIBLE);
            this.txtTotalPrice.setVisibility(View.GONE);
        } else {
            SalesReportAdapter salesReportAdapter = new SalesReportAdapter(this, this.orderDetailsList);
            this.orderDetailsAdapter = salesReportAdapter;
            this.recyclerView.setAdapter(salesReportAdapter);
        }
        databaseAccess.open();
        String currency = databaseAccess.getCurrency();
        databaseAccess.open();
        double sub_total = databaseAccess.getTotalOrderPrice("all");
        this.txtTotalPrice.setText(getString(R.string.total_sales) + currency + this.f.format(sub_total));
        databaseAccess.open();
        double get_tax = databaseAccess.getTotalTax("all");
        this.txtTotalTax.setText(getString(R.string.total_tax) + "(+) : " + currency + this.f.format(get_tax));
        databaseAccess.open();
        double get_discount = databaseAccess.getTotalDiscount("all");
        this.txtTotalDiscount.setText(getString(R.string.tatal_discount) + "(-) : " + currency + this.f.format(get_discount));
        double net_sales = (sub_total + get_tax) - get_discount;
        this.txtNetSales.setText(getString(R.string.net_sales) + ": " + currency + this.f.format(net_sales));
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_sales_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.menu_all_sales /* 2131296646 */:
                getReport("all");
                return true;
            case R.id.menu_daily /* 2131296648 */:
                getReport("daily");
                return true;
            case R.id.menu_export_data /* 2131296651 */:
                folderChooser();
                return true;
            case R.id.menu_monthly /* 2131296655 */:
                getReport("monthly");
                return true;
            case R.id.menu_yearly /* 2131296656 */:
                getReport("yearly");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getReport(String type) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Log.d("TYPE", type);
        ArrayList<HashMap<String, String>> salesReport = databaseAccess.getSalesReport(type);
        this.orderDetailsList = salesReport;
        if (salesReport.size() <= 0) {
            Toasty.info(this, (int) R.string.no_data_found, 0).show();
            this.recyclerView.setVisibility(View.GONE);
            this.imgNoProduct.setVisibility(View.VISIBLE);
            this.imgNoProduct.setImageResource(R.drawable.not_found);
            this.txtNoProducts.setVisibility(View.VISIBLE);
            this.txtTotalPrice.setVisibility(View.GONE);
        } else {
            SalesReportAdapter salesReportAdapter = new SalesReportAdapter(this, this.orderDetailsList);
            this.orderDetailsAdapter = salesReportAdapter;
            this.recyclerView.setAdapter(salesReportAdapter);
            this.recyclerView.setVisibility(View.VISIBLE);
            this.imgNoProduct.setVisibility(View.GONE);
            this.txtNoProducts.setVisibility(View.GONE);
            this.txtTotalPrice.setVisibility(View.VISIBLE);
        }
        databaseAccess.open();
        String currency = databaseAccess.getCurrency();
        databaseAccess.open();
        double sub_total = databaseAccess.getTotalOrderPrice(type);
        this.txtTotalPrice.setText(getString(R.string.total_sales) + currency + this.f.format(sub_total));
        databaseAccess.open();
        double get_tax = databaseAccess.getTotalTax(type);
        this.txtTotalTax.setText(getString(R.string.total_tax) + "(+) : " + currency + this.f.format(get_tax));
        databaseAccess.open();
        double get_discount = databaseAccess.getTotalDiscount(type);
        this.txtTotalDiscount.setText(getString(R.string.total_discount) + "(-) : " + currency + this.f.format(get_discount));
        double net_sales = (sub_total + get_tax) - get_discount;
        this.txtNetSales.setText(getString(R.string.net_sales) + ": " + currency + this.f.format(net_sales));
    }

    public void folderChooser() {
        new ChooserDialog((Activity) this).displayPath(true).withFilter(true, false, new String[0]).withChosenListener(new ChooserDialog.Result() { // from class: com.app.smartpos.report.SalesReportActivity.1
            @Override // com.obsez.android.lib.filechooser.ChooserDialog.Result
            public void onChoosePath(String path, File pathFile) {
                SalesReportActivity.this.onExport(path);
                Log.d("path", path);
            }
        }).build().show();
    }

    public void onExport(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DatabaseOpenHelper.DATABASE_NAME, path);
        sqliteToExcel.exportSingleTable("order_details", "order_details.xls", new SQLiteToExcel.ExportListener() { // from class: com.app.smartpos.report.SalesReportActivity.2
            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onStart() {
                SalesReportActivity.this.loading = new ProgressDialog(SalesReportActivity.this);
                SalesReportActivity.this.loading.setMessage(SalesReportActivity.this.getString(R.string.data_exporting_please_wait));
                SalesReportActivity.this.loading.setCancelable(false);
                SalesReportActivity.this.loading.show();
            }

            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onCompleted(String filePath) {
                Handler mHand = new Handler();
                mHand.postDelayed(new Runnable() { // from class: com.app.smartpos.report.SalesReportActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SalesReportActivity.this.loading.dismiss();
                        Toasty.success(SalesReportActivity.this, (int) R.string.data_successfully_exported, 0).show();
                    }
                }, 5000L);
            }

            @Override // com.ajts.androidmads.library.SQLiteToExcel.ExportListener
            public void onError(Exception e) {
                SalesReportActivity.this.loading.dismiss();
                Toasty.error(SalesReportActivity.this, (int) R.string.data_export_fail, 0).show();
            }
        });
    }
}
