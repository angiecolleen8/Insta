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

import com.codepath.acfoley.insta.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codepath.acfoley.insta.Constants.REQUEST_CODE_COMPOSE;

public class ComposeActivity extends AppCompatActivity {

    ImageView iv_new_pic;
    TextView tv_compose;
    EditText et_description;
    Button btn_post;
    Button btn_new_pic;
    String mCurrentPhotoPath;

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
                //button composes a post
                //createPost();

                //take you back to timeline with new post at top
                dispatchTakePictureIntent();
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button composes a post
                //createPost();

                //take you back to timeline with new post at top
               dispatchIntentForHome();
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
        } //this if block worked to display image on compose activity
    }

    /**after taking picture with camera, should take user to ComposeActivity*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_COMPOSE && resultCode == RESULT_OK) {
            //launch ComposeActivity - which is where the new picture will be displayed
            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath); //this line
            iv_new_pic.setImageBitmap(imageBitmap); //and this line let me see image on home screen

            Intent i = new Intent(this, HomeActivity.class); //"I intend to go from this activity (COMPOSE activity) to HOMEActivity
            setResult(Constants.REQUEST_CODE_TIMELINE, i);
            startActivityForResult(i, Constants.REQUEST_CODE_TIMELINE);
        }
    }

    //trying to go from Compose back to home
    public void dispatchIntentForHome() {
        Intent i = new Intent(this, HomeActivity.class);
        setResult(Constants.REQUEST_CODE_TIMELINE, i);
        startActivityForResult(i, Constants.REQUEST_CODE_TIMELINE);
    }

    private void createPost(String description, ParseFile image, ParseUser user) {
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
    }

    /**
     * creates unique filepath for photo
     */
    //TODO - move all picture logic to ComposeActivity
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


