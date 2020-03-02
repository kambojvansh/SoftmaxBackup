package com.softmax.vansh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.softmax.vansh.myapplication.AnimeActivity;
import com.softmax.vansh.myapplication.Anime;
import com.softmax.vansh.myapplication.R ;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.softmax.vansh.myapplication.ChatFragment.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

/**
 * Created by Aws on 11/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Anime> mData ;
    RequestOptions option;


    public RecyclerViewAdapter(Context mContext, List<Anime> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.video).error(R.drawable.video);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.anime_row_item,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mData.get(viewHolder.getAdapterPosition()).getImage_url().toString().contains(".mp4") || mData.get(viewHolder.getAdapterPosition()).getImage_url().toString().contains(".mp3")) {
                    // Word document
                    Intent i = new Intent(mContext, Video_url.class);
                    i.putExtra("anime_name",mData.get(viewHolder.getAdapterPosition()).getName());
                    i.putExtra("anime_description",mData.get(viewHolder.getAdapterPosition()).getDescription());
                    i.putExtra("anime_video",mData.get(viewHolder.getAdapterPosition()).getImage_url());
                    mContext.startActivity(i);
                }
                else{
                    Intent i = new Intent(mContext, AnimeActivity.class);
                    i.putExtra("anime_name",mData.get(viewHolder.getAdapterPosition()).getName());
                    i.putExtra("anime_description",mData.get(viewHolder.getAdapterPosition()).getDescription());
                    // i.putExtra("anime_studio",mData.get(viewHolder.getAdapterPosition()).getStudio());
                    //i.putExtra("anime_category",mData.get(viewHolder.getAdapterPosition()).getCategorie());
                    //i.putExtra("anime_nb_episode",mData.get(viewHolder.getAdapterPosition()).getNb_episode());
                    //i.putExtra("anime_rating",mData.get(viewHolder.getAdapterPosition()).getRating());
                    i.putExtra("anime_img",mData.get(viewHolder.getAdapterPosition()).getImage_url());

                    mContext.startActivity(i);

                }





            }
        });




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getName());
        //holder.tv_rating.setText(mData.get(position).getRating());
       // holder.tv_studio.setText(mData.get(position).getStudio());
        //holder.tv_category.setText(mData.get(position).getCategorie());

        // Load Image from the internet and set it into Imageview using Glide





        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(option).into(holder.img_thumbnail);



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name ;
        TextView tv_rating ;
        TextView tv_studio ;
        TextView tv_category;
        ImageView img_thumbnail;
        LinearLayout view_container;





        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            tv_name = itemView.findViewById(R.id.anime_name);
            //tv_category = itemView.findViewById(R.id.categorie);
            //tv_rating = itemView.findViewById(R.id.rating);
            //tv_studio = itemView.findViewById(R.id.studio);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }

}
