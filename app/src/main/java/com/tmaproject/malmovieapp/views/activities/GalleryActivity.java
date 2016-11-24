package com.tmaproject.malmovieapp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.models.networking.Image;
import com.tmaproject.malmovieapp.views.adapters.GalleryPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    public static final String GALLERY_URLS = "GALLERY_URLS";
    public static final String SELECTED_ITEM= "SELECTED_ITEM";
    public static final String TITLE= "TITLE";

    public static Intent getIntent(Context c, String title, List<Image> images, int selectedItem){
        List<String> urls = Stream.of(images).map(Image::getFilePath).collect(Collectors.toList());
        return new Intent(c,GalleryActivity.class)
                .putExtra(TITLE,title)
                .putStringArrayListExtra(GALLERY_URLS, (ArrayList<String>) urls)
                .putExtra(SELECTED_ITEM,selectedItem);
    }

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setTitle(getIntent().getStringExtra(TITLE));
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(new GalleryPagerAdapter(this,getIntent().getStringArrayExtra(GALLERY_URLS)));
        viewPager.setCurrentItem(getIntent().getIntExtra(SELECTED_ITEM,0));
    }
}
