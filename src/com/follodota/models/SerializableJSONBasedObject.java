package com.follodota.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * For other models(Match, Game, Team) to extend.
 * Serializable implemented so object can be passed inside intents
 * Also other low level methods implemented here.
 * @author kaka
 *
 */
public abstract class SerializableJSONBasedObject implements Serializable{
	private static final long serialVersionUID = 1L;

	abstract String getTag();
	protected JSONObject mObject;
	public SerializableJSONBasedObject(JSONObject o) {
		mObject = o;
	}
	/**
	 * Try to find "key" in mObject, logs the error if any.
	 * @param key
	 * @return String if exist, null if not
	 */
	protected String getString(String key){
		String string = null;
		try {
			string = mObject.getString(key);
		} catch (JSONException e) {
			Log.e(getTag(), e.getMessage());
		}
		return string;
	}
	

	/**
	 * returns id, which most models have
	 * @return
	 */
	public String getId() {
		return getString("id");
	}
	
	/**
	 * Try to find "nestedkey" in "key"mObject, logs the error if any.
	 * @param key eg. "home_team"
	 * @param nestedKey eg. "name"
	 * @return String if exist, null if not
	 */
	protected String getString(String key, String nestedKey){
		String string = null;
		try {
			string = mObject.getJSONObject(key).getString(nestedKey);
		} catch (JSONException e) {
			Log.e(getTag(), e.getMessage());
		}
		return string;
	}	
	
	/**
	 * 
	 * @param key
	 * @return the object with key "key", or null if none exist
	 */
	protected JSONObject getJSONObject(String key){
		JSONObject object=null;
		try {
			object = mObject.getJSONObject(key);
		} catch (JSONException e) {
			Log.e(getTag(), e.getMessage());
		}
		return object;
	}
	
	/**
	 * 
	 * @return Raw JSON form of the object
	 */
	public JSONObject getJSON(){
		return mObject;
	
	}
	/**
	 * 
	 * @param key
	 * @return the array with key "key", or null if none exist
	 */
	protected JSONArray getJSONArray(String key){
		JSONArray object=null;
		try {
			object = mObject.getJSONArray(key);
		} catch (JSONException e) {
			Log.e(getTag(), e.getMessage());
		}
		return object;
		
	}
    
	/**
    * Serializable implementation
    * @throws JSONException 
    */
    private void readObject(ObjectInputStream aInputStream) 
		    throws ClassNotFoundException, IOException, JSONException {
      String jString = (String) aInputStream.readObject();
      mObject = new JSONObject(jString);
    }

    /**
    * Serializable implementation
    */
    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
      aOutputStream.writeObject(mObject.toString());
    }
}
