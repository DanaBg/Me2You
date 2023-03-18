package com.example.me2you;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.me2you.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;


class StudentViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv;
    TextView idTv;
    List<Post> data;
    ImageView avatarImage;
    public StudentViewHolder(@NonNull View itemView, List<Post> data) {
        super(itemView);
        this.data = data;
        nameTv = itemView.findViewById(R.id.studentlistrow_name_tv);
        idTv = itemView.findViewById(R.id.studentlistrow_id_tv);
        avatarImage = itemView.findViewById(R.id.studentlistrow_avatar_img);
    }

    public void bind(Post st, int pos) {
        nameTv.setText(st.description);
        idTv.setText(st.id);
        if (st.getPictureUrl()  != null && st.getPictureUrl().length() > 5) {
            Picasso.get().load(st.getPictureUrl()).placeholder(R.drawable.avatar).into(avatarImage);
        }else{
            avatarImage.setImageResource(R.drawable.avatar);
        }
    }
}

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentViewHolder>{
    LayoutInflater inflater;
    List<Post> data;
    public void setData(List<Post> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public StudentRecyclerAdapter(LayoutInflater inflater, List<Post> data){
        this.inflater = inflater;
        this.data = data;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.student_list_row,parent,false);
        return new StudentViewHolder(view, data);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Post st = data.get(position);
        holder.bind(st,position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

}

