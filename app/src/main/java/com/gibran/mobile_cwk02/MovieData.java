package com.gibran.mobile_cwk02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static com.gibran.mobile_cwk02.Constants.*;


public class MovieData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Movie.db";
    private static final int DATABASE_VERSION = 1;

    Context ctx;
    SQLiteDatabase movieDB;

    public MovieData(Context ct) {
        super(ct, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = ct;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        movieDB.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID +" INTEGER PRIMARY KEY, "
                + TITLE + " TEXT NOT NULL UNIQUE,"
                + YEAR + " INTEGER NOT NULL,"
                + DIRECTOR + " TEXT NOT NULL,"
                + ACTORS + " TEXT NOT NULL,"
                + RATING + " INTEGER NOT NULL,"
                + REVIEW + " TEXT NOT NULL);"); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        movieDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMovie(Integer id, String title, Integer year, String director, String actors, Integer rating, String review) {
        movieDB = getWritableDatabase();
        Cursor c = movieDB.rawQuery("SELECT "+ TITLE + " FROM "+ TABLE_NAME + " WHERE "+ TITLE + " = '"+title+"'", null);
        if(c.moveToFirst())
        {
            Toast.makeText(ctx,"Movie already exists", Toast.LENGTH_SHORT).show();
        }else {
            ContentValues values = new ContentValues();
            values.put(TITLE, title);
            values.put(YEAR, year);
            values.put(DIRECTOR, director);
            values.put(ACTORS, actors);
            values.put(RATING, rating);
            values.put(RATING, review);
            movieDB.insert(TABLE_NAME, null, values);
        }
    }
}
