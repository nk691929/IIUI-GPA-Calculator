package com.example.iiuicgpacalculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.iiuicgpacalculator.fragments.cgpa_ragment;
import com.example.iiuicgpacalculator.fragments.gpa_ragment;

public class ViewPagerMessangerAdapter extends FragmentPagerAdapter {
    public ViewPagerMessangerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new gpa_ragment();
        }else {
            return new cgpa_ragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "GPA";
        }else {
            return "CGPA";
        }
    }
}
