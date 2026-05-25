package com.nyatetduwit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "target_amount")
    val targetAmount: Long,

    @ColumnInfo(name = "current_amount")
    val currentAmount: Long = 0L,

    @ColumnInfo(name = "deadline")
    val deadline: Long? = null,

    @ColumnInfo(name = "icon")
    val icon: String = "flag",

    @ColumnInfo(name = "color")
    val color: String = "#1A5C53",

    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),
)
