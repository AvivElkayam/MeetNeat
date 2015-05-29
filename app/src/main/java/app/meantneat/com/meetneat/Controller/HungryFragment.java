package app.meantneat.com.meetneat.Controller;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.astuetz.PagerSlidingTabStrip;

import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 5/17/15.
 */
public class HungryFragment extends Fragment {
    FragmentTabHost mTabHost;
    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "Tab1", "Tab2" };

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
                   return new HungryMapFragment();

                }
                case 1:
                {
                    return new HungryListFragment();
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
        View v = inflater.inflate(R.layout.hungry_fragment_layout,container,false);
        //View view = initTabs(v);
        initTabs2(v);
//        mTabHost = (FragmentTabHost)v.findViewById(R.id.hungryTabHost);
//        mTabHost.setCurrentTab(1);
        return v;
    }

    private void initTabs2(View v) {
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.hungry_view_pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) v.findViewById(R.id.hungry_view_pager_tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }


//    private View initTabs(View v)
//    {
//
//        mTabHost = (FragmentTabHost)v.findViewById(R.id.hungryTabHost);
//
//        mTabHost.setup(getActivity(), getActivity().getSupportFragmentManager(), R.id.hungry_tabs_container);
////dan
//
//        mTabHost.addTab(mTabHost.newTabSpec("HungryMapFragment").setIndicator("HungryMap"),
//                HungryMapFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("HungryListFragment").setIndicator("HungryList"),HungryMenuFragment.class,null);
//
//        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
//        {
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#E0E0E0")); //unselected
//        }
//        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.BLUE);
//
//        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//
//                for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
//                {
//                    mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#E0E0E0")); //unselected
//                }
//                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.BLUE);
//
//            }
//        });
//
//
//        return mTabHost;
//        // mTabHost.setCurrentTab(getIntent().getIntExtra("tab",0));
//    }
}
