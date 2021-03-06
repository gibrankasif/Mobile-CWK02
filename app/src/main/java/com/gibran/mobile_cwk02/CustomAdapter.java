//package com.gibran.mobile_cwk02;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckedTextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//public class CustomAdapter extends BaseAdapter {
//    ArrayList<Movie> movies;
//    Context context;
//    LayoutInflater inflter;
//    String value;
//
//    public CustomAdapter(Context context, ArrayList <Movie> movies) {
//        this.context = context;
//        this.movies = movies;
//        inflter = (LayoutInflater.from(context));
//
//    }
//
//    @Override
//    public int getCount() {
//        return movies.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        view = inflter.inflate(R.layout.activity_display_movies, null);
//        final CheckedTextView simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.simpleCheckedTextView);
//        simpleCheckedTextView.setText(movies.get(position).getTitle());
//// perform on Click Event Listener on CheckedTextView
//        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (simpleCheckedTextView.isChecked()) {
//// set cheek mark drawable and set checked property to false
//                    value = "un-Checked";
//                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.favourite_movie_icon);
//                    simpleCheckedTextView.setChecked(false);
//                } else {
//// set cheek mark drawable and set checked property to true
//                    value = "Checked";
//                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.favourite_movie_icon_checked);
//                    simpleCheckedTextView.setChecked(true);
//                }
//                Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
//            }
//        });
//        return view;
//    }
//}