package com.follodota.models;

import org.json.JSONObject;

public class Game extends SerializableJSONBasedObject{
	public Game(JSONObject o) {
		super(o);
	}

	private static final long serialVersionUID = 1127355230567509529L;

	/**
	 * 
	 * @return steam's match id to be used in the api for match details
	 */
	public String getSteamGameID(){
		//steam calls it match_id, we call it gameID
		//at follodota one match have many games
		return getString("match_id");
	}
	
	public String getYoutubeLink(){
		return getString("youtube_link");
	}
	
	@Override
	String getTag() {
		return "follodota.game";
	} 

}
