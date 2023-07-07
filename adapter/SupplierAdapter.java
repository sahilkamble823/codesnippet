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

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.suppliers.EditSuppliersActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder> {
    private Context context;
    private List<HashMap<String, String>> supplierData;

    public SupplierAdapter(Context context, List<HashMap<String, String>> supplierData) {
        this.context = context;
        this.supplierData = supplierData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String suppliers_id = this.supplierData.get(position).get("suppliers_id");
        String name = this.supplierData.get(position).get("suppliers_name");
        String contact_perosn = this.supplierData.get(position).get("suppliers_contact_person");
        final String cell = this.supplierData.get(position).get("suppliers_cell");
        String email = this.supplierData.get(position).get("suppliers_email");
        String address = this.supplierData.get(position).get("suppliers_address");
        holder.txtSuppliersName.setText(name);
        holder.txtSupplierContactPerson.setText(contact_perosn);
        holder.txtSupplierCell.setText(cell);
        holder.txtSupplierEmail.setText(email);
        holder.txtSupplierAddress.setText(address);
        holder.imgCall.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.SupplierAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent callIntent = new Intent("android.intent.action.DIAL");
                String phone = "tel:" + cell;
                callIntent.setData(Uri.parse(phone));
                SupplierAdapter.this.context.startActivity(callIntent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.SupplierAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SupplierAdapter.this.context);
                builder.setMessage(R.string.want_to_delete_supplier).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.SupplierAdapter.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(SupplierAdapter.this.context);
                        databaseAccess.open();
                        boolean deleteSupplier = databaseAccess.deleteSupplier(suppliers_id);
                        if (deleteSupplier) {
                            Toasty.error(SupplierAdapter.this.context, (int) R.string.supplier_deleted, Toast.LENGTH_SHORT).show();
                            SupplierAdapter.this.supplierData.remove(holder.getAdapterPosition());
                            SupplierAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(SupplierAdapter.this.context, (int) R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.SupplierAdapter.2.1
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
        return this.supplierData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgCall;
        ImageView imgDelete;
        TextView txtSupplierAddress;
        TextView txtSupplierCell;
        TextView txtSupplierContactPerson;
        TextView txtSupplierEmail;
        TextView txtSuppliersName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtSuppliersName = (TextView) itemView.findViewById(R.id.txt_supplier_name);
            this.txtSupplierContactPerson = (TextView) itemView.findViewById(R.id.txt_contact_person);
            this.txtSupplierCell = (TextView) itemView.findViewById(R.id.txt_supplier_cell);
            this.txtSupplierEmail = (TextView) itemView.findViewById(R.id.txt_supplier_email);
            this.txtSupplierAddress = (TextView) itemView.findViewById(R.id.txt_supplier_address);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            this.imgCall = (ImageView) itemView.findViewById(R.id.img_call);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent i = new Intent(SupplierAdapter.this.context, EditSuppliersActivity.class);
            i.putExtra("suppliers_id", (String) ((HashMap) SupplierAdapter.this.supplierData.get(getAdapterPosition())).get("suppliers_id"));
            i.putExtra("suppliers_name", (String) ((HashMap) SupplierAdapter.this.supplierData.get(getAdapterPosition())).get("suppliers_name"));
            i.putExtra("suppliers_contact_person", (String) ((HashMap) SupplierAdapter.this.supplierData.get(getAdapterPosition())).get("suppliers_contact_person"));
            i.putExtra("suppliers_cell", (String) ((HashMap) SupplierAdapter.this.supplierData.get(getAdapterPosition())).get("suppliers_cell"));
            i.putExtra("suppliers_email", (String) ((HashMap) SupplierAdapter.this.supplierData.get(getAdapterPosition())).get("suppliers_email"));
            i.putExtra("suppliers_address", (String) ((HashMap) SupplierAdapter.this.supplierData.get(getAdapterPosition())).get("suppliers_address"));
            SupplierAdapter.this.context.startActivity(i);
        }
    }
}
