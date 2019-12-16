package com.yechaoa.materialdesign.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yechaoa.materialdesign.R;

public class BookActivity extends ToolbarActivity implements View.OnClickListener {

//public TextView single_book_title, single_book_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {

    }
}
