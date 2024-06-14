package com.yogadimas.githubuser.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.adapter.FollowerAdapter;
import com.yogadimas.githubuser.model.FollowerResponse;
import com.yogadimas.githubuser.view.activity.DetailActivity;
import com.yogadimas.githubuser.viewmodel.FollowerViewModel;
import com.yogadimas.githubuser.viewmodel.ItemViewModel;

import java.util.ArrayList;


public class FollowerFragment extends Fragment {
    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(),
            LinearLayoutManager.VERTICAL, false);
    private ArrayList<FollowerResponse> mFollowerResponse = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private View mIncludeNoFollower;
    private FollowerViewModel mFollowerViewModel;
    private ItemViewModel mItemViewModel;
    private FollowerAdapter mFollowerAdapter;
    private boolean onPause = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mViewFollower = inflater.inflate(R.layout.fragment_follower,
                container, false);


        mRecyclerView = mViewFollower.findViewById(R.id.rv_follower);
        mIncludeNoFollower = mViewFollower.findViewById(R.id.include_no_follower);
        mFollowerViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory())
                .get(FollowerViewModel.class);

        mItemViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory())
                .get(ItemViewModel.class);

        mFollowerAdapter = new FollowerAdapter();
        mFollowerAdapter.setOnItemClickCallback(this::showSelectedItem);

        initRow();
        getData();


        return mViewFollower;
    }

    private void getData() {
        mItemViewModel.setLoadEventUser(DetailActivity.sItem);
        mFollowerViewModel.setLoadEventFollower(DetailActivity.sItem);
        mFollowerViewModel.getData().observe(getViewLifecycleOwner(), followerResponse -> {
            mFollowerResponse.addAll(followerResponse);
            if (mFollowerResponse.size() != followerResponse.size()) {
                if (!onPause) {
                    mFollowerResponse.clear();
                    mFollowerResponse.addAll(followerResponse);
                }
            }
            mFollowerAdapter.setData(mFollowerResponse);
            mRecyclerView.setAdapter(mFollowerAdapter);
            mFollowerAdapter.notifyDataSetChanged();

        });
        mItemViewModel.getData().observe(getViewLifecycleOwner(), ItemResponse -> {
            if (ItemResponse.getFollowers() != 0 || !mFollowerResponse.isEmpty()) {
                mIncludeNoFollower.setVisibility(View.GONE);
            } else if (ItemResponse.getFollowers() == 0 && mFollowerResponse.isEmpty()) {
                mIncludeNoFollower.setVisibility(View.VISIBLE);
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
        if (mFollowerResponse.isEmpty()) {
            mItemViewModel.setLoadEventUser(DetailActivity.sItem);
            mFollowerViewModel.setLoadEventFollower(DetailActivity.sItem);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }

    private void showSelectedItem(FollowerResponse item) {
        Intent mIntent = new Intent(getContext(), DetailActivity.class);
        mIntent.putExtra(getString(R.string.key_intent), item.getLogin());
        startActivity(mIntent);
    }
}
