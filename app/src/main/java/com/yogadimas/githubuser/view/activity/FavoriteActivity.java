package com.yogadimas.githubuser.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.adapter.FavoriteAdapter;
import com.yogadimas.githubuser.database.FavoriteHelper;
import com.yogadimas.githubuser.model.Item;
import com.yogadimas.githubuser.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView mRecyclerFavorite;
    private View mIncludeNoFavorites;
    private FavoriteAdapter mFavoriteAdapter;
    private ArrayList<Item> userArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar mToolbarFavorite = findViewById(R.id.toolbar_favorite);
        mRecyclerFavorite = findViewById(R.id.rv_favorite);
        mIncludeNoFavorites = findViewById(R.id.include_no_favorite);
        mFavoriteAdapter = new FavoriteAdapter(getApplicationContext(), userArray);

        setSupportActionBar(mToolbarFavorite);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_my_favorites);

        FavoriteHelper mFavoriteHelper = FavoriteHelper.getInstance(FavoriteActivity.this);
        mFavoriteHelper.open();
        mRecyclerFavorite.setHasFixedSize(true);
        mRecyclerFavorite.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerFavorite.setAdapter(mFavoriteAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        FavoriteViewModel mViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory())
                .get(FavoriteViewModel.class);
        mViewModel.getFavorites().observe(this, users -> {
            mFavoriteAdapter.setData(users);
            if (users.isEmpty()) {
                mRecyclerFavorite.setVisibility(View.GONE);
                mIncludeNoFavorites.setVisibility(View.VISIBLE);
            } else {
                mRecyclerFavorite.setVisibility(View.VISIBLE);
                mIncludeNoFavorites.setVisibility(View.GONE);
            }
            mFavoriteAdapter.OnItemClickListener((Item item) -> {
                Intent mIntent = new Intent(FavoriteActivity.this,
                        DetailActivity.class);
                mIntent.putExtra(getString(R.string.key_intent), item.getLogin());
                startActivity(mIntent);
            });
        });
        mViewModel.setFavorites(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_reminder) {
            startActivity(new Intent(this, ReminderActivity.class));
        } else if (item.getItemId() == R.id.menu_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
