package com.tmaproject.malmovieapp.views.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.squareup.picasso.Picasso;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.networking.Image;
import com.tmaproject.malmovieapp.models.networking.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TarekMA on 10/30/16.
 */

public class MovieImageVH extends BaseViewHolder<Movie> {
    private static final String TAG = "MovieVideosVH";
    private TextView header;
    private RecyclerView list;
    private boolean isPoster;
    public MovieImageVH(View v, boolean poster) {
        super(v);
        this.isPoster = poster;
        header = (TextView)v.findViewById(R.id.horizontal_list_header);
        list = (RecyclerView) v.findViewById(R.id.horizontal_list);
    }

    @Override
    public void bind(Movie data) {

        List<Image> allLangImages = (data.getImages()==null)?
                new ArrayList<Image>()
                :(isPoster)? data.getImages().getPosters() : data.getImages().getBackdrops();

        if (allLangImages.isEmpty())return;

        //filter the list from the strange languages
        List<Image> images = Stream.of(allLangImages).filter(value -> value.getIso6391()==null ||
                (value.getIso6391()!= null && value.getIso6391().contains("en")))
                .collect(Collectors.toList());

        header.setText((isPoster)?"Posters":"Wallpapers");
        list.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));


        list.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_image,parent,false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((VH)holder).bind(images.get(position));
            }

            @Override
            public int getItemCount() {
                return images.size();
            }

            class VH extends BaseViewHolder<Image>{
                ImageView image;
                public VH(View v) {
                    super(v);
                    final float scale = itemView.getContext().getResources().getDisplayMetrics().density;
                    int pixels = (int) (((isPoster)?100:200) * scale + 0.5f);
                    image = (ImageView)v.findViewById(R.id.movie_image_image);
                    image.getLayoutParams().width = pixels;
                }

                @Override
                public void bind(Image data) {
                    Picasso.with(itemView.getContext())
                            .load(TheMoviedbAPI.API_IMAGE_500+data.getFilePath())
                            .placeholder((isPoster)?R.drawable.placeholder_poster:R.drawable.placeholder_backdrop)
                            .into(image);
                }
            }
        });
    }
}
