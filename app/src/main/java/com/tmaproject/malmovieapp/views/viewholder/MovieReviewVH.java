package com.tmaproject.malmovieapp.views.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.models.networking.Review;

import java.util.List;

/**
 * Created by TarekMA on 11/25/16.
 * facebook/tarekkma1
 */

public class MovieReviewVH extends BaseViewHolder {

    Button readMoreBTN;
    TextView authorTV, contentTV;

    boolean contentExtended = false;

    public MovieReviewVH(View v) {
        super(v);
        readMoreBTN = (Button)v.findViewById(R.id.movie_review_readmore);
        readMoreBTN.setVisibility(View.GONE);
        authorTV = (TextView) v.findViewById(R.id.movie_review_author);
        contentTV = (TextView)v.findViewById(R.id.movie_review_content);
    }

    @Override
    public void bind(Object data) {
        Review review = ((Review) data);
        authorTV.setText(review.getAuthor());
        if(review.getContent().length()>200 && contentExtended == false){
            contentTV.setText(review.getContent().substring(0,200)+".....");
            readMoreBTN.setVisibility(View.VISIBLE);
        }else{
            contentTV.setText(review.getContent());
        }
        readMoreBTN.setOnClickListener(view -> {
            if(contentExtended) {
                contentTV.setText(review.getContent().substring(0,200)+".....");
            }else{
                contentTV.setText(review.getContent());
            }
            contentExtended = !contentExtended;
            readMoreBTN.setText(contentExtended?"Read Less":"Read More");
        });
    }
}
