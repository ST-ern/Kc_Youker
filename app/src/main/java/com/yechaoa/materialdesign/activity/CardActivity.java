package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.model.dao.Constant;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CardActivity extends AppCompatActivity {

    String title;
    String description;
    int star_count = -1;

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


        // 填充卡的内容:从后端读取content
//
        new Thread(new Runnable() {
            @Override
            public void run() {

                //通过userName查询后端有无账号和密码，得到密码password

                String url = Constant.FINDCARDBYNAME + "/" + title;
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();


                try (Response response = okHttpClient.newCall(request).execute()) {
                    Looper.prepare();

                    String answer = response.body().string();

                    JsonParser parser = new JsonParser();
                    JsonElement json = parser.parse(answer);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String temp=gson.toJson(json);
                    JsonArray jsonArray=parser.parse(temp).getAsJsonArray();

                    JsonObject jsonObject=jsonArray.get(0).getAsJsonObject();
                    description = jsonObject.get("discribe").getAsString();
                    star_count = jsonObject.get("stars").getAsInt();

//                                answer = " ";
                    //将answer（json）中的password提取出来
//                            String password = readPsw(userName);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tv_card_content = findViewById(R.id.tv_card_content);
                            TextView star = (TextView)findViewById(R.id.tv_star_count);

                            tv_card_content.setText(description);
                            //star添加相应的数值，超过99显示"99+"
                            if(star_count > 99) {
                                star.setText("99+");
                            } else {
                                star.setText(String.valueOf(star_count));
                            }

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


//        String content = " This is content. ";
//        TextView tv_card_content = findViewById(R.id.tv_card_content);
//        tv_card_content.setText(content);


//        star.setText(String.valueOf(star_count));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fbtn_add_card);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // 写一个新页面，选择book让add进行添加（还未添加的book的列表）
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

    @Override public void onResume(){
        super.onResume();
        if(description != null) {
//            bookAdapter.notifyItemChanged(books.size());

            Intent intent=new Intent(this, CardActivity.class);
            intent.putExtra("card_name", title);
            startActivity(intent);
            this.finish();
        }
    }
}
