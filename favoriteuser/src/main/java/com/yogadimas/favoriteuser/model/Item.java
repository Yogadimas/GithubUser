package com.yogadimas.favoriteuser.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.yogadimas.favoriteuser.database.DatabaseContract;

import static com.yogadimas.favoriteuser.database.DatabaseContract.getFavorite;


public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("id")
    private int mId;
    @SerializedName("html_url")
    private String mHtmlUrl;

    public Item(Parcel in) {
        this.mAvatarUrl = in.readString();
        this.mLogin = in.readString();
        this.mId = in.readInt();
        this.mHtmlUrl = in.readString();
    }

    public Item(Cursor cursor) {
        this.mAvatarUrl = getFavorite(cursor, DatabaseContract.ThisGithubColumns.AVATAR_URL);
        this.mLogin = getFavorite(cursor, DatabaseContract.ThisGithubColumns.LOGIN);
        this.mId = Integer.parseInt(getFavorite(cursor, DatabaseContract.ThisGithubColumns.ID));
        this.mHtmlUrl = getFavorite(cursor, DatabaseContract.ThisGithubColumns.HTML_URL);
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.mAvatarUrl = avatarUrl;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        this.mLogin = login;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.mHtmlUrl = htmlUrl;
    }


    @NonNull
    @Override
    public String toString() {
        return
                "ItemsItem{" +
                        ",avatar_url = '" + mAvatarUrl + '\'' +
                        ",login = '" + mLogin + '\'' +
                        ",id = '" + mId + '\'' +
                        ",html_url = '" + mHtmlUrl + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAvatarUrl);
        dest.writeString(this.mLogin);
        dest.writeInt(this.mId);
        dest.writeString(this.mHtmlUrl);
    }
}