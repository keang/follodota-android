package com.follodota;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.follodota.models.Team;
import com.follodota.utils.FolloDotaRequest;

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

public class TeamListFragment extends ListFragment {
	private static final String TAG = "follodota.teamlistfragment";
	private static final String teamIndexApi = "http://follodota.herokuapp.com/api/v1/teams.json";
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
    public TeamListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Listener<JSONObject> teamListener = new Listener<JSONObject>() {
        	@Override
			public void onResponse(JSONObject responseObject) {
				ArrayList<Team> teamList = new ArrayList<Team>();
				JSONArray JSONmatchList = null;
				try{
					JSONmatchList = responseObject.getJSONArray("teams");
				} catch( JSONException e){
					Log.e(TAG, e.getMessage());
				}
				for(int i=0; i<JSONmatchList.length(); i++){
					Team t=null;
					try {
						t = new Team(JSONmatchList.getJSONObject(i));
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage());
					}
					if (t!=null)teamList.add(t);
				}
				TeamListAdapter mAdapter = new TeamListAdapter(TeamListFragment.this.getActivity()
						, teamList);
				TeamListFragment.this.setListAdapter(mAdapter);
			}
        };
        
		JsonObjectRequest listRequest = new JsonObjectRequest(teamIndexApi, null, teamListener, null);
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
        	.onTeamSelected((Team) listView.getItemAtPosition(position));
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
    

    

    private class TeamListAdapter extends BaseAdapter{
    	private final Context mContext;
    	private final List<Team> mList;
    	public TeamListAdapter(Context context,List<Team> objects) {
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
    			rowView = inflater.inflate(R.layout.list_item_team, parent, false);
    			holder = new ViewHolder();
    			holder.icon = (ImageView) rowView.findViewById(R.id.icon);
    			holder.name = (TextView)rowView.findViewById(R.id.name);
    			rowView.setTag(holder);
    		}
    		if(holder==null) holder = (ViewHolder) rowView.getTag();
    		Team curTeam = (Team)mList.get(position);
    		holder.icon.setImageResource(curTeam.getLogoResourceId(mContext));
    		holder.name.setText(curTeam.getName());
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
    		public ImageView icon;
    		public TextView name;
    	}
    }
}
