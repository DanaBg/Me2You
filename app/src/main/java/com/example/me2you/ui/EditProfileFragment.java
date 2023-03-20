package com.example.me2you.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.me2you.R;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.me2you.databinding.FragmentEditProfileBinding;
import com.example.me2you.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;


import java.util.UUID;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    private EditText userNameEt;
    private ImageView imageView;
    private ActivityResultLauncher<Void> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private Boolean isAvatarSelected = false;
    private UserProfileChangeRequest profileUpdates;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        EditProfileFragmentArgs args = EditProfileFragmentArgs.fromBundle(getArguments());
        View root = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userNameEt = root.findViewById(R.id.username);
        imageView = root.findViewById(R.id.profileImg);

        String userNameDesc = args.getUserName();
        Uri urlImage = args.getProfileUrl();
        userNameEt.setText(userNameDesc, TextView.BufferType.EDITABLE);
        if(args.getProfileUrl() != null) {
            Picasso.get().load(urlImage).into(imageView);
        }
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.profileImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.profileImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });

        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        binding.save.setOnClickListener(view1 ->{
            profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userNameEt.getText().toString()).build();

            if(isAvatarSelected == true) {
                String id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                binding.profileImg.setDrawingCacheEnabled(true);
                binding.profileImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.profileImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(id, bitmap, url -> {
                    profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userNameEt.getText().toString())
                            .setPhotoUri(Uri.parse(url)).build();
                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Navigation.findNavController(view1).popBackStack();
                            }
                        }
                    });
                });
            }else{
                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Navigation.findNavController(view1).popBackStack();
                        }
                    }
                });
            }
            isAvatarSelected = false;

        });

        binding.cancel.setOnClickListener(view1 ->
                Navigation.findNavController(view1).popBackStack()
        );

        return root;
    }
}
