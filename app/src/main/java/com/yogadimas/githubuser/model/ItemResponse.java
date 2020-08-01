package com.yogadimas.githubuser.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemResponse {

    @SerializedName("total_count")
    private int mTotalCount;

    @SerializedName("incomplete_results")
    private boolean mIncompleteResults;

    @SerializedName("items")
    private List<Item> mItemList;

    public List<Item> getItems() {
        return mItemList;
    }

    @NotNull
    @Override
    public String toString() {
        return
                "ItemResponse{" +
                        "total_count = '" + mTotalCount + '\'' +
                        ",incomplete_results = '" + mIncompleteResults + '\'' +
                        ",items = '" + mItemList + '\'' +
                        "}";
    }
}