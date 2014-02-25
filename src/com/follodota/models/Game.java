package com.follodota.models;

import org.json.JSONObject;

import android.util.Log;

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

	/**
	 * 
	 * @return the Youtube id of the link
	 */
	public String getYoutubeLink(){
		String url = getString("youtube_link");
		String[] params = url.split("[&,?]");
		for (String param : params){
			try {
				String name = param.split("=")[0];
				String value = param.split("=")[1];
				if(name.equalsIgnoreCase("v"))
					return value;
			} catch(Exception e){
				Log.e(getTag(), e.getMessage());
			}
		}
		return null;
	}
	@Override
	String getTag() {
		return "follodota.game";
	} 
	
	@Override
	public String toString(){
		return "Game " + getString("game_number");
	}
}
