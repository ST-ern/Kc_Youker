package com.yechaoa.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.activity.BookActivity;
import com.yechaoa.materialdesign.holder.BookHolder;
import com.yechaoa.materialdesign.model.Book.BookItem;
import com.yechaoa.materialdesign.model.Book.BookItemClickListener;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookHolder> {
    Context c;
    ArrayList<BookItem> books;

    public BookAdapter(Context c, ArrayList<BookItem> books) {
        this.c=c;
        this.books=books;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_book, null);

        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookHolder holder, int position) {
        holder.book_title.setText(books.get(position).getBook_title());

        holder.setBookItemClickListener(new BookItemClickListener() {
            @Override
            public void onBookItemClickListener(View v, int position) {
                String book_title = books.get(position).getBook_title();//get data from last activity
//                ImageView image = holder.book_cover;
//                Drawable drawable = image.getDrawable();// get image from drawable
//                BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
//
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream(); //image will get streams and bytes (?)
//
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //compress image
//
//                byte[] bytes = stream.toByteArray();

                //打开具体的书本页面
                Toast.makeText(c,"Open Book", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(c, BookActivity.class);
                intent.putExtra("title", book_title);
//                intent.putExtra("cover", bytes);
                c.startActivity(intent);
            }
            @Override
            public void onBookItemDeleteClickListener(View v, int position) {
                String book_title = books.get(position).getBook_title();
                removeData(position);
            }
        });
    }

    //添加列表
    public void addData(int position) {
        BookItem book = new BookItem();
        book.setBook_title("Default");
        //Todo：调用一个新界面CreateBook，在那个界面具体设置title和description
        //。。。


        books.add(position, book);
        notifyItemInserted(position);
        //Todo: 通知后端添加一个Book，信息为book的内容
    }

    public void removeData(int position) {
        books.remove(position);
        notifyItemRemoved(position);
        //Todo:通知后端删除一个Book，信息为books[position]
    }



    @Override
    public int getItemCount() {
        return books.size();
    }
}
