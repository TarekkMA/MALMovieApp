package com.tmaproject.malmovieapp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.models.networking.Image;
import com.tmaproject.malmovieapp.views.adapters.GalleryPagerAdapter;

import java.util.List;

import icepick.State;

public class GalleryActivity extends AppCompatActivity {
    public static final String GALLERY_URLS = "GALLERY_URLS";
    public static final String SELECTED_ITEM= "SELECTED_ITEM";
    public static final String TITLE= "TITLE";

    public static Intent getIntent(Context c, String title, List<Image> images, int selectedItem){
        Bundle b = new Bundle();
                b.putString(TITLE,title);
                b.putString(GALLERY_URLS, new Gson().toJson(images));
                b.putInt(SELECTED_ITEM,selectedItem);
        return new Intent(c,GalleryActivity.class).putExtras(b);

    }

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setTitle(getIntent().getStringExtra(TITLE));
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        List<Image> images = new Gson().fromJson(getIntent().getStringExtra(GALLERY_URLS),
                new TypeToken<List<Image>>(){}.getType());
        viewPager.setOffscreenPageLimit(0);//focus on showing one image
        viewPager.setAdapter(new GalleryPagerAdapter(this,images));
        viewPager.setCurrentItem(getIntent().getIntExtra(SELECTED_ITEM,0));//FIXME SELECTED_ITEM is always -1
    }
}
