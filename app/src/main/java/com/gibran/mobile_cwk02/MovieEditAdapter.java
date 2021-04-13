package com.gibran.mobile_cwk02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieEditAdapter extends BaseAdapter {
    private final Context context;
    private final List<Movie> movies;
    LayoutInflater inflater;
    private String value;


    //constructor, call on creation
    public MovieEditAdapter(Context context, ArrayList<Movie> objects) {
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.single_movie_choice_selection, null);
        TextView editMovie = convertView.findViewById(R.id.edit_movie_textView);
//        CheckedTextView textView = convertView.findViewById(R.id.edit_movie_textView);
        //get the property we are displaying
        Movie movie = movies.get(position);
        String results = movie.getTitle()+ " " +movie.getYear() + "\n" +movie.getDirector() +"\n"+movie.getActors();
        editMovie.setText(results);

        return convertView;
    }




}