package com.hdcompany.admin.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.hdcompany.admin.R;
import com.hdcompany.admin.databinding.FragmentProfileBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FirebaseUser firebaseUser;
    private User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        do{
            firebaseUser = Auth.firebaseAuth().getCurrentUser();
        }while (firebaseUser == null);
        user = new User(firebaseUser.getEmail(),"");
    }

    /*
    CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE CODE
     */
    private FragmentProfileBinding profileBinding;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater,container,false);
        this.context = profileBinding.getRoot().getContext();
        profileBinding.setUser(user);
        profileBinding.signOutButton.setOnClickListener(v->{
            user.setUsername("Signed out");
            Toast.makeText(context, "App close in 2 seconds", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Auth.firebaseAuth().signOut();
                    requireActivity().finish();
                }
            }, 2000);
        });
        return profileBinding.getRoot();
    }
}