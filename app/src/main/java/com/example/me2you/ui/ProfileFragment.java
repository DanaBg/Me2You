package com.example.me2you.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.me2you.LoginActivity;
import com.example.me2you.R;
import com.example.me2you.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    private FirebaseUser user;
    private Button logoutButton;
    private Button editButton;
    private FragmentProfileBinding binding;
    private String userDisplayName;
    private String userEmail;
    private String userPassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        logoutButton = root.findViewById(R.id.logout);
        editButton = root.findViewById(R.id.editProfile);
        if(user != null) {
            userEmail = user.getEmail();
            userDisplayName = user.getDisplayName();

            binding.email.setText(userEmail);
            binding.profileName.setText(userDisplayName);
            binding.password.setText("******");

//            Uri imageUri = Uri.parse("https://cdn.primedia.co.za/primedia-broadcasting/image/upload/c_fill,h_436,w_700/ykcxncolmdjmafvnznrm");
//
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setPhotoUri(imageUri)
//                    .build();
//
//            user.updateProfile(profileUpdates);

            Handler hand = new Handler();
            hand.postDelayed(setText(),100);
            if(user.getPhotoUrl() != null) {
                Picasso.get().load(user.getPhotoUrl()).into(binding.profileImg);
            }
        }
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
            }
        });

//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ProfileFragmentDirections.ActionNavigationProfileToEditProfile action =
//                        ProfileFragmentDirections.actionNavigationProfileToEditProfile(user.getDisplayName(),
//                                user.getPhotoUrl());
//
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
        return root;
    }
    public Runnable setText(){
        return new Runnable() {
            @Override
            public void run() {
                binding.profileName.setText(user.getDisplayName());
            }
        };
    }
}