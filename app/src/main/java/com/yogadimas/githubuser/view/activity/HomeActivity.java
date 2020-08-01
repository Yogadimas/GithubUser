package com.yogadimas.githubuser.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.adapter.SearchAdapter;
import com.yogadimas.githubuser.viewmodel.SearchViewModel;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private SearchViewModel mSearchViewModel;
    private SearchAdapter mSearchAdapter;
    private ProgressBar mProgressBarHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mProgressBarHome = findViewById(R.id.progress_bar_home);
        SearchView mSearchHome = findViewById(R.id.search_home);
        RecyclerView mRecyclerHome = findViewById(R.id.rv_home);
        Toolbar mToolbarHome = findViewById(R.id.toolbar_home);

        setSupportActionBar(mToolbarHome);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_search_user);

        mSearchHome.setQueryHint(getString(R.string.search_view_find));

        mRecyclerHome.setLayoutManager(new LinearLayoutManager(this));
        mSearchAdapter = new SearchAdapter(this);
        mSearchAdapter.notifyDataSetChanged();

        mRecyclerHome.setAdapter(mSearchAdapter);
        mSearchViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory())
                .get(SearchViewModel.class);
        mSearchHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mProgressBarHome.setVisibility(View.VISIBLE);
                mSearchViewModel.setLoadEventSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProgressBarHome.setVisibility(View.VISIBLE);
                mSearchViewModel.setLoadEventSearch(newText);
                return false;
            }
        });

        mSearchViewModel.getData().observe(this, searchUserItems -> {
            mProgressBarHome.setVisibility(View.GONE);
            if (searchUserItems.size() != 0) {
                mSearchAdapter.setData(searchUserItems);
            } else {
                Toast.makeText(HomeActivity.this, R.string.msg_no_result,
                        Toast.LENGTH_SHORT).show();
            }
        });

        mSearchHome.setOnCloseListener(() -> {
            mProgressBarHome.setVisibility(View.GONE);
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_favorite) {
            startActivity(new Intent(this, FavoriteActivity.class));
        } else if (item.getItemId() == R.id.menu_reminder) {
            startActivity(new Intent(this, ReminderActivity.class));
        } else if (item.getItemId() == R.id.menu_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
