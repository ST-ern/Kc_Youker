package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.adapter.BookAdapter;
import com.yechaoa.materialdesign.model.dao.Constant;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookGenerateActivity extends ToolbarActivity implements View.OnClickListener{


    @BindView(R.id.til_book_title)
    TextInputLayout til_book_title;
    @BindView(R.id.til_book_description)
    TextInputLayout til_book_description;
    @BindView(R.id.btn_book_generate)
    Button btn_title;

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    private String userName;
    private String title;
    private String description;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_generate;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle("Title Setting");
    }

    @Override
    protected void initView(){
        userName = AnalysisUtils.readLoginUserName(this);
        btn_title.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_book_generate:
//                submit();

                {
                    title = til_book_title.getEditText().getText().toString().trim();
                    description = til_book_description.getEditText().getText().toString().trim();

                    //将description传到后端
                    Toast.makeText(this, "Description Ok", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient();
                            HashMap<String, String> paramsMap = new HashMap<>();
                            paramsMap.put("cardbag_id", title);
                            paramsMap.put("card_describe", description);
                            paramsMap.put("user_name", userName);

                            Gson gson = new Gson();
                            String data = gson.toJson(paramsMap);


                            RequestBody formBody;
                            formBody = RequestBody.create(JSON, data);
                            Request request = new Request.Builder().url(Constant.INSERTBOOK).post(formBody).build();

                            try (Response response = okHttpClient.newCall(request).execute()) {
                                Looper.prepare();
                                final String answer = response.body().string();
                                Boolean t;
                                if (answer.equals("1")) {
                                    t = true;
                                } else {
                                    t = false;
                                }
                                final Boolean add_success = t;
                                //同名卡包添加失败的显示

                                BookGenerateActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (add_success) {
//                                            Intent intent=new Intent(BookGenerateActivity.this, BookCollectionActivity.class);
//                                            startActivity(intent);
                                            BookGenerateActivity.this.finish();
                                        } else {
                                            Toast.makeText(BookGenerateActivity.this, "name is used.", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
                break;
            default:
                break;
        }
    }

    private void submit() {
//        title = til_book_title.getEditText().getText().toString().trim();
//        description = til_book_description.getEditText().getText().toString().trim();
//
//        //将description传到后端
//        Toast.makeText(this, "Description Ok", Toast.LENGTH_SHORT).show();
//
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                HashMap<String,String> paramsMap=new HashMap<>();
//                    paramsMap.put("cardbag_id", title);
//                    paramsMap.put("card_describe", description);
//                    paramsMap.put("user_naem", userName);
//
//                Gson gson=new Gson();
//                String data=gson.toJson(paramsMap);
//
//
//                RequestBody formBody;
//                formBody=RequestBody.create(JSON, data);
//                Request request=new Request.Builder().url(Constant.INSERT).post(formBody).build();
//
//                    try (
//                        Response response = okHttpClient.newCall(request).execute()) {
//                            Looper.prepare();
//                            String answer = response.body().string();
//                            Boolean t;
//                            if(answer.equals("1")) {
//                                t = true;
//                            } else {
//                                t = false;
//                            }
//                            final Boolean add_success = t;
//                            //同名卡包添加失败的显示
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(add_success) {
//                                    BookGenerateActivity.this.finish();
//                                } else {
//                                    Toast.makeText(BookGenerateActivity.this, "name is used.", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

}
