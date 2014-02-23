package com.follodota;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.follodota.models.Match;

/**
 * A fragment representing a single match detail screen.
 * This fragment is either contained in a {@link matchListActivity}
 * in two-pane mode (on tablets) or a {@link matchDetailActivity}
 * on handsets.
 */
public class matchDetailFragment extends Fragment {
    /**
     * The fragment argument representing the match that this fragment
     * represents.
     */
    public static final String SELECTED_MATCH = "follodota.match";

    private Match mMatch;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public matchDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(SELECTED_MATCH)) {
            mMatch = (Match) getArguments().getSerializable(SELECTED_MATCH);
            Log.d("detail fragment", mMatch.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_detail, container, false);

        //TODO: implement view to represent mMatch here

        return rootView;
    }
}
