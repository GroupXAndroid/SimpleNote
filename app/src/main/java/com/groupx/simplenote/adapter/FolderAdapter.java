package com.groupx.simplenote.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.entity.Folder;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder>{

    private List<Folder> folderList;

    public FolderAdapter(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FolderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_folder,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        holder.setFolder(folderList.get(position));

    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {
        CardView viewFolderContainer;
        TextView textViewFolderName;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            viewFolderContainer = itemView.findViewById(R.id.viewFolderContainer);
            textViewFolderName = itemView.findViewById(R.id.textViewFolderName);
        }

        void setFolder(Folder folder) {
            textViewFolderName.setText(folder.getFolderName());

            if (folder.getColor() != null && !folder.getColor().trim().isEmpty()) {
                viewFolderContainer.setCardBackgroundColor(
                        Color.parseColor(folder.getColor())
                );
            }
        }
    }
}
