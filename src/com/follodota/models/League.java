package com.follodota.models;

import org.json.JSONObject;

public class League extends SerializableJSONBasedObject{

	private static final long serialVersionUID = 4238594931581663352L;

	public League(JSONObject o) {
		super(o);
	}

	@Override
	String getTag() {
		return "follodota.league";
	}

	public String getName() {
		return getString("name");
	}
}
