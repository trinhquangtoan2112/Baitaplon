package com.example.testfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class MainAdpter extends RecyclerView.Adapter<mainholder> {

    private Context context;
    private List<File> pdfFile;
    private OnPDFSelectList listener;

    public MainAdpter(Context context, List<File> pdfFile, OnPDFSelectList listener) {
        this.context = context;
        this.pdfFile = pdfFile;
        this.listener = listener;
    }

    @NonNull
    @Override
    public mainholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mainholder(LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull mainholder holder, int position) {
        holder.txt.setText(pdfFile.get(position).getName());
        holder.txt.setSelected(true);



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 listener.onPDFSelected(pdfFile.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfFile.size();
    }
}
