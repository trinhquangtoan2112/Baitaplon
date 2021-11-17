package com.example.testfile;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

public class PDFactivity extends AppCompatActivity {
    String filePath = "";
    File myExternalFile;
    private static final String My_Shared_FREFERENCES = "My_Shared_FREFERENCES";
    private Context mContext;
    PDFView pdfView;
    private String filename ="Luudulieudoc.txt";
    private String filepath ="pdfReader";
    PdfDocument.Page page;
    TextView pagenumber;
    Button button2;
    ImageButton save;
    EditText editText;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pdfactivity);
         getSupportActionBar().hide();

        PDFView pdfView = findViewById(R.id.pdfview);
        filePath = getIntent().getStringExtra("path");

        File file = new File(filePath);
        Uri path = Uri.fromFile(file);
        pdfView.fromUri(path)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(2)
                .load();
        getView();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pagenumber2 = 0;
                try {
                    pagenumber2 = Integer.parseInt((String.valueOf(editText.getText().toString())));
                } catch (NumberFormatException nfe) {
                    System.out.println("Khong thanh cong");

                }
                pdfView.jumpTo(pagenumber2 - 1);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pagenumber = pdfView.getCurrentPage();
                Toast.makeText(PDFactivity.this, "Đã lưu thành công:" + (pagenumber + 1), Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("Dulieu.txt", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Page", pagenumber);
                editor.apply();
            }
        });
    }
    public void getView(){
        button2=(Button) findViewById(R.id.move);
        editText=(EditText) findViewById(R.id.movepage);
        save=(ImageButton) findViewById(R.id.savepage);



    }



}