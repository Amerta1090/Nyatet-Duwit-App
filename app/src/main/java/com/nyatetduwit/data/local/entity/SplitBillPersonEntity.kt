package com.nyatetduwit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "split_bill_persons",
    foreignKeys = [
        ForeignKey(
            entity = SplitBillEntity::class,
            parentColumns = ["id"],
            childColumns = ["split_bill_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("split_bill_id")],
)
data class SplitBillPersonEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "split_bill_id")
    val splitBillId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "amount")
    val amount: Long,

    @ColumnInfo(name = "is_paid")
    val isPaid: Boolean = false,
)
