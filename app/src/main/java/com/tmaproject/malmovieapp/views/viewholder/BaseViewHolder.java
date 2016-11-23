package com.tmaproject.malmovieapp.views.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by TarekMA on 10/30/16.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    abstract public void bind(T data);
}
