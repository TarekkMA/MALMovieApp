package com.tmaproject.malmovieapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tmaproject.malmovieapp.database.tabels.MovieTable;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by tarekkma on 4/11/17.
 */

public class MovieProvider extends ContentProvider {

    private DatabaseHelper database;

    static final String PROVIDER_NAME = "com.tmaproject.malmovieapp.MovieProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/movies";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private static final int MOVIES = 416;
    private static final int MOVIE_ID = 389;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MovieTable.TABLE_NAME);

        checkColumns(projection);

        switch (uriMatcher.match(uri)){
            case MOVIES:
                // default sorting order
                if (TextUtils.isEmpty(sortOrder)) sortOrder = MovieTable.COLUMN_DATE_ADDED + " DESC";
                break;
            case MOVIE_ID:
                queryBuilder.appendWhere(MovieTable.COLUMN_ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db  = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = database.getWritableDatabase();
        long id = 0;
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                id = db.insert(MovieTable.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
        getContext().getContentResolver().notifyChange(_uri, null);
        return _uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deletedCount = 0;
        SQLiteDatabase db = database.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case MOVIES:
                deletedCount = db.delete(MovieTable.TABLE_NAME,selection,selectionArgs);
                break;
            case MOVIE_ID:
                String id = uri.getLastPathSegment();
                deletedCount = db.delete( MovieTable.TABLE_NAME, MovieTable.COLUMN_ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deletedCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updatedCount = 0;
        SQLiteDatabase db = database.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case MOVIES:
                updatedCount = db.update(MovieTable.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case MOVIE_ID:
                String id = uri.getLastPathSegment();
                updatedCount = db.delete( MovieTable.TABLE_NAME, MovieTable.COLUMN_ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return updatedCount;
    }


    private void checkColumns(String[] projection) {
        String[] available = {
            MovieTable.COLUMN_ID,
            MovieTable.COLUMN_DATE_ADDED,
            MovieTable.COLUMN_TITLE,
            MovieTable.COLUMN_BACKDROP_PATH,
            MovieTable.COLUMN_GENRES,
            MovieTable.COLUMN_OVERVIEW,
            MovieTable.COLUMN_POSTER_PATH,
            MovieTable.COLUMN_TAGLINE,
            MovieTable.COLUMN_VOTE_AVERAGE,
            MovieTable.COLUMN_RUNTIME,
            MovieTable.COLUMN_RELEASE_DATE
            };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }
}
