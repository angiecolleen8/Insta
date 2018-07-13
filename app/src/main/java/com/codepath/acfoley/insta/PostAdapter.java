package com.codepath.acfoley.insta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.acfoley.insta.model.Post;
import com.parse.ParseException;

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
       // public TextView tv_timestamp;


        //do item_post view by id lookups
        public ViewHolder(View itemView) {
            super(itemView); //Why do we need to call the super-constructor?

            //do the findbyid lookups
            iv_image = (ImageView) itemView.findViewById(R.id.iv_new_pic); //not sure if finding picture like this will work
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            //tv_timestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);
        }


        //TODO - revisit when making details page
        //@Override
       /* public void onClick(View view) {
            //gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the post at the position
                Post post = mPosts.get(position);
                // create intent for new activity
                Intent intent = new Intent(context, ComposeActivity.class); //TODO - take a look at this intent
                intent.putExtra(Post.class.getSimpleName(), post);
                // show the activity
                context.startActivity(intent);
            }
        }*/
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //get data according to position so that we know which post to populate
        Post post = mPosts.get(position);

        //populate the views according to this data
        try {
            viewHolder.tv_username.setText(post.getUser().fetchIfNeeded().getUsername());
            viewHolder.tv_description.setText(post.getDescription());
            //viewHolder.tv_timestamp.setText(post.getRelativeTimeAgo(tweet.createdAt));//TODO - come back to this when you add timestamp

//            load images using Glide
//            Glide.with(context)
//                    .load(post.getImage().getUrl())
//                    .into(viewHolder.iv_image);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        /*
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        //round corners on profile images
        GlideApp.with(context) //changed from GlideApp to Glide
                .load(post.getImage())
                .transform(new RoundedCornersTransformation(radius, margin))//imageURL is profileImageURL
                .into(viewHolder.iv_image);*/
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

}

