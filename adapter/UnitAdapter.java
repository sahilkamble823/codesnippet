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
import com.example.pointofsalef.settings.unit.EditUnitActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.MyViewHolder> {
    private Context context;
    private List<HashMap<String, String>> unitData;

    public UnitAdapter(Context context, List<HashMap<String, String>> unitData) {
        this.context = context;
        this.unitData = unitData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String weightId = this.unitData.get(position).get("weight_id");
        String weightUnit = this.unitData.get(position).get("weight_unit");
        holder.txtUnitName.setText(weightUnit);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.UnitAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UnitAdapter.this.context);
                builder.setMessage(R.string.want_to_delete).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.UnitAdapter.1.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(UnitAdapter.this.context);
                        databaseAccess.open();
                        boolean deleteCustomer = databaseAccess.deleteUnit(weightId);
                        if (deleteCustomer) {
                            Toasty.success(UnitAdapter.this.context, (int) R.string.unit_deleted, 0).show();
                            UnitAdapter.this.unitData.remove(holder.getAdapterPosition());
                            UnitAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toasty.error(UnitAdapter.this.context, (int) R.string.failed, 0).show();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.UnitAdapter.1.1
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
        return this.unitData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgDelete;
        TextView txtUnitName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtUnitName = (TextView) itemView.findViewById(R.id.txt_unit_name);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent i = new Intent(UnitAdapter.this.context, EditUnitActivity.class);
            i.putExtra("weight_id", (String) ((HashMap) UnitAdapter.this.unitData.get(getAdapterPosition())).get("weight_id"));
            i.putExtra("weight_unit", (String) ((HashMap) UnitAdapter.this.unitData.get(getAdapterPosition())).get("weight_unit"));
            UnitAdapter.this.context.startActivity(i);
        }
    }
}
