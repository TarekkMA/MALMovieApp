package com.tmaproject.malmovieapp;

import android.app.Application;
import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.tmaproject.malmovieapp.logic.DatabaseManager;

/**
 * Created by TarekMA on 11/23/16.
 * facebook/tarekkma1
 */

public class MyApp extends Application {
    private static MyApp instance;

    DatabaseManager DBManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DBManager = new DatabaseManager(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBManager.closeDatabases();
    }

    public DatabaseManager getDBManager() {
        return DBManager;
    }

    public static MyApp getInstance() {
        return instance;
    }
}
