package com.follodota.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Match extends SerializableJSONBasedObject{
	public Match(JSONObject o) {
		super(o);
	}

	private static final long serialVersionUID = 6376691382150876370L;
    
	public Team getHomeTeam(){
		if(getString("home_team")==null)return null;
		return new Team(getJSONObject("home_team"));
	}
	
	public Team getAwayTeam(){
		if(getString("away_team")==null)return null;
		return new Team(getJSONObject("away_team"));
	}
	
	public int getHomeTeamLogo(Context ctx){
		return getHomeTeam()!=null?getHomeTeam().getLogoResourceId(ctx):Team.default_team_logo;
	}
	
	public int getAwayTeamLogo(Context ctx){
		return getAwayTeam()!=null?getAwayTeam().getLogoResourceId(ctx):Team.default_team_logo;
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
		return getString("round");
	}

	public String getPlayedDate() {
		//2013-08-11T00:00:00.000Z
	    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
	        Locale.getDefault());
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy",
	    		Locale.getDefault());
	    try {
	      return dateFormat.format(parser.parse(getString("last_game_at")));
	    } catch (ParseException e) {
	      Log.v(getTag(), "Can't parse date format", e);
	    }
		return null;
	}

	/**
	 * 
	 * @return caster for the first game, assume to be the same for the rest of the match
	 */
	public String getCaster() {
		return getAllGames().get(0).getString("caster");
	}

}
