package com.yogadimas.githubuser.network;

import com.yogadimas.githubuser.model.DetailResponse;
import com.yogadimas.githubuser.model.FollowerResponse;
import com.yogadimas.githubuser.model.FollowingResponse;
import com.yogadimas.githubuser.model.ItemResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @GET("users/{username}")
    Call<DetailResponse> detailItemGithub
            (@Header("Authorization") String authorization, @Path("username") String username);

    @GET("/search/users")
    Call<ItemResponse> searchItemGithub
            (@Header("Authorization") String authorization, @Query("q") String username);

    @GET("users/{username}/followers")
    Call<ArrayList<FollowerResponse>> FollowerItemGithub
            (@Header("Authorization") String authorization, @Path("username") String username);

    @GET("users/{username}/following")
    Call<ArrayList<FollowingResponse>> FollowingItemGithub
            (@Header("Authorization") String authorization, @Path("username") String username);

}
