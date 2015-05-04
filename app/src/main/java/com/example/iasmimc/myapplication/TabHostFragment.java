package com.example.iasmimc.myapplication;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.iasmimc.myapplication.Adapters.MyPageAdapter;
import com.example.iasmimc.myapplication.Fragment.ChartFragment;
import com.example.iasmimc.myapplication.Fragment.FinanceiroFragment;
import com.example.iasmimc.myapplication.Fragment.OpcaoFornecedoresFragmento;
import com.example.iasmimc.myapplication.Fragment.TimeRegreFragment;
import com.example.iasmimc.myapplication.Screen.ListConvActivity;

import java.util.ArrayList;
import java.util.List;


public class TabHostFragment extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    MyPageAdapter pageAdapter;
    private ViewPager mViewPager;
    private TabHost mTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab_host_fragment, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        // Tab Initialization
        initialiseTabHost(view);

        // Fragments and ViewPager Initialization
        List<Fragment> fragments = getFragments();
        pageAdapter = new MyPageAdapter(getActivity().getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOnPageChangeListener(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Method to add a TabHost
    private static void AddTab(ListConvActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
        tabSpec.setContent(new MyTabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    // Manages the Tab changes, synchronizing it with Pages
    public void onTabChanged(String tag) {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // Manages the Page changes, synchronizing it with Tabs
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        int pos = this.mViewPager.getCurrentItem();
        this.mTabHost.setCurrentTab(pos);
    }

    @Override
    public void onPageSelected(int arg0) {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        Fragment f1 = TimeRegreFragment.newInstance(0);
        Fragment f2 = ListConvActivity.PlaceholderFragment.newInstance(1);
        Fragment f3 = ChartFragment.newInstance(2);
        Fragment f4 = OpcaoFornecedoresFragmento.newInstance(3);
        Fragment f5 = FinanceiroFragment.newInstance(4);
        fList.add(f1);
        fList.add(f2);
        fList.add(f3);
        fList.add(f4);
        fList.add(f5);
        return fList;
    }

    // Tabs Creation
    private void initialiseTabHost(View view) {
        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup();

        // TODO Put here your Tabs

        Drawable home = getResources().getDrawable(R.drawable.ic_action_home);
        Drawable list = getResources().getDrawable(R.drawable.ic_action_list_2);
        Drawable chart = getResources().getDrawable(R.drawable.ic_action_pie_chart);
        Drawable debt = getResources().getDrawable(R.drawable.ic_action_search);
        Drawable chat = getResources().getDrawable(R.drawable.ic_action_creditcard);
        AddTab((ListConvActivity) getActivity(), mTabHost, mTabHost.newTabSpec("Tab1").setIndicator("",home));
        AddTab((ListConvActivity) getActivity(), mTabHost, mTabHost.newTabSpec("Tab2").setIndicator("",list));
        AddTab((ListConvActivity) getActivity(), mTabHost, mTabHost.newTabSpec("Tab3").setIndicator("",chart));
        AddTab((ListConvActivity) getActivity(), mTabHost, mTabHost.newTabSpec("Tab4").setIndicator("",debt));
        AddTab((ListConvActivity) getActivity(), mTabHost, mTabHost.newTabSpec("Tab4").setIndicator("",chat));
        mTabHost.setOnTabChangedListener(this);
    }
}