package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ReplyActivity extends AppCompatActivity {

    TwitterClient client;
    EditText message;
    JsonHttpResponseHandler handler;
    TextView counter;
    Integer type;
    Tweet tweet;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        client = new TwitterClient(this);

        Button button = (Button) findViewById(R.id.tweet_button);
        message = (EditText) findViewById(R.id.tweet_message);
        counter = (TextView) findViewById(R.id.counter);

        username = "@" + Parcels.unwrap(getIntent().getParcelableExtra("username"));
        message.setText(username);
        message.setSelection(username.length());

        handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                tweet = null;
                try {
                    tweet = Tweet.fromJSON(response);
                    Intent intent = new Intent(ReplyActivity.this, TimelineActivity.class);
                    intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                client.replyTweet(" " + message.getText().toString(), handler);
            }
        });

        final TextWatcher textEditor = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counter.setText(String.valueOf(140 - s.length()) + " characters left");
            }

            public void afterTextChanged(Editable s) {
            }
        };

        message.addTextChangedListener(textEditor);
    }


}
