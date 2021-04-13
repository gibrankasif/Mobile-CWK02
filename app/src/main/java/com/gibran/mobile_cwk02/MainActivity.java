package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Removes the title bar of the apps main screen
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException ignored) {
        }

        Button registerBtn = findViewById(R.id.reg_movie_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieOptionRegister = new Intent(MainActivity.this, RegisterMovie.class);
                startActivity(movieOptionRegister);
            }
        });

        Button displayBtn = findViewById(R.id.display_movies_button);
        displayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieOptionDisplay = new Intent(MainActivity.this, DisplayMovies.class);
                startActivity(movieOptionDisplay);
            }
        });

        Button favouriteBtn = findViewById(R.id.favourites_button);
        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieOptionDisplayFavourites = new Intent(MainActivity.this, FavouriteMovies.class);
                startActivity(movieOptionDisplayFavourites);
            }
        });

        Button editBtn = findViewById(R.id.edit_movies_button);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieOptionEdit = new Intent(MainActivity.this, EditMovie.class);
                startActivity(movieOptionEdit);
            }
        });

        Button searchBtn = findViewById(R.id.search_movie_button);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieOptionSearch = new Intent(MainActivity.this, SearchMovies.class);
                startActivity(movieOptionSearch);
            }
        });

        Button ratingsBtn = findViewById(R.id.ratings_button);
        ratingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieOptionRatings = new Intent(MainActivity.this, MovieRatings.class);
                startActivity(movieOptionRatings);
            }
        });

    }

}