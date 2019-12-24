package com.yechaoa.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.activity.AddCardToBookActivity;
import com.yechaoa.materialdesign.activity.BookGenerateActivity;
import com.yechaoa.materialdesign.activity.CardActivity;
import com.yechaoa.materialdesign.holder.AddBookHolder;
import com.yechaoa.materialdesign.holder.AddBookHolder;
import com.yechaoa.materialdesign.model.AddBook.AddBookItem;
import com.yechaoa.materialdesign.model.AddBook.AddBookItem;
import com.yechaoa.materialdesign.model.AddBook.AddBookItemClickListener;
import com.yechaoa.materialdesign.model.dao.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddBookAdapter extends RecyclerView.Adapter<AddBookHolder> {
    Context c;
    ArrayList<AddBookItem> addBooks;
    String card_name;
    String user_name;

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");


    public AddBookAdapter(Context c, ArrayList<AddBookItem> add_books, String card_name, String user_name) {
        this.c = c;
        this.addBooks = add_books;
        this.card_name = card_name;
        this.user_name = user_name;
    }

    @NonNull
    @Override
    public AddBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_add_book, null);

        return new AddBookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddBookHolder holder, int position) {
        holder.add_book_title.setText(addBooks.get(position).getAdd_book_title());

        holder.setAddBookItemClickListener(new AddBookItemClickListener() {
            @Override
            public void onAddBookItemClickListener(View v, int position) {
                final String add_book_title = addBooks.get(position).getAdd_book_title();

                //Todo:将add_book_title和card_name, user_name的关系存入后端
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        HashMap<String, String> paramsMap = new HashMap<>();
                        paramsMap.put("user_name", user_name);
                        paramsMap.put("cardbag_id", add_book_title);
                        paramsMap.put("name", card_name);

                        Gson gson = new Gson();
                        String data = gson.toJson(paramsMap);


                        RequestBody formBody;
                        formBody = RequestBody.create(JSON, data);
                        Request request = new Request.Builder().url(Constant.INSERTCARD).post(formBody).build();

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

                            ((Activity)c).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (add_success) {
//                                            Intent intent=new Intent(BookGenerateActivity.this, BookCollectionActivity.class);
//                                            startActivity(intent);
                                        Toast.makeText(c, "Add Succeed!", Toast.LENGTH_SHORT).show();
                                        ((Activity)c).finish();
                                    } else {
                                        Toast.makeText(c, "Something wrong, exit and try again.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }


    //添加列表
    public void updateData(ArrayList<AddBookItem> c) {
        this.addBooks = c;
    }


    @Override
    public int getItemCount() {
        return addBooks.size();
    }
}

