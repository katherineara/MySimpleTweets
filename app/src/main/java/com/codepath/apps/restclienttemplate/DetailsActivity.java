package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    Tweet tweet;
    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvUsername = (TextView) findViewById(R.id.tvUserName);


        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvBody.setText(tweet.body);
        tvUsername.setText(tweet.user.name);

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .into(ivProfileImage);

    }
}
