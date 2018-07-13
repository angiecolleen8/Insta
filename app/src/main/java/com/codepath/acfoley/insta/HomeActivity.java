package com.codepath.acfoley.insta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codepath.acfoley.insta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //START with refresh

    RecyclerView rv_timeline;
    private Button btn_create;
    private Button btn_refresh;
    ArrayList<Post> posts;
    PostAdapter postAdapter;
    SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ParseObject.registerSubclass(Post.class);

        rv_timeline = (RecyclerView) findViewById(R.id.rv_timeline);
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        //data source
        posts = new ArrayList<>();


        //construct adapter from data source
        postAdapter = new PostAdapter(posts);


        rv_timeline.setLayoutManager(new LinearLayoutManager(this));
        rv_timeline.setAdapter(postAdapter);

        loadTopPosts();
        btn_create.setOnClickListener(new View.OnClickListener() {

            /**What happens when you click the 'create new post' button*/
            @Override
            public void onClick(View view) {
                //first - we force user to take a picture
                dispatchToCompose();
            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postAdapter.clear();
                postAdapter.addAll(posts);
                loadTopPosts();
                swipeContainer.setRefreshing(false);
                rv_timeline.scrollToPosition(0);

                //refresh();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    /**
     * takes user back to home activity and makes post
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_COMPOSE && resultCode == RESULT_OK) {
            rv_timeline.scrollToPosition(0); //I think it should be scrolling to top, because this should happen after result from compose activity comes back...but it istn' scrolling
// posts.clear();
//            loadTopPosts();

            // Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
    }

    public void dispatchToCompose() {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, Constants.REQUEST_CODE_COMPOSE);
    }

    private void loadTopPosts() {
        // final ParseQuery<Post> parseQuery = ParseQuery.getQuery(Post.class);
        final Post.Query postsQuery = new Post.Query(); //"enclosing class" - what does that mean? I "fixed it" by making Query a static class. how does this work?
        postsQuery.getTop().withUser();
        Log.d("HomeActivity", "ENTERING LOAD TOP POST");
        System.out.println("Coming here");
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < objects.size(); i++) {
                        posts.add(0, objects.get(i));
                        postAdapter.notifyItemInserted(0);
                        Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i).getDescription() + "\nusername = " + objects.get(i).getUser().getUsername()); //just to show
                    }
                } else {
                    e.printStackTrace();
                    Log.d("HomeActivity", e.getMessage()); //just to show
                }
            }
        });

    }
}

