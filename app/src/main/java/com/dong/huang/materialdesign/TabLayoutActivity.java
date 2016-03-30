package com.dong.huang.materialdesign;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends AppCompatActivity {

    private List<String> mTitles;
    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MyFragmentAdapter mMyFragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        mTabLayout = (TabLayout) findViewById(R.id.tab_title);

        mViewPager = (ViewPager) findViewById(R.id.vp_pager);

        mFragments = new ArrayList<>();
        mFragments.add(new TabFragment("Tab1"));
        mFragments.add(new TabFragment("Tab2"));
        mFragments.add(new TabFragment("Tab3"));
        mFragments.add(new TabFragment("Tab4"));
        mFragments.add(new TabFragment("Tab5"));
        mFragments.add(new TabFragment("Tab5"));
        mFragments.add(new TabFragment("Tab5"));
        mFragments.add(new TabFragment("Tab5"));

        mTitles = new ArrayList<>();
        mTitles.add("Title1");
        mTitles.add("Title2");
        mTitles.add("Title3");
        mTitles.add("Title4");
        mTitles.add("Title5");
        mTitles.add("Title5");
        mTitles.add("Title5");
        mTitles.add("Title5");

        mMyFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(4)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(5)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(6)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(7)));

        mViewPager.setAdapter(mMyFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);


    }


    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position % mTitles.size());
        }
    }
}
