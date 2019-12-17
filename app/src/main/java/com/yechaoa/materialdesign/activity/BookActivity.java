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

import butterknife.BindView;

public class BookActivity extends AppCompatActivity {

//public TextView single_book_title, single_book_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_book);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView tv_book_title = (TextView) findViewById(R.id.tv_book_title);
        TextView tv_book_description = (TextView)findViewById(R.id.tv_book_description);

        // Todo: 从后端读取数据并改写内容
        String title = "-Title-";
        String description = "-Description-";
        tv_book_title.setText(title);
        tv_book_description.setText(description);
        // 上面需要改

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fbtn_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: 访问词卡用户的个人主页
                Snackbar.make(view, "访问个人主页", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
//                startActivity(new Intent(BookActivity.this,TabViewPagerScrollActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
