package com.yogadimas.githubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yogadimas.githubuser.BuildConfig;
import com.yogadimas.githubuser.model.Item;
import com.yogadimas.githubuser.model.ItemResponse;
import com.yogadimas.githubuser.network.ApiService;
import com.yogadimas.githubuser.network.Client;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Item>> mSearchItemDataList = new MutableLiveData<>();

    public void setLoadEventSearch(String query) {
        try {
            ApiService mApiService = Client.getClient().create(ApiService.class);
            Call<ItemResponse> mCallSearch = mApiService.searchItemGithub
                    (BuildConfig.API_KEY, query);
            mCallSearch.enqueue(new Callback<ItemResponse>() {

                @Override
                public void onResponse(@NotNull Call<ItemResponse> call,
                                       @NotNull Response<ItemResponse> responseSearchData) {

                    if (!responseSearchData.isSuccessful()) {
                        Log.d("On Response Success", responseSearchData.message());
                    } else if (responseSearchData.body() != null) {
                        ArrayList<Item> mItemList = new ArrayList<>(responseSearchData
                                .body().getItems());
                        mSearchItemDataList.postValue(mItemList);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ItemResponse> call,
                                      @NotNull Throwable t) {
                    Log.e("On failure search", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Catch exception e", String.valueOf(e));
        }
    }

    public LiveData<ArrayList<Item>> getData() {
        return mSearchItemDataList;
    }
}
