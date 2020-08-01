package com.yogadimas.githubuser.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yogadimas.githubuser.database.FavoriteHelper;

import java.util.Objects;

import static com.yogadimas.githubuser.database.DatabaseContract.AUTH;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.GITHUB_URI;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.TABLE_GITHUB_NAME;

public class FavoritesProvider extends ContentProvider {
    static final int GITHUB_FAVORITE_USER = 80;
    static final int GITHUB_FAVORITE_ID = 81;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTH, TABLE_GITHUB_NAME, GITHUB_FAVORITE_USER);
        URI_MATCHER.addURI(AUTH, TABLE_GITHUB_NAME + "/#", GITHUB_FAVORITE_ID);
    }

    FavoriteHelper mFavoriteHelper;

    @Override
    public boolean onCreate() {
        mFavoriteHelper = new FavoriteHelper(getContext());
        mFavoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor mCursor;
        switch (URI_MATCHER.match(uri)) {
            case GITHUB_FAVORITE_USER:
                mCursor = mFavoriteHelper.getCursorFavorites();
                break;
            case GITHUB_FAVORITE_ID:
                mCursor = mFavoriteHelper.getIdCursorFavorites(uri.getLastPathSegment());
                break;
            default:
                mCursor = null;
                break;
        }
        if (mCursor != null) {
            mCursor.setNotificationUri(Objects.requireNonNull(getContext())
                    .getContentResolver(), uri);
        }
        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long mAdd;
        Uri mContentUri = null;

        if (URI_MATCHER.match(uri) == GITHUB_FAVORITE_USER) {
            mAdd = mFavoriteHelper.insertFavoritesProvider(values);
            if (mAdd > 0) {
                mContentUri = ContentUris.withAppendedId(GITHUB_URI, mAdd);
            }
        } else {
            mAdd = 0;
        }
        if (mAdd > 0) {
            Objects.requireNonNull(getContext())
                    .getContentResolver().notifyChange(uri, null);
        }
        return mContentUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int mDeleted;
        if (URI_MATCHER.match(uri) == GITHUB_FAVORITE_ID) {
            mDeleted = mFavoriteHelper.deleteFavoritesProvider(uri.getLastPathSegment());
        } else {
            mDeleted = 0;
        }
        if (mDeleted > 0) {
            Objects.requireNonNull(getContext())
                    .getContentResolver().notifyChange(uri, null);
        }
        return mDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int mUpdate;
        if (URI_MATCHER.match(uri) == GITHUB_FAVORITE_ID) {
            mUpdate = mFavoriteHelper.updateFavoritesProvider(uri.getLastPathSegment(), values);
        } else {
            mUpdate = 0;
        }
        if (mUpdate > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return mUpdate;
    }
}
