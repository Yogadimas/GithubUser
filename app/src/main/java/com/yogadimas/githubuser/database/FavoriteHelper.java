package com.yogadimas.githubuser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.LOGIN;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.TABLE_GITHUB_NAME;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.USER_ID;


public class FavoriteHelper {

    private static final String DATABASE_TABLE_FAVORITE_GITHUB_NAME = TABLE_GITHUB_NAME;
    private static SQLiteDatabase sDatabase;
    private static DatabaseHelper sDatabaseHelper;
    private static FavoriteHelper sFavoriteHelper;

    public FavoriteHelper(Context context) {
        sDatabaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (sFavoriteHelper == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (sFavoriteHelper == null) {
                    sFavoriteHelper = new FavoriteHelper(context);
                }
            }
        }
        return sFavoriteHelper;
    }

    public void open() throws SQLException {
        sDatabase = sDatabaseHelper.getWritableDatabase();
    }

    public Cursor getIdCursorFavorites(String id) {
        return sDatabase.query(DATABASE_TABLE_FAVORITE_GITHUB_NAME,
                null,
                USER_ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor getCursorFavorites() {
        return sDatabase.query(DATABASE_TABLE_FAVORITE_GITHUB_NAME,
                null,
                null,
                null,
                null,
                null,
                USER_ID + " ASC");
    }

    public void removeFavorites(String title) {
        sDatabase.delete(TABLE_GITHUB_NAME,
                LOGIN + " = '" + title + "'",
                null);
    }

    public long insertFavoritesProvider(ContentValues values) {
        return sDatabase.insert(DATABASE_TABLE_FAVORITE_GITHUB_NAME,
                null, values);
    }

    public int deleteFavoritesProvider(String id) {
        return sDatabase.delete(TABLE_GITHUB_NAME,
                USER_ID + "=?",
                new String[]{id});
    }

    public int updateFavoritesProvider(String id, ContentValues values) {
        return sDatabase.update(DATABASE_TABLE_FAVORITE_GITHUB_NAME,
                values,
                USER_ID + " =?",
                new String[]{id});
    }

}
