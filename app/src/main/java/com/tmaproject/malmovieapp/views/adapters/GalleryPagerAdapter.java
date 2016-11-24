package com.tmaproject.malmovieapp.views.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.networking.Image;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by TarekMA on 11/24/16.
 * facebook/tarekkma1
 */

public class GalleryPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Image> images;
    private LayoutInflater mLayoutInflater;

    public GalleryPagerAdapter(Context context, List<Image> imagePaths) {
        mContext = context;
        this.images = imagePaths;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_gallery, container, false);

        ContentLoadingProgressBar progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.loading_bar);
        PhotoView photoView = (PhotoView) itemView.findViewById(R.id.gallery_photoView);

        Image image = images.get(position);
        String url = TheMoviedbAPI.API_IMAGE_ORIGINAL+ image.getFilePath();

//        DrawableRequestBuilder<String> thumbnailRequest = Glide
//                .with(mContext)
//                .load(url)
//                .override(image.getWidth(),image.getHeight())
//                .thumbnail(0.1f);

        Glide.with(mContext)
                .load(url)
                .thumbnail(0.1f)
                .into(photoView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
