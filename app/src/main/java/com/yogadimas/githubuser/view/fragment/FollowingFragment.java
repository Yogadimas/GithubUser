package com.yogadimas.githubuser.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.adapter.FollowingAdapter;
import com.yogadimas.githubuser.model.FollowingResponse;
import com.yogadimas.githubuser.view.activity.DetailActivity;
import com.yogadimas.githubuser.viewmodel.FollowingViewModel;
import com.yogadimas.githubuser.viewmodel.ItemViewModel;

import java.util.ArrayList;


public class FollowingFragment extends Fragment {

    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(),
            LinearLayoutManager.VERTICAL, false);
    private ArrayList<FollowingResponse> mFollowingResponses = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private View mIncludeNoFollowing;
    private FollowingViewModel mFollowingViewModel;
    private ItemViewModel mItemViewModel;
    private FollowingAdapter mFollowingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mViewFollowing = inflater.inflate(R.layout.fragment_following,
                container, false);

        mRecyclerView = mViewFollowing.findViewById(R.id.rv_following);
        mIncludeNoFollowing = mViewFollowing.findViewById(R.id.include_no_following);
        mFollowingViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory())
                .get(FollowingViewModel.class);

        mItemViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory())
                .get(ItemViewModel.class);

        mFollowingAdapter = new FollowingAdapter();
        mFollowingAdapter.setOnItemClickCallback(this::showSelectedItem);

        initRow();
        getData();

        return mViewFollowing;
    }

    private void getData() {
        mFollowingViewModel.setLoadEventFollowing(DetailActivity.sItem);
        mFollowingViewModel.getData().observe(getViewLifecycleOwner(), followingResponses -> {
            mFollowingAdapter.setData(mFollowingResponses);
            mFollowingResponses.addAll(followingResponses);
            mRecyclerView.setAdapter(mFollowingAdapter);
            mFollowingAdapter.notifyDataSetChanged();
        });
        mItemViewModel.setLoadEventUser(DetailActivity.sItem);
        mItemViewModel.getData().observe(getViewLifecycleOwner(), ItemResponse -> {
            if (ItemResponse.getFollowing() != 0) {
                mIncludeNoFollowing.setVisibility(View.GONE);
            } else {
                mIncludeNoFollowing.setVisibility(View.VISIBLE);
            }
        });
    }


    private void initRow() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        mItemViewModel.setLoadEventUser(DetailActivity.sItem);
        mFollowingViewModel.setLoadEventFollowing(DetailActivity.sItem);

    }

    private void showSelectedItem(FollowingResponse item) {
        Intent mIntent = new Intent(getContext(), DetailActivity.class);
        mIntent.putExtra(getString(R.string.key_intent), item.getLogin());
        startActivity(mIntent);
    }
}
