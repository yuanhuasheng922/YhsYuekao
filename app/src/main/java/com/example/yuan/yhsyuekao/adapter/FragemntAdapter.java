package com.example.yuan.yhsyuekao.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yuan.yhsyuekao.fragemnt.HomeFragment;
import com.example.yuan.yhsyuekao.fragemnt.NoLoginFragment;
import com.example.yuan.yhsyuekao.fragemnt.SelectPeopleFragment;

public class FragemntAdapter extends FragmentPagerAdapter {
    private String[] content=new String[]{"首页","找人","未登录"};
    public FragemntAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return new HomeFragment();
            case 1:
                return new SelectPeopleFragment();
            case 2:
                return new NoLoginFragment();

        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return content[position];
    }

    @Override
    public int getCount() {
        return content.length;
    }
}
