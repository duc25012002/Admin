package com.hdcompany.admin.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hdcompany.admin.fragment.CartFragment;
import com.hdcompany.admin.fragment.NotifyFragment;
import com.hdcompany.admin.fragment.ProfileFragment;
import com.hdcompany.admin.fragment.ShopFragment;

public class MyViewPager2Adapter extends FragmentStateAdapter {

    public MyViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: {
                return new CartFragment();
            }
            case 2: {
                return new NotifyFragment();
            }
            case 3: {
                return new ProfileFragment();
            }
            default: {
                return new ShopFragment();
            }

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
