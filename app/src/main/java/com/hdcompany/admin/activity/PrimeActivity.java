package com.hdcompany.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.hdcompany.admin.R;
import com.hdcompany.admin.databinding.ActivityPrimeBinding;

public class PrimeActivity extends AppCompatActivity {

    private ActivityPrimeBinding primeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        primeBinding = ActivityPrimeBinding.inflate(getLayoutInflater());
        setContentView(primeBinding.getRoot());

        /*
        Set Bottom Nav Clicks
         */

        primeBinding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                item.setIcon(R.drawable.shop_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_cart).setIcon(R.drawable.cart_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_notify).setIcon(R.drawable.no_news_notify_not_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.profile_not_select);
            } else if (id == R.id.nav_cart) {
                item.setIcon(R.drawable.cart_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.shop_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_notify).setIcon(R.drawable.no_news_notify_not_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.profile_not_select);
            } else if (id == R.id.nav_notify) {
                item.setIcon(R.drawable.no_news_notify_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_cart).setIcon(R.drawable.cart_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.shop_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.profile_not_select);
            } else if (id == R.id.nav_profile) {
                item.setIcon(R.drawable.profile_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_cart).setIcon(R.drawable.cart_not_select);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_notify).setIcon(R.drawable.no_news_notify_not_selected);
                primeBinding.bottomNavigationView.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.shop_not_select);
            }
            return true;
        });
    }
}