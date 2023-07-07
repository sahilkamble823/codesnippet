package com.example.pointofsalef.pos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;

import com.example.pointofsalef.adapter.CartAdapter;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.orders.OrdersActivity;
import com.example.pointofsalef.utils.BaseActivity;
import com.github.mikephil.charting.utils.Utils;
import es.dmoral.toasty.Toasty;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ProductCart extends BaseActivity {
    Button btnSubmitOrder;
    ArrayAdapter<String> customerAdapter;
    List<String> customerNames;
    DecimalFormat f;
    ImageView imgNoProduct;
    LinearLayout linearLayout;
    ArrayAdapter<String> orderTypeAdapter;
    List<String> orderTypeNames;
    ArrayAdapter<String> paymentMethodAdapter;
    List<String> paymentMethodNames;
    CartAdapter productCartAdapter;
    private RecyclerView recyclerView;
    TextView txt_no_product;
    TextView txt_total_price;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.product_cart);
        this.f = new DecimalFormat("#0.00");
        this.recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);
        this.imgNoProduct = (ImageView) findViewById(R.id.image_no_product);
        this.btnSubmitOrder = (Button) findViewById(R.id.btn_submit_order);
        this.txt_no_product = (TextView) findViewById(R.id.txt_no_product);
        this.linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        this.txt_total_price = (TextView) findViewById(R.id.txt_total_price);
        this.txt_no_product.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> cartProductList = databaseAccess.getCartProduct();
        if (cartProductList.isEmpty()) {
            this.imgNoProduct.setImageResource(R.drawable.empty_cart);
            this.imgNoProduct.setVisibility(View.VISIBLE);
            this.txt_no_product.setVisibility(View.VISIBLE);
            this.btnSubmitOrder.setVisibility(View.GONE);
            this.recyclerView.setVisibility(View.GONE);
            this.linearLayout.setVisibility(View.GONE);
            this.txt_total_price.setVisibility(View.GONE);
        } else {
            this.imgNoProduct.setVisibility(View.GONE);
            CartAdapter cartAdapter = new CartAdapter(this, cartProductList, this.txt_total_price, this.btnSubmitOrder, this.imgNoProduct, this.txt_no_product);
            this.productCartAdapter = cartAdapter;
            this.recyclerView.setAdapter(cartAdapter);
        }
        this.btnSubmitOrder.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ProductCart.this.dialog();
            }
        });
    }

    public void proceedOrder(String type, String payment_method, String customer_name, double calculated_tax, String discount) {
        String weight_unit;
        String product_image;
        JSONObject objp = null;
        DatabaseAccess databaseAccess;
        String str = "product_id";
        DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(this);
        databaseAccess2.open();
        int itemCount = databaseAccess2.getCartItemCount();
        if (itemCount <= 0) {
            Toasty.error(this, (int) R.string.no_product_in_cart, 0).show();
            return;
        }
        databaseAccess2.open();
        List<HashMap<String, String>> lines = databaseAccess2.getCartProduct();
        if (lines.isEmpty()) {
            Toasty.error(this, (int) R.string.no_product_found, 0).show();
            return;
        }
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date());
        Long tsLong = Long.valueOf(System.currentTimeMillis() / 1000);
        String timeStamp = tsLong.toString();
        Log.d("Time", timeStamp);
        JSONObject obj = new JSONObject();
        try {
            obj.put("order_date", currentDate);
            obj.put("order_time", currentTime);
            obj.put("order_type", type);
            try {
                obj.put("order_payment_method", payment_method);
                obj.put("customer_name", customer_name);
                try {
                    obj.put("tax", calculated_tax);
                    obj.put("discount", discount);
                    JSONArray array = new JSONArray();
                    int i = 0;
                    while (i < lines.size()) {
                        databaseAccess2.open();
                        String product_id = lines.get(i).get(str);
                        String product_name = databaseAccess2.getProductName(product_id);
                        String weight_unit_id = lines.get(i).get("product_weight_unit");
                        weight_unit = databaseAccess2.getWeightUnitName(weight_unit_id);
                        databaseAccess2.open();
                        product_image = databaseAccess2.getProductImage(product_id);
                        databaseAccess2.open();
                        String timeStamp2 = timeStamp;
                        try {

                            objp = new JSONObject();
                            databaseAccess = databaseAccess2;
                        } catch (Exception e) {
                            e = e;
                            e.printStackTrace();
                            saveOrderInOfflineDb(obj);
                        }
                        try {
                            objp.put(str, product_id);
                            objp.put("product_name", product_name);
                            objp.put("product_weight", lines.get(i).get("product_weight") + " " + weight_unit);
                            objp.put("product_qty", lines.get(i).get("product_qty"));
                            objp.put("stock", lines.get(i).get("stock"));
                            objp.put("product_price", lines.get(i).get("product_price"));
                            objp.put("product_image", lines.get(i).get("product_image"));
                            objp.put("product_order_date", currentDate);
                            array.put(objp);
                            i++;
                            timeStamp = timeStamp2;
                            str = str;
                        } catch (JSONException e2) {
                            e2 = e2;
                            e2.printStackTrace();
                            saveOrderInOfflineDb(obj);
                        }
                    }
                    obj.put("lines", array);
                } catch (JSONException e3) {
                    e3 = e3;
                }
            } catch (JSONException e4) {
                e4 = e4;
            }
        } catch (JSONException e5) {
            e5 = e5;
        }
        saveOrderInOfflineDb(obj);
    }

    private void saveOrderInOfflineDb(JSONObject obj) {
        Long tsLong = Long.valueOf(System.currentTimeMillis() / 1000);
        String timeStamp = tsLong.toString();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        databaseAccess.insertOrder(timeStamp, obj);
        Toasty.success(this, (int) R.string.order_done_successful, 0).show();
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
        finish();
    }

    public void dialog() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> shopData = databaseAccess.getShopInformation();
        final String shop_currency = shopData.get(0).get("shop_currency");
        String tax = shopData.get(0).get("tax");
        double getTax = Double.parseDouble(tax);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_payment, (ViewGroup) null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        final Button dialog_btn_submit = (Button) dialogView.findViewById(R.id.btn_submit);
        ImageButton dialog_btn_close = (ImageButton) dialogView.findViewById(R.id.btn_close);
        final TextView dialog_order_payment_method = (TextView) dialogView.findViewById(R.id.dialog_order_status);
        final TextView dialog_order_type = (TextView) dialogView.findViewById(R.id.dialog_order_type);
        final TextView dialog_customer = (TextView) dialogView.findViewById(R.id.dialog_customer);
        TextView dialog_txt_total = (TextView) dialogView.findViewById(R.id.dialog_txt_total);
        TextView dialog_txt_total_tax = (TextView) dialogView.findViewById(R.id.dialog_txt_total_tax);
        TextView dialog_txt_level_tax = (TextView) dialogView.findViewById(R.id.dialog_level_tax);
        final TextView dialog_txt_total_cost = (TextView) dialogView.findViewById(R.id.dialog_txt_total_cost);
        final EditText dialog_etxt_discount = (EditText) dialogView.findViewById(R.id.etxt_dialog_discount);
        ImageButton dialog_img_customer = (ImageButton) dialogView.findViewById(R.id.img_select_customer);
        ImageButton dialog_img_order_payment_method = (ImageButton) dialogView.findViewById(R.id.img_order_payment_method);
        ImageButton dialog_img_order_type = (ImageButton) dialogView.findViewById(R.id.img_order_type);
        dialog_txt_level_tax.setText(getString(R.string.total_tax) + "( " + tax + "%) : ");
        final double total_cost = CartAdapter.total_price.doubleValue();
        dialog_txt_total.setText(shop_currency + this.f.format(total_cost));
        final double calculated_tax = (total_cost * getTax) / 100.0d;
        dialog_txt_total_tax.setText(shop_currency + this.f.format(calculated_tax));
        double calculated_total_cost = (total_cost + calculated_tax) - Utils.DOUBLE_EPSILON;
        dialog_txt_total_cost.setText(shop_currency + this.f.format(calculated_total_cost));
        dialog_etxt_discount.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.pos.ProductCart.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String get_discount = s.toString();
                if (get_discount.isEmpty() || get_discount.equals(".")) {
                    double calculated_total_cost2 = (total_cost + calculated_tax) - Utils.DOUBLE_EPSILON;
                    dialog_txt_total_cost.setText(shop_currency + ProductCart.this.f.format(calculated_total_cost2));
                    return;
                }
                double calculated_total_cost3 = total_cost + calculated_tax;
                double discount = Double.parseDouble(get_discount);
                if (discount > calculated_total_cost3) {
                    dialog_etxt_discount.setError(ProductCart.this.getString(R.string.discount_cant_be_greater_than_total_price));
                    dialog_etxt_discount.requestFocus();
                    dialog_btn_submit.setVisibility(View.INVISIBLE);
                    return;
                }
                dialog_btn_submit.setVisibility(View.VISIBLE);
                double calculated_total_cost4 = (total_cost + calculated_tax) - discount;
                dialog_txt_total_cost.setText(shop_currency + ProductCart.this.f.format(calculated_total_cost4));
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
        this.customerNames = new ArrayList();
        databaseAccess.open();
        List<HashMap<String, String>> customer = databaseAccess.getCustomers();
        for (int i = 0; i < customer.size(); i++) {
            this.customerNames.add(customer.get(i).get("customer_name"));
        }
        this.orderTypeNames = new ArrayList();
        databaseAccess.open();
        List<HashMap<String, String>> order_type = databaseAccess.getOrderType();
        for (int i2 = 0; i2 < order_type.size(); i2++) {
            this.orderTypeNames.add(order_type.get(i2).get("order_type_name"));
        }
        this.paymentMethodNames = new ArrayList();
        databaseAccess.open();
        List<HashMap<String, String>> payment_method = databaseAccess.getPaymentMethod();
        for (int i3 = 0; i3 < payment_method.size(); i3++) {
            this.paymentMethodNames.add(payment_method.get(i3).get("payment_method_name"));
        }
        dialog_img_order_payment_method.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ProductCart.this.paymentMethodAdapter = new ArrayAdapter<>(ProductCart.this,R.layout.payment_method_item);
                ProductCart.this.paymentMethodAdapter.addAll(ProductCart.this.paymentMethodNames);
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(ProductCart.this);
                View dialogView2 = ProductCart.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog2.setView(dialogView2);
                dialog2.setCancelable(false);
                Button dialog_button = (Button) dialogView2.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView2.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView2.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView2.findViewById(R.id.dialog_list);
                dialog_title.setText(R.string.select_payment_method);
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) ProductCart.this.paymentMethodAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.pos.ProductCart.3.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        ProductCart.this.paymentMethodAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog2.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.3.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.pos.ProductCart.3.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = ProductCart.this.paymentMethodAdapter.getItem(position);
                        dialog_order_payment_method.setText(selectedItem);
                    }
                });
            }
        });
        dialog_img_order_type.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ProductCart.this.orderTypeAdapter = new ArrayAdapter<>(ProductCart.this, R.layout.order_type_item);
                ProductCart.this.orderTypeAdapter.addAll(ProductCart.this.orderTypeNames);
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(ProductCart.this);
                View dialogView2 = ProductCart.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog2.setView(dialogView2);
                dialog2.setCancelable(false);
                Button dialog_button = (Button) dialogView2.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView2.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView2.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView2.findViewById(R.id.dialog_list);
                dialog_title.setText(R.string.select_order_type);
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) ProductCart.this.orderTypeAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.pos.ProductCart.4.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        ProductCart.this.orderTypeAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog2.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.4.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.pos.ProductCart.4.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = ProductCart.this.orderTypeAdapter.getItem(position);
                        dialog_order_type.setText(selectedItem);
                    }
                });
            }
        });
        dialog_img_customer.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ProductCart.this.customerAdapter = new ArrayAdapter<>(ProductCart.this, R.layout.customer_item);
                ProductCart.this.customerAdapter.addAll(ProductCart.this.customerNames);
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(ProductCart.this);
                View dialogView2 = ProductCart.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog2.setView(dialogView2);
                dialog2.setCancelable(false);
                Button dialog_button = (Button) dialogView2.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView2.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView2.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView2.findViewById(R.id.dialog_list);
                dialog_title.setText(R.string.select_customer);
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) ProductCart.this.customerAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.pos.ProductCart.5.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        ProductCart.this.customerAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog2.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.5.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.pos.ProductCart.5.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = ProductCart.this.customerAdapter.getItem(position);
                        dialog_customer.setText(selectedItem);
                    }
                });
            }
        });
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        dialog_btn_submit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String discount;
                String order_type2 = dialog_order_type.getText().toString().trim();
                String order_payment_method = dialog_order_payment_method.getText().toString().trim();
                String customer_name = dialog_customer.getText().toString().trim();
                String discount2 = dialog_etxt_discount.getText().toString().trim();
                if (!discount2.isEmpty()) {
                    discount = discount2;
                } else {
                    discount = "0.00";
                }
                ProductCart.this.proceedOrder(order_type2, order_payment_method, customer_name, calculated_tax, discount);
                alertDialog.dismiss();
            }
        });
        dialog_btn_close.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.pos.ProductCart.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                Intent intent = new Intent(this, PosActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
