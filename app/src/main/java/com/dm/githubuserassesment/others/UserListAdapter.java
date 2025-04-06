package com.dm.githubuserassesment.others;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dm.githubuserassesment.R;
import com.dm.githubuserassesment.model.GitHubUserSimple;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private List<GitHubUserSimple> userList = new ArrayList<>();
    private final OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(String username);
    }

    public UserListAdapter(OnUserClickListener listener) {
        this.listener = listener;
    }

    public void setUserList(List<GitHubUserSimple> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        GitHubUserSimple user = userList.get(position);
        holder.usernameText.setText(user.login);
        Glide.with(holder.avatarImage.getContext()).load(user.avatarUrl).into(holder.avatarImage);

        holder.itemView.setOnClickListener(v -> listener.onUserClick(user.login));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView usernameText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.avatar_image);
            usernameText = itemView.findViewById(R.id.username_text);
        }
    }
}
