package com.follodota;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.follodota.models.Match;
import com.follodota.models.Team;
import com.follodota.utils.FolloDotaRequest;
import com.follodota.utils.MatchesListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TeamDetailActivity extends Activity{

	public static final String SELECTED_TEAM = "follodota.selected_team";
	public static final String TAG = "follodota.teamdetailactivity";
	private Team mTeam;
	private ListView listView;
	private ArrayList<Match> matchList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mTeam = (Team) getIntent().getSerializableExtra(SELECTED_TEAM);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team);
		getActionBar().setTitle(mTeam.getName());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setLogo(mTeam.getLogoResourceId(this));
		listView = (ListView) findViewById(R.id.team_list_view);
		Listener<JSONObject> listener=  new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				matchList = new ArrayList<Match>();
				JSONArray JSONmatchList = null;
				try{
					JSONmatchList = response.getJSONArray("matches");
				} catch( JSONException e){
					Log.e(TAG, e.getMessage());
				}
				for(int i=0; i<JSONmatchList.length(); i++){
					Match m=null;
					try {
						m = new Match(JSONmatchList.getJSONObject(i));
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage());
					}
					if (m!=null)matchList.add(m);
				}
				MatchesListAdapter matchAdapter = new MatchesListAdapter(TeamDetailActivity.this, matchList); 
			    listView.setAdapter(matchAdapter);
			    listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int pos,
							long id) {
						Intent detailIntent = new Intent(TeamDetailActivity.this, MatchDetailActivity.class);
						Match m = matchList.get(pos);
						detailIntent.putExtra(MatchDetailActivity.SELECTED_MATCH, m);
						TeamDetailActivity.this.startActivity(detailIntent);
					}

				});
			    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			    listView.setItemChecked(0, true);
			}
		};
		JsonObjectRequest teamRequest = new JsonObjectRequest(getUrl(mTeam.getId()), 
				null, listener, null);

		FolloDotaRequest.getInstance(this).addRequest(teamRequest);
		
	    
	}

	private String getUrl(String id) {
		return "http://follodota.herokuapp.com/api/v1/teams/"+id+".json";
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case android.R.id.home:
	        onBackPressed();
	    }
	    return true;
	}
}
