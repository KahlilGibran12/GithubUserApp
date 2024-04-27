package com.example.githubuserapp.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubuserapp.R;
import com.example.githubuserapp.data.response.ItemsItem;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private List<ItemsItem> listUsers;

    public ListAdapter(ArrayList<ItemsItem> listUsers) {
        this.listUsers = listUsers;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ItemsItem user = listUsers.get(position);
        holder.tvName.setText(user.getLogin());
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatarUrl())
                .into(holder.ivFotoItem);

        // Pass the entire user object (cleaner and future-proof)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_USERNAME, user.getLogin()); // Send the entire user object
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public void setUsers(List<ItemsItem> listUsers) {
        this.listUsers = listUsers;
        notifyDataSetChanged();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivFotoItem;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivFotoItem = itemView.findViewById(R.id.ivFotoItem);
        }
    }
}
