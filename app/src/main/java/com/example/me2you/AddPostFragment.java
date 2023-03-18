package com.example.me2you;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.me2you.databinding.FragmentAddPostBinding;
import com.example.me2you.model.CityModel;
import com.example.me2you.model.Model;
import com.example.me2you.model.Post;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class AddPostFragment extends Fragment {
    FragmentAddPostBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    Boolean isAvatarSelected = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.avatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.avatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        AddPostFragmentArgs args = AddPostFragmentArgs.fromBundle(getArguments());

        ArrayList<String> cities = new ArrayList<String>();
        LiveData<List<String>> data = CityModel.instance.getCities();

        Spinner citySpinner = (Spinner) binding.spinner;

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, cities);

        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);


        data.observe(getViewLifecycleOwner(),list->{
            list.forEach(item->{
                cities.add(item);
            });

            String chosenCity = args.getCity();
            if(chosenCity != null) {
                binding.spinner.setSelection(cities.indexOf(chosenCity));
            }

            cityAdapter.notifyDataSetChanged();
//            binding.progressBarAddPost.setVisibility(View.GONE);
        });


        binding.saveBtn.setOnClickListener(view1 -> {
            String id;
            String description = binding.descriptionTV.getText().toString();
            String itemType = binding.itemTypeTV.getText().toString();
            String phoneNumber = binding.phoneNumTV.getText().toString();
            String location = args.getCity();
            String currentUserId = auth.getCurrentUser().getUid();

            id = args.getPostId();
            if(id == null) {
                id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            }
            Post newPost = new Post(id,currentUserId,"",location,itemType,description);

            if (isAvatarSelected){
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(currentUserId, bitmap, url->{
                    if (url != null){
                        newPost.setPictureUrl(url);
                    }
                    Model.instance().addPost(newPost, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            }else {
                Model.instance().addPost(newPost, (unused) -> {
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });

        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.studentsListFragment,false));

        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
        return view;
    }

}