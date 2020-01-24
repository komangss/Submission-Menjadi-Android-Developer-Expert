package com.dicoding.submissionmade2_1.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dicoding.submissionmade2_1.Adapter.FavoriteAdapter;
import com.dicoding.submissionmade2_1.Adapter.SectionsPagerAdapter;
import com.dicoding.submissionmade2_1.Item.Favorite;
import com.dicoding.submissionmade2_1.R;
import com.dicoding.submissionmade2_1.ViewModel.FavoriteViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private FavoriteViewModel favoriteViewModel;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FavoriteAdapter adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance( int index) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragmentView = inflater.inflate(R.layout.fragment_favorite, container, false);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this.getContext(), getFragmentManager());
        ViewPager viewPager = myFragmentView.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = myFragmentView.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

//        buat adapter
        adapter = new FavoriteAdapter();
        RecyclerView recyclerView = myFragmentView.findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

//        buat view model
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                adapter.setFavorites(favorites);
                Toast.makeText(getActivity(),"onChanged",Toast.LENGTH_SHORT).show();
            }
        });
        return myFragmentView;
    }

}
