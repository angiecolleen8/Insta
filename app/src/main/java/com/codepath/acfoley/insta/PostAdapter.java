package com.codepath.acfoley.insta;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.codepath.acfoley.insta.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    //pass in Tweets list in constructor
    private List<Post> mPosts;
    Context context;

    public PostAdapter(List<Post> posts) { mPosts = posts; }

    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder {     //Should I add "implements View.OnClickListener"? //No static?

        //declare the views that are inside our item_tweet
        public ImageView iv_image;
        public TextView tv_username;
        public TextView tv_description;
        public TextView tv_timestamp;


        //do item_post view by id lookups
        public ViewHolder(View itemView) {
            super(itemView); //Why do we need to call the super-constructor?

            //do the findbyid lookups
            iv_image = (ImageView) itemView.findViewById(R.id.iv_new_pic); //not sure if finding picture like this will work
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_timestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);
        }

        //for each row, inflate the layout and cache the references into ViewHolder
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

            context = parent.getContext(); //Context is a request to the OS to do something
            LayoutInflater inflater = LayoutInflater.from(context);

            View postView = inflater.inflate(R.layout.item_post, parent, false); //look into this method
            ViewHolder viewHolder = new ViewHolder(postView);
            return viewHolder;
        }


        //TODO - revisit
        //@Override
        public void onClick(View view) {
            //gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the post at the position
                Post post = mPosts.get(position);
                // create intent for new activity
                Intent intent = new Intent(context, ComposeActivity.class);
                // serialize tweet using parceller, short name is key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                // show the activity
                context.startActivity(intent);
            }
        }
    }



    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



}

