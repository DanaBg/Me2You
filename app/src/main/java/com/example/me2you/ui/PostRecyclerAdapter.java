package com.example.me2you.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.me2you.R;
import com.example.me2you.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;


class PostViewHolder extends RecyclerView.ViewHolder{
    TextView descriptionTv;
    TextView itemTypeTv;
    TextView locationTv;
    TextView phoneNumberTv;
    List<Post> data;
    ImageView avatarImage;
    ImageView editBtn;

    public PostViewHolder(@NonNull View itemView, List<Post> data, Boolean shouldShowEdit) {
        super(itemView);
        this.data = data;
        descriptionTv = itemView.findViewById(R.id.postDescriptionTV);
        itemTypeTv = itemView.findViewById(R.id.postItemTypeTV);
        phoneNumberTv = itemView.findViewById(R.id.postPhoneNumberTV);
        locationTv = itemView.findViewById(R.id.postLocationTV);
        avatarImage = itemView.findViewById(R.id.studentlistrow_avatar_img);
        editBtn = itemView.findViewById(R.id.postrow_edit);

        if(!shouldShowEdit) {
            editBtn.setVisibility(View.GONE);
        };

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                Post post = data.get(pos);
                MyItemsFragmentDirections.ActionNavigationMyItemsToEditProfile action =
                        MyItemsFragmentDirections.actionNavigationMyItemsToEditProfile();
                action.setPostId(post.getId());
                action.setDescription(post.getDescription());
                action.setPhoneNumber(post.getPhoneNumber());
                action.setPictureUrl(post.getPictureUrl());
                action.setCity(post.getLocation());
                action.setItemType(post.getItemType());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    public void bind(Post st, int pos) {
        descriptionTv.setText(st.description);
        itemTypeTv.setText(st.itemType);
        locationTv.setText(st.location);
        phoneNumberTv.setText(st.phoneNumber);
        if (st.getPictureUrl()  != null && st.getPictureUrl().length() > 5) {
            Picasso.get().load(st.getPictureUrl()).placeholder(R.drawable.avatar).into(avatarImage);
        }else{
            avatarImage.setImageResource(R.drawable.avatar);
        }
    }
}

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder>{
    LayoutInflater inflater;
    List<Post> data;
    Boolean shouldShowEdit;
    public void setData(List<Post> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data, Boolean shouldShowEdit){
        this.inflater = inflater;
        this.data = data;
        this.shouldShowEdit = shouldShowEdit;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_row,parent,false);
        return new PostViewHolder(view, data, this.shouldShowEdit);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post st = data.get(position);
        holder.bind(st,position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

}

