package com.yechaoa.materialdesign.model.Book;

import android.view.View;

public interface BookItemClickListener {
    void onBookItemClickListener(View v, int position);
    void onBookItemDeleteClickListener(View v, int position);
}
