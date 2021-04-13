package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditMovie extends AppCompatActivity {
    private ListView listView;
    private MovieData movieData;
    private ArrayList<Movie> movieList;
    private MovieEditAdapter movieAdapter;
    private StringBuilder actorsPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        movieData = new MovieData(this);
        movieList = movieData.getMovieObjects();
        movieAdapter = new MovieEditAdapter(getApplicationContext(), movieList);

        setListView();
    }
    public void setListView() {
        listView = findViewById(R.id.editable_movies_listView);
        listView.setAdapter(movieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                changeMovieInfo(movieList.get(position));
                Log.i("Result:", movieAdapter.getItem(position).toString());
                // Get data from your adapter,   the above code of line give the custom adapter's object of   current position of selected list item
            }
        });
    }

    public void changeMovieInfo( Movie movie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView textView = new TextView(this);
        textView.setText("Please update the following info: ");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundColor(Color.BLACK);
        textView.setTextColor(Color.WHITE);
        LayoutInflater inflater = EditMovie.this.getLayoutInflater();
        builder.setCustomTitle(textView);
//        builder.setMessage("Edit Movie");
        builder.setView(R.layout.movie_edit_form_dialog);
        //In case it gives you an error for setView(View) try
        builder.setView(inflater.inflate(R.layout.movie_edit_form_dialog, null));
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText movieTitle = dialog.findViewById(R.id.alertDialog_title);
        EditText movieYear = dialog.findViewById(R.id.alertDialog_year);
        EditText movieDirector = dialog.findViewById(R.id.alertDialog_director);
        EditText movieActors = dialog.findViewById(R.id.alertDialog_actors);
        RatingBar movieRating = dialog.findViewById(R.id.alertbox_rating);
        EditText movieReview = dialog.findViewById(R.id.alertDialog_review);

        alertFormValidator(movieYear, movieActors);
        editTextErrorAlert(movieTitle);
        editTextErrorAlert(movieDirector);
        editTextErrorAlert(movieReview);

        Spinner favouriteOption = dialog.findViewById(R.id.favourite_movie_spinner);
        ArrayList<String> selectionList = new ArrayList<>();
        selectionList.add("Favourite");
        selectionList.add("Not Favourite");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectionList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favouriteOption.setAdapter(arrayAdapter);


        movieTitle.setText(movie.getTitle());
        movieYear.setText(String.valueOf(movie.getYear()));
        movieDirector.setText(movie.getDirector());
        movieActors.setText(movie.getActors());
        movieRating.setRating(movie.getRating());
        movieRating.setOnRatingBarChangeListener((ratingBar, ratings, fromUser) -> {
            if(movieRating.getRating()<=0) {
                movieRating.setRating(1);
            }
        });
        movieReview.setText(movie.getReview());
        final String[] movieStatus = {""};
        if (movie.isFavourite()) {
            movieStatus[0] =  "0";
            favouriteOption.setSelection(0);
        }else{
            movieStatus[0] =  "1";
            favouriteOption.setSelection(1);
        }

        favouriteOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String status = parent.getItemAtPosition(position).toString();
               if(status.equals("Favourite")){
                   movieStatus[0] = "0";
               }else {
                   movieStatus[0] = "1";
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button cancel = dialog.findViewById(R.id.cancelUpdate_buttob);
        Button update = dialog.findViewById(R.id.updateMovie_button);
        update.setOnClickListener(v -> {
            if(movieTitle.getError() != null || movieYear.getError() != null || movieDirector.getError() != null ||
                    movieActors.getError() != null ||  movieReview.getError() != null) {
                Toast.makeText(getApplicationContext(),"Please check form contents!", Toast.LENGTH_LONG).show(); }
            else {
                movieData.updateMovieEntry(movie.getId(), movieTitle.getText().toString(), Integer.parseInt(movieYear.getText().toString()), movieDirector.getText().toString(), movieActors.getText().toString(), movieRating.getProgress(), movieReview.getText().toString(), movieStatus[0]);
                movieList = movieData.getMovieObjects();
                movieAdapter = new MovieEditAdapter(getApplicationContext(),  movieList);
                setListView();
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(v -> dialog.cancel());

        Rect displayRectangle = new Rect();

//        Window window = this.getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//        dialog.getWindow().setLayout((int) (displayRectangle.width() *
//                1.0f), (int) (displayRectangle.height() * 0.82f));
//        dialog.getWindow().setBackgroundDrawableResource(android.R.drawable.alert_dark_frame);
    }

    public void alertFormValidator(EditText movieYear, EditText movieActors) {
        movieYear.addTextChangedListener(new TextValidator(movieYear) {
            @Override public void validate(EditText editText, String text) {
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int presentYear = cal.get(Calendar.YEAR);
                if (text.isEmpty() || text == null) {
                    editText.setError("Field is Empty!");
                }
                else if(!(isInteger(text))){
                    editText.setError("Invalid Year!");
                }

                else if(Integer.parseInt(text) <= 1895){
                    editText.setError("Year must be above 1895");
                }

                else if(Integer.parseInt(text) > presentYear){
                    editText.setError("Enter within the present year!: " + presentYear);
                }

                else {
                    editText.setError(null);
                }
            }
        });
        movieActors.addTextChangedListener(new TextValidator(movieActors) {
            @Override public void validate(EditText editText, String text) {
                emptyStringField(editText, text);
                String validation = "^[a-zA-Z,\\s]+";

                if (text.isEmpty() || text == null) {
                    editText.setError("Field is Empty!");
                }
                else if(!text.matches(validation)) {
                    editText.setError("Unwanted Characters present!");
                }
                else{
                    editText.setError(null);
                    String[] actors = text.split("\\s*,\\s*");
                    List<String> actorsList = new ArrayList<>(Arrays.asList(actors));
                    actorsList.removeAll(Arrays.asList("", null));
                    if (actorsList.size() >= 1) {
                        actorsPlaying = new StringBuilder(actorsList.get(0));
                    }
                    for (int i = 1; i < actorsList.size(); i++) {
                        actorsPlaying.append(", ").append(actorsList.get(i));
                    }
                }
            }
        });
    }

    public void editTextErrorAlert(EditText editText){
        editText.addTextChangedListener(new TextValidator(editText) {
            @Override public void validate(EditText editText, String text) {
                emptyStringField(editText, text);
            }
        });
    }

    public void emptyStringField(EditText editText, String text){
        if (text.isEmpty() || text == null) {
            editText.setError("Field is Empty!");
        }else{
            editText.setError(null);
        }
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

}