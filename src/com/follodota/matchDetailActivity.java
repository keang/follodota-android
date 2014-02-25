/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.follodota;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import com.follodota.models.Game;
import com.follodota.models.Match;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class MatchDetailActivity extends YouTubeFailureRecoveryActivity implements
    YouTubePlayer.OnFullscreenListener {

  public static final String SELECTED_MATCH="follodota.selected_match";
  private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
      ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
      : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

  private Match mMatch;
  private YouTubePlayerView playerView;
  private YouTubePlayer player;
  private Button fullscreenButton;
  private CompoundButton checkbox;
  private View otherViews;
  private ListView listView;
  private boolean fullscreen;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_video);
    playerView = (YouTubePlayerView) findViewById(R.id.player);
    otherViews = findViewById(R.id.other_views);
    listView = (ListView) findViewById(R.id.game_list_view);
    mMatch = (Match) getIntent().getSerializableExtra(SELECTED_MATCH);
    ArrayAdapter<Game> gameAdapter = new ArrayAdapter<Game>(this, R.layout.list_item_game
    		, R.id.game_number, mMatch.getAllGames());
    listView.setAdapter(gameAdapter);
    listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int pos,
				long id) {
			player.cueVideo(mMatch.getAllGames().get(pos).getYoutubeLink());
		}

	});
    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    listView.setItemChecked(0, true);
    
    TextView title = (TextView) findViewById(R.id.match_title);
    title.setText(mMatch.toString());
    
    TextView roundText = (TextView)findViewById(R.id.round);
    roundText.setText(mMatch.getRound());
    
    TextView leagueNameText = (TextView)findViewById(R.id.league_name);
    leagueNameText.setText(mMatch.getLeagueName());
    playerView.initialize("AIzaSyCQ9pDHYz_bxjj4yQeHAK3G0P-WKA1GQfk", this);

    doLayout();
  }

  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
      boolean wasRestored) {
    this.player = player;
    //setControlsEnabled();
    //player.setFullscreen(!fullscreen);
    // Specify that we want to handle fullscreen behavior ourselves.
    player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
    player.setOnFullscreenListener(this);
    if (!wasRestored) {
        Match mMatch = (Match)getIntent().getExtras().getSerializable(SELECTED_MATCH);
        player.cueVideo(mMatch.getAllGames().get(0).getYoutubeLink());
    }
  }

  @Override
  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
    return playerView;
  }

  private void doLayout() {
    LinearLayout.LayoutParams playerParams =
        (LinearLayout.LayoutParams) playerView.getLayoutParams();
    if (fullscreen) {
      // When in fullscreen, the visibility of all other views than the player should be set to
      // GONE and the player should be laid out across the whole screen.
    	//TODO: add sharing overlay here
      playerParams.width = LayoutParams.MATCH_PARENT;
      playerParams.height = LayoutParams.MATCH_PARENT;

      otherViews.setVisibility(View.GONE);
    } else {
      // This layout is up to you - this is just a simple example (vertically stacked boxes in
      // portrait, horizontally stacked in landscape).
      otherViews.setVisibility(View.VISIBLE);
      ViewGroup.LayoutParams otherViewsParams = otherViews.getLayoutParams();
      if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
    	  //player.setFullscreen(true);
      } else {
        playerParams.width = otherViewsParams.width = MATCH_PARENT;
        playerParams.height = WRAP_CONTENT;
        playerParams.weight = 0;
        otherViewsParams.height = 0;
        //baseLayout.setOrientation(LinearLayout.VERTICAL);
      }
     // setControlsEnabled();
    }
  }

  private void setControlsEnabled() {
    checkbox.setEnabled(player != null
        && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    fullscreenButton.setEnabled(player != null);
  }

  @Override
  public void onFullscreen(boolean isFullscreen) {
    fullscreen = isFullscreen;
    doLayout();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    doLayout();
  }

}
