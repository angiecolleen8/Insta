package com.codepath.acfoley.insta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.acfoley.insta.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComposeActivity extends AppCompatActivity {

    private ImageView iv_new_pic;
    private TextView tv_compose;
    private EditText et_description;
    private Button btn_post;
    private Button btn_new_pic;
    private String mCurrentPhotoPath;

    //TODO - START HERE - create parse file and other stuff from video, and make var private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        iv_new_pic = (ImageView) findViewById(R.id.iv_new_pic);
        tv_compose = (TextView) findViewById(R.id.tv_compose);
        et_description = (EditText) findViewById(R.id.et_description);
        btn_post = (Button) findViewById(R.id.btn_post);
        btn_new_pic = (Button) findViewById(R.id.btn_new_pic);

        btn_new_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take you to camera
                dispatchTakePictureIntent();
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button composes a post
                String description = et_description.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(mCurrentPhotoPath);
                final ParseFile parseFile = new ParseFile(file);
                createPost(description, parseFile, user);

                //take you back to timeline with new post at top
                Intent data = new Intent(ComposeActivity.this, HomeActivity.class);
                setResult(Constants.REQUEST_CODE_TIMELINE, data);  //had dispatchIntentforHome(Constants.REQUEST_CODE_COMPOSE, RESULT_OK, data)
                startActivityForResult(data, Constants.REQUEST_CODE_TIMELINE);
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
                Log.e("ComposeActivity", "error related to dispatchTakePictureIntent");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.codepath.acfoley.insta",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.REQUEST_CODE_CAMERA);
            }
        } //display image on compose activity
    }

    /**
     * after taking picture with camera, should take user to ComposeActivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_CAMERA && resultCode == RESULT_OK) { //WAS REQUEST CODE COMPOSE
            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath); //this line
            iv_new_pic.setImageBitmap(imageBitmap); //and this line let me see image on home screen
        }
    }

    /**
     * makes new post
     */
    private void createPost(String description, ParseFile image, ParseUser user) {
        final Post newPost = new Post();
        //user.signUp(); //Debugger was giving me "must call signUp on ParseUser
        newPost.setDescription(description);
        newPost.setUser(user);
        newPost.setImage(image);
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                    if (e == null) {
                    Log.d("ComposeActivity", "Post created successfully!");
                    Toast.makeText(getApplicationContext(), "Post created successfully!", Toast.LENGTH_LONG).show();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * creates unique filepath for photo
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}


