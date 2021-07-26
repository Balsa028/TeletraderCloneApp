package com.balsa.teletraderentryapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.balsa.teletraderentryapp.GitHubActivity;
import com.balsa.teletraderentryapp.MainActivity;
import com.balsa.teletraderentryapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private BottomNavigationView bottomNavigationView;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);

        // inicijalizacija pager adaptera
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        //dodavanje fragmenata
        pagerAdapter.addFragment(new ChgLastFragment(),"Change%/Last");
        pagerAdapter.addFragment(new BidAskFragment(),"Bid/Ask,High/Low");

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        initBottomNavigationView();

        return view;
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.web:
                        Intent intent = new Intent(getActivity(), GitHubActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.home:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer,new MainFragment())
                                .commit();
                        break;
                    case R.id.news:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer,new NewsFragment())
                                .commit();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    private void initViews(View view) {

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        bottomNavigationView = view.findViewById(R.id.bottomNavView);
    }


    public class PagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public PagerAdapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ChgLastFragment();
                case 1:
                    return new BidAskFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment , String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}