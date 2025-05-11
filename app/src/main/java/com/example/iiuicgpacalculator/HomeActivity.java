package com.example.iiuicgpacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.iiuicgpacalculator.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private ViewPagerMessangerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        adapter=new ViewPagerMessangerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.tab.setupWithViewPager(binding.viewPager);
    }
}