package com.example.pointofsalef.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;

import es.dmoral.toasty.Toasty;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    public static Double total_price;
    Button btnSubmitOrder;
    private List<HashMap<String, String>> cart_product;
    private Context context;
    DecimalFormat f = new DecimalFormat("#0.00");
    ImageView imgNoProduct;
    MediaPlayer player;
    TextView txt_no_product;
    TextView txt_total_price;

    public CartAdapter(Context context, List<HashMap<String, String>> cart_product, TextView txt_total_price, Button btnSubmitOrder, ImageView imgNoProduct, TextView txt_no_product) {
        this.context = context;
        this.cart_product = cart_product;
        this.player = MediaPlayer.create(context, (int) R.raw.delete_sound);
        this.txt_total_price = txt_total_price;
        this.btnSubmitOrder = btnSubmitOrder;
        this.imgNoProduct = imgNoProduct;
        this.txt_no_product = txt_no_product;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this.context);
        databaseAccess.open();
        final String cart_id = this.cart_product.get(position).get("cart_id");
        String product_id = this.cart_product.get(position).get("product_id");
        String product_name = databaseAccess.getProductName(product_id);
        final String price = this.cart_product.get(position).get("product_price");
        String weight_unit_id = this.cart_product.get(position).get("product_weight_unit");
        String weight = this.cart_product.get(position).get("product_weight");
        String qty = this.cart_product.get(position).get("product_qty");
        String stock = this.cart_product.get(position).get("stock");
        final int getStock = Integer.parseInt(stock);
        databaseAccess.open();
        String base64Image = databaseAccess.getProductImage(product_id);
        databaseAccess.open();
        String weight_unit_name = databaseAccess.getWeightUnitName(weight_unit_id);
        databaseAccess.open();
        final String currency = databaseAccess.getCurrency();
        databaseAccess.open();
        total_price = Double.valueOf(databaseAccess.getTotalPrice());
        this.txt_total_price.setText(this.context.getString(R.string.total_price) + currency + this.f.format(total_price));
        if (base64Image != null) {
            if (base64Image.isEmpty() || base64Image.length() < 6) {
                holder.imgProduct.setImageResource(R.drawable.image_placeholder);
            } else {
                byte[] bytes = Base64.decode(base64Image, 0);
                holder.imgProduct.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        }
        double parseDouble = Double.parseDouble(price);
        double parseInt = Integer.parseInt(qty);
        Double.isNaN(parseInt);
        double getPrice = parseInt * parseDouble;
        holder.txtItemName.setText(product_name);
        holder.txtPrice.setText(currency + this.f.format(getPrice));
        holder.txtWeight.setText(weight + " " + weight_unit_name);
        holder.txtQtyNumber.setText(qty);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.CartAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(CartAdapter.this.context);
                databaseAccess2.open();
                boolean deleteProduct = databaseAccess2.deleteProductFromCart(cart_id);
                if (deleteProduct) {
                    Toasty.success(CartAdapter.this.context, CartAdapter.this.context.getString(R.string.product_removed_from_cart), 0).show();
                    CartAdapter.this.player.start();
                    CartAdapter.this.cart_product.remove(holder.getAdapterPosition());
                    CartAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                    databaseAccess2.open();
                    CartAdapter.total_price = Double.valueOf(databaseAccess2.getTotalPrice());
                    CartAdapter.this.txt_total_price.setText(CartAdapter.this.context.getString(R.string.total_price) + currency + CartAdapter.this.f.format(CartAdapter.total_price));
                } else {
                    Toasty.error(CartAdapter.this.context, CartAdapter.this.context.getString(R.string.failed), 0).show();
                }
                databaseAccess2.open();
                int itemCount = databaseAccess2.getCartItemCount();
                Log.d("itemCount", "" + itemCount);
                if (itemCount <= 0) {
                    CartAdapter.this.txt_total_price.setVisibility(View.GONE);
                    CartAdapter.this.btnSubmitOrder.setVisibility(View.GONE);
                    CartAdapter.this.imgNoProduct.setVisibility(View.VISIBLE);
                    CartAdapter.this.txt_no_product.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.txtPlus.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.CartAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String qty1 = holder.txtQtyNumber.getText().toString();
                int get_qty = Integer.parseInt(qty1);
                if (get_qty >= getStock) {
                    Toasty.error(CartAdapter.this.context, CartAdapter.this.context.getString(R.string.available_stock) + " " + getStock, 0).show();
                    return;
                }
                int get_qty2 = get_qty + 1;
                double parseDouble2 = Double.parseDouble(price);
                double d = get_qty2;
                Double.isNaN(d);
                double cost = parseDouble2 * d;
                holder.txtPrice.setText(currency + CartAdapter.this.f.format(cost));
                holder.txtQtyNumber.setText("" + get_qty2);
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(CartAdapter.this.context);
                databaseAccess2.open();
                databaseAccess2.updateProductQty(cart_id, "" + get_qty2);
                CartAdapter.total_price = Double.valueOf(CartAdapter.total_price.doubleValue() + Double.valueOf(price).doubleValue());
                CartAdapter.this.txt_total_price.setText(CartAdapter.this.context.getString(R.string.total_price) + currency + CartAdapter.this.f.format(CartAdapter.total_price));
            }
        });
        holder.txtMinus.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.CartAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String qty2 = holder.txtQtyNumber.getText().toString();
                int get_qty = Integer.parseInt(qty2);
                if (get_qty >= 2) {
                    int get_qty2 = get_qty - 1;
                    double parseDouble2 = Double.parseDouble(price);
                    double d = get_qty2;
                    Double.isNaN(d);
                    double cost = parseDouble2 * d;
                    holder.txtPrice.setText(currency + CartAdapter.this.f.format(cost));
                    holder.txtQtyNumber.setText("" + get_qty2);
                    DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(CartAdapter.this.context);
                    databaseAccess2.open();
                    databaseAccess2.updateProductQty(cart_id, "" + get_qty2);
                    CartAdapter.total_price = Double.valueOf(CartAdapter.total_price.doubleValue() - Double.valueOf(price).doubleValue());
                    CartAdapter.this.txt_total_price.setText(CartAdapter.this.context.getString(R.string.total_price) + currency + CartAdapter.this.f.format(CartAdapter.total_price));
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.cart_product.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDelete;
        ImageView imgProduct;
        TextView txtItemName;
        TextView txtMinus;
        TextView txtPlus;
        TextView txtPrice;
        TextView txtQtyNumber;
        TextView txtWeight;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtItemName = (TextView) itemView.findViewById(R.id.txt_item_name);
            this.txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            this.txtWeight = (TextView) itemView.findViewById(R.id.txt_weight);
            this.txtQtyNumber = (TextView) itemView.findViewById(R.id.txt_number);
            this.imgProduct = (ImageView) itemView.findViewById(R.id.cart_product_image);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            this.txtMinus = (TextView) itemView.findViewById(R.id.txt_minus);
            this.txtPlus = (TextView) itemView.findViewById(R.id.txt_plus);
        }
    }
}
