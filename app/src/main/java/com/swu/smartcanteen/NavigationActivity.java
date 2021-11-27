package com.swu.smartcanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class NavigationActivity extends AppCompatActivity {

    private BottomNavigationBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initBar();
        pressMenu();
    }

    private void initBar() {
        bar = findViewById(R.id.bottom_navigation_bar);
        bar
                .addItem(new BottomNavigationItem(R.drawable.home_selector, R.string.home))
                .addItem(new BottomNavigationItem(R.drawable.user_selector, R.string.user))
                .addItem(new BottomNavigationItem(R.drawable.order_selector,R.string.order))
                .setFirstSelectedPosition(0)
                .initialise();
    }
    private void pressMenu(){
        bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){

                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
}