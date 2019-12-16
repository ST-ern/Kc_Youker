package com.yechaoa.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.holder.CardHolder;
import com.yechaoa.materialdesign.model.Card.CardItem;
import com.yechaoa.materialdesign.model.Card.CardItemClickListener;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardHolder> {

    Context c;
    ArrayList<CardItem> cards;

    public CardAdapter(Context c, ArrayList<CardItem> cards) {
        this.c=c;
        this.cards=cards;
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
//                Intent intent = new Intent(c, CardActivity.class);
//                intent.putExtra("name", card_name);
////                intent.putExtra("cover", bytes);
//                c.startActivity(intent);
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
}
