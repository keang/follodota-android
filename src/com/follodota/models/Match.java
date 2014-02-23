package com.follodota.models;

import org.json.JSONObject;

public class Match extends SerializableJSONBasedObject{
	private static final long serialVersionUID = 6376691382150876370L;
	public Match(JSONObject j){
		mObject = j;
	}
    
	public String getHomeTeam(){
		return getString("home_team", "name");
	}
	
	public String getAwayTeam(){
		return getString("away_team", "name");
	}

	@Override
	String getTag() {
		return "follodota.match";
	}
	

}
