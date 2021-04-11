package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayMovies extends AppCompatActivity {
    ListView listView;
    MovieData movieData;
    ArrayList<Movie> movieList;
    MovieAdapter adapter;
    Button addToFavourites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        movieData = new MovieData(this);
        movieList = movieData.getMovieObjects();
        addToFavourites = findViewById(R.id.saveFav_button);
        adapter = new MovieAdapter(getApplicationContext(),  movieList);
        setListView();
    }


    public void setListView() {
        listView = findViewById(R.id.displayed_movies_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                Log.i("Result:", adapter.getItem(arg2).toString());
                // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
            }
        });
    }

    public void saveFavourites(View view) {
        List<Movie> updateMovieList = adapter.getList();
        for(Movie movie:updateMovieList){
            movieData.updateFavourites(movie);
        }
    }








//    public void listMovies(){
//        MovieAdapter adapter = new MovieAdapter(getApplicationContext(),  movieList);
//
//        listView = findViewById(R.id.displayed_movies_listview);
//        listView.setAdapter(adapter);
////        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                listView.getItemAtPosition(position);
//
//                Log.i("POSITION", String.valueOf(position) + "\n " + listView.getItemAtPosition(position) +"\n"+
//                        adapter.getItem(position));
//                Toast.makeText(getApplicationContext(), listView.getItemAtPosition(position) +"\n"+
//                        adapter.getItem(position), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }


}