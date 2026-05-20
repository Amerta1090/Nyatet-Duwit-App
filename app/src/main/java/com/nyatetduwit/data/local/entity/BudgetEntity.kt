package com.nyatetduwit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "category_id")
    val categoryId: String?,

    @ColumnInfo(name = "amount")
    val amount: Long,

    @ColumnInfo(name = "period")
    val period: String = "monthly",

    @ColumnInfo(name = "start_date")
    val startDate: Long,

    @ColumnInfo(name = "end_date")
    val endDate: Long,

    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),
)
