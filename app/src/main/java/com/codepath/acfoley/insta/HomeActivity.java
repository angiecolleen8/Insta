package com.codepath.acfoley.insta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codepath.acfoley.insta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //TextView tvHomeActivity = (TextView) findViewById(R.id.tvHomeActivity);
        final Post.Query postsQuery = new Post.Query(); //"enclosing class" - what does that mean? I "fixed it" by making Query a static class. how does this work?
        postsQuery.getTop().withUser();


        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i).getDescription() + "\nusername = " + objects.get(i).getUser().getUsername()); //just to show
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }



    //do I still need this block bnow that I have PostsQuery? What is the difference?
        /*ParseQuery.getQuery(Post.class).findInBackground(new FindCallback<Post>() {

            @Override
            public void done(List<Post> objects, ParseException e) { //When is this called? When parse object is coming back from the ParseQuery?
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i).getDescription()); //just to show
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }*/
}