package com.example.pointofsalef.product;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
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
import androidx.core.internal.view.SupportMenu;
import com.app.smartpos.R;

import com.example.pointofsalef.database.DatabaseAccess;
import com.example.pointofsalef.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class EditProductActivity extends BaseActivity {
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
    String mediaPath;
    String productID;
    String selectedCategoryID;
    String selectedSupplierID;
    String selectedWeightUnitID;
    ArrayAdapter<String> supplierAdapter;
    List<String> supplierNames;
    TextView txtChooseImage;
    TextView txtEditProduct;
    TextView txtUpdate;
    ArrayAdapter<String> weightUnitAdapter;
    List<String> weightUnitNames;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.product_details);
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
        this.txtEditProduct = (TextView) findViewById(R.id.txt_edit_product);
        this.txtUpdate = (TextView) findViewById(R.id.txt_update);
        this.txtChooseImage = (TextView) findViewById(R.id.txt_choose_image);
        this.imgProduct = (ImageView) findViewById(R.id.image_product);
        this.imgScanCode = (ImageView) findViewById(R.id.img_scan_code);
        this.txtEditProduct = (TextView) findViewById(R.id.txt_edit_product);
        this.productID = getIntent().getExtras().getString("product_id");
        this.etxtProductName.setEnabled(false);
        etxtProductCode.setEnabled(false);
        this.etxtProductCategory.setEnabled(false);
        this.etxtProductDescription.setEnabled(false);
        this.etxtProductBuyPrice.setEnabled(false);
        this.etxtProductSellPrice.setEnabled(false);
        this.etxtProductSupplier.setEnabled(false);
        this.etxtProdcutWeightUnit.setEnabled(false);
        this.etxtProductWeight.setEnabled(false);
        this.txtChooseImage.setEnabled(false);
        this.etxtProductStock.setEnabled(false);
        this.imgProduct.setEnabled(false);
        this.imgScanCode.setEnabled(false);
        this.txtUpdate.setVisibility(View.GONE);
        this.imgProduct.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(EditProductActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);
               startActivity(intent);
            }
        });
        this.txtChooseImage.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(EditProductActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);
                EditProductActivity.this.startActivityForResult(intent, 1213);
            }
        });
        this.imgScanCode.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditProductActivity.this.imgScanCode.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        Intent intent = new Intent(EditProductActivity.this, EditProductScannerViewActivity.class);
                        EditProductActivity.this.startActivity(intent);
                    }
                });
            }
        });
        this.categoryNames = new ArrayList();
        this.supplierNames = new ArrayList();
        this.weightUnitNames = new ArrayList();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<HashMap<String, String>> productData = databaseAccess.getProductsInfo(this.productID);
        String product_name = productData.get(0).get("product_name");
        String product_code = productData.get(0).get("product_code");
        String product_category_id = productData.get(0).get("product_category");
        String product_description = productData.get(0).get("product_description");
        String product_buy_price = productData.get(0).get("product_buy_price");
        String product_sell_price = productData.get(0).get("product_sell_price");
        String product_supplier_id = productData.get(0).get("product_supplier");
        String product_image = productData.get(0).get("product_image");
        String product_stock = productData.get(0).get("product_stock");
        String product_weight_unit_id = productData.get(0).get("product_weight_unit_id");
        String product_weight = productData.get(0).get("product_weight");
        this.etxtProductName.setText(product_name);
        etxtProductCode.setText(product_code);
        databaseAccess.open();
        String category_name = databaseAccess.getCategoryName(product_category_id);
        this.etxtProductCategory.setText(category_name);
        this.etxtProductDescription.setText(product_description);
        this.etxtProductBuyPrice.setText(product_buy_price);
        this.etxtProductSellPrice.setText(product_sell_price);
        databaseAccess.open();
        String supplier_name = databaseAccess.getSupplierName(product_supplier_id);
        this.etxtProductSupplier.setText(supplier_name);
        this.etxtProductStock.setText(product_stock);
        databaseAccess.open();
        String weight_unit_name = databaseAccess.getWeightUnitName(product_weight_unit_id);
        this.etxtProdcutWeightUnit.setText(weight_unit_name);
        this.etxtProductWeight.setText(product_weight);
        if (product_image != null) {
            if (product_image.length() < 6) {
                this.imgProduct.setImageResource(R.drawable.image_placeholder);
            } else {
                byte[] bytes = Base64.decode(product_image, 0);
                this.imgProduct.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        }
        this.selectedCategoryID = product_category_id;
        this.selectedSupplierID = product_supplier_id;
        this.selectedWeightUnitID = product_weight_unit_id;
        this.encodedImage = product_image;
        databaseAccess.open();
        final List<HashMap<String, String>> productCategory = databaseAccess.getProductCategory();
        databaseAccess.open();
        final List<HashMap<String, String>> productSupplier = databaseAccess.getProductSupplier();
        databaseAccess.open();
        final List<HashMap<String, String>> weightUnit = databaseAccess.getWeightUnit();
        int i = 0;
        while (true) {
            DatabaseAccess databaseAccess2 = databaseAccess;
            if (i >= productCategory.size()) {
                break;
            }
            String product_category_id2 = product_category_id;
            this.categoryNames.add(productCategory.get(i).get("category_name"));
            i++;
            databaseAccess = databaseAccess2;
            product_description = product_description;
            product_category_id = product_category_id2;
        }
        for (int i2 = 0; i2 < productSupplier.size(); i2++) {
            this.supplierNames.add(productSupplier.get(i2).get("suppliers_name"));
        }
        for (int i3 = 0; i3 < weightUnit.size(); i3++) {
            this.weightUnitNames.add(weightUnit.get(i3).get("weight_unit"));
        }
        this.etxtProductCategory.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditProductActivity.this.categoryAdapter = new ArrayAdapter<>(EditProductActivity.this,R.layout.category_item);
                EditProductActivity.this.categoryAdapter.addAll(EditProductActivity.this.categoryNames);
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditProductActivity.this);
                View dialogView = EditProductActivity.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                dialog_title.setText(R.string.product_category);
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) EditProductActivity.this.categoryAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.product.EditProductActivity.4.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        EditProductActivity.this.categoryAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.4.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.product.EditProductActivity.4.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = EditProductActivity.this.categoryAdapter.getItem(position);
                        String category_id = "0";
                        EditProductActivity.this.etxtProductCategory.setText(selectedItem);
                        for (int i4 = 0; i4 < EditProductActivity.this.categoryNames.size(); i4++) {
                            if (EditProductActivity.this.categoryNames.get(i4).equalsIgnoreCase(selectedItem)) {
                                category_id = (String) ((HashMap) productCategory.get(i4)).get("category_id");
                            }
                        }
                        EditProductActivity.this.selectedCategoryID = category_id;
                        Log.d("category_id", category_id);
                    }
                });
            }
        });
        this.etxtProductSupplier.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditProductActivity.this.supplierAdapter = new ArrayAdapter<>(EditProductActivity.this, R.layout.supplier_item);
                EditProductActivity.this.supplierAdapter.addAll(EditProductActivity.this.supplierNames);
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditProductActivity.this);
                View dialogView = EditProductActivity.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                dialog_title.setText("Suppliers");
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) EditProductActivity.this.supplierAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.product.EditProductActivity.5.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        EditProductActivity.this.supplierAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.5.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.product.EditProductActivity.5.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = EditProductActivity.this.supplierAdapter.getItem(position);
                        String supplier_id = "0";
                        EditProductActivity.this.etxtProductSupplier.setText(selectedItem);
                        for (int i4 = 0; i4 < EditProductActivity.this.supplierNames.size(); i4++) {
                            if (EditProductActivity.this.supplierNames.get(i4).equalsIgnoreCase(selectedItem)) {
                                supplier_id = (String) ((HashMap) productSupplier.get(i4)).get("suppliers_id");
                            }
                        }
                        EditProductActivity.this.selectedSupplierID = supplier_id;
                    }
                });
            }
        });
        this.etxtProdcutWeightUnit.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditProductActivity.this.weightUnitAdapter = new ArrayAdapter<>(EditProductActivity.this, R.layout.unit_item);
                EditProductActivity.this.weightUnitAdapter.addAll(EditProductActivity.this.weightUnitNames);
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditProductActivity.this);
                View dialogView = EditProductActivity.this.getLayoutInflater().inflate(R.layout.dialog_list_search, (ViewGroup) null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                dialog_title.setText(R.string.product_weight_unit);
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter((ListAdapter) EditProductActivity.this.weightUnitAdapter);
                dialog_input.addTextChangedListener(new TextWatcher() { // from class: com.app.smartpos.product.EditProductActivity.6.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        EditProductActivity.this.weightUnitAdapter.getFilter().filter(charSequence);
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable s) {
                    }
                });
                final AlertDialog alertDialog = dialog.create();
                dialog_button.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.6.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.app.smartpos.product.EditProductActivity.6.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialog.dismiss();
                        String selectedItem = EditProductActivity.this.weightUnitAdapter.getItem(position);
                        String weight_unit_id = "0";
                        EditProductActivity.this.etxtProdcutWeightUnit.setText(selectedItem);
                        for (int i4 = 0; i4 < EditProductActivity.this.weightUnitNames.size(); i4++) {
                            if (EditProductActivity.this.weightUnitNames.get(i4).equalsIgnoreCase(selectedItem)) {
                                weight_unit_id = (String) ((HashMap) weightUnit.get(i4)).get("weight_id");
                            }
                        }
                        EditProductActivity.this.selectedWeightUnitID = weight_unit_id;
                        Log.d("weight_unit", EditProductActivity.this.selectedWeightUnitID);
                    }
                });
            }
        });
        this.txtEditProduct.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditProductActivity.this.etxtProductName.setEnabled(true);
                EditProductActivity.etxtProductCode.setEnabled(true);
                EditProductActivity.this.etxtProductCategory.setEnabled(true);
                EditProductActivity.this.etxtProductDescription.setEnabled(true);
                EditProductActivity.this.etxtProductSellPrice.setEnabled(true);
                EditProductActivity.this.etxtProductBuyPrice.setEnabled(true);
                EditProductActivity.this.etxtProductSupplier.setEnabled(true);
                EditProductActivity.this.etxtProdcutWeightUnit.setEnabled(true);
                EditProductActivity.this.etxtProductWeight.setEnabled(true);
                EditProductActivity.this.etxtProductStock.setEnabled(true);
                EditProductActivity.this.txtChooseImage.setEnabled(true);
                EditProductActivity.this.imgProduct.setEnabled(true);
                EditProductActivity.this.imgScanCode.setEnabled(true);
//                EditProductActivity.this.etxtProductName.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.etxtProductCode.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.this.etxtProductCategory.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.this.etxtProductDescription.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.this.etxtProductSellPrice.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.this.etxtProductBuyPrice.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.this.etxtProductSupplier.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.this.etxtProdcutWeightUnit.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.this.etxtProductWeight.setTextColor(SupportMenu.CATEGORY_MASK);
//                EditProductActivity.this.etxtProductStock.setTextColor(SupportMenu.CATEGORY_MASK);
                EditProductActivity.this.imgProduct.setEnabled(true);
                EditProductActivity.this.imgScanCode.setEnabled(true);
                EditProductActivity.this.txtUpdate.setVisibility(View.VISIBLE);
                EditProductActivity.this.txtEditProduct.setVisibility(View.GONE);
            }
        });
        this.txtUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.app.smartpos.product.EditProductActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String product_name2 = EditProductActivity.this.etxtProductName.getText().toString();
                String product_code2 = EditProductActivity.etxtProductCode.getText().toString();
                String product_category = EditProductActivity.this.selectedCategoryID;
                String product_description2 = EditProductActivity.this.etxtProductDescription.getText().toString();
                String product_buy_price2 = EditProductActivity.this.etxtProductBuyPrice.getText().toString();
                String product_sell_price2 = EditProductActivity.this.etxtProductSellPrice.getText().toString();
                String product_stock2 = EditProductActivity.this.etxtProductStock.getText().toString();
                String product_supplier = EditProductActivity.this.selectedSupplierID;
                String str = EditProductActivity.this.selectedWeightUnitID;
                String product_weight2 = EditProductActivity.this.etxtProductWeight.getText().toString();
                if (product_name2.isEmpty()) {
                    EditProductActivity.this.etxtProductName.setError(EditProductActivity.this.getString(R.string.product_name_cannot_be_empty));
                    EditProductActivity.this.etxtProductName.requestFocus();
                } else if (product_category.isEmpty()) {
                    EditProductActivity.this.etxtProductCategory.setError(EditProductActivity.this.getString(R.string.product_category_cannot_be_empty));
                    EditProductActivity.this.etxtProductCategory.requestFocus();
                } else if (product_sell_price2.isEmpty()) {
                    EditProductActivity.this.etxtProductSellPrice.setError(EditProductActivity.this.getString(R.string.product_sell_price_cannot_be_empty));
                    EditProductActivity.this.etxtProductSellPrice.requestFocus();
                } else if (product_stock2.isEmpty()) {
                    EditProductActivity.this.etxtProductStock.setError(EditProductActivity.this.getString(R.string.product_stock_cannot_be_empty));
                    EditProductActivity.this.etxtProductStock.requestFocus();
                } else if (product_supplier.isEmpty()) {
                    EditProductActivity.this.etxtProductSupplier.setError(EditProductActivity.this.getString(R.string.product_supplier_cannot_be_empty));
                    EditProductActivity.this.etxtProductSupplier.requestFocus();
                } else if (product_weight2.isEmpty()) {
                    EditProductActivity.this.etxtProductWeight.setError(EditProductActivity.this.getString(R.string.product_weight_cannot_be_empty));
                    EditProductActivity.this.etxtProductWeight.requestFocus();
                } else {
                    DatabaseAccess databaseAccess3 = DatabaseAccess.getInstance(EditProductActivity.this);
                    databaseAccess3.open();
                    boolean check = databaseAccess3.updateProduct(product_name2, product_code2, product_category, product_description2, product_buy_price2, product_sell_price2, product_stock2, product_supplier, EditProductActivity.this.encodedImage, str, product_weight2, EditProductActivity.this.productID);
                    if (check) {
                        Toasty.success(EditProductActivity.this, (int) R.string.update_successfully, 0).show();
                        Intent intent = new Intent(EditProductActivity.this, ProductActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        EditProductActivity.this.startActivity(intent);
                        EditProductActivity.this.finish();
                        return;
                    }
                    Toasty.error(EditProductActivity.this, (int) R.string.failed, 0).show();
                }
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
