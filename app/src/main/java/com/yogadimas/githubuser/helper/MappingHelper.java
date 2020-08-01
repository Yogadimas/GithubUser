package com.yogadimas.githubuser.helper;

import android.database.Cursor;

import com.yogadimas.githubuser.model.Item;

import java.util.ArrayList;

import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.AVATAR_URL;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.HTML_URL;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.LOGIN;
import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.USER_ID;


public class MappingHelper {
    public static ArrayList<Item> mapCursorToArrayList(Cursor item) {
        ArrayList<Item> mItemList = new ArrayList<>();
        while (item.moveToNext()) {
            String mAvatar_Url = item.getString(item.getColumnIndexOrThrow(AVATAR_URL));
            String mLogin = item.getString(item.getColumnIndexOrThrow(LOGIN));
            Integer mUser_id = item.getInt(item.getColumnIndexOrThrow(USER_ID));
            String mHtml_Url = item.getString(item.getColumnIndexOrThrow(HTML_URL));
            mItemList.add(new Item(mUser_id, mLogin, mAvatar_Url, mHtml_Url));
        }
        return mItemList;
    }
}
