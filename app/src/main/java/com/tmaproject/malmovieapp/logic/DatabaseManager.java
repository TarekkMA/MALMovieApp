package com.tmaproject.malmovieapp.logic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.tmaproject.malmovieapp.MyApp;

/**
 * Created by TarekMA on 11/23/16.
 * facebook/tarekkma1
 */

public class DatabaseManager {
    private Context context;
    private DB favoritesDB;


    public DatabaseManager(Context context) {
        this.context = context;
        openDatabases();
    }



    private void openDatabases(){
        try {
            favoritesDB = DBFactory.open(context,"favorites");
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void closeDatabases(){
        try {
            favoritesDB.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public DB getFavoritesDB() {
        return favoritesDB;
    }
}
