package com.example.pointofsalef.adapter;

import android.content.Context;
import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.pos.PosActivity;
import com.example.pointofsalef.product.EditProductActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class PosProductAdapter extends RecyclerView.Adapter<PosProductAdapter.MyViewHolder> {
    public static int count;
    private Context context;
    MediaPlayer player;
    private List<HashMap<String, String>> productData;

    public PosProductAdapter(Context context, List<HashMap<String, String>> productData) {
        this.context = context;
        this.productData = productData;
        this.player = MediaPlayer.create(context, (int) R.raw.delete_sound);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pos_product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int getStock;
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this.context);
        databaseAccess.open();
        String currency = databaseAccess.getCurrency();
        final String product_id = this.productData.get(position).get("product_id");
        String name = this.productData.get(position).get("product_name");
        final String product_weight = this.productData.get(position).get("product_weight");
        final String product_stock = this.productData.get(position).get("product_stock");
        final String product_price = this.productData.get(position).get("product_sell_price");
        final String weight_unit_id = this.productData.get(position).get("product_weight_unit_id");
        String base64Image = this.productData.get(position).get("product_image");
        databaseAccess.open();
        String weight_unit_name = databaseAccess.getWeightUnitName(weight_unit_id);
        holder.txtProductName.setText(name);
        int getStock2 = Integer.parseInt(product_stock);
        if (getStock2 > 5) {
            getStock = getStock2;
            holder.txtStock.setText(this.context.getString(R.string.stock) + " : " + product_stock);
        } else {
            getStock = getStock2;
            holder.txtStock.setText(this.context.getString(R.string.stock) + " : " + product_stock);

        }
        holder.txtWeight.setText(product_weight + " " + weight_unit_name);
        holder.txtPrice.setText(currency + product_price);
        holder.cardProduct.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.PosProductAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PosProductAdapter.this.player.start();
                Intent intent = new Intent(PosProductAdapter.this.context, EditProductActivity.class);
                intent.putExtra("product_id", product_id);
                PosProductAdapter.this.context.startActivity(intent);
            }
        });
        if (base64Image != null) {
            if (base64Image.length() < 6) {
                Log.d("64base", base64Image);
                holder.product_image.setImageResource(R.drawable.image_placeholder);
                holder.product_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                byte[] bytes = Base64.decode(base64Image, 0);
                holder.product_image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        }
        final int getStock3 = getStock;
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.PosProductAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (getStock3 <= 0) {
                    Toasty.warning(PosProductAdapter.this.context, (int) R.string.stock_is_low_please_update_stock, 0).show();
                    return;
                }
                Log.d("w_id", weight_unit_id);
                databaseAccess.open();
                int check = databaseAccess.addToCart(product_id, product_weight, weight_unit_id, product_price, 1, product_stock);
                databaseAccess.open();
                int count2 = databaseAccess.getCartItemCount();
                if (count2 == 0) {
                    PosActivity.txtCount.setVisibility(View.INVISIBLE);
                } else {
                    PosActivity.txtCount.setVisibility(View.VISIBLE);
                    PosActivity.txtCount.setText(String.valueOf(count2));
                }
                if (check == 1) {
                    Toasty.success(PosProductAdapter.this.context, (int) R.string.product_added_to_cart, 0).show();
                    PosProductAdapter.this.player.start();
                } else if (check == 2) {
                    Toasty.info(PosProductAdapter.this.context, (int) R.string.product_already_added_to_cart, 0).show();
                } else {
                    Toasty.error(PosProductAdapter.this.context, (int) R.string.product_added_to_cart_failed_try_again, 0).show();
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.productData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnAddToCart;
        CardView cardProduct;
        ImageView product_image;
        TextView txtPrice;
        TextView txtProductName;
        TextView txtStock;
        TextView txtWeight;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
            this.txtWeight = (TextView) itemView.findViewById(R.id.txt_weight);
            this.txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            this.txtStock = (TextView) itemView.findViewById(R.id.txt_stock);
            this.product_image = (ImageView) itemView.findViewById(R.id.img_product);
            this.btnAddToCart = (Button) itemView.findViewById(R.id.btn_add_cart);
            this.cardProduct = (CardView) itemView.findViewById(R.id.card_product);
        }
    }
}
