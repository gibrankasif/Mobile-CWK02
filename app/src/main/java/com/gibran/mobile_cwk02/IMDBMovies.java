package com.gibran.mobile_cwk02;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class IMDBMovies extends AppCompatActivity {
    ListView listView;
    ArrayList<String> rate;
    ArrayList<String> title;
    ArrayList<String> url;
    ArrayList<IMDBMovie> movies;

    IMDBMovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imbd_movies);
        listView = findViewById(R.id.imbd_movieListView32);
        movies = (ArrayList<IMDBMovie>) getIntent().getSerializableExtra("api");

        Toast.makeText(getApplicationContext(),movies.toString(), Toast.LENGTH_SHORT).show();
        try {
            LoadImageFromWebOperations();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        setListView();


    }

    public void setListView() {
        movieAdapter = new IMDBMovieAdapter(getApplicationContext(), movies);
        listView = findViewById(R.id.imbd_movieListView32);
        listView.setAdapter(movieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                runOnUiThread(() -> dialogBox(position));

                Log.i("Result:", movieAdapter.getItem(position).toString());
                // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
            }
        });
    }


    public void dialogBox(int position) {
        Dialog dialog = new Dialog(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.alert_imdb_image, null);
        ImageView imageView = dialogView.findViewById(R.id.imdb_img);
        imageView.setImageDrawable(movies.get(position).getDrawable());

        dialog.setContentView(dialogView);
        dialog.show();
    }
    public void LoadImageFromWebOperations() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                for (IMDBMovie movie : movies) {
                    String url = movie.getImageURL();
                    InputStream is = (InputStream) new URL(url).getContent();
                    Drawable d = Drawable.createFromStream(is, "src name");
                    movie.setDrawable(d);
                }
            } catch (Exception e) {
                e.getMessage();

            }

        });
        thread.start();
    }
    public static void LoadImageFromWebOperations2(String url, ImageView imageView) {
        Thread thread = new Thread(() -> {

            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                imageView.setImageDrawable(d);

            } catch (Exception e) {
            }

        });

        thread.start();
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}