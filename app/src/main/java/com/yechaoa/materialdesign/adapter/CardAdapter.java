package com.yechaoa.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.activity.BookCollectionActivity;
import com.yechaoa.materialdesign.activity.CardActivity;
import com.yechaoa.materialdesign.holder.CardHolder;
import com.yechaoa.materialdesign.model.Card.CardItem;
import com.yechaoa.materialdesign.model.Card.CardItemClickListener;
import com.yechaoa.materialdesign.model.dao.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yechaoa.materialdesign.activity.BookGenerateActivity.JSON;

public class CardAdapter extends RecyclerView.Adapter<CardHolder> {

    Context c;
    ArrayList<CardItem> cards;
    String bookTitle;

    public CardAdapter(Context c, ArrayList<CardItem> cards, String title) {
        this.c=c;
        this.cards=cards;
        this.bookTitle=title;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card, null);

        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardHolder holder, int position) {
        holder.card_name.setText(cards.get(position).getCard_name());

        holder.setCardItemClickListener(new CardItemClickListener() {
            @Override
            public void onCardItemClickListener(View v, int position) {
                    String card_name = cards.get(position).getCard_name();
    //                ImageView image = holder.card_cover;
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

                    //Todo:进入单独卡的界面
                    Intent intent = new Intent(c, CardActivity.class);
                    intent.putExtra("card_name", card_name);
    //                intent.putExtra("cover", bytes);
                    c.startActivity(intent);
            }
            @Override
            public void onCardItemDeleteClickListener(View v, int position) {
                String card_title = cards.get(position).getCard_name();
                removeData(position);
            }

        });
    }


    //添加列表
    public void updateData(ArrayList<CardItem> c) {
        this.cards = c;
    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

    private void removeData(int position) {

        //通知后端删除一个Card-book的关系,name卡名，bookTitle包名

        final String name = cards.get(position).getCard_name();
        final int count = position;
        cards.remove(count);
        notifyItemRemoved(count);
//        final BookCollectionActivity activity = (BookCollectionActivity)weak.get();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                HashMap<String, String> paramsMap = new HashMap<>();
//                paramsMap.put("cardbag_id", title);
//                paramsMap.put("user_name", userName);
//
//                Gson gson = new Gson();
//                String data = gson.toJson(paramsMap);
//
//
//                RequestBody formBody;
//                formBody = RequestBody.create(JSON, data);
//                Request request = new Request.Builder().url(Constant.DELETEBOOK).post(formBody).build();
//
//                try (Response response = okHttpClient.newCall(request).execute()) {
//                    Looper.prepare();
//                    final String answer = response.body().string();
//
//                    books.remove(count);
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            notifyItemRemoved(count);
//                        }
//                    });
////                    Boolean t;
////                    if (answer.equals("1")) {
////                        t = true;
////                    } else {
////                        t = false;
////                    }
////                    final Boolean add_success = t;
////                    //同名卡包添加失败的显示
////
////                    BookGenerateActivity.this.runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            if (add_success) {
////                                Intent intent=new Intent(BookGenerateActivity.this, BookCollectionActivity.class);
////                                startActivity(intent);
////                                BookGenerateActivity.this.finish();
////                            } else {
////                                Toast.makeText(BookGenerateActivity.this, "name is used.", Toast.LENGTH_SHORT).show();
////                            }
////
////                        }
////                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

}
