package com.yechaoa.materialdesign.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.model.Card.CardItemClickListener;

public class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView card_name;
    CardItemClickListener cardItemClickListener;

    public CardHolder(@NonNull View itemView) {
        super(itemView);

        this.card_name=(TextView)itemView.findViewById(R.id.tv_card_name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.cardItemClickListener.onCardItemClickListener(v, getLayoutPosition());
    }

    public void setCardItemClickListener(CardItemClickListener bc) {
        this.cardItemClickListener=bc;

    }
}
