package com.tmaproject.malmovieapp.views.activities;

import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.ResponsiveUi;
import com.tmaproject.malmovieapp.models.events.MovieSelectedEvent;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.adapters.SectionsPagerAdapter;
import com.tmaproject.malmovieapp.views.fragments.MainFragment;
import com.tmaproject.malmovieapp.views.fragments.MovieDetailsFragment;
import com.tmaproject.malmovieapp.views.fragments.MoviesFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ResponsiveUi.isTablet(this)==false){
            findViewById(R.id.details).setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void movieSelected(MovieSelectedEvent event){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.details, MovieDetailsFragment.newInstance(event.movie))
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
