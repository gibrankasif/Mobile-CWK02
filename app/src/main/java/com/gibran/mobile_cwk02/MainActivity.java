package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Removes the title bar of the apps main screen
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_movie_button:
                Intent movieOptionRegister = new Intent(MainActivity.this, RegisterMovie.class);
                startActivity(movieOptionRegister);
                break;
            case R.id.display_movies_button:
                Intent movieOptionDisplay = new Intent(MainActivity.this, DisplayMovies.class);
                startActivity(movieOptionDisplay);
                break;
            default:
                break;
        }
    }
}