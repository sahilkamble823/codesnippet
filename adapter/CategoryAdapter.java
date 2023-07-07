package com.example.pointofsalef.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.settings.categories.EditCategoryActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<HashMap<String, String>> categoryData;
    private Context context;

    public CategoryAdapter(Context context, List<HashMap<String, String>> categoryData) {
        this.context = context;
        this.categoryData = categoryData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String category_id = this.categoryData.get(position).get("category_id");
        String category_name = this.categoryData.get(position).get("category_name");
        holder.txtCategoryName.setText(category_name);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.CategoryAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryAdapter.this.context);
                builder.setMessage(R.string.want_to_delete_category).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.CategoryAdapter.1.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(CategoryAdapter.this.context);
                        databaseAccess.open();
                        boolean deleteCustomer = databaseAccess.deleteCategory(category_id);
                        if (deleteCustomer) {
                            Toasty.success(CategoryAdapter.this.context, (int) R.string.category_deleted, 0).show();
                            CategoryAdapter.this.categoryData.remove(holder.getAdapterPosition());
                            CategoryAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toasty.error(CategoryAdapter.this.context, (int) R.string.failed, 0).show();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.CategoryAdapter.1.1
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
        return this.categoryData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgDelete;
        TextView txtCategoryName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtCategoryName = (TextView) itemView.findViewById(R.id.txt_category_name);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent i = new Intent(CategoryAdapter.this.context, EditCategoryActivity.class);
            i.putExtra("category_id", (String) ((HashMap) CategoryAdapter.this.categoryData.get(getAdapterPosition())).get("category_id"));
            i.putExtra("category_name", (String) ((HashMap) CategoryAdapter.this.categoryData.get(getAdapterPosition())).get("category_name"));
            CategoryAdapter.this.context.startActivity(i);
        }
    }
}
