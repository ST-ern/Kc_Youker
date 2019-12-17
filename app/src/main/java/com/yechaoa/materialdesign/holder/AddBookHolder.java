package com.yechaoa.materialdesign.holder;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.model.AddBook.AddBookItemClickListener;

public class AddBookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView add_book_title;
    AddBookItemClickListener addBookItemClickListener;

    public AddBookHolder(@NonNull View itemView) {
        super(itemView);

        this.add_book_title=(TextView)itemView.findViewById(R.id.tv_add_book_title);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.addBookItemClickListener.onAddBookItemClickListener(v, getLayoutPosition());
    }

    public void setAddBookItemClickListener(AddBookItemClickListener ac) {
        this.addBookItemClickListener = ac;

    }
}
