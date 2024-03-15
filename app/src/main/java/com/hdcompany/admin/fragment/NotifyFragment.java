package com.hdcompany.admin.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.hdcompany.admin.R;
import com.hdcompany.admin.activity.FeedbackAdminActivity;
import com.hdcompany.admin.databinding.FragmentNotifyBinding;
import com.hdcompany.admin.firebase.Auth;
import com.hdcompany.admin.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifyFragment newInstance(String param1, String param2) {
        NotifyFragment fragment = new NotifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FirebaseUser firebaseUser =null;
    private User user = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        try{
            do{
                firebaseUser = Auth.firebaseAuth().getCurrentUser();
            }while (firebaseUser == null);
            user = new User(firebaseUser.getEmail(),"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private FragmentNotifyBinding binding;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotifyBinding.inflate(inflater,container,false);
        this.context = binding.getRoot().getContext();
        binding.setUser(user);

        if(user != null && user.getUsername() != null){
            if(user.getUsername().equals("admin@admin.com")||user.getUsername().equals("bechjkjen@gmail.com") || user.getUsername().equals("ductrieuhoang@gmail.com") || user.getUsername().equals("neikihceb@gmail.com")){
                binding.toAdminButton.setVisibility(View.VISIBLE);
                binding.toAdminButton.setOnClickListener(v->{
                    toAdmin();
                });
                Toast.makeText(context, "Login as admin activated!", Toast.LENGTH_SHORT).show();
                binding.textViewAdminAccessibility.setText(R.string.admin_accessed);

            }else {
                binding.toAdminButton.setVisibility(View.GONE);
            }
        }

        binding.signOutButton.setOnClickListener(v->{
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
        return binding.getRoot();
    }
    private void toAdmin(){
        Intent i = new Intent(context, FeedbackAdminActivity.class);
        context.startActivity(i);
    }
}