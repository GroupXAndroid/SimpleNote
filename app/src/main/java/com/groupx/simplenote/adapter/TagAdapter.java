package com.groupx.simplenote.adapter;

import android.app.AlertDialog;
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
import com.groupx.simplenote.activity.TagActivity;
import com.groupx.simplenote.entity.Tag;
import com.groupx.simplenote.fragment.EditTagFragment;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private final List<Tag> tagList;
    private final TagActivity activity;

    public TagAdapter(List<Tag> tagList, TagActivity activity) {
        this.tagList = tagList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_tag,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.setTag(tagList.get(position));
        holder.imageViewTagOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupMenuTag(holder, holder.getBindingAdapterPosition(), v.getContext());
            }
        });
    }

    private void initPopupMenuTag(@NonNull TagViewHolder holder, int position, Context context) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, holder.imageViewTagOption);
        //inflating menu from xml resource
        popup.inflate(R.menu.option_edit_tag);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuTagEdit:
                        //handle edit click
                        EditTagFragment editFragment = new EditTagFragment();
                        activity.setEditTagFragment(editFragment);
                        Bundle args = new  Bundle();
                        args.putBoolean("isEditing", true);
                        args.putSerializable("editTag", tagList.get(position));

                        editFragment.setArguments(args);
                        editFragment.show(((FragmentActivity) context).getSupportFragmentManager(),
                                "editTagFragment");
                        break;
                    case R.id.menuTagDelete:
                        //handle menu2 click
                        new AlertDialog.Builder(context)
                                .setIcon(android.R.drawable.ic_delete)
                                .setTitle("Delete Tag")
                                .setMessage("Are you sure you want to delete?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                        activity.deleteTag(tagList.get(position));
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
        return tagList.size();
    }

    static class TagViewHolder extends RecyclerView.ViewHolder {
        CardView viewTagContainer;
        TextView textViewTagName;
        ImageView imageViewTagOption;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            viewTagContainer = itemView.findViewById(R.id.viewTagContainer);
            textViewTagName = itemView.findViewById(R.id.textViewTagName);
            imageViewTagOption = itemView.findViewById(R.id.imageViewTagEditOption);
        }

        void setTag(Tag tag) {
            textViewTagName.setText(tag.getTagName());

            if (tag.getColor() != null && !tag.getColor().trim().isEmpty()) {
                viewTagContainer.setCardBackgroundColor(
                        Color.parseColor(tag.getColor())
                );
            }
        }
    }
}
