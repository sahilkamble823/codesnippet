package com.example.pointofsalef.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ajts.androidmads.library.ExcelToSQLite;

import com.app.smartpos.R;

import com.example.pointofsalef.HomeActivity;
import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.database.DatabaseOpenHelper;
import com.example.pointofsalef.utils.BaseActivity;

import com.obsez.android.lib.filechooser.ChooserDialog;
import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class AddProductActivity extends BaseActivity {
    public static EditText etxtProductCode;
    ArrayAdapter<String> categoryAdapter;
    List<String> categoryNames;
    String encodedImage = "N/A";
    EditText etxtProdcutWeightUnit;
    EditText etxtProductBuyPrice;
    EditText etxtProductCategory;
    EditText etxtProductDescription;
    EditText etxtProductName;
    EditText etxtProductSellPrice;
    EditText etxtProductStock;
    EditText etxtProductSupplier;
    EditText etxtProductWeight;
    ImageView imgProduct;
    ImageView imgScanCode;
    ProgressDialog loading;
    String mediaPath;
    String selectedCategoryID;
    String selectedSupplierID;
    String selectedWeightUnitID;
    ArrayAdapter<String> supplierAdapter;
    List<String> supplierNames;
    TextView txtAddProdcut;
    TextView txtChooseImage;
    ArrayAdapter<String> weightUnitAdapter;
    List<String> weightUnitNames;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_product);
        this.etxtProductName = (EditText) findViewById(R.id.etxt_product_name);
        etxtProductCode = (EditText) findViewById(R.id.etxt_product_code);
        this.etxtProductCategory = (EditText) findViewById(R.id.etxt_product_category);
        this.etxtProductDescription = (EditText) findViewById(R.id.etxt_product_description);
        this.etxtProductBuyPrice = (EditText) findViewById(R.id.etxt_buy_price);
        this.etxtProductSellPrice = (EditText) findViewById(R.id.etxt_product_sell_price);
        this.etxtProductStock = (EditText) findViewById(R.id.etxt_product_stock);
        this.etxtProductSupplier = (EditText) findViewById(R.id.etxt_supplier);
        this.etxtProdcutWeightUnit = (EditText) findViewById(R.id.etxt_product_weight_unit);
        this.etxtProductWeight = (EditText) findViewById(R.id.etxt_product_weight);
        this.txtAddProdcut = (TextView) findViewById(R.id.txt_add_product);
        this.imgProduct = (ImageView) findViewById(R.id.image_product);
        this.imgScanCode = (ImageView) findViewById(R.id.img_scan_code);
        this.txtChooseImage = (TextView) findViewById(R.id.txt_choose_image);
        this.imgScanCode.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, ScannerViewActivity.class);
                AddProductActivity.this.startActivity(intent);
            }
        });
        this.txtChooseImage.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);
//                AddProductActivity.this.startActivityForResult(intent, 1213);
                startActivity(intent);
            }
        });
        this.imgProduct.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);
//                AddProductActivity.this.startActivityForResult(intent, 1213);
                startActivity(intent);
            }
        });
        this.categoryNames = new ArrayList();
        this.supplierNames = new ArrayList();
        this.weightUnitNames = new ArrayList();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        final List<HashMap<String, String>> productCategory = databaseAccess.getProductCategory();
        databaseAccess.open();
        final List<HashMap<String, String>> productSupplier = databaseAccess.getProductSupplier();
        databaseAccess.open();
        final List<HashMap<String, String>> weightUnit = databaseAccess.getWeightUnit();
        for (int i = 0; i < productCategory.size(); i++) {
            this.categoryNames.add(productCategory.get(i).get("category_name"));
        }
        for (int i2 = 0; i2 < productSupplier.size(); i2++) {
            this.supplierNames.add(productSupplier.get(i2).get("suppliers_name"));
        }
        for (int i3 = 0; i3 < weightUnit.size(); i3++) {
            this.weightUnitNames.add(weightUnit.get(i3).get("weight_unit"));
        }
        this.etxtProductCategory.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddProductActivity.this.categoryAdapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.category_item);
                AddProductActivity.this.categoryAdapter.addAll(AddProductActivity.this.categoryNames);
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddProductActivity.this);
                View dialogView = AddProductActivity.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                dialog_title.setText(R.string.product_category);
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) AddProductActivity.this.categoryAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.product.AddProductActivity.4.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        AddProductActivity.this.categoryAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.4.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.product.AddProductActivity.4.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = AddProductActivity.this.categoryAdapter.getItem(position);
                        String category_id = "0";
                        AddProductActivity.this.etxtProductCategory.setText(selectedItem);
                        for (int i4 = 0; i4 < AddProductActivity.this.categoryNames.size(); i4++) {
                            if (AddProductActivity.this.categoryNames.get(i4).equalsIgnoreCase(selectedItem)) {
                                category_id = (String) ((HashMap) productCategory.get(i4)).get("category_id");
                            }
                        }
                        AddProductActivity.this.selectedCategoryID = category_id;
                        Log.d("category_id", category_id);
                    }
                });
            }
        });
        this.etxtProductSupplier.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddProductActivity.this.supplierAdapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.supplier_item);
                AddProductActivity.this.supplierAdapter.addAll(AddProductActivity.this.supplierNames);
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddProductActivity.this);
                View dialogView = AddProductActivity.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                dialog_title.setText(R.string.suppliers);
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) AddProductActivity.this.supplierAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.product.AddProductActivity.5.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        AddProductActivity.this.supplierAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.5.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.product.AddProductActivity.5.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = AddProductActivity.this.supplierAdapter.getItem(position);
                        String supplier_id = "0";
                        AddProductActivity.this.etxtProductSupplier.setText(selectedItem);
                        for (int i4 = 0; i4 < AddProductActivity.this.supplierNames.size(); i4++) {
                            if (AddProductActivity.this.supplierNames.get(i4).equalsIgnoreCase(selectedItem)) {
                                supplier_id = (String) ((HashMap) productSupplier.get(i4)).get("suppliers_id");
                            }
                        }
                        AddProductActivity.this.selectedSupplierID = supplier_id;
                    }
                });
            }
        });
        this.etxtProdcutWeightUnit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddProductActivity.this.weightUnitAdapter = new ArrayAdapter<>(AddProductActivity.this, R.layout.unit_item);
                AddProductActivity.this.weightUnitAdapter.addAll(AddProductActivity.this.weightUnitNames);
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddProductActivity.this);
                View dialogView = AddProductActivity.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                dialog_title.setText(R.string.product_weight_unit);
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) AddProductActivity.this.weightUnitAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.product.AddProductActivity.6.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        AddProductActivity.this.weightUnitAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.6.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.product.AddProductActivity.6.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = AddProductActivity.this.weightUnitAdapter.getItem(position);
                        String weight_unit_id = "0";
                        AddProductActivity.this.etxtProdcutWeightUnit.setText(selectedItem);
                        for (int i4 = 0; i4 < AddProductActivity.this.weightUnitNames.size(); i4++) {
                            if (AddProductActivity.this.weightUnitNames.get(i4).equalsIgnoreCase(selectedItem)) {
                                weight_unit_id = (String) ((HashMap) weightUnit.get(i4)).get("weight_id");
                            }
                        }
                        AddProductActivity.this.selectedWeightUnitID = weight_unit_id;
                        Log.d("weight_unit", AddProductActivity.this.selectedWeightUnitID);
                    }
                });
            }
        });
        this.txtAddProdcut.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.AddProductActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String product_name = AddProductActivity.this.etxtProductName.getText().toString();
                String product_code = AddProductActivity.etxtProductCode.getText().toString();
                String product_category_name = AddProductActivity.this.etxtProductCategory.getText().toString();
                String product_category_id = AddProductActivity.this.selectedCategoryID;
                String product_description = AddProductActivity.this.etxtProductDescription.getText().toString();
                String product_buy_price = AddProductActivity.this.etxtProductBuyPrice.getText().toString();
                String product_sell_price = AddProductActivity.this.etxtProductSellPrice.getText().toString();
                String product_stock = AddProductActivity.this.etxtProductStock.getText().toString();
                String product_supplier_name = AddProductActivity.this.etxtProductSupplier.getText().toString();
                String product_supplier = AddProductActivity.this.selectedSupplierID;
                String product_Weight_unit_name = AddProductActivity.this.etxtProdcutWeightUnit.getText().toString();
                String product_weight_unit_id = AddProductActivity.this.selectedWeightUnitID;
                String product_weight = AddProductActivity.this.etxtProductWeight.getText().toString();
                if (product_name.isEmpty()) {
                    AddProductActivity.this.etxtProductName.setError(AddProductActivity.this.getString(R.string.product_name_cannot_be_empty));
                    AddProductActivity.this.etxtProductName.requestFocus();
                    return;
                }
                if (!product_category_name.isEmpty() && !product_category_id.isEmpty()) {
                    if (product_sell_price.isEmpty()) {
                        AddProductActivity.this.etxtProductSellPrice.setError(AddProductActivity.this.getString(R.string.product_sell_price_cannot_be_empty));
                        AddProductActivity.this.etxtProductSellPrice.requestFocus();
                        return;
                    }
                    if (!product_Weight_unit_name.isEmpty() && !product_weight.isEmpty()) {
                        if (product_stock.isEmpty()) {
                            AddProductActivity.this.etxtProductStock.setError(AddProductActivity.this.getString(R.string.product_stock_cannot_be_empty));
                            AddProductActivity.this.etxtProductStock.requestFocus();
                            return;
                        }
                        if (!product_supplier_name.isEmpty() && !product_supplier.isEmpty()) {
                            DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(AddProductActivity.this);
                            databaseAccess2.open();
                            boolean check = databaseAccess2.addProduct(product_name, product_code, product_category_id, product_description, product_buy_price, product_sell_price, product_stock, product_supplier, AddProductActivity.this.encodedImage, product_weight_unit_id, product_weight);
                            if (check) {
                                Toasty.success(AddProductActivity.this, (int) R.string.product_successfully_added, 0).show();
                                Intent intent = new Intent(AddProductActivity.this, ProductActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                AddProductActivity.this.startActivity(intent);
                                return;
                            }
                            Toasty.error(AddProductActivity.this, (int) R.string.failed, 0).show();
                            return;
                        }
                        AddProductActivity.this.etxtProductSupplier.setError(AddProductActivity.this.getString(R.string.product_supplier_cannot_be_empty));
                        AddProductActivity.this.etxtProductSupplier.requestFocus();
                        return;
                    }
                    AddProductActivity.this.etxtProductWeight.setError(AddProductActivity.this.getString(R.string.product_weight_cannot_be_empty));
                    AddProductActivity.this.etxtProductWeight.requestFocus();
                    return;
                }
                AddProductActivity.this.etxtProductCategory.setError(AddProductActivity.this.getString(R.string.product_category_cannot_be_empty));
                AddProductActivity.this.etxtProductCategory.requestFocus();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == -1 && data != null) {
            try {
                String stringExtra = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                this.mediaPath = stringExtra;
                Bitmap selectedImage = BitmapFactory.decodeFile(stringExtra);
                this.imgProduct.setImageBitmap(selectedImage);
                this.encodedImage = encodeImage(selectedImage);
            } catch (Exception e) {
                Toast.makeText(this, (int) R.string.something_went_wrong, Toast.LENGTH_LONG).show();
            }
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, 0);
        return encImage;
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.menu_import /* 2131296653 */:
                fileChooser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onImport(String path) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, (int) R.string.no_file_found, Toast.LENGTH_SHORT).show();
            return;
        }
        ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), DatabaseOpenHelper.DATABASE_NAME, false);
        excelToSQLite.importFromFile(path, new ExcelToSQLite.ImportListener() { // from class: com.app.smartpos.product.AddProductActivity.8
            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onStart() {
                AddProductActivity.this.loading = new ProgressDialog(AddProductActivity.this);
                AddProductActivity.this.loading.setMessage(AddProductActivity.this.getString(R.string.data_importing_please_wait));
                AddProductActivity.this.loading.setCancelable(false);
                AddProductActivity.this.loading.show();
            }

            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onCompleted(String dbName) {
                Handler mHand = new Handler();
                mHand.postDelayed(new Runnable() { // from class: com.app.smartpos.product.AddProductActivity.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AddProductActivity.this.loading.dismiss();
                        Toasty.success(AddProductActivity.this, (int) R.string.data_successfully_imported, 0).show();
                        Intent intent = new Intent(AddProductActivity.this, HomeActivity.class);
                        AddProductActivity.this.startActivity(intent);
                        AddProductActivity.this.finish();
                    }
                }, 5000L);
            }

            @Override // com.ajts.androidmads.library.ExcelToSQLite.ImportListener
            public void onError(Exception e) {
                AddProductActivity.this.loading.dismiss();
                Log.d("Error : ", "" + e.getMessage());
                Toasty.error(AddProductActivity.this, (int) R.string.data_import_fail, 0).show();
            }
        });
    }

    public void fileChooser() {
        new ChooserDialog((Activity) this).displayPath(true).withFilter(false, false, "xls").withChosenListener(new ChooserDialog.Result() { // from class: com.app.smartpos.product.AddProductActivity.10
            @Override // com.obsez.android.lib.filechooser.ChooserDialog.Result
            public void onChoosePath(String path, File pathFile) {
                AddProductActivity.this.onImport(path);
            }
        }).withOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.app.smartpos.product.AddProductActivity.9
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                Log.d("CANCEL", "CANCEL");
                dialog.cancel();
            }
        }).build().show();
    }
}
