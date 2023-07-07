package com.example.pointofsalef.orders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;
import com.example.pointofsalef.adapter.OrderDetailsAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.pdf_report.BarCodeEncoder;
import com.example.pointofsalef.pdf_report.TemplatePDF;
import com.example.pointofsalef.utils.BaseActivity;
import com.example.pointofsalef.utils.IPrintToPrinter;
import com.example.pointofsalef.utils.PrefMng;
import com.example.pointofsalef.utils.Tools;
import com.example.pointofsalef.utils.WoosimPrnMng;
import com.example.pointofsalef.utils.printerFactory;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import es.dmoral.toasty.Toasty;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class OrderDetailsActivity extends BaseActivity {
    private static final int REQUEST_CONNECT = 100;
    Button btnPdfReceipt;
    Button btnThermalPrinter;
    double calculated_total_price;
    String currency;
    String customer_name;
    String discount;
    DecimalFormat f;
    double getDiscount;
    double getTax;
    ImageView imgNoProduct;
    String longText;
    private OrderDetailsAdapter orderDetailsAdapter;
    String order_date;
    String order_id;
    String order_time;
    private RecyclerView recyclerView;
    String shop_address;
    String shop_contact;
    String shop_email;
    String shop_name;
    String shortText;
    String tax;
    private TemplatePDF templatePDF;
    double total_price;
    TextView txtDiscount;
    TextView txtNoProducts;
    TextView txtTax;
    TextView txtTotalCost;
    TextView txtTotalPrice;
    private String[] header = {"Description", "Price"};
    Bitmap bm = null;
    private WoosimPrnMng mPrnMng = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.txtTotalPrice = (TextView) findViewById(R.id.txt_total_price);
        this.txtTax = (TextView) findViewById(R.id.txt_tax);
        this.txtDiscount = (TextView) findViewById(R.id.txt_discount);
        this.txtTotalCost = (TextView) findViewById(R.id.txt_total_cost);
        this.btnPdfReceipt = (Button) findViewById(R.id.btn_pdf_receipt);
        this.btnThermalPrinter = (Button) findViewById(R.id.btn_thermal_printer);
        this.f = new DecimalFormat("#0.00");
        this.txtNoProducts = (TextView) findViewById(R.id.txt_no_products);
        this.order_id = getIntent().getExtras().getString("order_id");
        this.order_date = getIntent().getExtras().getString("order_date");
        this.order_time = getIntent().getExtras().getString("order_time");
        this.customer_name = getIntent().getExtras().getString("customer_name");
        this.tax = getIntent().getExtras().getString("tax");
        this.discount = getIntent().getExtras().getString("discount");
        this.imgNoProduct.setVisibility(View.GONE);
        this.txtNoProducts.setVisibility(View.GONE);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.order_details);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> orderDetailsList = databaseAccess.getOrderDetailsList(this.order_id);
        if (orderDetailsList.isEmpty()) {
            Toasty.info(this, (int) R.string.no_data_found, 0).show();
        } else {
            OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(this, orderDetailsList);
            this.orderDetailsAdapter = orderDetailsAdapter;
            this.recyclerView.setAdapter(orderDetailsAdapter);
        }
        databaseAccess.open();
        List<HashMap<String, String>> shopData = databaseAccess.getShopInformation();
        this.shop_name = shopData.get(0).get("shop_name");
        this.shop_contact = shopData.get(0).get("shop_contact");
        this.shop_email = shopData.get(0).get("shop_email");
        this.shop_address = shopData.get(0).get("shop_address");
        this.currency = shopData.get(0).get("shop_currency");
        databaseAccess.open();
        this.total_price = databaseAccess.totalOrderPrice(this.order_id);
        this.getTax = Double.parseDouble(this.tax);
        this.getDiscount = Double.parseDouble(this.discount);
        this.txtTax.setText(getString(R.string.total_tax) + " : " + this.currency + this.f.format(this.getTax));
        this.txtDiscount.setText(getString(R.string.discount) + " : " + this.currency + this.f.format(this.getDiscount));
        this.calculated_total_price = (this.total_price + this.getTax) - this.getDiscount;
        this.txtTotalPrice.setText(getString(R.string.sub_total) + this.currency + this.f.format(this.total_price));
        this.txtTotalCost.setText(getString(R.string.total_price) + this.currency + this.f.format(this.calculated_total_price));
        this.shortText = "Customer Name: Mr/Mrs. " + this.customer_name;
        this.longText = "Thanks for purchase. Visit again";
        TemplatePDF templatePDF = new TemplatePDF(getApplicationContext());
        this.templatePDF = templatePDF;
        templatePDF.openDocument();
        this.templatePDF.addMetaData("Order Receipt", "Order Receipt", "Smart POS");
        this.templatePDF.addTitle(this.shop_name, this.shop_address + "\n Email: " + this.shop_email + "\nContact: " + this.shop_contact + "\nInvoice ID:" + this.order_id, this.order_time + " " + this.order_date);
        this.templatePDF.addParagraph(this.shortText);
        BarCodeEncoder qrCodeEncoder = new BarCodeEncoder();
        try {
            this.bm = qrCodeEncoder.encodeAsBitmap(this.order_id, BarcodeFormat.CODE_128, 600, 300);
        } catch (WriterException e) {
            Log.d("Data", e.toString());
        }
        this.btnPdfReceipt.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.orders.OrderDetailsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OrderDetailsActivity.this.m48lambda$onCreate$0$comappsmartposordersOrderDetailsActivity(view);
            }
        });
        this.btnThermalPrinter.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.orders.OrderDetailsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OrderDetailsActivity.this.m49lambda$onCreate$1$comappsmartposordersOrderDetailsActivity(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$0$com-app-smartpos-orders-OrderDetailsActivity  reason: not valid java name */
    public /* synthetic */ void m48lambda$onCreate$0$comappsmartposordersOrderDetailsActivity(View v) {
        this.templatePDF.createTable(this.header, getOrdersData());
        this.templatePDF.addRightParagraph(this.longText);
        this.templatePDF.addImage(this.bm);
        this.templatePDF.closeDocument();
        this.templatePDF.viewPDF();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$1$com-app-smartpos-orders-OrderDetailsActivity  reason: not valid java name */
    public /* synthetic */ void m49lambda$onCreate$1$comappsmartposordersOrderDetailsActivity(View v) {
        if (Tools.isBlueToothOn(this)) {
            PrefMng.saveActivePrinter(this, 1);
            Intent i = new Intent(this, DeviceListActivity.class);
            startActivityForResult(i, 100);
        }
    }

    private ArrayList<String[]> getOrdersData() {
        ArrayList<String[]> rows = new ArrayList<>();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> orderDetailsList = databaseAccess.getOrderDetailsList(this.order_id);
        for (int i = 0; i < orderDetailsList.size(); i++) {
            String name = orderDetailsList.get(i).get("product_name");
            String price = orderDetailsList.get(i).get("product_price");
            String qty = orderDetailsList.get(i).get("product_qty");
            String weight = orderDetailsList.get(i).get("product_weight");
            double parseInt = Integer.parseInt(qty);
            double parseDouble = Double.parseDouble(price);
            Double.isNaN(parseInt);
            double cost_total = parseInt * parseDouble;
            rows.add(new String[]{name + "\n" + weight + "\n(" + qty + "x" + this.currency + price + ")", this.currency + this.f.format(cost_total)});
        }
        rows.add(new String[]{"..........................................", ".................................."});
        rows.add(new String[]{"Sub Total: ", this.currency + this.f.format(this.total_price)});
        rows.add(new String[]{"Total Tax: ", this.currency + this.f.format(this.getTax)});
        rows.add(new String[]{"Discount: ", this.currency + this.f.format(this.getDiscount)});
        rows.add(new String[]{"..........................................", ".................................."});
        rows.add(new String[]{"Total Price: ", this.currency + this.f.format(this.calculated_total_price)});
        return rows;
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        OrderDetailsActivity orderDetailsActivity;
        if (requestCode == 100 && resultCode == -1) {
            try {
                String blutoothAddr = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                String str = this.shop_name;
                String str2 = this.shop_address;
                String str3 = this.shop_email;
                String str4 = this.shop_contact;
                String str5 = this.order_id;
                String str6 = this.order_date;
                String str7 = this.order_time;
                String str8 = this.shortText;
                String str9 = this.longText;
                double d = this.total_price;
                double d2 = this.calculated_total_price;
                String blutoothAddr2 = this.tax;
                try {
                    IPrintToPrinter testPrinter = new TestPrinter(this, str, str2, str3, str4, str5, str6, str7, str8, str9, d, d2, blutoothAddr2, this.discount, this.currency);
                    orderDetailsActivity = this;
                    try {
                        orderDetailsActivity.mPrnMng = printerFactory.createPrnMng(orderDetailsActivity, blutoothAddr, testPrinter);
                    } catch (Exception e) {
                        e = e;
                        Toast.makeText(orderDetailsActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e = e;
                    orderDetailsActivity = this;
                }
            } catch (Exception e) {
                e = e;
                orderDetailsActivity = this;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        WoosimPrnMng woosimPrnMng = this.mPrnMng;
        if (woosimPrnMng != null) {
            woosimPrnMng.releaseAllocatoins();
        }
        super.onDestroy();
    }
}
