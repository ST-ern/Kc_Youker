package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.adapter.CardAdapter;
import com.yechaoa.materialdesign.model.Card.CardItem;

import java.util.ArrayList;

import butterknife.BindView;

public class BookActivity extends ToolbarActivity {

//public TextView single_book_title, single_book_description;

    @BindView(R.id.rv_card_list)
    RecyclerView cardList;
    @BindView(R.id.tv_book_description)
    TextView tv_book_description;

    CardAdapter cardAdapter;
    ArrayList<CardItem> cards = new ArrayList<>();

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
        String title = bundle.getString("book_title");
        if(title==null){ title = "No found."; }

        // Todo: 从后端读取数据并改写内容
        String description = "-Description-";
        mToolbar.setTitle(title);
        tv_book_description.setText(description);
        // 上面需要改

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(layoutManager); // create a recyclerView in a LinearView

        cards = getMyListByBook(title);
        cardAdapter=new CardAdapter(this, cards);
        cardList.setAdapter(cardAdapter);

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

        //Todo:通过Book的名字从后端数据库读取词卡信息
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
                Toast.makeText(this, "back-to-home", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_share:
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                //Todo：开启卡包设置页面，修改description
                Intent intent=new Intent(BookActivity.this, BookGenerateActivity.class);
                startActivityForResult(intent,1);
                return super.onOptionsItemSelected(item);
        }
    }
}
