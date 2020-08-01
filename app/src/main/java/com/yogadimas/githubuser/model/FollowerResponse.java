package com.yogadimas.githubuser.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class FollowerResponse {

    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("id")
    private int mId;
    @SerializedName("html_url")
    private String mHtmlUrl;

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public String getLogin() {
        return mLogin;
    }

    public Integer getId() {
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
                "FollowerResponse{" +
                        ",avatar_url = '" + mAvatarUrl + '\'' +
                        ",login = '" + mLogin + '\'' +
                        ",id = '" + mId + '\'' +
                        ",html_url = '" + mHtmlUrl + '\'' +
                        "}";
    }
}