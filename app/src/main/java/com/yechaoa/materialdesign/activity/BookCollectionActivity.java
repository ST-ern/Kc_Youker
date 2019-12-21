package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.adapter.BookAdapter;
import com.yechaoa.materialdesign.model.Book.BookItem;
import com.yechaoa.materialdesign.model.dao.Constant;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookCollectionActivity extends ToolbarActivity {

    @BindView(R.id.rv_book_list)
    RecyclerView rv_book_list;
    @BindView(R.id.btn_create_book)
    FloatingActionButton btn_create_book;

    BookAdapter bookAdapter;
    ArrayList<BookItem> books;
    String userName;
    ArrayList<BookItem> booksArray = new ArrayList<>();

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

        userName = AnalysisUtils.readLoginUserName(this);

        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = Constant.LISTBAGBYNAME + "/" + userName;
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
                        final String bookName = jsonObject.get("cardbag_id").getAsString();
                        BookItem book = new BookItem();
                        book.setBook_title(bookName);
                        booksArray.add(book);
                    }

                    books = booksArray;

//                        final String result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bookAdapter=new BookAdapter(BookCollectionActivity.this, books, userName);
                            rv_book_list.setAdapter(bookAdapter);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
//        books = getMyList();

    }


//    private ArrayList<BookItem> getMyList() {
//
//
//
//
//
//        ArrayList<BookItem> booksArray = new ArrayList<>();
//
//
//
//        //从后端数据库读取书本信息
//        BookItem book = new BookItem();
//        book.setBook_title("A");
//        booksArray .add(book);
//
//        book = new BookItem();
//        book.setBook_title("B");
//        booksArray .add(book);
//
//        book = new BookItem();
//        book.setBook_title("C");
//        booksArray .add(book);
//
//        book = new BookItem();
//        book.setBook_title("D");
//        booksArray .add(book);
//
//        book = new BookItem();
//        book.setBook_title("E");
//        booksArray .add(book);
//
//        book = new BookItem();
//        book.setBook_title("F");
//        booksArray .add(book);
//
//        book = new BookItem();
//        book.setBook_title("G");
//        booksArray .add(book);
//
//        return booksArray ;
//    }

    @OnClick(R.id.btn_create_book)
    public void onClick() {
        // Todo：打开generate页面设置title
        Intent intent=new Intent(this, BookGenerateActivity.class);
        startActivity(intent);
//        bookAdapter.addData(books.size());
        bookAdapter.addData(books.size());
    }


    //共享信息的函数，考虑重写
    //检验是否能够获取用户的userName信息
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            bookAdapter.notifyItemChanged(0);
        }
    }


    @Override public void onResume(){
        super.onResume();
        if(bookAdapter != null) {

//            bookAdapter.notifyItemChanged(books.size());
            Intent intent=new Intent(this, BookCollectionActivity.class);
            startActivity(intent);
            this.finish();
        }
    }



}
