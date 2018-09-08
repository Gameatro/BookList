package com.example.android.booksearch;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class BookList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {

    private TextView mEmptyStateTextView;
    private static final String LOG_TAG = BookList.class.getName();

    private static final int BOOKLOADER_ID = 1;

    private BooksAdapter mAdapter;


    private   String USGS_REQUEST_URL = "  https://www.googleapis.com/books/v1/volumes?q="  ;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BookList.this, BookSearchActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        Bundle bundle = getIntent().getExtras();
        String bookSearch = bundle.getString("bookSearch");
        USGS_REQUEST_URL+=bookSearch;

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        ListView bookView = (ListView) findViewById(R.id.list);
        mAdapter = new BooksAdapter(this, new ArrayList<Books>());
        bookView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.emptyView);
        bookView.setEmptyView(mEmptyStateTextView);

        bookView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Books currentBook = mAdapter.getItem(i);

                Uri bookUri = Uri.parse(currentBook.getURL());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOKLOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progress);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_connection);
        }

    }
    @Override
    public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, USGS_REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> books) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
        mEmptyStateTextView.setText(R.string.not_found);
        ProgressBar indetermined =  (ProgressBar) findViewById(R.id.progress);
        indetermined.setVisibility(View.GONE);
    }
    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
    }

