package com.yogadimas.favoriteuser.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yogadimas.favoriteuser.R;
import com.yogadimas.favoriteuser.model.Item;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Cursor mCursorFavorites;
    private Context mContextFavorites;

    public FavoriteAdapter(Context applicationContext) {
        this.mContextFavorites = applicationContext;

    }

    public void setCursor(Cursor CursorFavorites) {
        this.mCursorFavorites = CursorFavorites;
    }

    private Item getItems(int i) {
        if (!mCursorFavorites.moveToPosition(i)) {
            throw new IllegalStateException("Invalid Position");
        }
        return new Item(mCursorFavorites);
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder
            (@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContextFavorites).inflate(R.layout.item_github_user,
                viewGroup, false);
        return new FavoriteAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Item mItem = getItems(i);
        holder.bindView(mItem);
    }

    @Override
    public int getItemCount() {
        if (mCursorFavorites == null) return 0;
        return mCursorFavorites.getCount();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextName, mTextHtmlUrl, mTextId;
        CircleImageView mCircleImageProfile;

        ViewHolder(@NonNull View favoriteItemView) {
            super(favoriteItemView);
            mTextName = favoriteItemView.findViewById(R.id.text_item);
            mTextHtmlUrl = favoriteItemView.findViewById(R.id.text_html_url_item);
            mTextId = favoriteItemView.findViewById(R.id.text_id_item);
            mCircleImageProfile = favoriteItemView.findViewById(R.id.circle_image_profile_item);
        }

        public void bindView(Item userFavorites) {
            mTextName.setText(userFavorites.getLogin());
            mTextId.setText(String.valueOf(userFavorites.getId()));
            mTextHtmlUrl.setText(userFavorites.getHtmlUrl());
            Glide.with(itemView.getContext())
                    .load(userFavorites.getAvatarUrl())
                    .apply(new RequestOptions().override(130, 130))
                    .into(mCircleImageProfile);
        }

    }
}
