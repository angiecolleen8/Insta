package com.codepath.acfoley.insta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.acfoley.insta.model.GlideApp;
import com.codepath.acfoley.insta.model.Post;
import com.parse.ParseUser;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity {

    Post post;
    TextView tv_username_detail;
    TextView tv_description_detail;
    TextView tv_timestamp_detail;
    ImageView iv_image_detail;
    ParseUser parseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        parseUser = ParseUser.getCurrentUser();
        post = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        tv_username_detail = (TextView) findViewById(R.id.tv_username);
        tv_description_detail = (TextView) findViewById(R.id.tv_description_detail);
        tv_timestamp_detail = (TextView) findViewById(R.id.tv_timestamp_detail);
        iv_image_detail = (ImageView) findViewById(R.id.iv_image_detail);

        tv_username_detail.setText(parseUser.getUsername());
        tv_description_detail.setText(post.getDescription());
        tv_description_detail.setText(post.getDescription().toString());
        tv_timestamp_detail.setText(post.getCreatedAt().toString());

        GlideApp.with(this).load(post.getImage().getUrl()).transform(new RoundedCornersTransformation(30, 10)).into(iv_image_detail);
    }
}
