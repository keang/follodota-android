package com.follodota;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.follodota.models.Match;
import com.follodota.utils.FolloDotaRequest;

/**
 * A list fragment representing a list of matches. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link matchDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class matchListFragment extends ListFragment {
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
    public matchListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		JsonArrayRequest listRequest = new JsonArrayRequest(matchesIndexApi, new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				ArrayList<Match> matchesList = new ArrayList<Match>();
				for(int i=0; i<response.length(); i++){
					Match m=null;
					try {
						m = new Match(response.getJSONObject(i));
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage());
					}
					if (m!=null)matchesList.add(m);
				}
				MatchesListAdapter mAdapter = new MatchesListAdapter(matchListFragment.this.getActivity()
						, matchesList);
				matchListFragment.this.setListAdapter(mAdapter);
			}
		}, null);
		//calling the api!
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
        ((matchListActivity)getActivity())
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
    

    private class MatchesListAdapter extends BaseAdapter{
    	private final Context mContext;
    	private final List<Match> mList;
    	public MatchesListAdapter(Context context,List<Match> objects) {
    		super();
    		mContext = context;
    		mList = objects;
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		View rowView = convertView;
    		ViewHolder holder = null;
    		if(rowView ==null){
    			LayoutInflater inflater = (LayoutInflater) mContext
    		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			rowView = inflater.inflate(R.layout.list_item_match, parent, false);
    			holder = new ViewHolder();
    			holder.hometeam = (ImageView) rowView.findViewById(R.id.hometeam);
    			holder.awayteam = (ImageView) rowView.findViewById(R.id.awayteam);
    			rowView.setTag(holder);
    		}
    		if(holder==null) holder = (ViewHolder) rowView.getTag();
    		holder.hometeam.setImageResource(((Match)mList.get(position)).getHomeTeam().getLogoResourceId(mContext));
    		holder.awayteam.setImageResource(((Match)mList.get(position)).getAwayTeam().getLogoResourceId(mContext));
    		return rowView;
    	}

    	@Override
    	public int getCount() {
    		return mList.size();
    	}

    	@Override
    	public Object getItem(int position) {
    		return mList.get(position);
    	}

    	@Override
    	public long getItemId(int position) {
    		return mList.indexOf(getItem(position));
    	}
    	
    	class ViewHolder{
    		public ImageView hometeam;
    		public ImageView awayteam;
    	}
    }

}

