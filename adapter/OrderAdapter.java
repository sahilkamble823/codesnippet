package com.example.pointofsalef.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartpos.R;

import com.example.pointofsalef.Constant;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.orders.OrderDetailsActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    Context context;
    private List<HashMap<String, String>> orderData;

    public OrderAdapter(Context context, List<HashMap<String, String>> orderData) {
        this.context = context;
        this.orderData = orderData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String customer_name = this.orderData.get(position).get("customer_name");
        final String invoice_id = this.orderData.get(position).get("invoice_id");
        String order_date = this.orderData.get(position).get("order_date");
        String order_time = this.orderData.get(position).get("order_time");
        String payment_method = this.orderData.get(position).get("order_payment_method");
        String order_type = this.orderData.get(position).get("order_type");
        String orderStatus = this.orderData.get(position).get(Constant.ORDER_STATUS);
        holder.txt_customer_name.setText(customer_name);
        holder.txt_order_id.setText(this.context.getString(R.string.order_id) + invoice_id);
        holder.txt_payment_method.setText(this.context.getString(R.string.payment_method) + payment_method);
        holder.txt_order_type.setText(this.context.getString(R.string.order_type) + order_type);
        holder.txt_date.setText(order_time + " " + order_date);
        holder.txt_order_status.setText(orderStatus);
        if (orderStatus.equals(Constant.COMPLETED)) {
            holder.txt_order_status.setBackgroundColor(Color.parseColor("#43a047"));
            holder.txt_order_status.setTextColor(-1);
            holder.imgStatus.setVisibility(View.GONE);
        } else if (orderStatus.equals(Constant.CANCEL)) {
            holder.txt_order_status.setBackgroundColor(Color.parseColor("#e53935"));
            holder.txt_order_status.setTextColor(-1);
            holder.imgStatus.setVisibility(View.GONE);
        }
        holder.imgStatus.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.OrderAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(OrderAdapter.this.context);
                dialogBuilder.withTitle(OrderAdapter.this.context.getString(R.string.change_order_status)).withMessage(OrderAdapter.this.context.getString(R.string.please_change_order_status_to_complete_or_cancel)).withEffect(Effectstype.Slidetop).withDialogColor("#01baef").withButton1Text(OrderAdapter.this.context.getString(R.string.order_completed)).withButton2Text(OrderAdapter.this.context.getString(R.string.cancel_order)).setButton1Click(new View.OnClickListener() { // from class: com.app.smartpos.adapter.OrderAdapter.1.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(OrderAdapter.this.context);
                        databaseAccess.open();
                        boolean updateOrder = databaseAccess.updateOrder(invoice_id, Constant.COMPLETED);
                        if (updateOrder) {
                            Toasty.success(OrderAdapter.this.context, (int) R.string.order_updated, 0).show();
                            holder.txt_order_status.setText(Constant.COMPLETED);
                            holder.txt_order_status.setBackgroundColor(Color.parseColor("#43a047"));
                            holder.txt_order_status.setTextColor(-1);
                            holder.imgStatus.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(OrderAdapter.this.context, (int) R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                        dialogBuilder.dismiss();
                    }
                }).setButton2Click(new View.OnClickListener() { // from class: com.app.smartpos.adapter.OrderAdapter.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(OrderAdapter.this.context);
                        databaseAccess.open();
                        boolean updateOrder = databaseAccess.updateOrder(invoice_id, Constant.CANCEL);
                        if (updateOrder) {
                            Toasty.error(OrderAdapter.this.context, (int) R.string.order_updated, 0).show();
                            holder.txt_order_status.setText(Constant.CANCEL);
                            holder.txt_order_status.setBackgroundColor(Color.parseColor("#e53935"));
                            holder.txt_order_status.setTextColor(-1);
                            holder.imgStatus.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(OrderAdapter.this.context, (int) R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                        dialogBuilder.dismiss();
                    }
                }).show();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.orderData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgStatus;
        TextView txt_customer_name;
        TextView txt_date;
        TextView txt_order_id;
        TextView txt_order_status;
        TextView txt_order_type;
        TextView txt_payment_method;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txt_customer_name = (TextView) itemView.findViewById(R.id.txt_customer_name);
            this.txt_order_id = (TextView) itemView.findViewById(R.id.txt_order_id);
            this.txt_order_type = (TextView) itemView.findViewById(R.id.txt_order_type);
            this.txt_payment_method = (TextView) itemView.findViewById(R.id.txt_payment_method);
            this.txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            this.txt_order_status = (TextView) itemView.findViewById(R.id.txt_order_status);
            this.imgStatus = (ImageView) itemView.findViewById(R.id.img_status);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Intent i = new Intent(OrderAdapter.this.context, OrderDetailsActivity.class);
            i.putExtra("order_id", (String) ((HashMap) OrderAdapter.this.orderData.get(getAdapterPosition())).get("invoice_id"));
            i.putExtra("customer_name", (String) ((HashMap) OrderAdapter.this.orderData.get(getAdapterPosition())).get("customer_name"));
            i.putExtra("order_date", (String) ((HashMap) OrderAdapter.this.orderData.get(getAdapterPosition())).get("order_date"));
            i.putExtra("order_time", (String) ((HashMap) OrderAdapter.this.orderData.get(getAdapterPosition())).get("order_time"));
            i.putExtra("tax", (String) ((HashMap) OrderAdapter.this.orderData.get(getAdapterPosition())).get("tax"));
            i.putExtra("discount", (String) ((HashMap) OrderAdapter.this.orderData.get(getAdapterPosition())).get("discount"));
            OrderAdapter.this.context.startActivity(i);
        }
    }
}
