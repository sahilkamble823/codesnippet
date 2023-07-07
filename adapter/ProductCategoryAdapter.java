package com.example.pointofsalef.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.settings.categories.EditCategoryActivity;

import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.MyViewHolder> {
    private List<HashMap<String, String>> categoryData;
    private Context context;
    ImageView imgNoProduct;
    MediaPlayer player;
    RecyclerView recyclerView;
    TextView txtNoProducts;

    public ProductCategoryAdapter(Context context, List<HashMap<String, String>> categoryData, RecyclerView recyclerView, ImageView imgNoProduct, TextView txtNoProducts) {
        this.context = context;
        this.categoryData = categoryData;
        this.recyclerView = recyclerView;
        this.player = MediaPlayer.create(context, (int) R.raw.delete_sound);
        this.imgNoProduct = imgNoProduct;
        this.txtNoProducts = txtNoProducts;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_category_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String category_id = this.categoryData.get(position).get("category_id");
        String category_name = this.categoryData.get(position).get("category_name");
        holder.txtCategoryName.setText(category_name);
        holder.cardCategory.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.ProductCategoryAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ProductCategoryAdapter.this.player.start();
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ProductCategoryAdapter.this.context);
                databaseAccess.open();
                List<HashMap<String, String>> productList = databaseAccess.getTabProducts(category_id);
                if (productList.isEmpty()) {
                    ProductCategoryAdapter.this.recyclerView.setVisibility(View.INVISIBLE);
                    ProductCategoryAdapter.this.recyclerView.setVisibility(View.GONE);
                    ProductCategoryAdapter.this.imgNoProduct.setVisibility(View.VISIBLE);
                    ProductCategoryAdapter.this.imgNoProduct.setImageResource(R.drawable.not_found);
                    ProductCategoryAdapter.this.txtNoProducts.setVisibility(View.VISIBLE);
                    return;
                }
                ProductCategoryAdapter.this.recyclerView.setVisibility(View.VISIBLE);
                ProductCategoryAdapter.this.imgNoProduct.setVisibility(View.GONE);
                ProductCategoryAdapter.this.txtNoProducts.setVisibility(View.GONE);
                PosProductAdapter productAdapter = new PosProductAdapter(ProductCategoryAdapter.this.context, productList);
                ProductCategoryAdapter.this.recyclerView.setAdapter(productAdapter);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.categoryData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardCategory;
        TextView txtCategoryName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtCategoryName = (TextView) itemView.findViewById(R.id.txt_category_name);
            this.cardCategory = (CardView) itemView.findViewById(R.id.card_category);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent i = new Intent(ProductCategoryAdapter.this.context, EditCategoryActivity.class);
            i.putExtra("category_id", (String) ((HashMap) ProductCategoryAdapter.this.categoryData.get(getAdapterPosition())).get("category_id"));
            i.putExtra("category_name", (String) ((HashMap) ProductCategoryAdapter.this.categoryData.get(getAdapterPosition())).get("category_name"));
        }
    }
}
