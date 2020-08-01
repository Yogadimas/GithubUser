package com.yogadimas.githubuser.adapter;

import android.content.Intent;
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
import com.yogadimas.githubuser.view.activity.DetailActivity;
import com.yogadimas.githubuser.view.activity.HomeActivity;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<Item> mItemList = new ArrayList<>();
    private HomeActivity mHomeActivity;

    public SearchAdapter(HomeActivity mHomeActivity) {
        this.mHomeActivity = mHomeActivity;

    }

    public void setData(ArrayList<Item> data) {
        this.mItemList.clear();
        this.mItemList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_github_user,
                parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapter.ViewHolder holder,
                                 final int position) {
        Item mItem = mItemList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(mItem.getAvatarUrl())
                .apply(new RequestOptions().override(130, 130))
                .into(holder.mCircleImageProfile);

        holder.mTextName.setText(mItem.getLogin());
        holder.mTextHtmlUrl.setText(mItem.getHtmlUrl());
        holder.mTextId.setText(String.valueOf(mItem.getId()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextName, mTextHtmlUrl, mTextId;
        CircleImageView mCircleImageProfile;
        LinearLayout mLinearButtonDetailItem;

        public ViewHolder(@NonNull View searchItemView) {
            super(searchItemView);
            mTextName = searchItemView.findViewById(R.id.text_item);
            mTextHtmlUrl = searchItemView.findViewById(R.id.text_html_url_item);
            mTextId = searchItemView.findViewById(R.id.text_id_item);
            mCircleImageProfile = searchItemView.findViewById(R.id.circle_image_profile_item);
            mLinearButtonDetailItem = searchItemView.findViewById(R.id.linear_button_detail_item);

            mLinearButtonDetailItem.setOnClickListener(view -> {
                int mPosition = getAdapterPosition();

                if (mPosition != RecyclerView.NO_POSITION) {
                    Item mItem = mItemList.get(mPosition);
                    Intent mIntent = new Intent(mHomeActivity, DetailActivity.class);
                    mIntent.putExtra(mHomeActivity.getString(R.string.key_intent), mItem.getLogin());
                    Objects.requireNonNull(mHomeActivity).startActivity(mIntent);
                }
            });
        }
    }
}
