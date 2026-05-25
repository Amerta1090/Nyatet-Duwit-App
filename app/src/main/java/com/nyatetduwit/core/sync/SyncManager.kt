package com.nyatetduwit.core.sync

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

interface SyncProvider {
    suspend fun upload(data: ByteArray): Result<Unit>
    suspend fun download(): Result<ByteArray>
}

class LocalSyncProvider @Inject constructor() : SyncProvider {
    override suspend fun upload(data: ByteArray): Result<Unit> {
        return Result.failure(NotImplementedError("Cloud sync requires a backend provider"))
    }

    override suspend fun download(): Result<ByteArray> {
        return Result.failure(NotImplementedError("Cloud sync requires a backend provider"))
    }
}

@Singleton
class SyncManager @Inject constructor(
    private val syncProvider: SyncProvider,
) {
    var config: SyncConfig = SyncConfig()

    suspend fun syncAll(): Result<SyncResult> {
        if (!config.enabled) return Result.failure(IllegalStateException("Sync is disabled"))

        return try {
            val localData = prepareLocalData()
            val uploadResult = syncProvider.upload(localData)
            if (uploadResult.isFailure) {
                return Result.failure(uploadResult.exceptionOrNull() ?: Exception("Upload failed"))
            }
            Result.success(SyncResult(syncedItems = 0, failedItems = 0))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun forceSync(): Result<SyncResult> {
        return syncAll()
    }

    private suspend fun prepareLocalData(): ByteArray {
        return "nyatetduwit_sync_data".toByteArray()
    }
}

data class SyncResult(
    val syncedItems: Int,
    val failedItems: Int,
    val lastSyncTimestamp: Long = System.currentTimeMillis(),
)
