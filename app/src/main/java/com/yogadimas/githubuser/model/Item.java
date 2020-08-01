package com.yogadimas.githubuser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

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

    public Item(Integer userId, String login, String avatarUrl, String htmlUrl) {
        this.mId = userId;
        this.mLogin = login;
        this.mAvatarUrl = avatarUrl;
        this.mHtmlUrl = htmlUrl;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public String getLogin() {
        return mLogin;
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

    @NotNull
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