package com.follodota.models;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Team extends SerializableJSONBasedObject{
	private static final long serialVersionUID = 2705498849640071860L;

	public Team(JSONObject o) {
		super(o);
	}

	@Override
	String getTag() {
		return "follodota.team";
	}
	
	/**
	 * returns the resource id of the team's logo
	 * @param ctx: context from which the resource is accessed
	 * @return
	 */
	public int getLogoResourceId(Context ctx){
		int id = ctx.getResources().getIdentifier("logo"+getString("logo_x")+"_"+getString("logo_y"), "drawable",
		        ctx.getPackageName());
		return id;
	}
}


