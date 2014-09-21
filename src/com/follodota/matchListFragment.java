package com.follodota;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
public class MatchListFragment extends Fragment {
	private static final String TAG = "follodota.listfragment";
	private static final String matchesIndexApi = "http://follodota.herokuapp.com/api/v1/matches.json?page=";
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    
    private Listener<JSONObject> matchesListener;
    
    private int mPage;
    private MatchesListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchesListener = new Listener<JSONObject>() {
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
				if(mAdapter!=null){
					//this is not first page, append to list
					mAdapter.appendList(matchesList);
				} else {
				    mAdapter = new MatchesListAdapter(MatchListFragment.this.getActivity()
						, matchesList);
                    ((ListView) getActivity().findViewById(R.id.matches_listview)).setAdapter(mAdapter);
				}

				mSwipeRefreshLayout.setRefreshing(false);
			}
        };
        mPage=1;
        
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_match_list, null); 
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipeRefreshLayout_matchListFragment);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary_loading_color, R.color.secondary_loading_color,
                R.color.primary_loading_color, R.color.secondary_loading_color);

		mSwipeRefreshLayout.setEnabled(false);
        ListView list = (ListView) fragmentView.findViewById(R.id.matches_listview);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {  
        	  @Override
        	  public void onScrollStateChanged(AbsListView view, int scrollState) {
        	  }

        	  @Override
        	  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        	    Log.d(TAG, "at the bottom: "+((visibleItemCount+firstVisibleItem)==totalItemCount));
      		    if((visibleItemCount+firstVisibleItem)==totalItemCount){
      		    	mSwipeRefreshLayout.setRefreshing(true);
      		    	nextPage();
      		    }
        	  }
        	});
        
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		        ((MainActivity)getActivity())
		        	.onMatchSelected((Match) parent.getItemAtPosition(position));
			}
		});
        requestServerForMatches(mPage);	
        return fragmentView;
	}

	public void requestServerForMatches(int pageNumber){
		JsonObjectRequest listRequest = new JsonObjectRequest(matchesIndexApi+pageNumber, null, matchesListener, null); //calling the api!
		Log.d(TAG,"starting request");
		FolloDotaRequest.getInstance(getActivity()).addRequest(listRequest);
    }

    public void nextPage(){
    	mPage++;
    	requestServerForMatches(mPage);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    class ScrollRefreshLayout extends SwipeRefreshLayout{
    	ListView mListView;
		public ScrollRefreshLayout(Context context, ListView listview) {
			super(context);
		}
		
		@Override
		public boolean canChildScrollUp()
		{
			return false;
		}
    }
}


