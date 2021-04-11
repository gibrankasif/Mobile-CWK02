package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditMovie extends AppCompatActivity {
    ListView listView;
    MovieData movieData;
    ArrayList<Movie> movieList;
    MovieEditAdapter movieAdapter;
    String[] movieNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        movieData = new MovieData(this);
        movieList = movieData.getMovieObjects();
        movieAdapter = new MovieEditAdapter(getApplicationContext(), movieList);


        Toast.makeText(getApplicationContext(), Arrays.toString(movieNames), Toast.LENGTH_SHORT).show();

//        ArrayAdapter<Movie> adapter = new ArrayAdapter (this,R.layout.single_movie_choice_selection,R.id.edit_movie_textView,movieList);
//        listView = findViewById(R.id.editable_movies_listView);
//
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//
//                changeMovieInfo(movieList.get(position));
//            }
//        });

        Toast.makeText(this, movieList.toString(), Toast.LENGTH_SHORT).show();


        setListView();


    }
    public void setListView() {
        listView = findViewById(R.id.editable_movies_listView);
        listView.setAdapter(movieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {changeMovieInfo(movieList.get(position));
                Log.i("Result:", movieAdapter.getItem(position).toString());
                // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
            }
        });
    }

    public void changeMovieInfo( Movie movie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = EditMovie.this.getLayoutInflater();
        builder.setTitle("Custom view with 4 EditTexts");
        builder.setMessage("AlertDialog");
        builder.setView(R.layout.movie_edit_form_dialog);
        //In case it gives you an error for setView(View) try
        builder.setView(inflater.inflate(R.layout.movie_edit_form_dialog, null));

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
                AlertDialog dialog = builder.create();
                dialog.show();
        EditText movieTitle = dialog.findViewById(R.id.alertDialog_title);
        EditText movieYear = dialog.findViewById(R.id.alertDialog_year);
        EditText movieDirector = dialog.findViewById(R.id.alertDialog_director);
        EditText movieActors = dialog.findViewById(R.id.alertDialog_actors);
        RatingBar movieRating = dialog.findViewById(R.id.alertbox_rating);
        EditText movieReview = dialog.findViewById(R.id.alertDialog_review);

        Spinner favouriteOption = dialog.findViewById(R.id.favourite_movie_spinner);
        ArrayList<String> selectionList = new ArrayList<>();
        selectionList.add("JAVA");
        selectionList.add("ANDROID");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favouriteOption.setAdapter(arrayAdapter);



        movieTitle.setText(movie.getTitle());
        Rect displayRectangle = new Rect();

        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        dialog.getWindow().setLayout((int) (displayRectangle.width() *
                1.0f), (int) (displayRectangle.height() * 1.0f));
        dialog.getWindow().setBackgroundDrawableResource(android.R.drawable.alert_dark_frame);
    }
    //        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(layout);
//        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                //save info where you want it
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//



}