package com.follodota.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.follodota.R;
import com.follodota.models.Match;

public class MatchesListAdapter extends BaseAdapter{
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
			holder.date = (TextView)rowView.findViewById(R.id.date);
			holder.round = (TextView) rowView.findViewById(R.id.round_text);
			holder.leagueName = (TextView) rowView.findViewById(R.id.league_name2);
			holder.caster = (TextView) rowView.findViewById(R.id.caster);
			rowView.setTag(holder);
		}
		if(holder==null) holder = (ViewHolder) rowView.getTag();
		Match curMatch = (Match)mList.get(position);
		holder.hometeam.setImageResource(curMatch.getHomeTeamLogo(mContext));
		holder.awayteam.setImageResource(curMatch.getAwayTeamLogo(mContext));
		holder.date.setText(curMatch.getPlayedDate());
		holder.round.setText(curMatch.getRound());
		holder.caster.setText(curMatch.getCaster());
		holder.leagueName.setText(curMatch.getLeagueName());
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
		public TextView date;
		public TextView leagueName;
		public TextView round;
		public TextView caster;
	}
}
