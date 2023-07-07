package com.example.pointofsalef.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.product.EditProductActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context context;
    private List<HashMap<String, String>> productData;

    public ProductAdapter(Context context, List<HashMap<String, String>> productData) {
        this.context = context;
        this.productData = productData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this.context);
        final String product_id = this.productData.get(position).get("product_id");
        String name = this.productData.get(position).get("product_name");
        String supplier_id = this.productData.get(position).get("product_supplier");
        String buy_price = this.productData.get(position).get("product_buy_price");
        String sell_price = this.productData.get(position).get("product_sell_price");
        String base64Image = this.productData.get(position).get("product_image");
        databaseAccess.open();
        String currency = databaseAccess.getCurrency();
        databaseAccess.open();
        String supplier_name = databaseAccess.getSupplierName(supplier_id);
        holder.txtProductName.setText(name);
        holder.txtSupplierName.setText(this.context.getString(R.string.supplier) + supplier_name);
        holder.txtBuyPrice.setText(this.context.getString(R.string.buy_price) + currency + buy_price);
        holder.txtSellPrice.setText(this.context.getString(R.string.sell_price) + currency + sell_price);
        if (base64Image != null) {
            if (base64Image.length() < 6) {
                Log.d("64base", base64Image);
                holder.product_image.setImageResource(R.drawable.image_placeholder);
            } else {
                byte[] bytes = Base64.decode(base64Image, 0);
                holder.product_image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.ProductAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductAdapter.this.context);
                builder.setMessage(R.string.want_to_delete_product).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.ProductAdapter.1.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        databaseAccess.open();
                        boolean deleteProduct = databaseAccess.deleteProduct(product_id);
                        if (deleteProduct) {
                            Toasty.error(ProductAdapter.this.context, (int) R.string.product_deleted, 0).show();
                            ProductAdapter.this.productData.remove(holder.getAdapterPosition());
                            ProductAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(ProductAdapter.this.context, (int) R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.ProductAdapter.1.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.productData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgDelete;
        ImageView product_image;
        TextView txtBuyPrice;
        TextView txtProductName;
        TextView txtSellPrice;
        TextView txtSupplierName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
            this.txtSupplierName = (TextView) itemView.findViewById(R.id.txt_product_supplier);
            this.txtBuyPrice = (TextView) itemView.findViewById(R.id.txt_product_buy_price);
            this.txtSellPrice = (TextView) itemView.findViewById(R.id.txt_product_sell_price);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            this.product_image = (ImageView) itemView.findViewById(R.id.product_image);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Intent i = new Intent(ProductAdapter.this.context, EditProductActivity.class);
            i.putExtra("product_id", (String) ((HashMap) ProductAdapter.this.productData.get(getAdapterPosition())).get("product_id"));
            ProductAdapter.this.context.startActivity(i);
        }
    }
}
