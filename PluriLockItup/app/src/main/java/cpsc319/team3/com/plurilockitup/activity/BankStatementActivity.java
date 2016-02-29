package cpsc319.team3.com.plurilockitup.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cpsc319.team3.com.plurilockitup.R;

public class BankStatementActivity extends AppCompatActivity {

    FragmentPagerAdapter fmPageAdapter;
    ViewPager mainPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_statement);

        fmPageAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPager = (ViewPager)findViewById(R.id.viewpager);
        mainPager.setAdapter(fmPageAdapter);
    }






    private class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

//            switch (position) {
//
//                case 0:
//                    return new NearbyUsersFragment();
//                case 1:
//                    return new ProfileFragment();
//                case 2:
//                    return new MessagesFragment();
//                case 3:
//                    return new FriendsFragment();
//                default:
//                    return null;
//            }
            return new StatementFragment();
        }


        @Override
        public int getCount() {
            return 4;
        }

    }
}

