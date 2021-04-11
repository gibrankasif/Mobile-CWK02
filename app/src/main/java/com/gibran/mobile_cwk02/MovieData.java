package com.gibran.mobile_cwk02;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.gibran.mobile_cwk02.Constants.ACTORS;
import static com.gibran.mobile_cwk02.Constants.DIRECTOR;
import static com.gibran.mobile_cwk02.Constants.FAVOURITE;
import static com.gibran.mobile_cwk02.Constants.ID;
import static com.gibran.mobile_cwk02.Constants.RATING;
import static com.gibran.mobile_cwk02.Constants.REVIEW;
import static com.gibran.mobile_cwk02.Constants.TABLE_NAME;
import static com.gibran.mobile_cwk02.Constants.TITLE;
import static com.gibran.mobile_cwk02.Constants.YEAR;


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
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT NOT NULL,"
                + YEAR + " INTEGER NOT NULL,"
                + DIRECTOR + " TEXT NOT NULL,"
                + ACTORS + " TEXT NOT NULL,"
                + RATING + " INTEGER NOT NULL,"
                + REVIEW + " TEXT NOT NULL,"
                + FAVOURITE + " TEXT NOT NULL)"); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMovie(String title, Integer year, String director, String actors, Integer rating, String review) {
        movieDB = getWritableDatabase();
        Cursor c = movieDB.rawQuery("SELECT "+ TITLE + " FROM "+ TABLE_NAME + " WHERE "+ TITLE + " = '"+title.toUpperCase()+"'", null);
        try {
            if (c.moveToFirst()) {
                Toast.makeText(ctx, "Movie already exists", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues values = new ContentValues();
                values.put(TITLE, title.toUpperCase());
                values.put(YEAR, year);
                values.put(DIRECTOR, director);
                values.put(ACTORS, actors);
                values.put(RATING, rating);
                values.put(REVIEW, review);
                values.put(FAVOURITE, "1");

                movieDB.insert(TABLE_NAME, null, values);
                Toast.makeText(ctx, "Successfully Added " + title, Toast.LENGTH_LONG).show();
            }
        }
        catch (SQLiteException ex){
            Log.d("SQL Error", ex.getMessage());
        }
        finally
        {
            //release all your resources
            c.close();
            movieDB.close();
        }
    }

    public void updateFavourites(Movie movie) {
        movieDB  = this.getReadableDatabase();
        String whereClause = "title=?";
        String[] whereArgs = {movie.getTitle()};

        ContentValues values = new ContentValues();
        if(movie.isFavourite() == true){
            values.put(FAVOURITE, "0");
        }
        else{
            values.put(FAVOURITE, "1");
        }
        movieDB.update(TABLE_NAME, values, whereClause, whereArgs);

    }

    public StringBuilder getMovieDatabase() {
        movieDB = getReadableDatabase();
        Cursor cr = movieDB.rawQuery("Select * from " + TABLE_NAME, null);
        StringBuilder str = new StringBuilder();

        while(cr.moveToNext()){
            String title = cr.getString(1);
            String year = cr.getString(2);
            String director = cr.getString(3);
            String actors = cr.getString(4);
            String favourite = cr.getColumnName(5);
            str.append("Title: " + title+"\n");
            str.append("Year: " + year + "\n");
            str.append("Directed By: " + director + "\n");
            str.append("Actors: "  + actors+ "\n");
            str.append("Favourite: " + favourite+ "\n");
            str.append("\n");
            str.append("\n");
        }
        return str;
    }

    public ArrayList<Movie> getMovieObjects()
    {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + TITLE + " ASC";

        // Get the instance of the database
        movieDB  = this.getReadableDatabase();

        //get the cursor you're going to use
        Cursor cursor = movieDB.rawQuery(selectQuery, null);

        //this is optional - if you want to return one object
        //you don't need a list
        ArrayList<Movie> movieList = new ArrayList();

        //you should always use the try catch statement incase
        //something goes wrong when trying to read the data
        try
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    boolean favouriteMovie = false;
                    if(cursor.getString(7).equals("0")){
                        favouriteMovie = true;

                    }

                    Movie movie = new Movie(Integer.parseInt(cursor.getString(0)),cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)),cursor.getString(6), favouriteMovie);

                    // Adding contact to list
                    movieList.add(movie);
                } while (cursor.moveToNext());
            }
        }
        catch (SQLiteException e)
        {
            Log.d("SQL Error", e.getMessage());
            return null;
        }
        finally
        {
            //release all your resources
            cursor.close();
            movieDB.close();
        }
        return movieList;
    }
    public ArrayList<Movie> getFavouriteMovieObjects() {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + FAVOURITE + " = '0'"+ " ORDER BY " + TITLE + " ASC";

        // Get the instance of the database
        movieDB  = this.getReadableDatabase();
        //get the cursor you're going to use
        Cursor cursor = movieDB.rawQuery(selectQuery, null);
        //this is optional - if you want to return one object
        //you don't need a list
        ArrayList<Movie> movieList = new ArrayList();
        //you should always use the try catch statement incase
        //something goes wrong when trying to read the data
        try
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    boolean favouriteMovie = false;
                    if(cursor.getString(7).equals("0")){
                        favouriteMovie = true;

                    }
                    Movie movie = new Movie(cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)),cursor.getString(6), favouriteMovie);
                    // Adding contact to list
                    movieList.add(movie);
                } while (cursor.moveToNext());
            }
        }
        catch (SQLiteException e)
        {
            Log.d("SQL Error", e.getMessage());
            return null;
        }
        finally
        {
            //release all your resources
            cursor.close();
            movieDB.close();
        }
        return movieList;
    }

    public void updateMovieEntry(Integer id,String title, Integer year, String director, String actors, Integer rating, String review, String favourite) {
        // Get the instance of the database
        movieDB  = this.getWritableDatabase();
        String strFilter = "movie_id=" + id;
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(YEAR, year);
        values.put(DIRECTOR, director);
        values.put(ACTORS, actors);
        values.put(RATING, rating);
        values.put(REVIEW, review);
        values.put(FAVOURITE, favourite);
        //get the cursor you're going to use
        movieDB.update(TABLE_NAME, values, strFilter, null);
        movieDB.close();
    }

    public ArrayList<Movie> searchMovie(String query) {
        ArrayList<Movie> queriedMovies = new ArrayList<>();

        String[] parts = query.split(" "); /* Should split to {"1964", "ford", "mustang"} */
        movieDB = this.getReadableDatabase();

        String queryString = "";
        for (int i = 0; i < parts.length; i++) {
            queryString += TITLE + " LIKE '%" + parts[i] + "%' OR ";
            queryString += DIRECTOR + " LIKE '%" + parts[i] + "%' OR ";
            queryString += ACTORS + " LIKE '%" + parts[i] + "%'";
            if (i != (parts.length - 1)) {
                queryString += " OR ";
            }
        }
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ("+ queryString + ") ORDER BY " + TITLE + " ASC";
        movieDB  = this.getReadableDatabase();
        //get the cursor you're going to use
        Cursor cursor = movieDB.query(TABLE_NAME,
                new String[]{TITLE, YEAR, DIRECTOR, ACTORS, RATING, REVIEW, FAVOURITE}, queryString, null, null, null, TITLE + " ASC");
        try
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    boolean favourite = false;
                    if (cursor.getString(6).equals("0")){
                        favourite = true;
                    }

                    Movie movie = new Movie(cursor.getString(0),Integer.parseInt(cursor.getString(1)),cursor.getString(2), cursor.getString(3),Integer.parseInt(cursor.getString(4)),cursor.getString(5),false);
                    queriedMovies.add(movie);
                    // Adding contact to list
                } while (cursor.moveToNext());
            }else{
                return null;
            }
        }
        catch (SQLiteException e)
        {
            Log.d("SQL Error", e.getMessage());
            return null;
        }
        finally
        {
            //release all your resources
            cursor.close();
            movieDB.close();
        }


        return queriedMovies;

    }



}
