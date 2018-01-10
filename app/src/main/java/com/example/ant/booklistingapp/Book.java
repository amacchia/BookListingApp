package com.example.ant.booklistingapp;

import java.util.List;

/**
 * Created by Ant on 5/25/2017.
 */

public class Book {
    private List<String> authors;
    private String title;
    private String url;

    //Constructor
    public Book(List<String> authors, String title, String url){
        this.authors = authors;
        this.title = title;
        this.url = url;
    }

    //Getters
    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        String authorsString = authors.toString();
        return authorsString.substring(1, authorsString.length() - 1);
    }
}
