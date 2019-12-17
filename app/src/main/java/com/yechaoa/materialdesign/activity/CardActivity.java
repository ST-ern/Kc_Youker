package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yechaoa.materialdesign.R;

public class CardActivity extends AppCompatActivity {

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_card);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // 从Intent获取信息
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("card_name");
        if(title.isEmpty()) {
            title = "%-EMPTY-%"; //mean there is nothing
        }
        getSupportActionBar().setTitle(title);


        // Todo: 填充卡的内容:从后端读取content
        String content = " This is content. ";
        TextView tv_card_content = findViewById(R.id.tv_card_content);
        tv_card_content.setText(content);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fbtn_add_card);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Todo：写一个新页面，选择book让add进行添加（还未添加的book的列表）
            Snackbar.make(view, "Add card to book", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            //打开新页面并传递数据
            Intent intent=new Intent(CardActivity.this, AddCardToBookActivity.class);
            intent.putExtra("card_name", title);
            startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
//                startActivity(new Intent(ScrollingActivity.this,TabViewPagerScrollActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
