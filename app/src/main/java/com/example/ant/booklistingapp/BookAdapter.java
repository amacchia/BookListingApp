package com.example.ant.booklistingapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ant on 5/25/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    //Constructor
    public BookAdapter(Activity context, ArrayList<Book> books){
        //ArrayAdapter (Context context, int resource, List<T> objects)
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);
        String authors = currentBook.getAuthors();
        String title = currentBook.getTitle();

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);

        titleView.setText(title);
        authorView.setText(authors);


        return listItemView;
    }
}
