package com.yogadimas.githubuser.view.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.database.DatabaseContract;
import com.yogadimas.githubuser.database.DatabaseHelper;
import com.yogadimas.githubuser.database.FavoriteHelper;
import com.yogadimas.githubuser.model.DetailResponse;
import com.yogadimas.githubuser.view.fragment.FollowerFragment;
import com.yogadimas.githubuser.view.fragment.FollowingFragment;
import com.yogadimas.githubuser.viewmodel.ItemViewModel;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.AVATAR_URL;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.GITHUB_URI;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.HTML_URL;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.LOGIN;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.TABLE_GITHUB_NAME;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.USER_ID;

public class DetailActivity extends AppCompatActivity {
    public static String sItem;
    private SQLiteDatabase mDatabase;
    private boolean mClicked = false;
    private CircleImageView mCircleImageProfile;
    private TextView mTextName, mTextUsername, mTextYesHire, mTextNoHire, mTextRepository,
            mTextFollowers, mTextFollowing, mTextGists, mTextLocation, mTextCompany,
            mTextEmail, mTextWebsite;
    private ViewPager mViewPagerDetail;
    private FavoriteHelper mFavoriteHelper;
    private FloatingActionButton mButtonFavorite;
    private Toolbar mToolbarDetail;
    private TabLayout mTabNavigation;
    private Cursor mCursor;


    @SuppressLint("Assert")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        idInitiation();


        setSupportActionBar(mToolbarDetail);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_detail_user);

        mFavoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        mFavoriteHelper.open();

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(DetailActivity.this);
        mDatabase = mDatabaseHelper.getWritableDatabase();

        mViewPagerDetail.setAdapter(new viewPagerAdapter(getSupportFragmentManager(),
                mTabNavigation.getTabCount()));
        mViewPagerDetail.addOnPageChangeListener(new TabLayout
                .TabLayoutOnPageChangeListener(mTabNavigation));
        mTabNavigation.setTabMode(TabLayout.MODE_FIXED);
        mTabNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPagerDetail.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getDetailItem();

    }

    private void idInitiation() {
        mTextName = findViewById(R.id.text_name);
        mTextUsername = findViewById(R.id.text_username);
        mTextYesHire = findViewById(R.id.text_yes_hire_able);
        mTextNoHire = findViewById(R.id.text_no_hire_able);
        mTextRepository = findViewById(R.id.text_repository);
        mTextFollowers = findViewById(R.id.text_followers);
        mTextFollowing = findViewById(R.id.text_following);
        mTextGists = findViewById(R.id.text_gists);
        mTextLocation = findViewById(R.id.text_location);
        mTextCompany = findViewById(R.id.text_company);
        mTextEmail = findViewById(R.id.text_email);
        mTextWebsite = findViewById(R.id.text_website);
        mCircleImageProfile = findViewById(R.id.circle_image_profile);
        mToolbarDetail = findViewById(R.id.toolbar_detail);
        mTabNavigation = findViewById(R.id.tab_navigation);
        mButtonFavorite = findViewById(R.id.button_favorite);
        mViewPagerDetail = findViewById(R.id.viewpager_detail);
    }

    private void getDetailItem() {
        Intent intent = getIntent();
        sItem = intent.getStringExtra(getString(R.string.key_intent));
        ItemViewModel mItemViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory())
                .get(ItemViewModel.class);
        mItemViewModel.setLoadEventUser(sItem);
        mItemViewModel.getData().observe(this, DetailResponse -> {
            mTextName.setText(DetailResponse.getLogin());

            if (DetailResponse.getName() != null) {
                mTextUsername.setText(DetailResponse.getName());
            }

            if (DetailResponse.getEmployed() != null) {
                mTextYesHire.setVisibility(View.VISIBLE);
                mTextNoHire.setVisibility(View.GONE);
            } else {
                mTextYesHire.setVisibility(View.GONE);
                mTextNoHire.setVisibility(View.VISIBLE);
            }

            if (DetailResponse.getPublicRepos() != 0) {
                mTextRepository.setText(String.valueOf(DetailResponse.getPublicRepos()));
            }

            if (DetailResponse.getFollowers() != 0) {
                mTextFollowers.setText(String.valueOf(DetailResponse.getFollowers()));
            }

            if (DetailResponse.getFollowing() != 0) {
                mTextFollowing.setText(String.valueOf(DetailResponse.getFollowing()));
            }

            if (DetailResponse.getPublicGists() != 0) {
                mTextGists.setText(String.valueOf(DetailResponse.getPublicGists()));
            }

            if (DetailResponse.getLocation() != null) {
                mTextLocation.setText(DetailResponse.getLocation());
            }

            if (DetailResponse.getCompany() != null) {
                mTextCompany.setText(DetailResponse.getCompany());
            }

            if (!DetailResponse.getBlog().equals("")) {
                mTextWebsite.setText(DetailResponse.getBlog());
            }

            if (DetailResponse.getEmail() != null) {
                mTextEmail.setText(DetailResponse.getEmail());
            }

            Glide.with(getApplicationContext())
                    .load(DetailResponse.getAvatarUrl())
                    .into(mCircleImageProfile);

            if (userGithub(sItem)) {
                mButtonFavorite.setOnClickListener(v -> {
                    if (mClicked) {
                        setRemoveFavorites();
                    } else {
                        setInsertFavorites(DetailResponse);
                    }
                    mClicked = !mClicked;
                    setImageResource();
                });
            } else {
                mButtonFavorite.setOnClickListener(v -> {
                    if (mClicked) {
                        setRemoveFavorites();
                    } else {
                        setInsertFavorites(DetailResponse);
                    }
                    mClicked = !mClicked;
                    setImageResource();
                });
            }

        });

    }

    private void setRemoveFavorites() {
        mFavoriteHelper.removeFavorites(sItem);
        Toast.makeText(this, R.string.msg_favorite_remove,
                Toast.LENGTH_SHORT).show();
    }

    public void setInsertFavorites(DetailResponse detailResponse) {
        ContentValues mContentValuesFavorites = new ContentValues();
        mContentValuesFavorites.put(AVATAR_URL, detailResponse.getAvatarUrl());
        mContentValuesFavorites.put(LOGIN, detailResponse.getLogin());
        mContentValuesFavorites.put(USER_ID, detailResponse.getId());
        mContentValuesFavorites.put(HTML_URL, detailResponse.getHtmlUrl());
        getContentResolver().insert(GITHUB_URI, mContentValuesFavorites);
        Toast.makeText(this, R.string.msg_favorite_add,
                Toast.LENGTH_SHORT).show();
    }

    private void setImageResource() {
        if (mClicked) {
            mButtonFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            mButtonFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    public boolean userGithub(String user) {
        String[] mUserArray = {user};

        mFavoriteHelper = new FavoriteHelper(this);
        mFavoriteHelper.open();

        mCursor = mDatabase.query(TABLE_GITHUB_NAME,
                null,
                DatabaseContract.ThisGithubColumns.LOGIN + " =?",
                mUserArray,
                null,
                null,
                null,
                "1");

        setCursorMoveLoad();
        setImageResource();
        setCursorCount();
        return setCursorCount();
    }

    private void setCursorMoveLoad() {
        if (mCursor != null) {
            if (mCursor.moveToFirst()) mClicked = true;
            mCursor.close();
        }
    }

    private boolean setCursorCount() {
        boolean mLoadCount;
        assert mCursor != null;
        mLoadCount = (mCursor.getCount() > 0);
        mCursor.close();
        return mLoadCount;
    }

    @Override
    protected void onDestroy() {
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

    public static class viewPagerAdapter extends FragmentStatePagerAdapter {
        int mTabs;

        @SuppressWarnings("deprecation")
        viewPagerAdapter(FragmentManager mFragmentManager, int mTabs) {
            super(mFragmentManager);
            this.mTabs = mTabs;
        }

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FollowerFragment();
                case 1:
                    return new FollowingFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mTabs;
        }
    }


}
