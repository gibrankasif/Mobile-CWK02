package com.gibran.mobile_cwk02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import java.util.ArrayList;

public class IMDBMovieAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<IMDBMovie> movies;
    LayoutInflater inflater;



    //constructor, call on creation
    public IMDBMovieAdapter(Context context, ArrayList<IMDBMovie> objects) {
//        super(context, resource, objects);
        this.context = context;
        this.movies = objects;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public IMDBMovie getItem(int position)
    {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public ArrayList<IMDBMovie> getList(){
        return (ArrayList<IMDBMovie>) movies;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.single_layer_view_imdb, null);

        TextView editMovie = convertView.findViewById(R.id.imdb_movie_textView);

        IMDBMovie movie = movies.get(position);
        String result = movie.getTitle() + " \n"+ movie.getRating()+"/10.0";
        editMovie.setText(result);

        return convertView;
    }






}
