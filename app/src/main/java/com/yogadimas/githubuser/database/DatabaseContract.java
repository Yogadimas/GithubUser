package com.yogadimas.githubuser.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTH = "com.yogadimas.githubuser";
    public static final String SCHEME = "content";

    public static final class ThisGithubColumns implements BaseColumns {
        public static final String TABLE_GITHUB_NAME = "name_github_user_table";
        public static final String AVATAR_URL = "avatar_url";
        public static final String LOGIN = "login";
        public static final String USER_ID = "id";
        public static final String HTML_URL = "html_url";
        public static final Uri GITHUB_URI = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTH)
                .appendPath(TABLE_GITHUB_NAME)
                .build();
    }
}
