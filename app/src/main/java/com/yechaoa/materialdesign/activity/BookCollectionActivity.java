package com.yechaoa.materialdesign.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.adapter.BookAdapter;
import com.yechaoa.materialdesign.model.Book.BookItem;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BookCollectionActivity extends ToolbarActivity {

    @BindView(R.id.rv_book_list)
    RecyclerView rv_book_list;
    @BindView(R.id.btn_create_book)
    FloatingActionButton btn_create_book;

    BookAdapter bookAdapter;
    ArrayList<BookItem> books;
    String userName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_collection;
        }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle("Book Collection");
    }

    @Override
    protected void initView() {
        rv_book_list.setLayoutManager(new LinearLayoutManager(this)); // create a recyclerView in a LinearView

        books = getMyList();
        bookAdapter=new BookAdapter(this, books);
        rv_book_list.setAdapter(bookAdapter);

        userName = userName = AnalysisUtils.readLoginUserName(this);
        //Todo:看看userName是什么

    }


    private ArrayList<BookItem> getMyList() {

        ArrayList<BookItem> booksArray = new ArrayList<>();

        //Todo:从后端数据库读取书本信息
        BookItem book = new BookItem();
        book.setBook_title("A");
        booksArray .add(book);

        book = new BookItem();
        book.setBook_title("B");
        booksArray .add(book);

        book = new BookItem();
        book.setBook_title("C");
        booksArray .add(book);

        book = new BookItem();
        book.setBook_title("D");
        booksArray .add(book);

        book = new BookItem();
        book.setBook_title("E");
        booksArray .add(book);

        book = new BookItem();
        book.setBook_title("F");
        booksArray .add(book);

        book = new BookItem();
        book.setBook_title("G");
        booksArray .add(book);

        return booksArray ;
    }

    @OnClick(R.id.btn_create_book)
    public void onClick() {
        bookAdapter.addData(books.size());
    }
}
