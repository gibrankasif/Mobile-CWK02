package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchMovies extends AppCompatActivity {
    Button searchButton;
    EditText searchText;
    ListView movieListQuery;
    MovieData movieData;
    MovieEditAdapter movieEditAdapter;
    ArrayList<Movie> queriedResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);
        searchButton = findViewById(R.id.lookup_button);
        searchText = findViewById(R.id.search_editText);
        movieListQuery = findViewById(R.id.queried_movies_listView);
        movieData = new MovieData(this);

        movieEditAdapter = new MovieEditAdapter(getApplicationContext(),  queriedResults);

    }


    public void setListView() {
        movieListQuery = findViewById(R.id.queried_movies_listView);
        movieListQuery.setAdapter(movieEditAdapter);
        movieListQuery.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.i("Result:", movieEditAdapter.getItem(position).toString());
                // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
            }
        });
    }

    public void querySearch(View view) {
        String textToSearch = searchText.getText().toString();
        if(textToSearch == null || textToSearch.isEmpty()){
            movieListQuery.setAdapter(null);
            Toast.makeText(this, "Please enter a movie reference!", Toast.LENGTH_SHORT).show();
        }
        else{
            queriedResults = movieData.searchMovie(textToSearch);
            if(queriedResults == null){
                movieListQuery.setAdapter(null);
                Toast.makeText(this, "No results, try another search", Toast.LENGTH_SHORT).show();
            }
            else {
                movieEditAdapter = new MovieEditAdapter(getApplicationContext(),  queriedResults);
                setListView();
            }
        }
    }
}