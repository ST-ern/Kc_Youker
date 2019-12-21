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
import com.yechaoa.materialdesign.activity.BookActivity;
import com.yechaoa.materialdesign.activity.BookCollectionActivity;
import com.yechaoa.materialdesign.activity.BookGenerateActivity;
import com.yechaoa.materialdesign.holder.BookHolder;
import com.yechaoa.materialdesign.model.Book.BookItem;
import com.yechaoa.materialdesign.model.Book.BookItemClickListener;
import com.yechaoa.materialdesign.model.dao.Constant;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yechaoa.materialdesign.activity.BookGenerateActivity.JSON;

public class BookAdapter extends RecyclerView.Adapter<BookHolder> {
    Context c;
    ArrayList<BookItem> books;
    String userName;
    WeakReference<Activity> weak; // 定义弱引用变量

    public BookAdapter(Context c, ArrayList<BookItem> books, String userName) {
        this.c=c;
        this.books=books;
        this.userName = userName;
        this.weak = new WeakReference<Activity>((Activity)c);
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_book, null);

        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookHolder holder, int position) {
        holder.book_title.setText(books.get(position).getBook_title());

        holder.setBookItemClickListener(new BookItemClickListener() {
            @Override
            public void onBookItemClickListener(View v, int position) {
                String book_title = books.get(position).getBook_title();//get data from last activity
//                ImageView image = holder.book_cover;
//                Drawable drawable = image.getDrawable();// get image from drawable
//                BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
//
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream(); //image will get streams and bytes (?)
//
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //compress image
//
//                byte[] bytes = stream.toByteArray();

                //打开具体的书本页面
                Toast.makeText(c,"Open Book", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(c, BookActivity.class);
                intent.putExtra("book_title", book_title);
//                intent.putExtra("cover", bytes);
                c.startActivity(intent);
            }
            @Override
            public void onBookItemDeleteClickListener(View v, int position) {
                String book_title = books.get(position).getBook_title();
                removeData(position);
            }
        });
    }

    //添加列表
    public void addData(int position) {
//        BookItem book = new BookItem();
//        book.setBook_title("Default");
        //Todo：调用一个新界面CreateBook，在那个界面具体设置title
        // 在Book页面设置按钮进入modify页面修改description

//        books.add(position, book);
        notifyItemInserted(position);
//        notifyItemChanged(position);
        //Todo: 通知后端添加一个Book，信息为book的内容
    }

    public void removeData(int position) {

        //Todo:通知后端删除一个Book，信息为books[position]

        final String title = books.get(position).getBook_title();
        final int count = position;
        final BookCollectionActivity activity = (BookCollectionActivity)weak.get();

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("cardbag_id", title);
                paramsMap.put("user_name", userName);

                Gson gson = new Gson();
                String data = gson.toJson(paramsMap);


                RequestBody formBody;
                formBody = RequestBody.create(JSON, data);
                Request request = new Request.Builder().url(Constant.DELETEBOOK).post(formBody).build();

                try (Response response = okHttpClient.newCall(request).execute()) {
                    Looper.prepare();
                    final String answer = response.body().string();

                    books.remove(count);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyItemRemoved(count);
                        }
                    });
//                    Boolean t;
//                    if (answer.equals("1")) {
//                        t = true;
//                    } else {
//                        t = false;
//                    }
//                    final Boolean add_success = t;
//                    //同名卡包添加失败的显示
//
//                    BookGenerateActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (add_success) {
//                                Intent intent=new Intent(BookGenerateActivity.this, BookCollectionActivity.class);
//                                startActivity(intent);
//                                BookGenerateActivity.this.finish();
//                            } else {
//                                Toast.makeText(BookGenerateActivity.this, "name is used.", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



    @Override
    public int getItemCount() {
        return books.size();
    }
}
