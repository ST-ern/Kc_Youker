package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.adapter.BookAdapter;
import com.yechaoa.materialdesign.adapter.CardAdapter;
import com.yechaoa.materialdesign.model.Book.BookItem;
import com.yechaoa.materialdesign.model.Card.CardItem;
import com.yechaoa.materialdesign.model.dao.Constant;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookActivity extends ToolbarActivity {

//public TextView single_book_title, single_book_description;

    @BindView(R.id.rv_card_list)
    RecyclerView cardList;
    @BindView(R.id.tv_book_description)
    TextView tv_book_description;

    CardAdapter cardAdapter;
    ArrayList<CardItem> cards = new ArrayList<>();
    ArrayList<CardItem> cardsArray = new ArrayList<>();
    String userName;
    String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book;
    }

    @Override
    protected void setToolbar() {}

    @Override
    protected void initView() {
//        TextView tv_book_description = (TextView)findViewById(R.id.tv_book_description);

        // 从Intent获得book_title
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("book_title");
        if(title==null){ title = "No found."; }
        userName = AnalysisUtils.readLoginUserName(this);


        // Todo: 从后端读取数据并改写内容
//        String description = "-Description-";
        mToolbar.setTitle(title);
//        tv_book_description.setText(description);
        // 上面需要改


        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = Constant.LISTCARDBYBAG + "/" + userName + "/" + title;
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
                    String temp = gson.toJson(json);
                    JsonArray jsonArray = parser.parse(temp).getAsJsonArray();

                    JsonObject jsonObjectDescription = jsonArray.get(0).getAsJsonObject();
                    final String des = jsonObjectDescription.get("body").getAsString();

                    for(int i=1; i<jsonArray.size(); i++){
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        final String cardName = jsonObject.get("body").getAsString();
                        CardItem card = new CardItem();
                        card.setCard_name(cardName);
                        cardsArray.add(card);
                    }

                    cards = cardsArray;

//                        final String result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(BookActivity.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            cardList.setLayoutManager(layoutManager); // create a recyclerView in a LinearView

//                            cards = getMyListByBook(title);
                            cardAdapter=new CardAdapter(BookActivity.this, cards, title);
                            cardList.setAdapter(cardAdapter);

                            tv_book_description.setText(des);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();




//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        cardList.setLayoutManager(layoutManager); // create a recyclerView in a LinearView
//
//        cards = getMyListByBook(title);
//        cardAdapter=new CardAdapter(this, cards);
//        cardList.setAdapter(cardAdapter);

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_book);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_book);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//
//        TextView tv_book_description = (TextView)findViewById(R.id.tv_book_description);
//
//        // 从Intent获得book_title
//        Bundle bundle = getIntent().getExtras();
//        String title = bundle.getString("book_title");
//        if(title==null){ title = "No found."; }
//
//        // 从后端读取数据并改写内容
//        String description = "-Description-";
//        toolbar.setTitle(title);
//        tv_book_description.setText(description);
//        // 上面需要改
//
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        cardList.setLayoutManager(layoutManager); // create a recyclerView in a LinearView
//
//        cards = getMyListByBook(title);
//        cardAdapter=new CardAdapter(this, cards);
//        cardList.setAdapter(cardAdapter);
//
////        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fbtn_user);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                // 访问词卡用户的个人主页
////                Snackbar.make(view, "访问个人主页", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            case R.id.action_settings:
////                startActivity(new Intent(BookActivity.this,TabViewPagerScrollActivity.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    private ArrayList<CardItem> getMyListByBook(String title) {
        ArrayList<CardItem> cardsArray = new ArrayList<>();

        //通过Book的名字从后端数据库读取词卡信息
        CardItem card = new CardItem();
        card.setCard_name(title);
        cardsArray.add(card);

        card = new CardItem();
        card.setCard_name("Abuse");
        cardsArray.add(card);

        card = new CardItem();
        card.setCard_name("Attend");
        cardsArray.add(card);

        card = new CardItem();
        card.setCard_name("Alike");
        cardsArray.add(card);

        return cardsArray ;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.menu_settings:
//                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,ModifyBookDescriptionActivity.class);
                intent.putExtra("bookTitle", title);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onResume(){
        super.onResume();
        if(cardAdapter != null) {

//            bookAdapter.notifyItemChanged(books.size());
            Intent intent=new Intent(this, BookActivity.class);
            intent.putExtra("book_title", title);
            startActivity(intent);
            this.finish();

        }
    }
}
