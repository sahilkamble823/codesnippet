package com.example.pointofsalef.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;
import com.example.pointofsalef.customers.EditCustomersActivity;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.customers.EditCustomersActivity;
import com.example.pointofsalef.database.DatabaseAccess;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    private Context context;
    private List<HashMap<String, String>> customerData;

    public CustomerAdapter(Context context, List<HashMap<String, String>> customerData) {
        this.context = context;
        this.customerData = customerData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String customer_id = this.customerData.get(position).get("customer_id");
        String name = this.customerData.get(position).get("customer_name");
        final String cell = this.customerData.get(position).get("customer_cell");
        String email = this.customerData.get(position).get("customer_email");
        String address = this.customerData.get(position).get("customer_address");
        holder.txtCustomerName.setText(name);
        holder.txtCell.setText(cell);
        holder.txtEmail.setText(email);
        holder.txtAddress.setText(address);
        holder.imgCall.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.CustomerAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent callIntent = new Intent("android.intent.action.DIAL");
                String phone = "tel:" + cell;
                callIntent.setData(Uri.parse(phone));
                CustomerAdapter.this.context.startActivity(callIntent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.CustomerAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerAdapter.this.context);
                builder.setMessage(R.string.want_to_delete_customer).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.CustomerAdapter.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(CustomerAdapter.this.context);
                        databaseAccess.open();
                        boolean deleteCustomer = databaseAccess.deleteCustomer(customer_id);
                        if (deleteCustomer) {
                            Toasty.error(CustomerAdapter.this.context, (int) R.string.customer_deleted, 0).show();
                            CustomerAdapter.this.customerData.remove(holder.getAdapterPosition());
                            CustomerAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(CustomerAdapter.this.context, (int) R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.CustomerAdapter.2.1
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
        return this.customerData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgCall;
        ImageView imgDelete;
        TextView txtAddress;
        TextView txtCell;
        TextView txtCustomerName;
        TextView txtEmail;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtCustomerName = (TextView) itemView.findViewById(R.id.txt_customer_name);
            this.txtCell = (TextView) itemView.findViewById(R.id.txt_cell);
            this.txtEmail = (TextView) itemView.findViewById(R.id.txt_email);
            this.txtAddress = (TextView) itemView.findViewById(R.id.txt_address);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            this.imgCall = (ImageView) itemView.findViewById(R.id.img_call);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent i = new Intent(CustomerAdapter.this.context, EditCustomersActivity.class);
            i.putExtra("customer_id", (String) ((HashMap) CustomerAdapter.this.customerData.get(getAdapterPosition())).get("customer_id"));
            i.putExtra("customer_name", (String) ((HashMap) CustomerAdapter.this.customerData.get(getAdapterPosition())).get("customer_name"));
            i.putExtra("customer_cell", (String) ((HashMap) CustomerAdapter.this.customerData.get(getAdapterPosition())).get("customer_cell"));
            i.putExtra("customer_email", (String) ((HashMap) CustomerAdapter.this.customerData.get(getAdapterPosition())).get("customer_email"));
            i.putExtra("customer_address", (String) ((HashMap) CustomerAdapter.this.customerData.get(getAdapterPosition())).get("customer_address"));
            CustomerAdapter.this.context.startActivity(i);
        }
    }
}
