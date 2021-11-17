package com.example.testfile;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class mainholder extends RecyclerView.ViewHolder {

    public TextView txt;
    public CardView cardView;



    public mainholder(@NonNull View itemView) {
        super(itemView);

        txt = itemView.findViewById(R.id.pdf_txtname);
        cardView = itemView.findViewById(R.id.pdf_cardview);
    }
}
