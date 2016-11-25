package com.tmaproject.malmovieapp.views.viewholder;

import android.view.View;
import android.widget.TextView;

import com.tmaproject.malmovieapp.R;

/**
 * Created by TarekMA on 11/25/16.
 * facebook/tarekkma1
 */

public class MovieHeaderVH extends BaseViewHolder {
    TextView textView;
    public MovieHeaderVH(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.genres_headerTV);
    }

    @Override
    public void bind(Object data) {
        textView.setText((String)data);
    }
}
