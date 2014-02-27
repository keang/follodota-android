package com.follodota;

import com.follodota.R;
import com.follodota.models.Match;
import com.follodota.models.Team;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends FragmentActivity implements
	ActionBar.OnNavigationListener {
	
		/**
		* The serialization (saved instance state) Bundle key representing the
		* current dropdown position.
		*/
		private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

		@Override
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			Fragment fragment = null;
			if(itemPosition==1) fragment = new TeamListFragment();
			else if(itemPosition==0) fragment = new MatchListFragment();
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
			return true;
		} 
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			// Set up the action bar to show a dropdown list.
			final ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			// Set up the dropdown list navigation in the action bar.
			actionBar.setListNavigationCallbacks(
			// Specify a SpinnerAdapter to populate the dropdown list.
					new ArrayAdapter<String>(getActionBarThemedContextCompat(),
							android.R.layout.simple_list_item_1,
							android.R.id.text1, new String[] {
									//getString(R.string.nav_title_league),
									getString(R.string.nav_title_matches),
									getString(R.string.nav_title_teams)
									}), this);
		}
		
		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {
			// Restore the previously serialized current dropdown position.
			if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
				getActionBar().setSelectedNavigationItem(
						savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
			}
		}
		
		@Override
		public void onSaveInstanceState(Bundle outState) {
			// Serialize the current dropdown position.
			outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
					.getSelectedNavigationIndex());
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			//TODO: getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		/**
		 * Backward-compatible version of {@link ActionBar#getThemedContext()} that
		 * simply returns the {@link android.app.Activity} if
		 * <code>getThemedContext</code> is unavailable.
		 */
		@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		private Context getActionBarThemedContextCompat() {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				return getActionBar().getThemedContext();
			} else {
				return this;
			}
		}

		public void onMatchSelected(Match itemAtPosition) {
			Intent detailIntent = new Intent(this, MatchDetailActivity.class);
			detailIntent.putExtra(MatchDetailActivity.SELECTED_MATCH, itemAtPosition);
			this.startActivity(detailIntent);
		}

		public void onTeamSelected(Team itemAtPosition) {
			Intent detailIntent = new Intent(this, TeamDetailActivity.class);
			detailIntent.putExtra(TeamDetailActivity.SELECTED_TEAM, itemAtPosition);
			startActivity(detailIntent);
		}
}
