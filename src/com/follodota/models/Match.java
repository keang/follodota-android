package com.follodota.models;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Match extends SerializableJSONBasedObject{
	public Match(JSONObject o) {
		super(o);
	}

	private static final long serialVersionUID = 6376691382150876370L;
    
	public String getHomeTeam(){
		return getString("home_team", "name");
	}
	
	public String getAwayTeam(){
		return getString("away_team", "name");
	}
	
	public ArrayList<Game> getAllGames(){
		ArrayList<Game> gameList = new ArrayList<Game>();
		JSONArray jArray = getJSONArray("games");
		for(int i=0; i<jArray.length(); i++){
			try {
				gameList.add(new Game(jArray.getJSONObject(i)));
			} catch (JSONException e) {
				Log.e(getTag(), e.getMessage());
			}
		}
		return gameList;
	}
	@Override
	String getTag() {
		return "follodota.match";
	}
	

}
