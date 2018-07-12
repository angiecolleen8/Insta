package com.codepath.acfoley.insta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codepath.acfoley.insta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import static com.codepath.acfoley.insta.Constants.REQUEST_CODE_CAMERA;

public class HomeActivity extends AppCompatActivity {

    //next steps:
    //create post item
    //"inflate" post item with Parse /  adapter
    //inflate recycler view with post items

    private RecyclerView rv_timeline;
    private Button btn_create;
    private Button btn_refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rv_timeline= (RecyclerView) findViewById(R.id.rv_timeline);
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);

        loadTopPosts();

        btn_create.setOnClickListener(new View.OnClickListener() {

            /**What happens when you click the 'create new post' button*/
            @Override
            public void onClick(View view) {
                //first - we force user to take a picture
                dispatchToCompose();
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });
    }

    /*** takes user back to home activity and makes post*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {        //Changed request code from Camera to Compose
        //launch ComposeActivity - which is where the new picture will be displayed
            Intent i = new Intent(this, ComposeActivity.class); //"I intend to go from this activity (timeline activity) to ComposeActivity
            setResult(Constants.REQUEST_CODE_COMPOSE, i);
            startActivityForResult(i, Constants.REQUEST_CODE_COMPOSE);


            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
    }

    public void dispatchToCompose() {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, Constants.REQUEST_CODE_COMPOSE);
    }

    private void loadTopPosts() {
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
}

