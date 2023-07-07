package com.example.pointofsalef.expense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.core.internal.view.SupportMenu;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class EditExpenseActivity extends BaseActivity {
    String date_time = "";
    EditText etxtDate;
    EditText etxtExpenseAmount;
    EditText etxtExpenseName;
    EditText etxtExpenseNote;
    EditText etxtTime;
    int mDay;
    int mHour;
    int mMinute;
    int mMonth;
    int mYear;
    TextView txtEditExpense;
    TextView txtUpdateExpense;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.edit_expense);
        this.etxtExpenseName = (EditText) findViewById(R.id.etxt_expense_name);
        this.etxtExpenseNote = (EditText) findViewById(R.id.etxt_expense_note);
        this.etxtExpenseAmount = (EditText) findViewById(R.id.etxt_expense_amount);
        this.etxtDate = (EditText) findViewById(R.id.etxt_date);
        this.etxtTime = (EditText) findViewById(R.id.etxt_time);
        this.txtEditExpense = (TextView) findViewById(R.id.txt_edit_expense);
        this.txtUpdateExpense = (TextView) findViewById(R.id.txt_update_expense);
        final String get_expense_id = getIntent().getExtras().getString("expense_id");
        String get_expense_name = getIntent().getExtras().getString("expense_name");
        String get_expense_note = getIntent().getExtras().getString("expense_note");
        String get_expense_amount = getIntent().getExtras().getString("expense_amount");
        String get_expense_date = getIntent().getExtras().getString("expense_date");
        String get_expense_time = getIntent().getExtras().getString("expense_time");
        this.etxtExpenseName.setText(get_expense_name);
        this.etxtExpenseNote.setText(get_expense_note);
        this.etxtExpenseAmount.setText(get_expense_amount);
        this.etxtDate.setText(get_expense_date);
        this.etxtTime.setText(get_expense_time);
        this.etxtExpenseName.setEnabled(false);
        this.etxtExpenseNote.setEnabled(false);
        this.etxtExpenseAmount.setEnabled(false);
        this.etxtDate.setEnabled(false);
        this.etxtTime.setEnabled(false);
        this.txtUpdateExpense.setVisibility(View.INVISIBLE);
        this.etxtDate.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.expense.EditExpenseActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditExpenseActivity.this.datePicker();
            }
        });
        this.etxtTime.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.expense.EditExpenseActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditExpenseActivity.this.timePicker();
            }
        });
        this.txtEditExpense.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.expense.EditExpenseActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditExpenseActivity.this.etxtExpenseName.setEnabled(true);
                EditExpenseActivity.this.etxtExpenseNote.setEnabled(true);
                EditExpenseActivity.this.etxtExpenseAmount.setEnabled(true);
                EditExpenseActivity.this.etxtDate.setEnabled(true);
                EditExpenseActivity.this.etxtTime.setEnabled(true);
                EditExpenseActivity.this.txtUpdateExpense.setVisibility(View.VISIBLE);
                EditExpenseActivity.this.txtEditExpense.setVisibility(View.GONE);
            }
        });
        this.txtUpdateExpense.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.expense.EditExpenseActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String expense_name = EditExpenseActivity.this.etxtExpenseName.getText().toString();
                String expense_note = EditExpenseActivity.this.etxtExpenseNote.getText().toString();
                String expense_amount = EditExpenseActivity.this.etxtExpenseAmount.getText().toString();
                String expense_date = EditExpenseActivity.this.etxtDate.getText().toString();
                String expense_time = EditExpenseActivity.this.etxtTime.getText().toString();
                if (expense_name.isEmpty()) {
                    EditExpenseActivity.this.etxtExpenseName.setError(EditExpenseActivity.this.getString(R.string.expense_name_cannot_be_empty));
                    EditExpenseActivity.this.etxtExpenseName.requestFocus();
                } else if (expense_amount.isEmpty()) {
                    EditExpenseActivity.this.etxtExpenseAmount.setError(EditExpenseActivity.this.getString(R.string.expense_amount_cannot_be_empty));
                    EditExpenseActivity.this.etxtExpenseAmount.requestFocus();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(EditExpenseActivity.this);
                    databaseAccess.open();
                    boolean check = databaseAccess.updateExpense(get_expense_id, expense_name, expense_amount, expense_note, expense_date, expense_time);
                    if (check) {
                        Toasty.success(EditExpenseActivity.this, (int) R.string.update_successfully, 0).show();
                        Intent intent = new Intent(EditExpenseActivity.this, ExpenseActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        EditExpenseActivity.this.startActivity(intent);
                        return;
                    }
                    Toasty.error(EditExpenseActivity.this, (int) R.string.failed, 0).show();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void datePicker() {
        Calendar c = Calendar.getInstance();
        this.mYear = c.get(1);
        this.mMonth = c.get(2);
        this.mDay = c.get(5);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() { // from class: com.app.smartpos.expense.EditExpenseActivity.5
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String fm = "" + month;
                String fd = "" + dayOfMonth;
                if (monthOfYear < 10) {
                    fm = "0" + month;
                }
                if (dayOfMonth < 10) {
                    fd = "0" + dayOfMonth;
                }
                EditExpenseActivity.this.date_time = year + "-" + fm + "-" + fd;
                EditExpenseActivity.this.etxtDate.setText(EditExpenseActivity.this.date_time);
            }
        }, this.mYear, this.mMonth, this.mDay);
        datePickerDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void timePicker() {
        Calendar c = Calendar.getInstance();
        this.mHour = c.get(11);
        this.mMinute = c.get(12);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() { // from class: com.app.smartpos.expense.EditExpenseActivity.6
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm;
                EditExpenseActivity.this.mHour = hourOfDay;
                EditExpenseActivity.this.mMinute = minute;
                if (EditExpenseActivity.this.mHour < 12) {
                    am_pm = "AM";
                    EditExpenseActivity.this.mHour = hourOfDay;
                } else {
                    am_pm = "PM";
                    EditExpenseActivity.this.mHour = hourOfDay - 12;
                }
                EditExpenseActivity.this.etxtTime.setText(EditExpenseActivity.this.mHour + ":" + minute + " " + am_pm);
            }
        }, this.mHour, this.mMinute, false);
        timePickerDialog.show();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
