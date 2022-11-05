package com.groupx.simplenote.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.CreateNoteActivity;
import com.groupx.simplenote.entity.Tag;
import com.groupx.simplenote.fragment.AddTagFragment;

import java.util.List;

public class AddTagAdapter extends RecyclerView.Adapter<AddTagAdapter.AddTagViewHolder> {

    List<Tag> tagList;
    CreateNoteActivity activity;
    AddTagFragment fragment;

    public AddTagAdapter(List<Tag> tagList, CreateNoteActivity activity, AddTagFragment fragment) {
        this.tagList = tagList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AddTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddTagViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_container_tag_addtag_notedetail,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull AddTagViewHolder holder, int position) {
        Tag eachTag = tagList.get(position);
        holder.setTag(eachTag);
        if(activity.getTagIdSet().contains(eachTag.getId())){
            holder.setSelectedTag(activity.getApplicationContext());
        }

        holder.layoutAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.getTagIdSet().contains(eachTag.getId())) {
                    activity.getTagIdSet().remove(eachTag.getId());
                }else {
                    activity.getTagIdSet().add(eachTag.getId());
                }
                fragment.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    static class AddTagViewHolder extends RecyclerView.ViewHolder {

        TextView viewTagName;
        ImageView viewTagIcon;
        LinearLayout layoutAddTag;

        public AddTagViewHolder(@NonNull View itemView) {
            super(itemView);
            viewTagName = itemView.findViewById(R.id.textviewTagName);
            viewTagIcon = itemView.findViewById(R.id.imageViewTagIconAddTag);
            layoutAddTag = itemView.findViewById(R.id.layoutAddTag);
        }

        void setTag(Tag tag) {
            if (tag.getColor() != null || !tag.getColor().isEmpty()) {
                viewTagIcon.setColorFilter(Color.parseColor(tag.getColor()));
            }
            viewTagName.setText(tag.getTagName());
        }

        void setSelectedTag(Context context){
            layoutAddTag.setBackgroundColor(context.getColor(R.color.selectedItem));
        }
    }
}
