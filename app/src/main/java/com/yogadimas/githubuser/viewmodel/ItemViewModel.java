package com.yogadimas.githubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yogadimas.githubuser.BuildConfig;
import com.yogadimas.githubuser.model.DetailResponse;
import com.yogadimas.githubuser.network.ApiService;
import com.yogadimas.githubuser.network.Client;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<DetailResponse> mItemResponseData;

    public void setLoadEventUser(String username) {
        try {
            ApiService mApiService = Client.getClient().create(ApiService.class);
            Call<DetailResponse> mCallUser = mApiService.detailItemGithub
                    (BuildConfig.API_KEY, username);
            mCallUser.enqueue(new Callback<DetailResponse>() {

                Response<DetailResponse> mResponseItemData;

                @Override
                public void onResponse(@NotNull Call<DetailResponse> call,
                                       @NotNull Response<DetailResponse> responseItemData) {
                    this.mResponseItemData = responseItemData;
                    mItemResponseData.setValue(responseItemData.body());
                }

                @Override
                public void onFailure(@NotNull Call<DetailResponse> call,
                                      @NotNull Throwable t) {
                    Log.e("On failure item", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Catch exception e", String.valueOf(e));
        }
    }

    public LiveData<DetailResponse> getData() {
        if (mItemResponseData == null) {
            mItemResponseData = new MutableLiveData<>();
        }
        return mItemResponseData;
    }
}
