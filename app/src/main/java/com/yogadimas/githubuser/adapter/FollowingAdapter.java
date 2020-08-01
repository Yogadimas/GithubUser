package com.yogadimas.githubuser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.model.FollowingResponse;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

    private ArrayList<FollowingResponse> mFollowingResponseItemList = new ArrayList<>();
    private FollowingAdapter.OnItemClickCallback mOnItemClickCallback;

    public void setData(ArrayList<FollowingResponse> mData) {
        this.mFollowingResponseItemList.clear();
        this.mFollowingResponseItemList.addAll(mData);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(FollowingAdapter.OnItemClickCallback mOnItemClickCallback) {
        this.mOnItemClickCallback = mOnItemClickCallback;
    }


    @NonNull
    @Override
    public FollowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_github_user,
                parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingAdapter.ViewHolder holder, int position) {
        FollowingResponse mFollowingResponseItem = mFollowingResponseItemList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(mFollowingResponseItem.getAvatarUrl())
                .apply(new RequestOptions().override(110, 110))
                .into(holder.mCircleImageProfile);
        holder.mTextName.setText(mFollowingResponseItem.getLogin());
        holder.mTextHtmlUrl.setText(mFollowingResponseItem.getHtmlUrl());
        holder.mTextId.setText(String.valueOf(mFollowingResponseItem.getId()));
        holder.mLinearButtonDetailItem.setOnClickListener(v -> mOnItemClickCallback
                .onItemClicked(mFollowingResponseItemList.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return mFollowingResponseItemList.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(FollowingResponse followingResponse);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextName, mTextHtmlUrl, mTextId;
        CircleImageView mCircleImageProfile;
        LinearLayout mLinearButtonDetailItem;

        public ViewHolder(@NonNull View followingItemView) {
            super(followingItemView);
            mTextName = followingItemView.findViewById(R.id.text_item);
            mTextHtmlUrl = followingItemView.findViewById(R.id.text_html_url_item);
            mTextId = followingItemView.findViewById(R.id.text_id_item);
            mLinearButtonDetailItem = followingItemView.findViewById(R.id.linear_button_detail_item);
            mCircleImageProfile = followingItemView.findViewById(R.id.circle_image_profile_item);
        }
    }
}
