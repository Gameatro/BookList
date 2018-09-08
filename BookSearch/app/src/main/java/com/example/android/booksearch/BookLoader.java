package com.example.android.booksearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by shind on 1/5/2018.
 */

public class BookLoader extends AsyncTaskLoader {
    private static final String LOG_TAG = BookLoader.class.getName();
     private String mUrl;

     public BookLoader(Context context, String url){
         super(context);
         mUrl = url;
     }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Books> books = QueryUtils.fetchBookData(mUrl);
        return books;

    }
}
