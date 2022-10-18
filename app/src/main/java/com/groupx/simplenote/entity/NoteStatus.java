package com.groupx.simplenote.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NoteStatus implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int statusKey;
    private String statusValue;

}
