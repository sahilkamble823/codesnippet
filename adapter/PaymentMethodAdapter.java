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
import com.example.pointofsalef.settings.payment_method.EditPaymentMethodActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.MyViewHolder> {
    private Context context;
    private List<HashMap<String, String>> paymentMethodData;

    public PaymentMethodAdapter(Context context, List<HashMap<String, String>> paymentMethodData) {
        this.context = context;
        this.paymentMethodData = paymentMethodData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_method_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String payment_method_id = this.paymentMethodData.get(position).get("payment_method_id");
        String payment_method_name = this.paymentMethodData.get(position).get("payment_method_name");
        holder.txtPaymentMethodName.setText(payment_method_name);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.PaymentMethodAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMethodAdapter.this.context);
                builder.setMessage(R.string.want_to_delete).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.PaymentMethodAdapter.1.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(PaymentMethodAdapter.this.context);
                        databaseAccess.open();
                        boolean deleteCustomer = databaseAccess.deletePaymentMethod(payment_method_id);
                        if (deleteCustomer) {
                            Toasty.success(PaymentMethodAdapter.this.context, (int) R.string.payment_method_deleted, 0).show();
                            PaymentMethodAdapter.this.paymentMethodData.remove(holder.getAdapterPosition());
                            PaymentMethodAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toasty.error(PaymentMethodAdapter.this.context, (int) R.string.failed, 0).show();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.PaymentMethodAdapter.1.1
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
        return this.paymentMethodData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgDelete;
        TextView txtPaymentMethodName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtPaymentMethodName = (TextView) itemView.findViewById(R.id.txt_payment_method_name);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent i = new Intent(PaymentMethodAdapter.this.context, EditPaymentMethodActivity.class);
            i.putExtra("payment_method_id", (String) ((HashMap) PaymentMethodAdapter.this.paymentMethodData.get(getAdapterPosition())).get("payment_method_id"));
            i.putExtra("payment_method_name", (String) ((HashMap) PaymentMethodAdapter.this.paymentMethodData.get(getAdapterPosition())).get("payment_method_name"));
            PaymentMethodAdapter.this.context.startActivity(i);
        }
    }
}
