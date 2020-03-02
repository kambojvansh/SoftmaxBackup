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

public class CustomEbook extends ArrayAdapter {
    private String[] profilename;
    private String[] email;
    private String[] imagepath;
    private Activity context;
    Bitmap bitmap;
    private Typeface tf;


    public CustomEbook(Activity context,String[] profilename,String[] email) {
        super(context, R.layout.layout,profilename);
        //super(context, R.layout.layout);
        this.context=context;
        this.profilename=profilename;
        this.email=email;
        this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
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
            viewHolder= new ViewHolder(r);
            r.setTag(viewHolder);
            //Typeface fontHindi = Typeface.createFromAsset(getView(),
            //   "fonts/Ananda Lipi Bold Cn Bt.ttf");
            //massege.setTypeface(fontHindi);

        }
        else {
            viewHolder=(ViewHolder)r.getTag();

        }
        viewHolder.tvw1.setTypeface(tf);
        viewHolder.tvw2.setTypeface(tf);

        viewHolder.tvw1.setText(profilename[position]);
        viewHolder.tvw2.setText(email[position]);
        //new GetImageFromURL(viewHolder.ivw).execute(imagepath[position]);


        return r;
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;

        //ImageView ivw;

        ViewHolder(View v){
            tvw1=(TextView)v.findViewById(R.id.tvprofilename);
            tvw2=(TextView)v.findViewById(R.id.tvemail);
            //ivw=(ImageView)v.findViewById(R.id.imageView);


        }



    }

}
