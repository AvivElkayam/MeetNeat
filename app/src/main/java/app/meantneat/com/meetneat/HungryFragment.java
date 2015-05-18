package app.meantneat.com.meetneat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

/**
 * Created by mac on 5/17/15.
 */
public class HungryFragment extends Fragment {
    FragmentTabHost mTabHost;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        View v = inflater.inflate(R.layout.hungry_fragment_layout,container,false);
        return initTabs(v);
        //return view;
    }





    private View initTabs(View v)
    {

        mTabHost = (FragmentTabHost)v.findViewById(R.id.hungryTabHost);

        mTabHost.setup(getActivity(), getActivity().getSupportFragmentManager(), R.id.hungry_tabs_container);
//dan

        mTabHost.addTab(mTabHost.newTabSpec("HungryMapFragment").setIndicator("HungryMap"),
                HungryMapFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("HungryListFragment").setIndicator("HungryList"),HungryListFragment.class,null);

        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
        {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#E0E0E0")); //unselected
        }
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.BLUE);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
                {
                    mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#E0E0E0")); //unselected
                }
                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.BLUE);

            }
        });
        return mTabHost;
        // mTabHost.setCurrentTab(getIntent().getIntExtra("tab",0));
    }
}
