package com.gibran.mobile_cwk02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//custom ArrayAdapter
class propertyArrayAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private List<Movie> movies;
    private String value;

    //constructor, call on creation
    public propertyArrayAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);

        this.context = context;
        this.movies = objects;
    }

    //called when rendering the list
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        Movie movie = movies.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.singe_movie_choice, null);

//        TextView description = (TextView) view.findViewById(R.id.description);
//        TextView address = (TextView) view.findViewById(R.id.address);
//        TextView bedroom = (TextView) view.findViewById(R.id.bedroom);
//        TextView bathroom = (TextView) view.findViewById(R.id.bathroom);
//        TextView carspot = (TextView) view.findViewById(R.id.carspot);
//        TextView price = (TextView) view.findViewById(R.id.price);
//        ImageView image = (ImageView) view.findViewById(R.id.image);


        CheckedTextView textView = view.findViewById(R.id.simpleCheckedTextView);
        //set address and description
        String completeAddress = movie.getActors() + " " + movie.getDirector() + ", " + movie.getReview() + ", " + movie.getTitle();
        textView.setText(completeAddress);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.isChecked()) {
// set cheek mark drawable and set checked property to false
                    value = "un-Checked";
                    textView.setCheckMarkDrawable(R.drawable.favourite_movie_icon);
                    textView.setChecked(false);
                } else {
// set cheek mark drawable and set checked property to true
                    value = "Checked";
                    textView.setCheckMarkDrawable(R.drawable.favourite_movie_icon_checked);
                    textView.setChecked(true);
                }
                Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
            }
        });

//        //display trimmed excerpt for description
//        int descriptionLength = property.getDescription().length();
//        if(descriptionLength >= 100){
//            String descriptionTrim = property.getDescription().substring(0, 100) + "...";
//            description.setText(descriptionTrim);
//        }else{
//            description.setText(property.getDescription());
//        }

//        //set price and rental attributes
//        price.setText("$" + String.valueOf(property.getPrice()));
//        bedroom.setText("Bed: " + String.valueOf(property.getBedrooms()));
//        bathroom.setText("Bath: " + String.valueOf(property.getBathrooms()));
//        carspot.setText("Car: " + String.valueOf(property.getCarspots()));
//
//        //get the image associated with this property
//        int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
//        image.setImageResource(imageID);

        return view;
    }
}

public class DisplayMovies extends AppCompatActivity {
    ListView listView;
    MovieData movieData;
    ArrayList<Movie> movieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        movieData = new MovieData(this);
        movieList = movieData.getMovieObjects();
        Toast.makeText(this,movieList.toString() , Toast.LENGTH_LONG).show();
        ArrayAdapter<Movie> adapter = new propertyArrayAdapter(this, 0, movieList);

        listView = findViewById(R.id.displayed_movies_listview);
        listView.setAdapter(adapter);
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listView.getItemAtPosition(position);
                Log.i("POSITION", String.valueOf(position));
            }
        });

    }

    public void setListView() {
        final CustomAdapter adapter = new CustomAdapter(this, movieList);
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.getItemAtPosition(position);
                Log.i("POSITION", String.valueOf(position));
            }
        });
    }
}