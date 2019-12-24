package com.yechaoa.materialdesign.activity;

import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.adapter.BookAdapter;
import com.yechaoa.materialdesign.adapter.CardAdapter;
import com.yechaoa.materialdesign.adapter.SearchCardAdapter;
import com.yechaoa.materialdesign.model.Book.BookItem;
import com.yechaoa.materialdesign.model.Card.CardItem;
import com.yechaoa.materialdesign.model.SearchCard.SearchCardItem;
import com.yechaoa.materialdesign.model.dao.Constant;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends ToolbarActivity {

    @BindView(R.id.search_constraint_layout)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.tv_search_result)
    RecyclerView mSearchResult;

    SearchCardAdapter cardAdapter;
    ArrayList<SearchCardItem> cards = new ArrayList<>();
    ArrayList<SearchCardItem> cardsArray = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle(R.string.search_view);
    }

    @Override
    protected void initView() {
        mSearchResult.setLayoutManager(new LinearLayoutManager(this)); // create a recyclerView in a LinearView

//        cards = getMyList();
        cardAdapter=new SearchCardAdapter(this, cards);
        mSearchResult.setAdapter(cardAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //引用menu文件
        getMenuInflater().inflate(R.menu.menu_search, menu);

        //找到SearchView并配置相关参数
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //搜索图标是否显示在搜索框内
        mSearchView.setIconifiedByDefault(true);
        //设置搜索框展开时是否显示提交按钮，可不显示
        mSearchView.setSubmitButtonEnabled(true);
        //让键盘的回车键设置成搜索
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //搜索框是否展开，false表示展开
        mSearchView.setIconified(false);
        //获取焦点
        mSearchView.setFocusable(true);
        mSearchView.requestFocusFromTouch();
        //设置提示词
        mSearchView.setQueryHint("请输入关键字");
        //设置输入框文字颜色
        EditText editText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.white));
        editText.setTextColor(ContextCompat.getColor(this, R.color.white));

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(mConstraintLayout, "搜索内容===" + query, Snackbar.LENGTH_SHORT).show();

                final String search = query;
                //伪搜索
                mSearchResult.setVisibility(View.VISIBLE);
                //真搜索，通过传给后端搜索的内容取得返回值

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String url = Constant.FINDCARDBYWORD + "/" + search;
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
                                final String cardName = jsonObject.get("name").getAsString();
                                SearchCardItem card = new SearchCardItem();
                                card.setCard_name(cardName);
                                cardsArray.add(card);
                            }

                            cards = cardsArray;

//                        final String result = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cardAdapter=new SearchCardAdapter(SearchActivity.this, cards);
                                    mSearchResult.setAdapter(cardAdapter);

                                    //当没有输入任何内容的时候清除结果
                                    if (TextUtils.isEmpty(search)) {
                                        cards.clear();
                                        cardAdapter.updateData(cards);
                                    }

                                    cardAdapter.notifyDataSetChanged();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();



//                //当没有输入任何内容的时候清除结果
//                if (TextUtils.isEmpty(search)) {
//                    cards.clear();
//                    cardAdapter.updateData(cards);
//                }
////                else{
////                    cardAdapter.updateData(getMyList(query));
////                }
//                cardAdapter.notifyDataSetChanged();

                //清除焦点，收软键盘
                //mSearchView.clearFocus();

                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                //do something
//                //当没有输入任何内容的时候清除结果，看实际需求
//                if (TextUtils.isEmpty(newText)) {
//                    cards.clear();
//                }
//
//                cardAdapter.updateData(getMyList(newText));
//
//                cardAdapter.notifyDataSetChanged();
////                    mSearchResult.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private ArrayList<SearchCardItem> getMyList(String query) {

        ArrayList<SearchCardItem> cardsArray = new ArrayList<>();

        //通过搜索内容query从后端数据库读取词卡信息
        SearchCardItem card = new SearchCardItem();
        card.setCard_name(query);
        cardsArray.add(card);

        card = new SearchCardItem();
        card.setCard_name("Abuse");
        cardsArray.add(card);

        card = new SearchCardItem();
        card.setCard_name("Attend");
        cardsArray.add(card);

        card = new SearchCardItem();
        card.setCard_name("Alike");
        cardsArray.add(card);

        return cardsArray ;
    }
}
