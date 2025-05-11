package com.example.iiuicgpacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.example.iiuicgpacalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private Animation downAnim,upAnim;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();

        downAnim= AnimationUtils.loadAnimation(this,R.anim.down_anim);
        upAnim= AnimationUtils.loadAnimation(this,R.anim.slide_up);

        binding.relativeLayout.setAnimation(upAnim);
        binding.linearLayout.setAnimation(downAnim);
        binding.imageView.setAnimation(downAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

}