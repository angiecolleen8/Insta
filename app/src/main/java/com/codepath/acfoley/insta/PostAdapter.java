package com.codepath.acfoley.insta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.acfoley.insta.model.GlideApp;
import com.codepath.acfoley.insta.model.Post;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    //pass in Tweets list in constructor
    private List<Post> mPosts;
    Context context;

    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder {     //Should I add "implements View.OnClickListener"? //No static?

        //declare the views that are inside our item_tweet
        public ImageView iv_image;
        public TextView tv_username;
        public TextView tv_description;
        public TextView tv_timestamp;


        //do item_post view by id lookups
        public ViewHolder(View itemView) {
            super(itemView);

            //do the findbyid lookups
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image); //Glide null pointer starting here
            tv_username = (TextView) itemView.findViewById(R.id.tv_username_detail);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_timestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //gets item position
                    int position = getAdapterPosition();
                    // make sure the position is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        // get the post at the position
                        Post post = mPosts.get(position);
                        // create intent for new activity
                        Intent intent = new Intent(context, DetailsActivity.class); //TODO - take a look at this intent
                        intent.putExtra("user", Parcels.wrap(post));
                        // show the activity
                        context.startActivity(intent);
                    }
                }

            });
        }
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

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //get data according to position so that we know which post to populate
        Post post = mPosts.get(position);
        Post.Query postQuery = new Post.Query();

        //populate the views according to this data
        try {
            viewHolder.tv_username.setText(post.getUser().fetchIfNeeded().getUsername());
            viewHolder.tv_description.setText(post.getDescription());
            viewHolder.tv_timestamp.setText(post.getCreatedAt().toString());//TODO - come back to this when you add timestamp

            //load images using Glide
            GlideApp.with(context)
                    .load(post.getImage().getUrl())
                    .transform(new RoundedCornersTransformation(30, 10)).into(viewHolder.iv_image);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    /* helper methods for pull down to refresh */
    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> posts) {
        mPosts.addAll(posts);
        notifyDataSetChanged();
    }
}