package com.yechaoa.materialdesign.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.model.Book.BookItemClickListener;

public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView book_title;
    public ImageButton book_delete, book_enter;
    BookItemClickListener bookItemClickListener;
//    BookItemDeleteListener bookItemDeleteClickListener;

    public BookHolder(@NonNull View itemView) {
        super(itemView);

        this.book_title=(TextView)itemView.findViewById(R.id.tv_book_card_name);
        this.book_delete=(ImageButton)itemView.findViewById(R.id.btn_delete_book_card);
        this.book_enter=(ImageButton)itemView.findViewById(R.id.btn_enter_book_card);

        book_delete.setOnClickListener(this);
        book_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_enter_book_card:
                this.bookItemClickListener.onBookItemClickListener(v, getLayoutPosition());
                break;
            case R.id.btn_delete_book_card:
                this.bookItemClickListener.onBookItemDeleteClickListener(v, getLayoutPosition());
                break;
        }
//        this.bookItemClickListener.onBookItemClickListener(v, getLayoutPosition());

    }

    public void setBookItemClickListener(BookItemClickListener bc) {
        this.bookItemClickListener=bc;
    }
//    public void setBookItemDeleteClickListener(BookItemClickListener bc) {
//        this.bookItemDeleteClickListener=bc;
//    }
}
