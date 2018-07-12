package com.codepath.acfoley.insta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.acfoley.insta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.codepath.acfoley.insta.Constants.REQUEST_CODE_CAMERA;

public class HomeActivity extends AppCompatActivity {

    /*Flow:
     * Login/signup
     * Timeline Activity
     * hit create button--> camera
     * Take Picture in camera--> Compose Activity
     * Hit Post button in Compose Activity-->
     * Back to Timeline*/

    //ask user to take photo using camera
    //launch camera
    //intent to go from this activity to camera
    //Override startActivityForResult (needs intent and request code to go to/from camera)
    //Override onActivityResult using requestcode for camera? or requestcode for compose activity
    //onActivityResult should take us to Compose Actvity
    //Post button in Compose Activity takes us back to


    private static final String imagepath = "somestring"; //TODO- you will end up changing this
    private TextView tv_timeline;
    private Button btn_create;
    private Button btn_refresh;
    private ImageView iv_newTempPic;
    String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv_timeline = (TextView) findViewById(R.id.tv_timeline);
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        //TODO - once camera works, remove this image view and move this picture to the imageView in the OnCompose activity
        iv_newTempPic = (ImageView) findViewById(R.id.iv_newTempPic);

        btn_create.setOnClickListener(new View.OnClickListener() {

            /**What happens when you click the 'create new post' button*/
            @Override
            public void onClick(View view) {
                //first - we force user to take a picture
                dispatchTakePictureIntent();
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });
    }

    /**
     * take user to camera - invoke intent
     */

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("HomeActivity", "error related to dispatchTakePictureIntent");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.codepath.acfoley.insta",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.REQUEST_CODE_CAMERA);
            }
        } //this if block worked to display image on compose activity
    }

    /**
     * takes user back to home activity and makes post
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {        //Changed request code from Camera to Compose
        //launch ComposeActivity - which is where the new picture will be displayed
            Intent i = new Intent(this, ComposeActivity.class); //"I intend to go from this activity (timeline activity) to ComposeActivity
            setResult(Constants.REQUEST_CODE_COMPOSE, i);
            startActivityForResult(i, Constants.REQUEST_CODE_COMPOSE);


            // Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath); //this line
//            iv_newTempPic.setImageBitmap(imageBitmap);                           //and this line allowed me to see camera on home activity before I rewired


//            final String descriptionInput = et_description.getText().toString();
//                final ParseUser user = ParseUser.getCurrentUser();
//
//
//                //final File file = new File(imagepath); //I think I will end up changing this
//                final File file;
//                try {
//                    file = createImageFile();
//                    //grab photo
//                    final ParseFile parseFile = new ParseFile(file);
//                    createPost(descriptionInput, parseFile, user); //defined just above
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//            ask user to take photo using camera
//            launch camera
//            intent to go from this activity to camera
//            Override startActivityForResult (needs intent and request code to go to/from camera)
//            Override onActivityResult using requestcode for camera? or requestcode for compose activity

            //intent.putExtras //equivalent method for parse?
        }
    }

    //TODO - use this later, after you know camera works

    /**
     * what to do after Camera gets the picture
     */
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //launch ComposeActivity - which is where the new picture will be displayed
        Intent i = new Intent(this, ComposeActivity.class); //"I intend to go from this activity (timeline activity) to ComposeActivity
        startActivityForResult(i, Constants.REQUEST_CODE_COMPOSE);

        //I put this in Compose Activity
        /*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        } */
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

   /* private void createPost(String description, ParseFile image, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setUser(user);
        newPost.setImage(image);
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Post created successfully!");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }*/ //this worked for showing pic in home timeline


    //had createImageFile here when showing image on home worked

}


//don't think that I need this block now that I have postsQuery
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
