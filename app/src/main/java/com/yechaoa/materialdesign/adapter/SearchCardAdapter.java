package com.yechaoa.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.activity.CardActivity;
import com.yechaoa.materialdesign.holder.CardHolder;
import com.yechaoa.materialdesign.holder.SearchCardHolder;
import com.yechaoa.materialdesign.model.Card.CardItem;
import com.yechaoa.materialdesign.model.Card.CardItemClickListener;
import com.yechaoa.materialdesign.model.SearchCard.SearchCardItem;
import com.yechaoa.materialdesign.model.SearchCard.SearchCardItemClickListener;

import java.util.ArrayList;

public class SearchCardAdapter extends RecyclerView.Adapter<SearchCardHolder> {


    Context c;
    ArrayList<SearchCardItem> cards;

    public SearchCardAdapter(Context c, ArrayList<SearchCardItem> cards) {
        this.c=c;
        this.cards=cards;
    }

    @NonNull
    @Override
    public SearchCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_search_card, null);

        return new SearchCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchCardHolder holder, int position) {
        holder.card_name.setText(cards.get(position).getCard_name());

        holder.setSearchCardItemClickListener(new SearchCardItemClickListener() {
            @Override
            public void onSearchCardItemClickListener(View v, int position) {
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

                //进入单独卡的界面
                Intent intent = new Intent(c, CardActivity.class);
                intent.putExtra("card_name", card_name);
                //                intent.putExtra("cover", bytes);
                c.startActivity(intent);

            }
        });
    }


    //添加列表
    public void updateData(ArrayList<SearchCardItem> c) {
        this.cards = c;
    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

}
