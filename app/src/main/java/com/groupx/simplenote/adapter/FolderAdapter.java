package com.groupx.simplenote.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.FolderActivity;
import com.groupx.simplenote.entity.Folder;
import com.groupx.simplenote.fragment.EditFolderFragment;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private final List<Folder> folderList;
    private final FolderActivity activity;

    public FolderAdapter(List<Folder> folderList, FolderActivity activity) {
        this.folderList = folderList;
        this.activity = activity;
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
        holder.imageViewFolderOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupMenuFolder(holder, holder.getBindingAdapterPosition(), v.getContext());
            }
        });
    }

    private void initPopupMenuFolder(@NonNull FolderViewHolder holder, int position, Context context) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, holder.imageViewFolderOption);
        //inflating menu from xml resource
        popup.inflate(R.menu.option_edit_folder);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuFolderEdit:
                        //handle edit click
                        EditFolderFragment editFragment = new EditFolderFragment();
                        activity.setEditFolderFragment(editFragment);
                        Bundle args = new  Bundle();
                        args.putSerializable("editFolder", folderList.get(position));
                        args.putBoolean("isEditing", true);
                        editFragment.setArguments(args);
                        editFragment.show(((FragmentActivity) context).getSupportFragmentManager(),
                                "editFolderFragment");
                        break;
                    case R.id.menuFolderDelete:
                        //handle menu2 click
                        new AlertDialog.Builder(context)
                                .setIcon(android.R.drawable.ic_delete)
                                .setTitle("Delete Folder")
                                .setMessage("Are you sure you want to delete?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                        activity.deleteFolder(folderList.get(position));
                                    }

                                })
                                .setNegativeButton("No", null)
                                .show();
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {
        CardView viewFolderContainer;
        TextView textViewFolderName;
        ImageView imageViewFolderOption;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            viewFolderContainer = itemView.findViewById(R.id.viewFolderContainer);
            textViewFolderName = itemView.findViewById(R.id.textViewFolderName);
            imageViewFolderOption = itemView.findViewById(R.id.imageViewFolderEditOption);
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
