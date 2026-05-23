package com.nyatetduwit.core.worker

import android.content.Context
import android.os.Environment
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nyatetduwit.data.local.ExportManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltWorker
class AutoBackupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val exportManager: ExportManager,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("id"))
            val fileName = "nyatetduwit_backup_${dateFormat.format(Date())}.ndb"
            val backupDir = File(
                applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                "NyatetDuwitBackups"
            )
            backupDir.mkdirs()
            val backupFile = File(backupDir, fileName)
            val uri = android.net.Uri.fromFile(backupFile)

            exportManager.createBackup(applicationContext, uri, null)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
