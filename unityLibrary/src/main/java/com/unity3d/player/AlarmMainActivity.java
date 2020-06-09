package com.unity3d.player;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import static com.unity3d.player.sa_TabFragment_Alarm.alarmListAdapter;

public class AlarmMainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AlarmMainPagerAdapter mPageAdapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_main);
        /*
        mTabLayout = findViewById(R.id.main_tab);
        mViewPager = findViewById(R.id.viewpager);
        mPageAdapter = new AlarmMainPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPageAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

         */

        mViewPager = findViewById(R.id.viewpager);
        mPageAdapter = new AlarmMainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPageAdapter);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_Information).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_Alarm).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_Statistic).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.action_Syssetting).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_Information) {
                    mViewPager.setCurrentItem(0);
                }else if(item.getItemId() == R.id.action_Alarm){
                    mViewPager.setCurrentItem(1);
                }else if(item.getItemId() == R.id.action_Statistic){
                    mViewPager.setCurrentItem(2);
                }else if(item.getItemId() == R.id.action_Syssetting){
                    mViewPager.setCurrentItem(3);
                }
                return true;
            }
        });

        Intent intent2 = getIntent();
        try {
            String msg = intent2.getExtras().getString("alarm_addition");
            if (msg.equals("complete")) {
                Log.d("Added", "complete");
                mViewPager.setCurrentItem(1);
                bottomNavigationView.getMenu().findItem(R.id.action_Alarm).setChecked(true);
                alarmListAdapter.notifyDataSetChanged();
            }
        }catch (NullPointerException e){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPageAdapter.notifyDataSetChanged();
    }
}
