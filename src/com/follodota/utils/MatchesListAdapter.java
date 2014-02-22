package com.follodota.utils;

import java.util.List;

import com.follodota.models.Match;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MatchesListAdapter extends ArrayAdapter<Match>{

	public MatchesListAdapter(Context context, int resource,
			int textViewResourceId, List<Match> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	

}
