package com.yechaoa.materialdesign.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.model.Card.CardItemClickListener;

public class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView card_name;
    public Button card_delete, card_enter;
    CardItemClickListener cardItemClickListener;

    public CardHolder(@NonNull View itemView) {
        super(itemView);

        this.card_name=(TextView)itemView.findViewById(R.id.tv_card_name);
        this.card_delete=(Button)itemView.findViewById(R.id.btn_delete_card);
        this.card_enter=(Button)itemView.findViewById(R.id.btn_enter_card);

        card_delete.setOnClickListener(this);
        card_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_enter_card:
                this.cardItemClickListener.onCardItemClickListener(v, getLayoutPosition());
                break;
            case R.id.btn_delete_card:
                this.cardItemClickListener.onCardItemDeleteClickListener(v, getLayoutPosition());
                break;
        }
//        this.cardItemClickListener.onCardItemClickListener(v, getLayoutPosition());
    }

    public void setCardItemClickListener(CardItemClickListener bc) {
        this.cardItemClickListener=bc;

    }
}
