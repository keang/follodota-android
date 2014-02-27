package com.follodota.models;

import org.json.JSONObject;

import com.follodota.R;

import android.content.Context;

public class Team extends SerializableJSONBasedObject{
	private static final long serialVersionUID = 2705498849640071860L;
	public static final int default_team_logo = R.drawable.logo9_15;

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

	public String getName() {
		return getString("name");
	}

	public String getId() {
		return getString("id");
	}
}


