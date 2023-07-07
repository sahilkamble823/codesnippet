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
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class AddExpenseActivity extends BaseActivity {
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
    TextView txtAddExpense;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_expense);
        this.etxtExpenseName = (EditText) findViewById(R.id.etxt_expense_name);
        this.etxtExpenseNote = (EditText) findViewById(R.id.etxt_expense_note);
        this.etxtExpenseAmount = (EditText) findViewById(R.id.etxt_expense_amount);
        this.etxtDate = (EditText) findViewById(R.id.etxt_date);
        this.etxtTime = (EditText) findViewById(R.id.etxt_time);
        this.txtAddExpense = (TextView) findViewById(R.id.txt_add_expense);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date());
        this.etxtDate.setText(currentDate);
        this.etxtTime.setText(currentTime);
        this.etxtDate.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.expense.AddExpenseActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddExpenseActivity.this.datePicker();
            }
        });
        this.etxtTime.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.expense.AddExpenseActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddExpenseActivity.this.tiemPicker();
            }
        });
        this.txtAddExpense.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.expense.AddExpenseActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String expense_name = AddExpenseActivity.this.etxtExpenseName.getText().toString();
                String expense_note = AddExpenseActivity.this.etxtExpenseNote.getText().toString();
                String expense_amount = AddExpenseActivity.this.etxtExpenseAmount.getText().toString();
                String expense_date = AddExpenseActivity.this.etxtDate.getText().toString();
                String expense_time = AddExpenseActivity.this.etxtTime.getText().toString();
                if (expense_name.isEmpty()) {
                    AddExpenseActivity.this.etxtExpenseName.setError(AddExpenseActivity.this.getString(R.string.expense_name_cannot_be_empty));
                    AddExpenseActivity.this.etxtExpenseName.requestFocus();
                } else if (expense_amount.isEmpty()) {
                    AddExpenseActivity.this.etxtExpenseAmount.setError(AddExpenseActivity.this.getString(R.string.expense_amount_cannot_be_empty));
                    AddExpenseActivity.this.etxtExpenseAmount.requestFocus();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddExpenseActivity.this);
                    databaseAccess.open();
                    boolean check = databaseAccess.addExpense(expense_name, expense_amount, expense_note, expense_date, expense_time);
                    if (check) {
                        Toasty.success(AddExpenseActivity.this, (int) R.string.expense_successfully_added, 0).show();
                        Intent intent = new Intent(AddExpenseActivity.this, ExpenseActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        AddExpenseActivity.this.startActivity(intent);
                        return;
                    }
                    Toasty.error(AddExpenseActivity.this, (int) R.string.failed, 0).show();
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() { // from class: com.app.smartpos.expense.AddExpenseActivity.4
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
                AddExpenseActivity.this.date_time = year + "-" + fm + "-" + fd;
                AddExpenseActivity.this.etxtDate.setText(AddExpenseActivity.this.date_time);
            }
        }, this.mYear, this.mMonth, this.mDay);
        datePickerDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tiemPicker() {
        Calendar c = Calendar.getInstance();
        this.mHour = c.get(11);
        this.mMinute = c.get(12);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() { // from class: com.app.smartpos.expense.AddExpenseActivity.5
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm;
                AddExpenseActivity.this.mHour = hourOfDay;
                AddExpenseActivity.this.mMinute = minute;
                if (AddExpenseActivity.this.mHour < 12) {
                    am_pm = "AM";
                    AddExpenseActivity.this.mHour = hourOfDay;
                } else {
                    am_pm = "PM";
                    AddExpenseActivity.this.mHour = hourOfDay - 12;
                }
                AddExpenseActivity.this.etxtTime.setText(AddExpenseActivity.this.mHour + ":" + minute + " " + am_pm);
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
