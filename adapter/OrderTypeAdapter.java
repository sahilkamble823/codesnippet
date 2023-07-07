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
import com.example.pointofsalef.settings.order_type.EditOrderTypeActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class OrderTypeAdapter extends RecyclerView.Adapter<OrderTypeAdapter.MyViewHolder> {
    private Context context;
    private List<HashMap<String, String>> orderTypeData;

    public OrderTypeAdapter(Context context, List<HashMap<String, String>> orderTypeData) {
        this.context = context;
        this.orderTypeData = orderTypeData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_type_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String typeId = this.orderTypeData.get(position).get("order_type_id");
        String orderTypeName = this.orderTypeData.get(position).get("order_type_name");
        holder.txtTypeName.setText(orderTypeName);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.OrderTypeAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderTypeAdapter.this.context);
                builder.setMessage(R.string.want_to_delete).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.OrderTypeAdapter.1.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(OrderTypeAdapter.this.context);
                        databaseAccess.open();
                        boolean deleteCustomer = databaseAccess.deleteOrderType(typeId);
                        if (deleteCustomer) {
                            Toasty.success(OrderTypeAdapter.this.context, (int) R.string.order_type_deleted, 0).show();
                            OrderTypeAdapter.this.orderTypeData.remove(holder.getAdapterPosition());
                            OrderTypeAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toasty.error(OrderTypeAdapter.this.context, (int) R.string.failed, 0).show();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.OrderTypeAdapter.1.1
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
        return this.orderTypeData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgDelete;
        TextView txtTypeName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtTypeName = (TextView) itemView.findViewById(R.id.txt_type_name);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent i = new Intent(OrderTypeAdapter.this.context, EditOrderTypeActivity.class);
            i.putExtra("order_type_id", (String) ((HashMap) OrderTypeAdapter.this.orderTypeData.get(getAdapterPosition())).get("order_type_id"));
            i.putExtra("order_type_name", (String) ((HashMap) OrderTypeAdapter.this.orderTypeData.get(getAdapterPosition())).get("order_type_name"));
            OrderTypeAdapter.this.context.startActivity(i);
        }
    }
}
