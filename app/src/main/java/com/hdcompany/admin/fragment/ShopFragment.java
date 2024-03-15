package com.hdcompany.admin.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hdcompany.admin.R;
import com.hdcompany.admin.activity.PrimeActivity;
import com.hdcompany.admin.activity.ProductDetailsActivity;
import com.hdcompany.admin.databinding.FragmentShopBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.listener.IOnClickListener;
import com.hdcompany.admin.model.SportClothes;
import com.hdcompany.admin.utility.GridSpace;

import com.hdcompany.admin.utility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private FragmentShopBinding shopBinding;
    private Context context;
    /*
    Key for limited loading data
     */
    private String key = null;
    private boolean loadState = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shopBinding = FragmentShopBinding.inflate(inflater,container,false);
        this.context = shopBinding.getRoot().getContext();

        shopBinding.toSportsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    shopBinding.toSportsManager.setBackgroundResource(R.drawable.menu_background);
                    shopBinding.toSportsManager.setTextColor(Color.WHITE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shopBinding.toSportsManager.setBackgroundResource(R.color.white);
                            shopBinding.toSportsManager.setTextColor(Color.BLACK);
                            if(getActivity() != null){
                                ((PrimeActivity) getActivity()).navigateFragments(3);
                            }
                        }
                    },80);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        shopBinding.toFeedBackManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    shopBinding.toFeedBackManager.setBackgroundResource(R.drawable.menu_background);
                    shopBinding.toFeedBackManager.setTextColor(Color.WHITE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shopBinding.toFeedBackManager.setBackgroundResource(R.color.white);
                            shopBinding.toFeedBackManager.setTextColor(Color.BLACK);
                            if(getActivity() != null){
                                ((PrimeActivity) getActivity()).navigateFragments(2);
                            }
                        }
                    },80);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        shopBinding.toBookingManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    shopBinding.toBookingManager.setBackgroundResource(R.drawable.menu_background);
                    shopBinding.toBookingManager.setTextColor(Color.WHITE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shopBinding.toBookingManager.setBackgroundResource(R.color.white);
                            shopBinding.toBookingManager.setTextColor(Color.BLACK);
                            if(getActivity() != null){
                                ((PrimeActivity) getActivity()).navigateFragments(1);
                            }
                        }
                    },80);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        return shopBinding.getRoot();
    }
}