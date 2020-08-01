package com.yogadimas.githubuser.adapter;

import android.content.Context;
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
import com.yogadimas.githubuser.model.Item;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Item> mItemFavoriteList;
    private OnItemClickListener mOnItemClickListener;

    public FavoriteAdapter(Context context, List<Item> list) {
        this.mItemFavoriteList = list;
    }

    public void OnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<Item> item) {
        mItemFavoriteList.clear();
        mItemFavoriteList.addAll(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_github_user,
                parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item mItem = mItemFavoriteList.get(position);
        holder.bindItem(mItem, mOnItemClickListener);

        Glide.with(holder.itemView.getContext())
                .load(mItem.getAvatarUrl())
                .apply(new RequestOptions().override(130, 130))
                .into(holder.mCircleImageProfile);

        holder.mTextName.setText(mItem.getLogin());
        holder.mTextHtmlUrl.setText(mItem.getHtmlUrl());
        holder.mTextId.setText(String.valueOf(mItem.getId()));
    }

    @Override
    public int getItemCount() {
        return mItemFavoriteList.size();
    }

    public interface OnItemClickListener {
        void mOnItemClick(Item item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextName, mTextHtmlUrl, mTextId;
        CircleImageView mCircleImageProfile;
        LinearLayout mLinearButtonDetailItem;

        ViewHolder(@NonNull View favoriteItemView) {
            super(favoriteItemView);
            mTextName = favoriteItemView.findViewById(R.id.text_item);
            mTextHtmlUrl = favoriteItemView.findViewById(R.id.text_html_url_item);
            mTextId = favoriteItemView.findViewById(R.id.text_id_item);
            mLinearButtonDetailItem = favoriteItemView.findViewById(R.id.linear_button_detail_item);
            mCircleImageProfile = favoriteItemView.findViewById(R.id.circle_image_profile_item);
        }

        void bindItem(final Item item, final OnItemClickListener clickListener) {
            mLinearButtonDetailItem.setOnClickListener(v -> clickListener.mOnItemClick(item));
        }
    }
}
