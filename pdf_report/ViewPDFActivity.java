package com.example.pointofsalef.pdf_report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import com.app.smartpos.R;

import com.example.pointofsalef.utils.BaseActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.pdf.PdfObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class ViewPDFActivity extends BaseActivity {
    private File file;
    private PDFView pdfView;
    private Context primaryBaseActivity;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_p_d_f);
        this.pdfView = (PDFView) findViewById(R.id.pdfView);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.order_receipt);
        Bundle bundle = getIntent().getExtras();
        Log.d("location", bundle.toString());
        String dest = getExternalFilesDir(null) + "/";
        if (bundle != null) {
            this.file = new File(dest, "order_receipt.pdf");
        }
        this.pdfView.fromFile(this.file).enableSwipe(true).swipeHorizontal(false).enableDoubletap(true).enableAntialiasing(true).load();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_pdf, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Toast.makeText(this, (int) R.string.share, Toast.LENGTH_SHORT).show();
            sharePdfFile();
            return true;
        }
        if (id == R.id.action_open_pdf) {
            Toast.makeText(this, (int) R.string.open_with_external_pdf_reader, Toast.LENGTH_SHORT).show();
            openWithExternalPdfApp();
        } else if (id == R.id.action_print) {
            printPDf();
        } else if (id == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sharePdfFile() {
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", this.file);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setDataAndType(uri, "application/pdf");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    public void openWithExternalPdfApp() {
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", this.file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "application/pdf");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    public void printPDf() {
        if (Build.VERSION.SDK_INT >= 19) {
            PrintManager printManager = (PrintManager) this.primaryBaseActivity.getSystemService(Context.PRINT_SERVICE);
            String jobName = getString(R.string.app_name) + "Order Receipt";
            PrintDocumentAdapter pda = new PrintDocumentAdapter() { // from class: com.app.smartpos.pdf_report.ViewPDFActivity.1
                @Override // android.print.PrintDocumentAdapter
                public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
                    InputStream input = null;
                    OutputStream output = null;
                    try {
                        try {
                            File folder = new File(Environment.getExternalStorageDirectory().toString(), PdfObject.TEXT_PDFDOCENCODING);
                            if (!folder.exists()) {
                                folder.mkdir();
                            }
                            File file = new File(folder, "order_receipt.pdf");
                            input = new FileInputStream(file);
                            output = new FileOutputStream(destination.getFileDescriptor());
                            byte[] buf = new byte[1024];
                            while (true) {
                                int bytesRead = input.read(buf);
                                if (bytesRead > 0) {
                                    output.write(buf, 0, bytesRead);
                                } else {
                                    callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                                    input.close();
                                    output.close();
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            if (input != null) {
                                input.close();
                            }
                            if (output != null) {
                                output.close();
                            }
                        } catch (Throwable th) {
                            if (input != null) {
                                try {
                                    input.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                    throw th;
                                }
                            }
                            if (output != null) {
                                output.close();
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }

                @Override // android.print.PrintDocumentAdapter
                public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
                    if (cancellationSignal.isCanceled()) {
                        callback.onLayoutCancelled();
                        return;
                    }
                    PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("Name of file").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
                    callback.onLayoutFinished(pdi, true);
                }
            };
            if (printManager != null) {
                PrintAttributes.Builder builder = new PrintAttributes.Builder();
                builder.setMediaSize(PrintAttributes.MediaSize.PRC_6);
                printManager.print(jobName, pda, builder.build());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.app.smartpos.utils.BaseActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public void attachBaseContext(Context base) {
        this.primaryBaseActivity = base;
        super.attachBaseContext(base);
    }
}
