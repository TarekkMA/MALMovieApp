package com.tmaproject.malmovieapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.adapters.SectionsPagerAdapter;


public class MainFragment extends Fragment {

    SectionsPagerAdapter mSectionsPagerAdapter;


    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("My Movie Manager");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mSectionsPagerAdapter.addFragment(MoviesFragment.newInstance(Movie.MovieType.POPULAR), Movie.MovieType.POPULAR.getTitle());
        mSectionsPagerAdapter.addFragment(MoviesFragment.newInstance(Movie.MovieType.TOP_RATED), Movie.MovieType.TOP_RATED.getTitle());
        mSectionsPagerAdapter.addFragment(MoviesFragment.newInstance(Movie.MovieType.FAVORITES), Movie.MovieType.FAVORITES.getTitle());


        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        return rootView;
    }


}
