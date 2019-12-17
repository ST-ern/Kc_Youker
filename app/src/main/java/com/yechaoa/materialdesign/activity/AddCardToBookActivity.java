package com.yechaoa.materialdesign.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.adapter.AddBookAdapter;
import com.yechaoa.materialdesign.model.AddBook.AddBookItem;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class AddCardToBookActivity extends ToolbarActivity  {

    @BindView(R.id.rv_add_book_list)
    RecyclerView addBookList;

    String userName;
    AddBookAdapter addBookAdapter;
    ArrayList<AddBookItem> addBooks = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_card_to_book;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle("Choose a Book to ADD");
    }

    @Override
    protected void initView() {
        // 从Intent获得card_name
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("card_name");

        //debug
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();

        addBookList.setLayoutManager(new LinearLayoutManager(this)); // create a recyclerView in a LinearView

        addBooks = getMyList();
        addBookAdapter=new AddBookAdapter(this, addBooks, name);
        addBookList.setAdapter(addBookAdapter);

        userName = userName = AnalysisUtils.readLoginUserName(this);
        //Todo:看看userName是什么


    }


    private ArrayList<AddBookItem> getMyList() {
        ArrayList<AddBookItem> addBooksArray = new ArrayList<>();

        //Todo:通过搜索内容query从后端数据库读取词卡信息
        AddBookItem addBook = new AddBookItem();
        addBook.setAdd_book_title("A");
        addBooksArray.add(addBook);

        addBook = new AddBookItem();
        addBook.setAdd_book_title("Abuse");
        addBooksArray.add(addBook);

        addBook = new AddBookItem();
        addBook.setAdd_book_title("Attend");
        addBooksArray.add(addBook);

        addBook = new AddBookItem();
        addBook.setAdd_book_title("Alike");
        addBooksArray.add(addBook);

        return addBooksArray ;
    }

    public void closePage() {

        this.finish();
    }
}
