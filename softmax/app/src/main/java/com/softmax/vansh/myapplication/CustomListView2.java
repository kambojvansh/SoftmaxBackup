package com.softmax.vansh.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by jaiso on 13-02-2018.
 */

public class CustomListView2 extends ArrayAdapter<String>{

    private String[] profilename;
    private String[] email;
    //private String[] imagepath;
    private Typeface tf;
    private Activity context;
    Bitmap bitmap;

    public CustomListView2(Activity context, String[] profilename, String[] email) {
        super(context, R.layout.layout_ebooklist,profilename);
        this.context=context;
        this.profilename=profilename;
        this.email=email;
        //this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/kruti.ttf");
        //this.imagepath=imagepath;
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout_ebooklist,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)r.getTag();

        }
        //viewHolder.tvw1.setTypeface(tf);
        //viewHolder.tvw2.setTypeface(tf);

        viewHolder.tvw1.setText(profilename[position]);
        viewHolder.tvw2.setText(email[position]);
        //new GetImageFromURL(viewHolder.ivw).execute(imagepath[position]);

        return r;
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
       // ImageView ivw;

        ViewHolder(View v){
            tvw1=(TextView)v.findViewById(R.id.subject);
            tvw2=(TextView)v.findViewById(R.id.lession);
            //ivw=(ImageView)v.findViewById(R.id.imageView);
        }

    }

   /* public class GetImageFromURL extends AsyncTask<String,Void,Bitmap>
    {

        ImageView imgView;
        public GetImageFromURL(ImageView imgv)
        {
            this.imgView=imgv;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay=url[0];
            bitmap=null;

            try{

                InputStream ist=new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(ist);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){

            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }
    }*/



}
