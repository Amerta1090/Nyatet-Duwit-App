package com.nyatetduwit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "amount")
    val amount: Long,

    @ColumnInfo(name = "account_id")
    val accountId: String,

    @ColumnInfo(name = "category_id")
    val categoryId: String?,

    @ColumnInfo(name = "to_account_id")
    val toAccountId: String?,

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "date_time")
    val dateTime: Long,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false,

    @ColumnInfo(name = "deleted_at")
    val deletedAt: Long? = null,
)
