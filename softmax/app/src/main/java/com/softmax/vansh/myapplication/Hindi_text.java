package com.softmax.vansh.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.TextView;

//public class Hindi_text extends android.support.v7.widget.AppCompatTextView{

   /* public Hindi_text(Context context) {
        super(context);
        initTypeface(context);
    }

    public Hindi_text(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeface(context);
    }

    public Hindi_text(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeface(context);
    }
    private void initTypeface(Context context){
        Typeface typeface= Typeface.createFromAsset(context.getAssets(),"arial.ttf");
        this.setTypeface(typeface);
    }*/

    public class Hindi_text extends android.support.v7.widget.AppCompatTextView {

        /*
         * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
         */
        private static Typeface mTypeface;

        public Hindi_text(final Context context) {
            this(context, null);
        }

        public Hindi_text(final Context context, final AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public Hindi_text(final Context context, final AttributeSet attrs, final int defStyle) {
            super(context, attrs, defStyle);

            if (mTypeface == null) {
                mTypeface = Typeface.createFromAsset(context.getAssets(), "HelveticaiDesignVnLt.ttf");
            }
            setTypeface(mTypeface);
        }

    }
//}
  /*  private void initTypeface(Context context){
        Typeface typeface= Typeface.createFromAsset(context.getAssets(),"Anand Lipi Bold Cn Bt.ttf");
        this.setTypeface(typeface);
    }*/
