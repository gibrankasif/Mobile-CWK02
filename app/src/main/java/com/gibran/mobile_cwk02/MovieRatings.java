package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
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
    ArrayList<IMDBMovie> apiMovieList;


    ArrayList<String> movieTitles;
    ArrayList<String> movieURLs;
    ArrayList<String> movieRatings;

    String urlMovieQuery = "";
    String API_KEY = "k_ecqa1dty";
    Button clickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_ratings);

        movieData = new MovieData(this);
        movieList = movieData.getMovieObjects();

        setListView();
        clickButton = findViewById(R.id.findInIMDB_button);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieTitle == null || movieTitle.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "There are no results", Toast.LENGTH_SHORT).show();
                }else{
                getIMBDInfo();
                }
            }
        });
    }

    public void getIMBDInfo() {
        Thread thread = new Thread(() -> {

            movieURLs = new ArrayList<>();
            movieRatings = new ArrayList<>();
            apiMovieList = new ArrayList<>();

            urlMovieQuery = "https://imdb-api.com/en/API/SearchTitle/"+API_KEY+"/";;


            urlMovieQuery = urlMovieQuery + movieTitle;


            try {
               String jsonResponse =  getJSONResult(urlMovieQuery);
               JSONObject object = new JSONObject(jsonResponse);
               JSONArray movieResults = object.getJSONArray("results");
               if (movieResults.length() == 0){
//                   Toast.makeText(getApplicationContext(),"There were no results", Toast.LENGTH_SHORT).show();
                    Thread.interrupted();
                   if(Thread.currentThread().isInterrupted()) {return;}

               }



               for (int i = 0; i < movieResults.length(); i++) {
                    JSONObject object2 = movieResults.getJSONObject(i);
//                    movieRatings.add(returnMovieRating(object2.getString("id")));
//                    movieTitles.add(object2.getString("title"));
//                    movieURLs.add(object2.getString("image"));
                   IMDBMovie movie = new IMDBMovie(object2.getString("title"),object2.getString("image"),returnMovieRating(object2.getString("id")));
                    apiMovieList.add(movie);



                }

               if (movieRatings == null || movieResults.length() == 0){
                   System.out.println("No relevant results found!");

               }
               else{
                   Intent intent = new Intent(this, IMDBMovies.class);
//                   intent.putExtra("rate",movieRatings);
//                   intent.putExtra("title",movieTitles);
//                   intent.putExtra("url",movieURLs);
                   intent.putExtra("api", apiMovieList);
                   startActivity(intent);
               }

            } catch (InterruptedException | JSONException e) {
                e.printStackTrace();
            }


        });
        thread.start();

    }

    public String returnMovieRating(String movieID) throws InterruptedException {
        String urlMovie = "https://imdb-api.com/en/API/UserRatings/"+API_KEY+"/"+movieID;
        String jsonResponse = getJSONResult(urlMovie);
        JSONObject object;
        String rating = null;

        try {
            object = new JSONObject(jsonResponse);
            rating = object.getString("totalRating");

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }

        return rating;


    }



    public void setListView() {
        movieTitles = new ArrayList<>();
        for(Movie movie : movieList) {
            movieTitles.add(movie.getTitle());
        }
        listView = findViewById(R.id.movieRatings_listView);;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.single_choice_imdb_search, R.id.selectedMovieToAPI, movieTitles);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Toast.makeText(getApplicationContext(), adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
//                Movie selectedMovie = (Movie) listView.getItemAtPosition(position);
                movieTitle = adapter.getItem(position).toString();
                Log.i("Result:", adapter.getItem(position).toString());
                // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
            }
        });
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
                        while ((line = rd.readLine()) != null){
                            result.append(line);
                        }
                        rd.close();
                        Log.i("JSON Result",result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
        thread.start();
        thread.join();
        return result.toString();
    }



//    public void doStuff() throws InterruptedException {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                movieTitles = new ArrayList<>();
//                movieIds = new ArrayList<>();
//                movieURLs = new ArrayList<>();
//                movieRatings = new ArrayList<>();
//
//
//                urlMovieQuery = "https://imdb-api.com/en/API/SearchTitle/k_9zyb24xa/";
//
//                Log.d("Button", "Button Clicked!");
//                if (movieTitle.equals("")) {
//                    Log.d("FIELD NULL", "Enter Something!");
//                    return;
//                }
//                urlMovieQuery = urlMovieQuery + movieTitle;
//
//                String movieSearchResult = getSiteJSON(urlMovieQuery);
////                imbdMovieRates = new ArrayList<>();
//
//                Log.d("JSON SITE", urlMovieQuery);
//                Log.d("VALUES", movieSearchResult);
//
////                      Toast.makeText(getApplicationContext(), urlMovieQuery, Toast.LENGTH_SHORT).show();
////                      Toast.makeText(getApplicationContext(), siteJSON, Toast.LENGTH_SHORT).show();
//                    imbdMovieList = new ArrayList<IMBDMovie>();
//
//                    try {
//                        parseIMBDMovieObject(movieSearchResult);//ID TITLLE URL
//
//                        String urlMovie = "https://imdb-api.com/en/API/UserRatings/k_9zyb24xa/" + movieIds.get(0);
//                        movieRatingJSON = getSiteJSON(urlMovie);
////                        Log.d("JSON SITE", movieRatingJSON);
//                        parseIndividualMovieRating(movieRatingJSON, 0);
//                        System.out.println(movieTitles.toString());
//                        System.out.println(movieIds.toString());
//                        System.out.println(movieRatings.toString());
//                        System.out.println(movieURLs.toString());
//                        String done = "done";
//
//                        Intent intent = new Intent(getApplicationContext(), IMBDMovies.class);
//                        intent.putExtra("olo", done);
//                        startActivity(intent);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    }
//                });
//
//
//
//
////                runOnUiThread(() -> {
////
////                    try {
////                        for (int i = 0; i < movieIDIMBD.size(); i++) {
////                            String urlMovie = "https://imdb-api.com/en/API/UserRatings/k_522jp3ys/"+ movieIDIMBD.get(i);
////
////                            movieRatingJSON = getSiteJSON(urlMovie);
////                            Log.d("JSON SITE", movieRatingJSON);
////                            parseIndividualMovieRating(movieRatingJSON);
////                            Log.i("Result", imbdMovieRates.toString());
////                        }
////                        Log.i("Result", imbdMovieRates.toString());
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                });
//        thread.sleep(5000);
//
//    thread.start();
//
//
//
////        try {
////            thread.join();
////            pageViewAllMovies();
////        } catch (InterruptedException ie) {
////            ie.printStackTrace();
////        }
//
//
//    }
//
//    private void parseIndividualMovieRating(String responseString, int index) throws JSONException {
//        JSONObject data = new JSONObject(responseString);
////        imbdMovieList.get(index).setRating(data.getString("totalRating"));
//        movieRatings.add(data.getString("totalRating"));
//
//        Log.i("Result", imbdMovieList.toString());
//
////                        Toast.makeText(getApplicationContext(), imbdMovieList.toString(), Toast.LENGTH_SHORT).show();
//
//    }
//
//
//    private String getSiteJSON (String site)  {
//        String stream = "";
//
//        try {
//            URL url = new URL(site);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                bufferedReader.close();
//                stream = stringBuilder.toString();
//                urlConnection.disconnect();
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return stream;
//
//    }
//
//    public void parseIMBDMovieObject(String responseString) throws JSONException {
//        JSONObject data = new JSONObject(responseString);
//        JSONArray movieResults = data.getJSONArray("results");
//        Log.i("Result", movieResults.toString());
//        for (int i = 0; i < movieResults.length(); i++) {
//            JSONObject object = movieResults.getJSONObject(i);
////            IMBDMovie movie = new IMBDMovie(object.getString("id"),object.getString("title"),object.getString("image"));
////            imbdMovieList.add(movie);
//            movieIds.add(object.getString("id"));
//            movieTitles.add(object.getString("title"));
//            movieURLs.add(object.getString("image"));
//
//
//
//        }
//
//        Log.i("Result", imbdMovieList.toString());
//
//    }
//
////**************************************************
//
//
//
//
//
//
//
//








//    public void viewIMBDResults() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = MovieRatings.this.getLayoutInflater();
//        builder.setTitle("Custom view with 4 EditTexts");
//        builder.setMessage("AlertDialog");
//        builder.setView(R.layout.imbd_movie_view);
//        //In case it gives you an error for setView(View) try
//        builder.setView(inflater.inflate(R.layout.imbd_movie_view,null));
////        ArrayAdapter<IMBDMovie> adapter2 = new ArrayAdapter<IMBDMovie> (this, android.R.layout.simple_list_item_1,imbdMovieList);
//        Log.d("WTF::::" ,imbdMovieList.toString());
//
//
//        ListView listView2  = findViewById(R.id.imbd_movieListView32);
//        listView2.setAdapter(adapter);
//        listView2.setOnItemClickListener((parent, view, position, id) -> {
////                Toast.makeText(getApplicationContext(), adapter.getItem(position).getId(), Toast.LENGTH_SHORT).show();
//            Log.i("Result:", adapter.getItem(position).toString());
//            // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//
//    }
}
//    public void doStuff2() {
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                urlMovieQuery = "https://imdb-api.com/en/API/SearchTitle/k_cwedf9qn/";
//
//                Log.d("Button", "Button Clicked!");
//                if (movieTitle.equals("")) {
//                    Log.d("FIELD NULL", "Enter Something!");
//                    return;
//                }
//
//                urlMovieQuery = urlMovieQuery + movieTitle;
//                String siteJSON = getSiteJSON(urlMovieQuery);
//                imbdMovieRates = new ArrayList<>();
//
//                Log.d("JSON SITE", urlMovieQuery);
//                Log.d("VALUES", siteJSON);
//
//
//                try {
//                    parseJsonToString(siteJSON);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//
//                    String urlMovie = "https://imdb-api.com/en/API/UserRatings/k_cwedf9qn/" + movieIDIMBD.get(0);
//                    movieRatingJSON = getSiteJSON(urlMovie);
//                    Log.d("JSON SITE", movieRatingJSON);
//                    parseIndividualMovieRating(movieRatingJSON,0);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        thread1.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException ie) {
//            ie.printStackTrace();
//        }
//        thread2.start();
//        try {
//            thread2.join();
//        } catch (InterruptedException ie) {
//            ie.printStackTrace();
//        }
//    }