package cpsc319.team3.com.plurilockitup.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cpsc319.team3.com.plurilockitup.R;

public class BankStatementActivity extends AppCompatActivity {

    FragmentPagerAdapter fmPageAdapter;
    ViewPager mainPager;
    boolean swipeHelper = true;
    String [] monthList = new String[]{"January", "February", "March", "April"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_statement);

        fmPageAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPager = (ViewPager)findViewById(R.id.viewpager);
        mainPager.setAdapter(fmPageAdapter);


        mainPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position){
                if(swipeHelper){
                    findViewById(R.id.swipeReminder).setVisibility(View.GONE);
                    swipeHelper = false;
                }
            }
        });
    }






    private class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment statementFrag = new StatementFragment();
            Bundle bundle = new Bundle();
            bundle.putString("month", monthList[position]);
            bundle.putStringArray("monthList", monthList);
            statementFrag.setArguments(bundle);
            return statementFrag;
        }


        @Override
        public int getCount() {
            return monthList.length;
        }

    }
}

