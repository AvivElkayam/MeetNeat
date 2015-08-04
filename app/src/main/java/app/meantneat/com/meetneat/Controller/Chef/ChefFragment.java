package app.meantneat.com.meetneat.Controller.Chef;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;


import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 5/17/15.
 */
public class ChefFragment extends Fragment {

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "EventDishes", "EventMeals" };

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();
            switch (position)
            {
                case 0:
                {
                   return new ChefEventDishesFragment();

                }
                case 1:
                {
                    return new ChefEventMealsFragment();
                }


            }
           return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        View v = inflater.inflate(R.layout.chef_fragment_layout,container,false);
        initTabs(v);
        return v;
    }

    private void initTabs(View v) {
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.chef_view_pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) v.findViewById(R.id.chef_view_pager_tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }



}