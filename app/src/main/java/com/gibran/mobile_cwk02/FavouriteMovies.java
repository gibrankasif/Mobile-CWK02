package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMovies extends AppCompatActivity {
    ListView listView;
    MovieData movieData;
    ArrayList<Movie> movieList;
    MovieAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        movieData = new MovieData(this);
        movieList = movieData.getFavouriteMovieObjects();
        Toast.makeText(this, movieList.toString(), Toast.LENGTH_SHORT).show();

        adapter = new MovieAdapter(getApplicationContext(),  movieList);
        setListView();

    }


    public void setListView() {
        listView = findViewById(R.id.displayed_favourite_movies_listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.i("Result:", adapter.getItem(position).toString());
                // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
            }
        });
    }

    public void saveFavourites(View view) {
        List<Movie> updateMovieList = adapter.getList();
        for(Movie movie:updateMovieList){
            movieData.updateFavourites(movie);
        }
        movieList = movieData.getFavouriteMovieObjects();
        adapter = new MovieAdapter(getApplicationContext(),  movieList);
        setListView();
    }
}