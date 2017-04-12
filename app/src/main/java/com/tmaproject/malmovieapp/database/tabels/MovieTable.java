package com.tmaproject.malmovieapp.database.tabels;

/**
 * Created by tarekkma on 4/11/17.
 */

public class MovieTable {
    public static final String TABLE_NAME = "MOVIES_TABLE";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE_ADDED = "DATE_ADDED";

    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_OVERVIEW = "OVERVIEW";
    public static final String COLUMN_RELEASE_DATE = "RELEASE_DATE";

    public static final String COLUMN_BACKDROP_PATH = "BACKDROP_PATH";
    public static final String COLUMN_POSTER_PATH = "POSTER_PATH";
    public static final String COLUMN_GENRES = "GENERS";
    public static final String COLUMN_TAGLINE = "TAGLINE";
    public static final String COLUMN_VOTE_AVERAGE = "VOTE_AVERAGE";
    public static final String COLUMN_RUNTIME = "RUN_TIME";

    public static final String CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" (\n" +
            COLUMN_ID + " INT NOT NULL PRIMARY KEY,\n" +
            COLUMN_DATE_ADDED + " datetime default current_timestamp,\n"+
            COLUMN_TITLE+" TEXT NOT NULL,\n" +
            COLUMN_OVERVIEW+" TEXT ,\n" +
            COLUMN_RELEASE_DATE+" TEXT ,\n" +
            COLUMN_POSTER_PATH+" TEXT ,\n" +
            COLUMN_BACKDROP_PATH+" TEXT ,\n" +
            COLUMN_GENRES+" TEXT ,\n" +
            COLUMN_TAGLINE+" TEXT ,\n" +
            COLUMN_VOTE_AVERAGE+" REAL ,\n" +
            COLUMN_RUNTIME+" INT \n" +
            ");\n";

}
