package com.tmaproject.malmovieapp.views.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by TarekMA on 10/30/16.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    BaseViewHolder(View itemView) {
        super(itemView);
    }

    abstract public void bind(Object data);
}
