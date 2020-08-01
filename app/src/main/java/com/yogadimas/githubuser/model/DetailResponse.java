package com.yogadimas.githubuser.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class DetailResponse {

    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("id")
    private int mId;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("name")
    private String mName;
    @SerializedName("hire_able")
    private Object mEmployed;
    @SerializedName("public_repos")
    private int mPublicRepos;
    @SerializedName("followers")
    private int mFollowers;
    @SerializedName("following")
    private int mFollowing;
    @SerializedName("public_gists")
    private int mPublicGists;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("company")
    private String mCompany;
    @SerializedName("blog")
    private String mBlog;
    @SerializedName("email")
    private String mEmail;

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public String getLogin() {
        return mLogin;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public Object getEmployed() {
        return mEmployed;
    }

    public int getPublicRepos() {
        return mPublicRepos;
    }

    public int getFollowers() {
        return mFollowers;
    }

    public int getFollowing() {
        return mFollowing;
    }

    public int getPublicGists() {
        return mPublicGists;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getCompany() {
        return mCompany;
    }

    public String getBlog() {
        return mBlog;
    }

    public String getEmail() {
        return mEmail;
    }

    @NotNull
    @Override
    public String toString() {
        return
                "DetailResponse{" +
                        ",avatar_url = '" + mAvatarUrl + '\'' +
                        ",login = '" + mLogin + '\'' +
                        ",id = '" + mId + '\'' +
                        ",html_url = '" + mHtmlUrl + '\'' +
                        ",name = '" + mName + '\'' +
                        ",hire_able = '" + mEmployed + '\'' +
                        ",public_repos = '" + mPublicRepos + '\'' +
                        ",followers = '" + mFollowers + '\'' +
                        ",following = '" + mFollowing + '\'' +
                        ",public_gists = '" + mPublicGists + '\'' +
                        ",location = '" + mLocation + '\'' +
                        ",company = '" + mCompany + '\'' +
                        ",blog = '" + mBlog + '\'' +
                        ",email = '" + mEmail + '\'' +
                        "}";
    }
}