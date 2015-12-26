package app.com.ringmenot.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.com.ringmenot.AboutFragment;
import app.com.ringmenot.HomeFragment;
import app.com.ringmenot.MessageFragment;
import app.com.ringmenot.MissedFragment;

/**
 * Created by sonam on 22-Dec-15.
 */
public class Paginator extends FragmentPagerAdapter {

        public Paginator(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    // Top Rated fragment activity
                    return new HomeFragment();
                case 1:
                    // Games fragment activity
                    return new MessageFragment();
                case 2:
                    // Movies fragment activity
                    return new MissedFragment();
                case 3:
                    // Movies fragment activity
                    return new AboutFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 4;
        }

    }
