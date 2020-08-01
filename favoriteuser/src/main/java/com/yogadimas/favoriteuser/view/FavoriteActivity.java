package com.yogadimas.favoriteuser.view;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yogadimas.favoriteuser.R;

import java.util.Objects;

import static com.yogadimas.favoriteuser.database.DatabaseContract.ThisGithubColumns.GITHUB_URI;

public class FavoriteActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerFavorite;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar mToolbar = findViewById(R.id.toolbar_favorite);
        mSwipeRefresh = findViewById(R.id.swipe_refresh);
        mRecyclerFavorite = findViewById(R.id.rv_favorite);

        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_my_favorites);

        mRecyclerFavorite.setHasFixedSize(true);
        mRecyclerFavorite.setLayoutManager(new LinearLayoutManager(this));

        mSwipeRefresh.setOnRefreshListener(this);

        new FavoriteActivity.LoadItemFavoritesUser().execute();
    }

    @Override
    public void onRefresh() {
        new FavoriteActivity.LoadItemFavoritesUser().execute();
        UpdateData();
    }

    private void UpdateData() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_update) {
            new FavoriteActivity.LoadItemFavoritesUser().execute();
            mSwipeRefresh.setRefreshing(true);
            UpdateData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadItemFavoritesUser extends AsyncTask<Void, Void, Cursor> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return FavoriteActivity.this.getContentResolver().query(GITHUB_URI, null,
                    null, null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            com.yogadimas.favoriteuser.adapter.FavoriteAdapter mAdapterFavorite = new
                    com.yogadimas.favoriteuser.adapter.FavoriteAdapter(getApplicationContext());
            mAdapterFavorite.setCursor(cursor);
            mAdapterFavorite.notifyDataSetChanged();
            mRecyclerFavorite.setAdapter(mAdapterFavorite);
        }
    }

}
