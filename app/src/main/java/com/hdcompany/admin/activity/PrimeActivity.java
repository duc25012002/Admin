package com.hdcompany.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.hdcompany.admin.R;
import com.hdcompany.admin.adapter.MyViewPager2Adapter;
import com.hdcompany.admin.databinding.ActivityPrimeBinding;

public class PrimeActivity extends AppCompatActivity {

    private ActivityPrimeBinding primeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        primeBinding = ActivityPrimeBinding.inflate(getLayoutInflater());
        setContentView(primeBinding.getRoot());
        setOnClick();

    }

    /*
    Set onclick very first
     */
    private void setOnClick(){
        // Register a global layout listener for the activity's root view
        View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getHeight();
                int keypadHeight = screenHeight - r.bottom;

                // If keypadHeight is greater than 0, the soft keyboard is displayed
                boolean isKeyboardDisplayed = keypadHeight > screenHeight * 0.15; // Adjust threshold as needed

                if (isKeyboardDisplayed) {
                    // Soft keyboard is displayed
                    // Do something
                    primeBinding.bottomNavigationView.setVisibility(View.GONE);
                } else {
                    // Soft keyboard is not displayed
                    // Do something else
                    primeBinding.bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
        /*
        Set Bottom Nav Clicks
         */
        primeBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_shop) {
                primeBinding.viewPager2.setCurrentItem(0);
                item.setIcon(R.drawable.shop_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_cart).setIcon(R.drawable.cart_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_notify).setIcon(R.drawable.no_news_notify_not_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.profile_not_select);
            } else if (id == R.id.nav_cart) {
                primeBinding.viewPager2.setCurrentItem(1);
                item.setIcon(R.drawable.cart_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_shop).setIcon(R.drawable.shop_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_notify).setIcon(R.drawable.no_news_notify_not_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.profile_not_select);
            } else if (id == R.id.nav_notify) {
                primeBinding.viewPager2.setCurrentItem(2);
                item.setIcon(R.drawable.no_news_notify_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_cart).setIcon(R.drawable.cart_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_shop).setIcon(R.drawable.shop_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.profile_not_select);
            } else if (id == R.id.nav_profile) {
                primeBinding.viewPager2.setCurrentItem(3);
                item.setIcon(R.drawable.profile_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_cart).setIcon(R.drawable.cart_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_notify).setIcon(R.drawable.no_news_notify_not_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_shop).setIcon(R.drawable.shop_not_select);
            }
            return true;
        });
        /*
        set custom adapter
         */
        MyViewPager2Adapter myViewPager2Adapter = new MyViewPager2Adapter(this);
        primeBinding.viewPager2.setAdapter(myViewPager2Adapter);
        /*
        Disable swipe
         */
        primeBinding.viewPager2.setUserInputEnabled(false);
        /*
        Binding with bottom nav
         */
        primeBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch(position){
                    case 1:{
                        primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_cart).setChecked(true);
                        break;
                    }
                    case 2:{
                        primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_notify).setChecked(true);
                        break;
                    }
                    case 3:{
                        primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);
                        break;
                    }
                    default:{
                        primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_shop).setChecked(true);
                    }
                }
            }
        });
    }
    public void navigateFragments(int fragmentIndex){
        primeBinding.viewPager2.setCurrentItem(fragmentIndex);
    }
}