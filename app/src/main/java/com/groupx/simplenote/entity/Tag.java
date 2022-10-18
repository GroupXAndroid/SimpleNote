package com.groupx.simplenote.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Tag implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tagName;
    private String color;
}
