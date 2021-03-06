package com.gibran.mobile_cwk02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterMovie extends AppCompatActivity {
    private EditText movieTitle, movieYear, movieDirector, movieActors, movieRate, movieReview;
    private MovieData movieData;
    private StringBuilder actorsPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);
        movieTitle = findViewById(R.id.movie_title_textbox);
        movieYear = findViewById(R.id.movie_year_textbox);
        movieDirector = findViewById(R.id.movie_director_textbox);
        movieActors = findViewById(R.id.movie_actors_textbox);
        movieRate = findViewById(R.id.movie_rating_textbox);
        movieReview = findViewById(R.id.movie_review_textbox);
        movieData = new MovieData(this);

        movieTitle.setError("Fill");
        movieYear.setError("Fill");
        movieDirector.setError("Fill");
        movieActors.setError("Fill");
        movieRate.setError("Fill");
        movieReview.setError("Fill");

        editTextErrorAlert();

    }

    public void registerMovie(View view){

        if(movieTitle.getError() != null || movieYear.getError() != null || movieDirector.getError() != null ||
                movieActors.getError() != null || movieRate.getError() != null  || movieReview.getError() != null) {
            Toast.makeText(this,"Please check form contents!", Toast.LENGTH_LONG).show();

        }
        else {
            String title = movieTitle.getText().toString();
            Integer year = Integer.parseInt(movieYear.getText().toString());
            String director = movieDirector.getText().toString();
            String actors = actorsPlaying.toString();
            Integer rate = Integer.parseInt(movieRate.getText().toString());
            String review = movieReview.getText().toString();
//            Toast.makeText(this, actors , Toast.LENGTH_LONG).show();

            movieData.insertMovie(title, year, director, actors, rate, review);
        }
    }



    public void editTextErrorAlert(){
        movieTitle.addTextChangedListener(new TextValidator(movieTitle) {
            @Override public void validate(EditText editText, String text) {
                emptyStringField(editText, text);
            }
        });
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
        movieDirector.addTextChangedListener(new TextValidator(movieDirector) {
            @Override public void validate(EditText editText, String text) {
                emptyStringField(editText, text);
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

        movieRate.addTextChangedListener(new TextValidator(movieRate) {
            @Override public void validate(EditText editText, String text) {
                emptyStringField(editText, text);
                if (text.isEmpty() || text == null) {
                    editText.setError("Field is Empty!");
                }
                else if(!(isInteger(text))){
                    editText.setError("Invalid Rate!");
                }

                else if(Integer.parseInt(text) <= 1){
                    editText.setError("1 is MIN!");
                }

                else if(Integer.parseInt(text) > 10){
                    editText.setError("10 is MAX!");
                }
                else {
                    editText.setError(null);
                }

            }
        });
        movieReview.addTextChangedListener(new TextValidator(movieReview) {
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