package com.yogadimas.githubuser.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.helper.MappingHelper;
import com.yogadimas.githubuser.model.Item;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.yogadimas.githubuser.database.DatabaseContract.ThisGithubColumns.GITHUB_URI;


public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GithubUserWidgetItemFactory(getApplicationContext(), intent);
    }

    static class GithubUserWidgetItemFactory implements RemoteViewsFactory {
        private List<Bitmap> mWidgetItems = new ArrayList<>();
        private Context mContext;
        private Cursor mCursor;
        private int mAppWidgetId;
        private ArrayList<Item> mListFavorite = new ArrayList<>();

        GithubUserWidgetItemFactory(Context mContext, Intent intent) {
            this.mContext = mContext;
            this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {

            if (mCursor != null) {
                mCursor.close();
            }
            final long mIdentityToken = Binder.clearCallingIdentity();
            mCursor = mContext.getContentResolver().query(GITHUB_URI, null, null,
                    null, null);
            assert mCursor != null;
            ArrayList<Item> mList = MappingHelper.mapCursorToArrayList(mCursor);
            mListFavorite.addAll(mList);

            URL mProfileUrl = null;
            for (int i = 0; i < mListFavorite.size(); i++) {
                try {
                    mProfileUrl = new URL(mContext.getString(R.string.url_avatar_profile) +
                            mListFavorite.get(i).getId());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    assert mProfileUrl != null;
                    mWidgetItems.add(BitmapFactory.decodeStream(mProfileUrl.openConnection()
                            .getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Binder.restoreCallingIdentity(mIdentityToken);
        }


        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            return mWidgetItems.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews mViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            mViews.setImageViewBitmap(R.id.image_widget_item, mWidgetItems.get(position));

            Intent mFillIntent = new Intent();
            mFillIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            mViews.setOnClickFillInIntent(R.id.image_widget_item, mFillIntent);

            return mViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
