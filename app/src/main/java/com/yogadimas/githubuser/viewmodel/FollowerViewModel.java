package com.yogadimas.githubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yogadimas.githubuser.BuildConfig;
import com.yogadimas.githubuser.model.FollowerResponse;
import com.yogadimas.githubuser.network.ApiService;
import com.yogadimas.githubuser.network.Client;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowerViewModel extends ViewModel {
    private MutableLiveData<ArrayList<FollowerResponse>> mFollowerResponseItemDataList;

    public void setLoadEventFollower(String username) {
        try {
            ApiService mApiService = Client.getClient().create(ApiService.class);
            Call<ArrayList<FollowerResponse>> mCallFollower = mApiService
                    .FollowerItemGithub(BuildConfig.API_KEY, username);
            mCallFollower.enqueue(new Callback<ArrayList<FollowerResponse>>() {

                Response<ArrayList<FollowerResponse>> mResponseFollowerData;

                @Override
                public void onResponse(@NotNull Call<ArrayList<FollowerResponse>> call, @NotNull
                        Response<ArrayList<FollowerResponse>> responseFollowerData) {
                    this.mResponseFollowerData = responseFollowerData;
                    mFollowerResponseItemDataList.setValue(responseFollowerData.body());
                }

                @Override
                public void onFailure(@NotNull Call<ArrayList<FollowerResponse>> call,
                                      @NotNull Throwable t) {
                    Log.e("on failure follower", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("catch exception e", String.valueOf(e));
        }
    }

    public LiveData<ArrayList<FollowerResponse>> getData() {
        if (mFollowerResponseItemDataList == null) {
            mFollowerResponseItemDataList = new MutableLiveData<>();
        }
        return mFollowerResponseItemDataList;
    }
}
