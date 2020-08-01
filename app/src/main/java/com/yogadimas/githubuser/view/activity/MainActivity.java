package com.yogadimas.githubuser.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yogadimas.githubuser.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView mImageLogoApp = findViewById(R.id.image_app_logo);
        TextView mTextTitleApp = findViewById(R.id.text_title_app);

        Animation mAnimLogoApp = AnimationUtils.loadAnimation(this, R.anim.anim_app_logo);
        Animation mAnimTitleApp = AnimationUtils.loadAnimation(this, R.anim.anim_bot_to_top);

        mImageLogoApp.startAnimation(mAnimLogoApp);
        mTextTitleApp.startAnimation(mAnimTitleApp);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent mIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(mIntent);
            finish();
        }, 2000);
    }
}
