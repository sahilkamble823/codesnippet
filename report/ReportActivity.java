package com.example.pointofsalef.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.cardview.widget.CardView;
import com.app.smartpos.R;

import com.example.pointofsalef.utils.BaseActivity;

/* loaded from: classes2.dex */
public class ReportActivity extends BaseActivity {
    CardView cardExpenseGraph;
    CardView cardExpenseReport;
    CardView cardGraphReport;
    CardView cardSalesReport;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.report);
        this.cardSalesReport = (CardView) findViewById(R.id.card_sales_report);
        this.cardGraphReport = (CardView) findViewById(R.id.card_graph_report);
        this.cardExpenseGraph = (CardView) findViewById(R.id.card_expense_graph);
        this.cardExpenseReport = (CardView) findViewById(R.id.card_expense_report);
        this.cardSalesReport.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.report.ReportActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, SalesReportActivity.class);
                ReportActivity.this.startActivity(intent);
            }
        });
        this.cardGraphReport.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.report.ReportActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, GraphReportActivity.class);
                ReportActivity.this.startActivity(intent);
            }
        });
        this.cardExpenseReport.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.report.ReportActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, ExpenseReportActivity.class);
                ReportActivity.this.startActivity(intent);
            }
        });
        this.cardExpenseGraph.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.report.ReportActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, ExpenseGraphActivity.class);
                ReportActivity.this.startActivity(intent);
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
