package com.dicoding.submissionMade.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.adapter.TabAdapter;
import com.dicoding.submissionMade.fragment.favorite.FavoriteMovieFragment;
import com.dicoding.submissionMade.fragment.favorite.FavoriteTvShowFragment;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        TabAdapter tabAdapter = new TabAdapter(getFragmentManager());
        tabAdapter.addFragment(new FavoriteMovieFragment(), getResources().getString(R.string.movie));
        tabAdapter.addFragment(new FavoriteTvShowFragment(), getResources().getString(R.string.tv_show));


        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(tabAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return view;
    }
}
