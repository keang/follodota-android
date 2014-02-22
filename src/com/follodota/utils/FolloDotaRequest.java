package com.follodota.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

public class FolloDotaRequest {
	private static FolloDotaRequest instance;
	private RequestQueue mQueue;
	private ImageLoader mLoader;
	
	public FolloDotaRequest(Context c) {
		mQueue = Volley.newRequestQueue(c);
		mLoader = new ImageLoader(mQueue, new ImageCache(){

			@Override
			public Bitmap getBitmap(String url) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public static FolloDotaRequest getInstance(Context c){
		if(instance==null) instance = new FolloDotaRequest(c);
		return instance;
	}
	
	public void addRequest(Request<?> r){
		mQueue.add(r);
	}
	public void cancelAll(){
		mQueue.cancelAll(null);
	}
}
