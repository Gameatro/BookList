package com.example.android.booksearch;

import android.graphics.Bitmap;

/**
 * Created by shind on 1/5/2018.
 */

public class Books {

    private Bitmap mImage;

    private String mTitle;

    private String mAuthor;

    private String mURL;

    public Books(Bitmap image, String title, String author, String URL){
        mImage = image;
        mTitle = title;
        mAuthor = author;
        mURL = URL;
    }
    public String getTitle(){return mTitle;}
    public Bitmap getImage(){return mImage;}
    public String getAuthor(){return mAuthor;}
    public String getURL(){return mURL;}




}
