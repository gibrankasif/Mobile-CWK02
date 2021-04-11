package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MovieRatings extends AppCompatActivity {
    String movieTitle;
    ListView listView;
    MovieData movieData;
    ArrayList<Movie> movieList;
    ArrayList<String> imbdMoviesTitles;
    ArrayList<Integer> imbdMovieRates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_ratings);
        movieData = new MovieData(this);
        movieList = movieData.getMovieObjects();
//        movies = new Movie[movieList.size()];
//        movieList.toArray(movies);
        Toast.makeText(this, movieList.toString(), Toast.LENGTH_SHORT).show();
        setListView();

    }


    public void setListView() {
        listView = findViewById(R.id.movieRatings_listView);;
        ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(getApplicationContext(), android.R.layout.simple_list_item_checked, movieList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getApplicationContext(), adapter.getItem(position).getTitle(), Toast.LENGTH_SHORT).show();
                Movie selectedMovie = (Movie) listView.getItemAtPosition(position);
                movieTitle = selectedMovie.getTitle();
                Log.i("Result:", adapter.getItem(position).toString());
                // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
            }
        });
    }

    public void getIMBDData(View view) {
        String urlMovieQuery = "https://imdb-api.com/en/API/SearchTitle/k_522jp3ys/";
        Log.d("Button", "Button Clicked!");
        if (urlMovieQuery.equals("")) {
            Log.d("FIELD NULL", "Enter Something!");
            return;
        }
        urlMovieQuery = urlMovieQuery + movieTitle;
        try {
            parseJsonToString(getJSONResult(urlMovieQuery));
        } catch (JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void parseJsonToString(String responseString) throws JSONException {
        Log.i("Result", responseString);
        JSONObject data = new JSONObject(responseString);
        Button bf = findViewById(R.id.findInIMDB_button);

        JSONArray movieResults = data.getJSONArray("results");
        Log.i("Result", movieResults.toString());
        imbdMoviesTitles = new ArrayList<>();
        for(int i = 0; i < movieResults.length(); i++){
            JSONObject imbdMovie = movieResults.getJSONObject(i);
            imbdMoviesTitles.add(imbdMovie.getString("title"));
        }
        bf.setText(imbdMoviesTitles.toString());
        Toast.makeText(getApplicationContext(), imbdMoviesTitles.toString(), Toast.LENGTH_SHORT).show();
    }
    public String getJSONResult(final String query) throws InterruptedException {
        StringBuilder result = new StringBuilder();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("QUERY", query);
                    URL url = new URL(query);

                    URLConnection conn = url.openConnection();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    rd.close();

                    Log.i("JSON Result", result.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        return result.toString();
    }



//    public void getData(View view) {
//        Log.d("Button", "Button Clicked!");
//        String ingredient = editText.getText().toString();
//        if (ingredient.equals("")) {
//            Log.d("FIELD NULL", "Enter Something!");
//            return;
//        }
//        query = query + ingredient;
//        try {
//            parseJsonToString(getJSONResult(query));
//        } catch (JSONException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void parseJsonToString(String responseString) throws JSONException {
//        Log.i("Result", responseString);
//        JSONObject data = new JSONObject(responseString);
//        JSONArray main = data.getJSONArray("drinks");
//        JSONObject des = main.getJSONObject(0);
//        String recipe = des.getString("strInstructions");
//        String imageURL = des.getString("strDrinkThumb");
//    }
//
//    public String getJSONResult(final String query) throws InterruptedException {
//        StringBuilder result = new StringBuilder();
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    try {
//                        Log.d("QUERY", query);
//                        URL url = new URL(query);
//
//                        URLConnection conn = url.openConnection();
//                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                        String line;
//                        while ((line = rd.readLine()) != null) {
//                            result.append(line);
//                        }
//                        rd.close();
//                        Log.i("JSON Result", result.toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//        thread.sleep(4000);
//        String returnResult = result.toString();
//        return returnResult;
//    }
}