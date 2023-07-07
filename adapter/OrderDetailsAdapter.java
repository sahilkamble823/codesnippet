package com.example.pointofsalef.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;
import com.example.pointofsalef.database.DatabaseAccess;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {
    Context context;
    DecimalFormat f = new DecimalFormat("#0.00");
    private List<HashMap<String, String>> orderData;

    public OrderDetailsAdapter(Context context, List<HashMap<String, String>> orderData) {
        this.context = context;
        this.orderData = orderData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this.context);
        holder.txt_product_name.setText(this.orderData.get(position).get("product_name"));
        holder.txt_product_qty.setText(this.context.getString(R.string.quantity) + this.orderData.get(position).get("product_qty"));
        holder.txt_product_Weight.setText(this.context.getString(R.string.weight) + this.orderData.get(position).get("product_weight"));
        String base64Image = this.orderData.get(position).get("product_image");
        String unit_price = this.orderData.get(position).get("product_price");
        String qty = this.orderData.get(position).get("product_qty");
        double price = Double.parseDouble(unit_price);
        int quantity = Integer.parseInt(qty);
        double d = quantity;
        Double.isNaN(d);
        double cost = d * price;
        databaseAccess.open();
        String currency = databaseAccess.getCurrency();
        holder.txt_total_cost.setText(currency + unit_price + " x " + qty + " = " + currency + this.f.format(cost));
        if (base64Image != null) {
            if (base64Image.isEmpty() || base64Image.length() < 6) {
                holder.imgProduct.setImageResource(R.drawable.image_placeholder);
                return;
            }
            byte[] bytes = Base64.decode(base64Image, 0);
            holder.imgProduct.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.orderData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txt_product_Weight;
        TextView txt_product_name;
        TextView txt_product_price;
        TextView txt_product_qty;
        TextView txt_total_cost;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txt_product_name = (TextView) itemView.findViewById(R.id.txt_product_name);
            this.txt_product_price = (TextView) itemView.findViewById(R.id.txt_price);
            this.txt_product_qty = (TextView) itemView.findViewById(R.id.txt_qty);
            this.txt_product_Weight = (TextView) itemView.findViewById(R.id.txt_weight);
            this.imgProduct = (ImageView) itemView.findViewById(R.id.img_product);
            this.txt_total_cost = (TextView) itemView.findViewById(R.id.txt_total_cost);
        }
    }
}
