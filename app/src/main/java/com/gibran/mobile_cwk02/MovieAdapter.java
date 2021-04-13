package com.gibran.mobile_cwk02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

//custom ArrayAdapter
public class MovieAdapter extends BaseAdapter {

    private final Context context;
    private final List<Movie> movies;
    LayoutInflater inflater;
    private String value;

    //constructor, call on creation
    public MovieAdapter(Context context, ArrayList<Movie> objects) {
//        super(context, resource, objects);

        this.context = context;
        this.movies = objects;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    public ArrayList<Movie> getList(){
        return (ArrayList<Movie>) movies;
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.singe_movie_choice, null);
        CheckedTextView textView = convertView.findViewById(R.id.simpleCheckedTextView);
        TextView textView1 = convertView.findViewById(R.id.movieName);
        //get the property we are displaying
        Movie movie = movies.get(position);
        if(movie.isFavourite()){
            textView.setChecked(true);
            textView.setCheckMarkDrawable(R.drawable.favourite_movie_icon_checked);

        }else{
            textView.setChecked(false);
            textView.setCheckMarkDrawable(R.drawable.favourite_movie_icon);
        }

        //get the inflater and inflate the XML layout for each item
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.singe_movie_choice, null);


        //set address and description
        String completeAddress = movie.getTitle() +"  \n"+ movie.getYear() +"  \n"+ movie.getDirector();
        textView1.setText(completeAddress);

        textView.setOnClickListener(v -> {

            if (textView.isChecked()) {
// set cheek mark drawable and set checked property to false
                value = "Un-Selected";
                textView.setCheckMarkDrawable(R.drawable.favourite_movie_icon);
                movie.setFavourite(false);
                textView.setChecked(false);
            } else {
// set cheek mark drawable and set checked property to true
                value = "Favourite";
                textView.setCheckMarkDrawable(R.drawable.favourite_movie_icon_checked);
                movie.setFavourite(true);
                textView.setChecked(true);
            }
            Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
