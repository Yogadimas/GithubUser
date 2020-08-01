package com.yogadimas.githubuser.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.view.activity.HomeActivity;


public class WidgetProvider extends AppWidgetProvider {

    public static final String ACTION_REFRESH = "action_refresh";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent mIntentHome = new Intent(context, HomeActivity.class);
            PendingIntent mHomePendingIntent = PendingIntent.getActivity(context,
                    0, mIntentHome, 0);

            Intent mIntentService = new Intent(context, WidgetService.class);
            mIntentService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            mIntentService.setData(Uri.parse(mIntentService.toUri(Intent.URI_INTENT_SCHEME)));

            Intent mIntentUpdate = new Intent(context, WidgetProvider.class);
            mIntentUpdate.setAction(ACTION_REFRESH);
            PendingIntent mUpdatePendingIntent = PendingIntent.getBroadcast(context,
                    0, mIntentUpdate, 0);

            RemoteViews mViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);

            mViews.setRemoteAdapter(R.id.stack_items, mIntentService);
            mViews.setEmptyView(R.id.stack_items, R.id.text_no_data);
            mViews.setPendingIntentTemplate(R.id.stack_items, mUpdatePendingIntent);
            mViews.setOnClickPendingIntent(R.id.button_widget, mHomePendingIntent);

            Bundle mAppWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            resizeWidget(mAppWidgetOptions, mViews);

            appWidgetManager.updateAppWidget(appWidgetId, mViews);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        RemoteViews mViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
        resizeWidget(newOptions, mViews);
        appWidgetManager.updateAppWidget(appWidgetId, mViews);
    }

    private void resizeWidget(Bundle appWidgetOptions, RemoteViews views) {
        int mMaxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);

        if (mMaxWidth > 200) {
            views.setViewVisibility(R.id.text_to_app, View.VISIBLE);
            views.setViewVisibility(R.id.image_github_widget, View.GONE);
        } else {
            views.setViewVisibility(R.id.text_to_app, View.GONE);
            views.setViewVisibility(R.id.image_github_widget, View.VISIBLE);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.makeText(context, R.string.widget_provider_delete, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, R.string.widget_provider_enabled, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, R.string.widget_provider_disabled, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_REFRESH.equals(intent.getAction())) {
            int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(context);
            mAppWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetId, R.id.stack_items);
        }
        super.onReceive(context, intent);
    }
}
