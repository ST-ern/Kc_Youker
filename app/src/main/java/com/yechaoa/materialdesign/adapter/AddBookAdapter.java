package com.yechaoa.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.activity.AddCardToBookActivity;
import com.yechaoa.materialdesign.activity.CardActivity;
import com.yechaoa.materialdesign.holder.AddBookHolder;
import com.yechaoa.materialdesign.holder.AddBookHolder;
import com.yechaoa.materialdesign.model.AddBook.AddBookItem;
import com.yechaoa.materialdesign.model.AddBook.AddBookItem;
import com.yechaoa.materialdesign.model.AddBook.AddBookItemClickListener;

import java.util.ArrayList;

public class AddBookAdapter extends RecyclerView.Adapter<AddBookHolder> {
    Context c;
    ArrayList<AddBookItem> addBooks;
    String card_name;

    public AddBookAdapter(Context c, ArrayList<AddBookItem> add_books, String card_name) {
        this.c = c;
        this.addBooks = add_books;
        this.card_name = card_name;
    }

    @NonNull
    @Override
    public AddBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_add_book, null);

        return new AddBookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddBookHolder holder, int position) {
        holder.add_book_title.setText(addBooks.get(position).getAdd_book_title());

        holder.setAddBookItemClickListener(new AddBookItemClickListener() {
            @Override
            public void onAddBookItemClickListener(View v, int position) {
                String add_book_title = addBooks.get(position).getAdd_book_title();

                //Todo:将add_book_title和card_name的关系存入后端
                ((Activity)c).finish();
            }
        });
    }


    //添加列表
    public void updateData(ArrayList<AddBookItem> c) {
        this.addBooks = c;
    }


    @Override
    public int getItemCount() {
        return addBooks.size();
    }
}

