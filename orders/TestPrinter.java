package com.example.pointofsalef.orders;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;
import com.app.smartpos.R;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.pdf_report.BarCodeEncoder;
import com.example.pointofsalef.utils.IPrintToPrinter;
import com.example.pointofsalef.utils.WoosimPrnMng;
import com.example.pointofsalef.utils.printerFactory;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class TestPrinter implements IPrintToPrinter {
    Bitmap bm;
    private Context context;
    double cost_total;
    String currency;
    String customerName;
    String discount;
    DecimalFormat f = new DecimalFormat("#0.00");
    String footer;
    String invoiceId;
    String name;
    String orderDate;
    List<HashMap<String, String>> orderDetailsList;
    String orderTime;
    String price;
    String qty;
    String shopAddress;
    String shopContact;
    String shopEmail;
    String shopName;
    double subTotal;
    String tax;
    double totalPrice;
    String weight;

    public TestPrinter(Context context, String shopName, String shopAddress, String shopEmail, String shopContact, String invoiceId, String orderDate, String orderTime, String customerName, String footer, double subTotal, double totalPrice, String tax, String discount, String currency) {
        this.context = context;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopEmail = shopEmail;
        this.shopContact = shopContact;
        this.invoiceId = invoiceId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.customerName = customerName;
        this.footer = footer;
        this.subTotal = subTotal;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.discount = discount;
        this.currency = currency;
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        this.orderDetailsList = databaseAccess.getOrderDetailsList(invoiceId);
    }

    @Override // com.app.smartpos.utils.IPrintToPrinter
    public void printContent(WoosimPrnMng prnMng) {
        double getDiscount = Double.parseDouble(this.discount);
        double getTax = Double.parseDouble(this.tax);
        BarCodeEncoder qrCodeEncoder = new BarCodeEncoder();
        this.bm = null;
        try {
            this.bm = qrCodeEncoder.encodeAsBitmap(this.invoiceId, BarcodeFormat.CODE_128, 400, 48);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        printerFactory.createPaperMng(this.context);
        prnMng.printStr(this.shopName, 2, 1);
        prnMng.printStr(this.shopAddress, 1, 1);
        prnMng.printStr("Email: " + this.shopEmail, 1, 1);
        prnMng.printStr("Contact: " + this.shopContact, 1, 1);
        prnMng.printStr("Invoice ID: " + this.invoiceId, 1, 1);
        prnMng.printStr("Order Time: " + this.orderTime + " " + this.orderDate, 1, 1);
        prnMng.printStr(this.customerName, 1, 1);
        prnMng.printStr("Email: " + this.shopEmail, 1, 1);
        prnMng.printStr("--------------------------------");
        prnMng.printStr("  Items        Price  Qty  Total", 1, 1);
        prnMng.printStr("--------------------------------");
        for (int i = 0; i < this.orderDetailsList.size(); i++) {
            this.name = this.orderDetailsList.get(i).get("product_name");
            this.price = this.orderDetailsList.get(i).get("product_price");
            this.qty = this.orderDetailsList.get(i).get("product_qty");
            this.weight = this.orderDetailsList.get(i).get("product_weight");
            double parseInt = Integer.parseInt(this.qty);
            double parseDouble = Double.parseDouble(this.price);
            Double.isNaN(parseInt);
            this.cost_total = parseInt * parseDouble;
            prnMng.leftRightAlign(this.name.trim(), " " + this.price + " x" + this.qty + " " + this.f.format(this.cost_total));
        }
        prnMng.printStr("--------------------------------");
        prnMng.printStr("Sub Total: " + this.currency + this.f.format(this.subTotal), 1, 2);
        prnMng.printStr("Total Tax (+): " + this.currency + this.f.format(getTax), 1, 2);
        prnMng.printStr("Discount (-): " + this.currency + this.f.format(getDiscount), 1, 2);
        prnMng.printStr("--------------------------------");
        prnMng.printStr("Total Price: " + this.currency + this.f.format(this.totalPrice), 1, 2);
        prnMng.printStr(this.footer, 1, 1);
        prnMng.printNewLine();
        prnMng.printPhoto(this.bm);
        prnMng.printNewLine();
        prnMng.printNewLine();
        printEnded(prnMng);
    }

    @Override // com.app.smartpos.utils.IPrintToPrinter
    public void printEnded(WoosimPrnMng prnMng) {
        if (prnMng.printSucc()) {
            Toast.makeText(this.context, (int) R.string.print_succ, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.context, (int) R.string.print_error, Toast.LENGTH_LONG).show();
        }
    }
}
