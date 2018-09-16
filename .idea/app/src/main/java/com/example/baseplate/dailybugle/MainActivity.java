package com.example.baseplate.dailybugle;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<pojo>>{

    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String API_REQUEST_URL = "https://content.guardianapis.com/search?&show-fields=headline&api-key=150b0c4d-fcad-470a-a579-0424d80c045a";
    public static final int LOADER_ID = 1;

    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsItemListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsItemListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new NewsAdapter(this, new ArrayList<pojo>());
        newsItemListView.setAdapter(mAdapter);

        newsItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pojo currentNews = mAdapter.getItem(i);
                Uri newsUri = Uri.parse(currentNews.getURL());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(webIntent);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            ProgressBar loadingView = findViewById(R.id.loading_indicator);
            loadingView.setVisibility(View.GONE);
            mEmptyStateTextView.setText(getText(R.string.no_conn));
        }
    }

    @NonNull
    @Override
    public Loader<List<pojo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoaders(this, API_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<pojo>> loader, List<pojo> data) {
        mEmptyStateTextView.setText(getText(R.string.no_news));

        ProgressBar loadingView = findViewById(R.id.loading_indicator);
        loadingView.setVisibility(View.GONE);

        mAdapter.clear();

        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<pojo>> loader) {
        mAdapter.clear();
    }
}
