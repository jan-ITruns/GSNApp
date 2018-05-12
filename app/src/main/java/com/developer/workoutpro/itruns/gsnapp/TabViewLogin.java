package com.developer.workoutpro.itruns.gsnapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class TabViewLogin extends FragmentPagerAdapter{

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    } // Methode addFragment

    public TabViewLogin(FragmentManager fm) {
       super(fm);
    } // Konstruktor TabViewVertretungsplan

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    } // Methode getPageTitle

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    } // Methode getItem

    @Override
    public int getCount() {
        return fragmentList.size();
    } // Methode getCount*/
}
