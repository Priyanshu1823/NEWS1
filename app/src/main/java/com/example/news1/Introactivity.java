package com.example.news1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Introactivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position=0;
    Button btnGetStarted;
    Animation btnAnim;
    TextView tvskip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // make the activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);

        btnNext = findViewById(R.id.button);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvskip = findViewById(R.id.tv_skip);


        //when this activity is about to be lunch we need to check if its openened before or not

        if(restorePreData()){
            Intent intent= new Intent(getApplicationContext(),phone_number.class);
            startActivity(intent);
            finish();
        }


        final List<ScreenItem> mList = new ArrayList<>();
        boolean Allnews = mList.add(new ScreenItem("NEWS", "It's amazing that the amount of news that happens in the world every day always just exactly fits the newspaper", R.drawable.daily));
        mList.add(new ScreenItem("news", "I believe our world desperately needs Good News", R.drawable.po));
        mList.add(new ScreenItem("news", "The truth is, we know so little about life, we don't really know what the good news is and what the bad news is", R.drawable.live));

        //setup view pager
        screenPager = findViewById(R.id.screen_viewpage);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        //get started button click listner
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), phone_number.class);
                startActivity(intent);
                     savePrefsData();
                finish();
            }
        });

        //next button clicklistiner
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size() - 1) {//when we reach to the last screen
                    //TODO:show the GETSTARTED Button and hide the indicator and the next button
                    loaddLastScreen();
                }
            }

        });

        //skip button click listener
        tvskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });


        //tablayout add change listner
       tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               if (tab.getPosition()==mList.size()-1){
                   loaddLastScreen();
               }
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });

    }

    private void savePrefsData(){
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();

    }

    private boolean restorePreData(){
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore =pref.getBoolean("isIntroOpnend",false);
        return isIntroActivityOpnendBefore;
    }


        //show the Getstarted Button and hide is the indicator and the next button
        private void loaddLastScreen () {

            btnNext.setVisibility(View.INVISIBLE);
            btnGetStarted.setVisibility(View.VISIBLE);
            tvskip.setVisibility(View.INVISIBLE);
            tabIndicator.setVisibility(View.INVISIBLE);
            //TODO: ADD an animation the getstarted button
            //setup animation
            btnGetStarted.setAnimation(btnAnim);
        }
    }
