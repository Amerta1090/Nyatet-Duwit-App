package com.nyatetduwit.core.sync

interface Syncable {
    val syncStatus: SyncStatus
    val lastSyncedAt: Long?
    val version: Int
}

data class SyncConfig(
    val enabled: Boolean = false,
    val lastSyncTimestamp: Long = 0L,
    val wifiOnly: Boolean = true,
    val autoSync: Boolean = true,
)
