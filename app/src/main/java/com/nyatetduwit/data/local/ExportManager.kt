package com.nyatetduwit.data.local

import android.content.Context
import android.net.Uri
import com.nyatetduwit.data.local.dao.AccountDao
import com.nyatetduwit.data.local.dao.BudgetDao
import com.nyatetduwit.data.local.dao.CategoryDao
import com.nyatetduwit.data.local.dao.RecurringTransactionDao
import com.nyatetduwit.data.local.dao.TemplateDao
import com.nyatetduwit.data.local.dao.TransactionDao
import com.nyatetduwit.data.local.entity.AccountEntity
import com.nyatetduwit.data.local.entity.BudgetEntity
import com.nyatetduwit.data.local.entity.CategoryEntity
import com.nyatetduwit.data.local.entity.RecurringTransactionEntity
import com.nyatetduwit.data.local.entity.TemplateEntity
import com.nyatetduwit.data.local.entity.TransactionEntity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ExportManager @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val budgetDao: BudgetDao,
    private val recurringTransactionDao: RecurringTransactionDao,
    private val templateDao: TemplateDao,
) {

    suspend fun exportCsv(context: Context, uri: Uri): Result<Unit> = runCatching {
        val transactions = transactionDao.getAllTransactionsSync()
        val accounts = accountDao.getAllAccountsSync().associateBy { it.id }
        val categories = categoryDao.getAllCategoriesSync().associateBy { it.id }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale("id"))

        val csv = buildString {
            appendLine("Tanggal,Tipe,Nominal,Kategori,Akun,Catatan")
            transactions
                .filter { !it.isDeleted }
                .forEach { t ->
                    val account = accounts[t.accountId]
                    val category = t.categoryId?.let { categories[it] }
                    val dateStr = dateFormat.format(Date(t.dateTime))
                    val typeLabel = when (t.type) {
                        "income" -> "Pemasukan"
                        "expense" -> "Pengeluaran"
                        "transfer" -> "Transfer"
                        else -> t.type
                    }
                    val nominal = t.amount.toString()
                    val categoryName = category?.name
                        ?: if (t.type == "transfer") "Transfer" else "-"
                    val accountName = account?.name ?: t.accountId
                    val notes = (t.notes ?: "").replace("\"", "\"\"")
                    appendLine(
                        "$dateStr,$typeLabel,$nominal,$categoryName,$accountName,\"$notes\""
                    )
                }
        }

        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            outputStream.write(csv.toByteArray(Charsets.UTF_8))
        } ?: throw Exception("Tidak dapat menyimpan file")
    }

    suspend fun createBackup(
        context: Context,
        uri: Uri,
        encrypt: (suspend (String) -> String)?,
    ): Result<Unit> = runCatching {
        val backupJson = buildBackupJson()

        val finalData = if (encrypt != null) {
            "ENCRYPTED:" + encrypt(backupJson)
        } else {
            "PLAIN:" + backupJson
        }

        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            outputStream.write(finalData.toByteArray(Charsets.UTF_8))
        } ?: throw Exception("Tidak dapat menyimpan file backup")
    }

    suspend fun restoreBackup(
        context: Context,
        uri: Uri,
        decrypt: (suspend (String) -> String)?,
    ): Result<RestorePreview> = runCatching {
        val content = readBackupContent(context, uri, decrypt)
        val backup = parseBackupJson(content)

        val preview = RestorePreview(
            transactionCount = backup.transactions.size,
            accountCount = backup.accounts.size,
            categoryCount = backup.categories.size,
            createdAt = backup.timestamp,
        )

        clearAllData()
        insertBackupData(backup)

        preview
    }

    suspend fun previewBackup(
        context: Context,
        uri: Uri,
        decrypt: (suspend (String) -> String)?,
    ): Result<RestorePreview> = runCatching {
        val content = readBackupContent(context, uri, decrypt)
        val backup = parseBackupJson(content)

        RestorePreview(
            transactionCount = backup.transactions.size,
            accountCount = backup.accounts.size,
            categoryCount = backup.categories.size,
            createdAt = backup.timestamp,
        )
    }

    private suspend fun readBackupContent(
        context: Context,
        uri: Uri,
        decrypt: (suspend (String) -> String)?,
    ): String {
        val content = context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).readText()
        } ?: throw Exception("Tidak dapat membaca file backup")

        return if (content.startsWith("ENCRYPTED:")) {
            val encrypted = content.removePrefix("ENCRYPTED:")
            decrypt?.invoke(encrypted)
                ?: throw Exception("File terenkripsi. Masukkan PIN untuk membuka.")
        } else if (content.startsWith("PLAIN:")) {
            content.removePrefix("PLAIN:")
        } else {
            content
        }
    }

    private suspend fun buildBackupJson(): String {
        val accounts = accountDao.getAllAccountsSync()
        val categories = categoryDao.getAllCategoriesSync()
        val transactions = transactionDao.getAllTransactionsSync()
        val budgets = budgetDao.getAllBudgetsSync()
        val recurring = recurringTransactionDao.getAllRecurringSync()
        val templates = templateDao.getAllTemplatesSync()

        val root = JSONObject()
        root.put("version", BACKUP_VERSION)
        root.put("timestamp", System.currentTimeMillis())

        root.put("accounts", JSONArray().apply {
            accounts.forEach { put(accountToJson(it)) }
        })
        root.put("categories", JSONArray().apply {
            categories.forEach { put(categoryToJson(it)) }
        })
        root.put("transactions", JSONArray().apply {
            transactions.forEach { put(transactionToJson(it)) }
        })
        root.put("budgets", JSONArray().apply {
            budgets.forEach { put(budgetToJson(it)) }
        })
        root.put("recurring", JSONArray().apply {
            recurring.forEach { put(recurringToJson(it)) }
        })
        root.put("templates", JSONArray().apply {
            templates.forEach { put(templateToJson(it)) }
        })

        return root.toString()
    }

    private fun parseBackupJson(json: String): BackupData {
        val root = JSONObject(json)
        val version = root.optInt("version", 1)
        val timestamp = root.optLong("timestamp", System.currentTimeMillis())

        val accounts = root.getJSONArray("accounts").let { arr ->
            (0 until arr.length()).map { i ->
                jsonToAccount(arr.getJSONObject(i))
            }
        }

        val categories = root.getJSONArray("categories").let { arr ->
            (0 until arr.length()).map { i ->
                jsonToCategory(arr.getJSONObject(i))
            }
        }

        val transactions = root.getJSONArray("transactions").let { arr ->
            (0 until arr.length()).map { i ->
                jsonToTransaction(arr.getJSONObject(i))
            }
        }

        val budgets = root.optJSONArray("budgets")?.let { arr ->
            (0 until arr.length()).map { i ->
                jsonToBudget(arr.getJSONObject(i))
            }
        } ?: emptyList()

        val recurring = root.optJSONArray("recurring")?.let { arr ->
            (0 until arr.length()).map { i ->
                jsonToRecurring(arr.getJSONObject(i))
            }
        } ?: emptyList()

        val templates = root.optJSONArray("templates")?.let { arr ->
            (0 until arr.length()).map { i ->
                jsonToTemplate(arr.getJSONObject(i))
            }
        } ?: emptyList()

        return BackupData(
            version = version,
            timestamp = timestamp,
            accounts = accounts,
            categories = categories,
            transactions = transactions,
            budgets = budgets,
            recurring = recurring,
            templates = templates,
        )
    }

    private suspend fun clearAllData() {
        transactionDao.deleteAll()
        budgetDao.deleteAll()
        recurringTransactionDao.deleteAll()
        templateDao.deleteAll()
        categoryDao.deleteAll()
        accountDao.deleteAll()
    }

    private suspend fun insertBackupData(data: BackupData) {
        if (data.accounts.isNotEmpty()) accountDao.insertAll(data.accounts)
        if (data.categories.isNotEmpty()) categoryDao.insertAll(data.categories)
        if (data.transactions.isNotEmpty()) transactionDao.insertAll(data.transactions)
        if (data.budgets.isNotEmpty()) budgetDao.insertAll(data.budgets)
        if (data.recurring.isNotEmpty()) recurringTransactionDao.insertAll(data.recurring)
        if (data.templates.isNotEmpty()) templateDao.insertAll(data.templates)
    }

    private fun accountToJson(a: AccountEntity) = JSONObject().apply {
        put("id", a.id)
        put("name", a.name)
        put("type", a.type)
        put("balance", a.balance)
        put("icon", a.icon)
        put("color", a.color)
        put("is_hidden", a.isHidden)
        put("order_index", a.orderIndex)
        put("created_at", a.createdAt)
        put("updated_at", a.updatedAt)
    }

    private fun jsonToAccount(obj: JSONObject) = AccountEntity(
        id = obj.getString("id"),
        name = obj.getString("name"),
        type = obj.optString("type", "cash"),
        balance = obj.optLong("balance", 0L),
        icon = obj.optString("icon", "account_balance_wallet"),
        color = obj.optString("color", "#4F6B4E"),
        isHidden = obj.optBoolean("is_hidden", false),
        orderIndex = obj.optInt("order_index", 0),
        createdAt = obj.optLong("created_at", System.currentTimeMillis()),
        updatedAt = obj.optLong("updated_at", System.currentTimeMillis()),
    )

    private fun categoryToJson(c: CategoryEntity) = JSONObject().apply {
        put("id", c.id)
        put("name", c.name)
        put("type", c.type)
        put("icon", c.icon)
        put("color", c.color)
        put("parent_id", c.parentId ?: JSONObject.NULL)
        put("is_default", c.isDefault)
        put("order_index", c.orderIndex)
        put("created_at", c.createdAt)
    }

    private fun jsonToCategory(obj: JSONObject) = CategoryEntity(
        id = obj.getString("id"),
        name = obj.getString("name"),
        type = obj.optString("type", "expense"),
        icon = obj.optString("icon", "category"),
        color = obj.optString("color", "#4F6B4E"),
        parentId = if (obj.isNull("parent_id")) null else obj.optString("parent_id"),
        isDefault = obj.optBoolean("is_default", false),
        orderIndex = obj.optInt("order_index", 0),
        createdAt = obj.optLong("created_at", System.currentTimeMillis()),
    )

    private fun transactionToJson(t: TransactionEntity) = JSONObject().apply {
        put("id", t.id)
        put("type", t.type)
        put("amount", t.amount)
        put("account_id", t.accountId)
        put("category_id", t.categoryId ?: JSONObject.NULL as Any)
        put("to_account_id", t.toAccountId ?: JSONObject.NULL as Any)
        put("notes", t.notes ?: JSONObject.NULL as Any)
        put("date_time", t.dateTime)
        put("created_at", t.createdAt)
        put("updated_at", t.updatedAt)
        put("is_deleted", t.isDeleted)
        put("deleted_at", t.deletedAt ?: JSONObject.NULL as Any)
    }

    private fun jsonToTransaction(obj: JSONObject) = TransactionEntity(
        id = obj.getString("id"),
        type = obj.optString("type", "expense"),
        amount = obj.optLong("amount", 0L),
        accountId = obj.getString("account_id"),
        categoryId = if (obj.isNull("category_id")) null else obj.optString("category_id"),
        toAccountId = if (obj.isNull("to_account_id")) null else obj.optString("to_account_id"),
        notes = if (obj.isNull("notes")) null else obj.optString("notes"),
        dateTime = obj.optLong("date_time", System.currentTimeMillis()),
        createdAt = obj.optLong("created_at", System.currentTimeMillis()),
        updatedAt = obj.optLong("updated_at", System.currentTimeMillis()),
        isDeleted = obj.optBoolean("is_deleted", false),
        deletedAt = if (obj.isNull("deleted_at")) null else obj.optString("deleted_at").toLongOrNull(),
    )

    private fun budgetToJson(b: BudgetEntity) = JSONObject().apply {
        put("id", b.id)
        put("category_id", b.categoryId ?: JSONObject.NULL as Any)
        put("amount", b.amount)
        put("period", b.period)
        put("start_date", b.startDate)
        put("end_date", b.endDate)
        put("is_active", b.isActive)
        put("created_at", b.createdAt)
        put("updated_at", b.updatedAt)
    }

    private fun jsonToBudget(obj: JSONObject) = BudgetEntity(
        id = obj.getString("id"),
        categoryId = if (obj.isNull("category_id")) null else obj.optString("category_id"),
        amount = obj.optLong("amount", 0L),
        period = obj.optString("period", "monthly"),
        startDate = obj.optLong("start_date", System.currentTimeMillis()),
        endDate = obj.optLong("end_date", System.currentTimeMillis()),
        isActive = obj.optBoolean("is_active", true),
        createdAt = obj.optLong("created_at", System.currentTimeMillis()),
        updatedAt = obj.optLong("updated_at", System.currentTimeMillis()),
    )

    private fun recurringToJson(r: RecurringTransactionEntity) = JSONObject().apply {
        put("id", r.id)
        put("template_transaction_id", r.templateTransactionId)
        put("frequency", r.frequency)
        put("start_date", r.startDate)
        put("end_date", r.endDate ?: JSONObject.NULL as Any)
        put("next_due", r.nextDue)
        put("is_active", r.isActive)
        put("last_processed", r.lastProcessed ?: JSONObject.NULL as Any)
        put("skipped_dates", r.skippedDates)
        put("created_at", r.createdAt)
        put("updated_at", r.updatedAt)
    }

    private fun jsonToRecurring(obj: JSONObject) = RecurringTransactionEntity(
        id = obj.getString("id"),
        templateTransactionId = obj.getString("template_transaction_id"),
        frequency = obj.optString("frequency", "monthly"),
        startDate = obj.optLong("start_date", System.currentTimeMillis()),
        endDate = if (obj.isNull("end_date")) null else obj.optString("end_date").toLongOrNull(),
        nextDue = obj.optLong("next_due", System.currentTimeMillis()),
        isActive = obj.optBoolean("is_active", true),
        lastProcessed = if (obj.isNull("last_processed")) null else obj.optString("last_processed").toLongOrNull(),
        skippedDates = obj.optString("skipped_dates", ""),
        createdAt = obj.optLong("created_at", System.currentTimeMillis()),
        updatedAt = obj.optLong("updated_at", System.currentTimeMillis()),
    )

    private fun templateToJson(t: TemplateEntity) = JSONObject().apply {
        put("id", t.id)
        put("name", t.name)
        put("type", t.type)
        put("amount", t.amount)
        put("category_id", t.categoryId ?: JSONObject.NULL as Any)
        put("account_id", t.accountId ?: JSONObject.NULL as Any)
        put("notes", t.notes ?: JSONObject.NULL as Any)
        put("usage_count", t.usageCount)
        put("last_used", t.lastUsed ?: JSONObject.NULL as Any)
        put("is_pinned", t.isPinned)
        put("created_at", t.createdAt)
    }

    private fun jsonToTemplate(obj: JSONObject) = TemplateEntity(
        id = obj.getString("id"),
        name = obj.optString("name", ""),
        type = obj.optString("type", "expense"),
        amount = obj.optLong("amount", 0L),
        categoryId = if (obj.isNull("category_id")) null else obj.optString("category_id"),
        accountId = if (obj.isNull("account_id")) null else obj.optString("account_id"),
        notes = if (obj.isNull("notes")) null else obj.optString("notes"),
        usageCount = obj.optInt("usage_count", 0),
        lastUsed = if (obj.isNull("last_used")) null else obj.optString("last_used").toLongOrNull(),
        isPinned = obj.optBoolean("is_pinned", false),
        createdAt = obj.optLong("created_at", System.currentTimeMillis()),
    )

    companion object {
        private const val BACKUP_VERSION = 1
    }
}

data class RestorePreview(
    val transactionCount: Int,
    val accountCount: Int,
    val categoryCount: Int,
    val createdAt: Long,
)

data class BackupData(
    val version: Int,
    val timestamp: Long,
    val accounts: List<AccountEntity>,
    val categories: List<CategoryEntity>,
    val transactions: List<TransactionEntity>,
    val budgets: List<BudgetEntity>,
    val recurring: List<RecurringTransactionEntity>,
    val templates: List<TemplateEntity>,
)
