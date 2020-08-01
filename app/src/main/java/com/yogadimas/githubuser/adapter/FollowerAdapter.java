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
import com.yogadimas.githubuser.model.FollowerResponse;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolder> {

    private ArrayList<FollowerResponse> mFollowerResponseItemList = new ArrayList<>();
    private OnItemClickCallback mOnItemClickCallback;

    public void setData(ArrayList<FollowerResponse> mData) {
        this.mFollowerResponseItemList.clear();
        this.mFollowerResponseItemList.addAll(mData);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback mOnItemClickCallback) {
        this.mOnItemClickCallback = mOnItemClickCallback;
    }

    @NonNull
    @Override
    public FollowerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_github_user,
                parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerAdapter.ViewHolder holder, int position) {
        FollowerResponse mFollowerResponseItem = mFollowerResponseItemList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(mFollowerResponseItem.getAvatarUrl())
                .apply(new RequestOptions().override(110, 110))
                .into(holder.mCircleImageProfile);

        holder.mTextName.setText(mFollowerResponseItem.getLogin());
        holder.mTextHtmlUrl.setText(mFollowerResponseItem.getHtmlUrl());
        holder.mTextId.setText(String.valueOf(mFollowerResponseItem.getId()));
        holder.mLinearButtonDetailItem.setOnClickListener(v -> mOnItemClickCallback
                .onItemClicked(mFollowerResponseItemList.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return mFollowerResponseItemList.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(FollowerResponse followerResponse);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextName, mTextHtmlUrl, mTextId;
        CircleImageView mCircleImageProfile;
        LinearLayout mLinearButtonDetailItem;

        public ViewHolder(@NonNull View followerItemView) {
            super(followerItemView);
            mTextName = followerItemView.findViewById(R.id.text_item);
            mTextHtmlUrl = followerItemView.findViewById(R.id.text_html_url_item);
            mTextId = followerItemView.findViewById(R.id.text_id_item);
            mLinearButtonDetailItem = followerItemView.findViewById(R.id.linear_button_detail_item);
            mCircleImageProfile = followerItemView.findViewById(R.id.circle_image_profile_item);
        }
    }
}
