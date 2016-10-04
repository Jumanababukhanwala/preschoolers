package com.example.dell.kindergarten;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by admin on 3/22/2016.
 */
public class PreButtonView extends Button {
    public PreButtonView(Context context) {
        super(context);
    }

    public PreButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()){
            init(attrs);
        }
    }

    public PreButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()){
            init(attrs);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PreButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(!isInEditMode()){
            init(attrs);
        }
    }

    private void init(AttributeSet attrs){
        if(attrs!=null){
            TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.PreTextView);
            String fontName = a.getString(R.styleable.PreTextView_fontName);
            if(fontName!=null){
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/"+fontName);
                setTypeface(typeface);
            }
            a.recycle();
        }
    }
}
