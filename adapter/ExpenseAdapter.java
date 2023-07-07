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
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.expense.EditExpenseActivity;

import es.dmoral.toasty.Toasty;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    private Context context;
    private List<HashMap<String, String>> expenseData;

    public ExpenseAdapter(Context context, List<HashMap<String, String>> expenseData) {
        this.context = context;
        this.expenseData = expenseData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this.context);
        final String expense_id = this.expenseData.get(position).get("expense_id");
        String expense_name = this.expenseData.get(position).get("expense_name");
        String expense_note = this.expenseData.get(position).get("expense_note");
        String expense_amount = this.expenseData.get(position).get("expense_amount");
        String date = this.expenseData.get(position).get("expense_date");
        String time = this.expenseData.get(position).get("expense_time");
        databaseAccess.open();
        String currency = databaseAccess.getCurrency();
        holder.txtExpenseName.setText(expense_name);
        holder.txtExpenseAmount.setText(currency + expense_amount);
        holder.txtExpenseDateTime.setText(date + " " + time);
        holder.txtExpenseNote.setText(this.context.getString(R.string.note) + expense_note);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.adapter.ExpenseAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseAdapter.this.context);
                builder.setMessage(R.string.want_to_delete_expense).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.ExpenseAdapter.1.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        databaseAccess.open();
                        boolean deleteProduct = databaseAccess.deleteExpense(expense_id);
                        if (deleteProduct) {
                            Toasty.error(ExpenseAdapter.this.context, (int) R.string.expense_deleted, 0).show();
                            ExpenseAdapter.this.expenseData.remove(holder.getAdapterPosition());
                            ExpenseAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(ExpenseAdapter.this.context, (int) R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.app.smartpos.adapter.ExpenseAdapter.1.1
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
        return this.expenseData.size();
    }

    /* loaded from: classes2.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgDelete;
        ImageView product_image;
        TextView txtExpenseAmount;
        TextView txtExpenseDateTime;
        TextView txtExpenseName;
        TextView txtExpenseNote;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtExpenseName = (TextView) itemView.findViewById(R.id.txt_expense_name);
            this.txtExpenseAmount = (TextView) itemView.findViewById(R.id.txt_expense_amount);
            this.txtExpenseNote = (TextView) itemView.findViewById(R.id.txt_expense_note);
            this.txtExpenseDateTime = (TextView) itemView.findViewById(R.id.txt_date_time);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            this.product_image = (ImageView) itemView.findViewById(R.id.product_image);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent i = new Intent(ExpenseAdapter.this.context, EditExpenseActivity.class);
            i.putExtra("expense_id", (String) ((HashMap) ExpenseAdapter.this.expenseData.get(getAdapterPosition())).get("expense_id"));
            i.putExtra("expense_name", (String) ((HashMap) ExpenseAdapter.this.expenseData.get(getAdapterPosition())).get("expense_name"));
            i.putExtra("expense_note", (String) ((HashMap) ExpenseAdapter.this.expenseData.get(getAdapterPosition())).get("expense_note"));
            i.putExtra("expense_amount", (String) ((HashMap) ExpenseAdapter.this.expenseData.get(getAdapterPosition())).get("expense_amount"));
            i.putExtra("expense_date", (String) ((HashMap) ExpenseAdapter.this.expenseData.get(getAdapterPosition())).get("expense_date"));
            i.putExtra("expense_time", (String) ((HashMap) ExpenseAdapter.this.expenseData.get(getAdapterPosition())).get("expense_time"));
            ExpenseAdapter.this.context.startActivity(i);
        }
    }
}
