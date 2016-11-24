package com.tmaproject.malmovieapp.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by TarekMA on 11/24/16.
 * facebook/tarekkma1
 */

public class GalleryPagerAdapter extends PagerAdapter {
    Context mContext;
    String[] imagePaths;
    LayoutInflater mLayoutInflater;

    public GalleryPagerAdapter(Context context, String[] imagePaths) {
        mContext = context;
        this.imagePaths = imagePaths;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagePaths.length;
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
        Picasso.with(mContext).load(TheMoviedbAPI.API_IMAGE_ORIGINAL+imagePaths[position])
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        progressBar.setVisibility(View.GONE);
                        photoView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
