package com.yogadimas.githubuser.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yogadimas.githubuser.helper.MappingHelper;
import com.yogadimas.githubuser.model.Item;

import java.util.List;

import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.GITHUB_URI;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<List<Item>> mFavoritesItemDataList = new MutableLiveData<>();

    public LiveData<List<Item>> getFavorites() {
        return mFavoritesItemDataList;
    }

    public void setFavorites(Context context) {
        Uri mFavoriteUri = Uri.parse(GITHUB_URI.toString());
        Cursor mCursor = context.getContentResolver().query(mFavoriteUri,
                null,
                null,
                null,
                null);
        assert mCursor != null;
        List<Item> mItemList = MappingHelper.mapCursorToArrayList(mCursor);
        mCursor.moveToNext();
        mFavoritesItemDataList.postValue(mItemList);
        mCursor.close();
    }
}

