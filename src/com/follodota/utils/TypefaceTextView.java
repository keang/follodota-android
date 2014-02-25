package com.follodota.utils;

import com.follodota.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TypefaceTextView extends TextView {

	public TypefaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    	
        if (true) {
        	TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.TypefaceTextView);
        	String family = a.getString(R.styleable.TypefaceTextView_font);
        	Typeface tf = null;
        	if(family!=null){
		    	if(family.equals("thin"))
		    		tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto_Thin.ttf");
		    	else if (family.equals("thin_italic"))
		    		tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto_ThinItalic.ttf");
		    	else if (family.equals("condensed"))
		    		tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto_Condensed.ttf");
		    	else if (family.equals("light"))
		    		tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto_Light.ttf");
		    	else if (family.equals("light_italic"))
		    		tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto_LightItalic.ttf");
        	}
	    	else tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Prosto_One.ttf"); 
    		
	        setTypeface(tf);
	        
	        a.recycle();
        }
    }

}