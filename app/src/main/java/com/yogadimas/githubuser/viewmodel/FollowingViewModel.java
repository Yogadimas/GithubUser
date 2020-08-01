package com.yogadimas.githubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yogadimas.githubuser.BuildConfig;
import com.yogadimas.githubuser.model.FollowingResponse;
import com.yogadimas.githubuser.network.ApiService;
import com.yogadimas.githubuser.network.Client;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingViewModel extends ViewModel {
    private MutableLiveData<ArrayList<FollowingResponse>> mFollowingResponseItemDataList;

    public void setLoadEventFollowing(String username) {
        try {
            ApiService mApiService = Client.getClient().create(ApiService.class);
            Call<ArrayList<FollowingResponse>> mCallFollowing = mApiService
                    .FollowingItemGithub(BuildConfig.API_KEY, username);
            mCallFollowing.enqueue(new Callback<ArrayList<FollowingResponse>>() {

                Response<ArrayList<FollowingResponse>> mResponseFollowingData;

                @Override
                public void onResponse(@NotNull Call<ArrayList<FollowingResponse>> call, @NotNull
                        Response<ArrayList<FollowingResponse>> responseFollowingData) {
                    this.mResponseFollowingData = responseFollowingData;
                    mFollowingResponseItemDataList.setValue(responseFollowingData.body());
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<FollowingResponse>> call,
                                      @NotNull Throwable t) {
                    Log.e("On failure following", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Catch exception e", String.valueOf(e));
        }
    }

    public LiveData<ArrayList<FollowingResponse>> getData() {
        if (mFollowingResponseItemDataList == null) {
            mFollowingResponseItemDataList = new MutableLiveData<>();
        }
        return mFollowingResponseItemDataList;
    }
}
