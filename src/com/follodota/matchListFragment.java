package com.follodota;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.follodota.models.Match;
import com.follodota.utils.FolloDotaRequest;
import com.follodota.utils.MatchesListAdapter;

/**
 * A list fragment representing a list of matches. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link matchDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class MatchListFragment extends ListFragment {
	private static final String TAG = "follodota.listfragment";
	private static final String matchesIndexApi = "http://follodota.herokuapp.com/api/v1/matches.json";
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Listener<JSONObject> matchesListener = new Listener<JSONObject>() {
        	@Override
			public void onResponse(JSONObject responseObject) {
				ArrayList<Match> matchesList = new ArrayList<Match>();
				JSONArray JSONmatchList = null;
				try{
					JSONmatchList = responseObject.getJSONArray("matches");
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
					if (m!=null)matchesList.add(m);
				}
				MatchesListAdapter mAdapter = new MatchesListAdapter(MatchListFragment.this.getActivity()
						, matchesList);
				MatchListFragment.this.setListAdapter(mAdapter);
			}
        };
        
		JsonObjectRequest listRequest = new JsonObjectRequest(matchesIndexApi, null, matchesListener, null);
		//calling the api!
		Log.d(TAG,"starting request");
		FolloDotaRequest.getInstance(getActivity()).addRequest(listRequest);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        ((MainActivity)getActivity())
        	.onMatchSelected((Match) listView.getItemAtPosition(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}


