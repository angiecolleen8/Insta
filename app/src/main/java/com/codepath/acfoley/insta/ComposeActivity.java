package com.codepath.acfoley.insta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ComposeActivity extends AppCompatActivity {

    ImageView iv_new_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        iv_new_pic = (ImageView) findViewById(R.id.iv_new_pic);


        //TODO - "rewire" to take user back to compose after getting picture
        /**after taking picutre with camera, should take user to ComposeActivity*/
        /*@Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == Constants.REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                iv_new_pic.setImageBitmap(imageBitmap);
            }
        }*/
    }
}
