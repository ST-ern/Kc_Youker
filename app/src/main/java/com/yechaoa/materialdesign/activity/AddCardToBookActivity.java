package com.yechaoa.materialdesign.activity;

import android.os.Looper;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.adapter.AddBookAdapter;
import com.yechaoa.materialdesign.adapter.CardAdapter;
import com.yechaoa.materialdesign.model.AddBook.AddBookItem;
import com.yechaoa.materialdesign.model.Card.CardItem;
import com.yechaoa.materialdesign.model.dao.Constant;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddCardToBookActivity extends ToolbarActivity  {

    @BindView(R.id.rv_add_book_list)
    RecyclerView addBookList;

    String userName;
    String cardName;
    AddBookAdapter addBookAdapter;
    ArrayList<AddBookItem> addBooks = new ArrayList<>();
    ArrayList<AddBookItem> addBooksArray = new ArrayList<>();

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
        cardName = bundle.getString("card_name");

        userName = AnalysisUtils.readLoginUserName(this);


        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = Constant.LISTBAGWITHOUTCARD + "/" + userName + "/" + cardName;
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

                    for(int i=0; i<jsonArray.size(); i++){
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        final String bookTitle = jsonObject.get("cardbag_id").getAsString();
                        AddBookItem addBookItem = new AddBookItem();
                        addBookItem.setAdd_book_title(bookTitle);
                        addBooksArray.add(addBookItem);
                    }

                    addBooks = addBooksArray;

//                        final String result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //debug
//                            Toast.makeText(AddCardToBookActivity.this, cardName, Toast.LENGTH_LONG).show();

                            addBookList.setLayoutManager(new LinearLayoutManager(AddCardToBookActivity.this)); // create a recyclerView in a LinearView

//                            addBooks = getMyList();
                            addBookAdapter=new AddBookAdapter(AddCardToBookActivity.this, addBooks, cardName, userName);
                            addBookList.setAdapter(addBookAdapter);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private ArrayList<AddBookItem> getMyList() {
        ArrayList<AddBookItem> addBooksArray = new ArrayList<>();

        //通过搜索内容query从后端数据库读取词卡信息
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
