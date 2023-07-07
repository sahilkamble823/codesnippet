package com.example.pointofsalef.report;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class GraphReportActivity extends BaseActivity {
    BarChart barChart;
    DecimalFormat f;
    LinearLayout layoutYear;
    int mYear;
    TextView txtNetSales;
    TextView txtSelectYear;
    TextView txtTotalDiscount;
    TextView txtTotalSales;
    TextView txtTotalTax;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_report);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.monthly_sales_graph);
        this.layoutYear = (LinearLayout) findViewById(R.id.layout_year);
        this.f = new DecimalFormat("#0.00");
        this.barChart = (BarChart) findViewById(R.id.barchart);
        this.txtTotalSales = (TextView) findViewById(R.id.txt_total_sales);
        this.txtSelectYear = (TextView) findViewById(R.id.txt_select_year);
        this.txtTotalTax = (TextView) findViewById(R.id.txt_total_tax);
        this.txtTotalDiscount = (TextView) findViewById(R.id.txt_discount);
        this.txtNetSales = (TextView) findViewById(R.id.txt_net_sales);
        this.barChart.setDrawBarShadow(false);
        this.barChart.setDrawValueAboveBar(true);
        this.barChart.setMaxVisibleValueCount(50);
        this.barChart.setPinchZoom(false);
        this.barChart.setDrawGridBackground(true);
        String currentYear = new SimpleDateFormat("yyyy", Locale.ENGLISH).format(new Date());
        this.txtSelectYear.setText(getString(R.string.year) + " " + currentYear);
        int parseInt = Integer.parseInt(currentYear);
        this.mYear = parseInt;
        getGraphData(parseInt);
        this.layoutYear.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.report.GraphReportActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                GraphReportActivity.this.chooseYearOnly();
            }
        });
    }

    public void getGraphData(int mYear) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        String[] monthNumber = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            databaseAccess.open();
            barEntries.add(new BarEntry(i, databaseAccess.getMonthlySalesAmount(monthNumber[i], "" + mYear)));
        }
        String[] monthList = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        XAxis xAxis = this.barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthList));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1.0f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(12);
        BarDataSet barDataSet = new BarDataSet(barEntries, getString(R.string.monthly_sales_report));
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);
        this.barChart.setData(barData);
        this.barChart.setScaleEnabled(false);
        this.barChart.notifyDataSetChanged();
        this.barChart.invalidate();
        databaseAccess.open();
        String currency = databaseAccess.getCurrency();
        databaseAccess.open();
        double sub_total = databaseAccess.getTotalOrderPriceForGraph("yearly", mYear);
        this.txtTotalSales.setText(getString(R.string.total_sales) + currency + this.f.format(sub_total));
        databaseAccess.open();
        double get_tax = databaseAccess.getTotalTaxForGraph("yearly", mYear);
        this.txtTotalTax.setText(getString(R.string.total_tax) + "(+) : " + currency + this.f.format(get_tax));
        databaseAccess.open();
        double get_discount = databaseAccess.getTotalDiscountForGraph("yearly", mYear);
        this.txtTotalDiscount.setText(getString(R.string.total_discount) + "(-) : " + currency + this.f.format(get_discount));
        double net_sales = (sub_total + get_tax) - get_discount;
        this.txtNetSales.setText(getString(R.string.net_sales) + ": " + currency + this.f.format(net_sales));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseYearOnly() {
        findViewById(R.id.txt_select_year).setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.report.GraphReportActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(GraphReportActivity.this, new MonthPickerDialog.OnDateSetListener() { // from class: com.app.smartpos.report.GraphReportActivity.2.1
                    @Override // com.whiteelephant.monthpicker.MonthPickerDialog.OnDateSetListener
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        GraphReportActivity.this.txtSelectYear.setText(GraphReportActivity.this.getString(R.string.year) + " " + selectedYear);
                        GraphReportActivity.this.mYear = selectedYear;
                        GraphReportActivity.this.getGraphData(GraphReportActivity.this.mYear);
                    }
                }, GraphReportActivity.this.mYear, 0);
                builder.showYearOnly().setTitle(GraphReportActivity.this.getString(R.string.select_year)).build().show();
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
