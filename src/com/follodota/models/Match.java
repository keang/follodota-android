package com.follodota.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Match extends SerializableJSONBasedObject{
	public Match(JSONObject o) {
		super(o);
	}

	private static final long serialVersionUID = 6376691382150876370L;
    
	public Team getHomeTeam(){
		return new Team(getJSONObject("home_team"));
	}
	
	public Team getAwayTeam(){
		return new Team(getJSONObject("away_team"));
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
	
    @Override
    public String toString(){
    	return getHomeTeam().getName() + " vs " + getAwayTeam().getName();
    }

	public String getLeagueName() {
		return (new League(getJSONObject("league")).getName());
	}

	public String getRound() {
		return null;
	}

	public String getPlayedDate() {
		//2013-08-11T00:00:00.000Z
	    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
	        Locale.getDefault());
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy",
	    		Locale.getDefault());
	    try {
	      return dateFormat.format(parser.parse(getAllGames().get(0).getString("played_at")));
	    } catch (ParseException e) {
	      Log.v(getTag(), "Can't parse date format", e);
	    }
		return null;
	}

	public String getCaster() {
		return getAllGames().get(0).getString("caster");
	}

}
