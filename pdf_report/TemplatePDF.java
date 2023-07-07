package com.example.pointofsalef.pdf_report;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontFamily;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class TemplatePDF {
    private Context context;
    private Document document;
    private Paragraph paragraph;
    private File pdfFile;
    PdfWriter pdfWriter;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 6.0f, 0, BaseColor.BLACK);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 4.0f, 2, BaseColor.BLACK);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 4.0f, 2, BaseColor.BLACK);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 4.0f, 2, BaseColor.BLACK);
    private Font fRowText = new Font(Font.FontFamily.TIMES_ROMAN, 4.0f, 2, BaseColor.BLACK);

    public TemplatePDF(Context context) {
        this.context = context;
    }

    public void openDocument() {
        createFile();
        try {
            Rectangle pageSize = new Rectangle(164.41f, 500.41f);
            Document document = new Document(pageSize);
            this.document = document;
            this.pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(this.pdfFile));
            this.document.open();
        } catch (Exception e) {
            Log.e("createFile", e.toString());
        }
    }

    private void createFile() {
        String dest = this.context.getExternalFilesDir(null) + "/";
        File folder = new File(dest);
        if (!folder.exists()) {
            folder.mkdir();
        }
        this.pdfFile = new File(folder, "order_receipt.pdf");
    }

    public void closeDocument() {
        this.document.close();
    }

    public void addMetaData(String title, String subject, String author) {
        this.document.addTitle(title);
        this.document.addSubject(subject);
        this.document.addAuthor(author);
    }

    public void addTitle(String title, String subTitle, String date) {
        try {
            this.paragraph = new Paragraph();
            addChildP(new Paragraph(title, this.fTitle));
            addChildP(new Paragraph(subTitle, this.fSubTitle));
            addChildP(new Paragraph("Order Date:" + date, this.fHighText));
            this.document.add(this.paragraph);
        } catch (Exception e) {
            Log.e("addTitle", e.toString());
        }
    }

    public void addImage(Bitmap bm) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] byteArray = stream.toByteArray();
            Image img = Image.getInstance(byteArray);
            img.setAlignment(6);
            img.setAlignment(1);
            img.scaleAbsolute(80.0f, 20.0f);
            this.document.add(img);
        } catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }
    }

    public void addChildP(Paragraph childParagraph) {
        childParagraph.setAlignment(1);
        this.paragraph.add((Element) childParagraph);
    }

    public void addParagraph(String text) {
        try {
            Paragraph paragraph = new Paragraph(text, this.fText);
            this.paragraph = paragraph;
            paragraph.setAlignment(1);
            this.document.add(this.paragraph);
        } catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }
    }

    public void addRightParagraph(String text) {
        try {
            Paragraph paragraph = new Paragraph(text, this.fText);
            this.paragraph = paragraph;
            paragraph.setSpacingAfter(5.0f);
            this.paragraph.setSpacingBefore(5.0f);
            this.paragraph.setAlignment(1);
            this.document.add(this.paragraph);
        } catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }
    }

    public void createTable(String[] header, ArrayList<String[]> clients) {
        try {
            Paragraph paragraph = new Paragraph();
            this.paragraph = paragraph;
            paragraph.setFont(this.fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100.0f);
            pdfPTable.setSpacingBefore(5.0f);
            for (String str : header) {
                PdfPCell pdfPCell = new PdfPCell(new Phrase(str, this.fSubTitle));
                pdfPCell.setHorizontalAlignment(1);
                pdfPCell.setBorderColor(BaseColor.GRAY);
                pdfPTable.addCell(pdfPCell);
            }
            for (int indexR = 0; indexR < clients.size(); indexR++) {
                String[] row = clients.get(indexR);
                for (int indexC = 0; indexC < header.length; indexC++) {
                    PdfPCell pdfPCell2 = new PdfPCell(new Phrase(row[indexC], this.fRowText));
                    pdfPCell2.setHorizontalAlignment(1);
                    pdfPCell2.setBorder(0);
                    pdfPTable.addCell(pdfPCell2);
                }
            }
            this.paragraph.add((Element) pdfPTable);
            this.document.add(this.paragraph);
        } catch (Exception e) {
            Log.e("createTable", e.toString());
        }
    }

    public void viewPDF() {
        Intent intent = new Intent(this.context, ViewPDFActivity.class);
        intent.putExtra("path", this.pdfFile.getAbsolutePath());
        Log.d("Path", this.pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
    }
}
