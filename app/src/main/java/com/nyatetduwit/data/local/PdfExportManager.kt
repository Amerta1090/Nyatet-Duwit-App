package com.nyatetduwit.data.local

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.nyatetduwit.data.local.dao.TransactionDao
import com.nyatetduwit.data.local.entity.TransactionEntity
import com.nyatetduwit.core.util.formatRupiah
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class PdfExportManager @Inject constructor(
    private val transactionDao: TransactionDao,
) {

    suspend fun exportPdf(
        context: Context,
        uri: Uri,
        monthLabel: String,
        startDate: Long,
        endDate: Long,
    ): Result<Unit> = runCatching {
        val transactions = transactionDao.getAllTransactionsSync()
            .filter { !it.isDeleted && it.dateTime in startDate..endDate }

        val document = PdfDocument()

        fun createPage(pageNumber: Int): Pair<PdfDocument.Page, Canvas> {
            val info = PdfDocument.PageInfo.Builder(595, 842, pageNumber).create()
            val p = document.startPage(info)
            return p to p.canvas
        }

        var currentResult = createPage(1)
        var page = currentResult.first
        var canvas = currentResult.second

        val titlePaint = Paint().apply {
            textSize = 24f
            isFakeBoldText = true
            color = android.graphics.Color.parseColor("#1A5C53")
        }
        val headerPaint = Paint().apply {
            textSize = 14f
            isFakeBoldText = true
            color = android.graphics.Color.parseColor("#282D2C")
        }
        val bodyPaint = Paint().apply {
            textSize = 12f
            color = android.graphics.Color.parseColor("#3D4241")
        }
        val linePaint = Paint().apply {
            color = android.graphics.Color.parseColor("#E4E6E5")
            strokeWidth = 1f
        }

        var y = 50f
        val leftMargin = 40f

        canvas.drawText("Laporan Keuangan", leftMargin, y, titlePaint)
        y += 30f
        canvas.drawText(monthLabel, leftMargin, y, headerPaint)
        y += 25f

        canvas.drawLine(leftMargin, y, 555f, y, linePaint)
        y += 20f

        val dateFormat = SimpleDateFormat("d MMM yyyy", Locale("id"))

        val totalIncome = transactions.filter { it.type == "income" }.sumOf { it.amount }
        val totalExpense = transactions.filter { it.type == "expense" }.sumOf { it.amount }

        canvas.drawText("Total Pemasukan: ${formatRupiah(totalIncome)}", leftMargin, y, bodyPaint)
        y += 18f
        canvas.drawText("Total Pengeluaran: ${formatRupiah(totalExpense)}", leftMargin, y, bodyPaint)
        y += 18f
        canvas.drawText("Jumlah Transaksi: ${transactions.size}", leftMargin, y, bodyPaint)
        y += 25f

        canvas.drawLine(leftMargin, y, 555f, y, linePaint)
        y += 20f

        canvas.drawText("Detail Transaksi", leftMargin, y, headerPaint)
        y += 25f

        val col1 = leftMargin
        val col2 = leftMargin + 200f
        val col3 = leftMargin + 320f
        val col4 = leftMargin + 420f

        canvas.drawText("Tanggal", col1, y, headerPaint)
        canvas.drawText("Tipe", col2, y, headerPaint)
        canvas.drawText("Nominal", col3, y, headerPaint)
        canvas.drawText("Kategori", col4, y, headerPaint)
        y += 5f
        canvas.drawLine(leftMargin, y, 555f, y, linePaint)
        y += 15f

        for (t in transactions) {
            val dateStr = dateFormat.format(Date(t.dateTime))
            val typeLabel = when (t.type) {
                "income" -> "Income"
                "expense" -> "Expense"
                "transfer" -> "Transfer"
                else -> t.type
            }
            val nominal = formatRupiah(t.amount)

            canvas.drawText(dateStr, col1, y, bodyPaint)
            canvas.drawText(typeLabel, col2, y, bodyPaint)
            canvas.drawText(nominal, col3, y, bodyPaint)
            canvas.drawText(t.categoryId ?: "-", col4, y, bodyPaint)
            y += 18f

            if (y > 800f) {
                document.finishPage(page)
                val result = createPage(document.pages.size + 1)
                page = result.first
                canvas = result.second
                y = 50f
            }
        }

        if (document.pages.isNotEmpty() && document.pages.last() != page) {
            document.finishPage(page)
        }

        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            document.writeTo(outputStream)
        } ?: throw Exception("Tidak dapat menyimpan file PDF")

        document.close()
    }
}
