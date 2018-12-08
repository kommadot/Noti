package com.example.simhyobin.noti;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    public static GoogleAccountCredential mCredential;
    Calendar service;



    private static final String[] SCOPES = { CalendarScopes.CALENDAR };
    static final int REQUEST_ACCOUNT_PICKER = 2;
    static final int REQUEST_AUTHORIZATION = 3;

    insertCalendar insertcalender;


    private ViewPager mViewPager;
    FragmentPagerAdapter adapterViewPager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getWindow().getDecorView();

        if(Build.VERSION.SDK_INT >= 23){
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.parseColor("#f2f2f2"));
        }else if(Build.VERSION.SDK_INT >= 21){
            getWindow().setStatusBarColor(Color.WHITE);
        }


        setContentView(R.layout.activity_main);
        ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
        //FirebaseMessaging.getInstance().subscribeToTopic("news");

        mCredential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(SCOPES)).setBackOff(new ExponentialBackOff());
        if (mCredential.getSelectedAccount() == null) {
            Log.d("fail", "no Account");
            startActivityForResult(mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
        }



        FirebaseInstanceId.getInstance().getToken();
        Intent intent = getIntent();
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

        TabLayout mTab = (TabLayout)findViewById(R.id.tabs);
        mTab.setupWithViewPager(vpPager);

        mTab.getTabAt(0).setIcon(R.drawable.baseline_person_black_48);
        mTab.getTabAt(1).setIcon(R.drawable.baseline_chat_black_48);
        mTab.getTabAt(2).setIcon(R.drawable.baseline_settings_black_48);

    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return fragment_1.newInstance(0, "Page # 1");
                case 1:
                    return fragment_2.newInstance(1, "Page # 2");
                case 2:
                    return fragment_3.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "친구";
                case 1:
                    return "메시지";
                case 2:
                    return "설정";
                default:
                    return "NULL";
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ACCOUNT_PICKER){
            if(resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null){
                String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                if(accountName != null){
                    mCredential.setSelectedAccountName(accountName);
                }
            }
        }else if(requestCode == REQUEST_AUTHORIZATION){
            startActivityForResult(mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
        }
    }

}
