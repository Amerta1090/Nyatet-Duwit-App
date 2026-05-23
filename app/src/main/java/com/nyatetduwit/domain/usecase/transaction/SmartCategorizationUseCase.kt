package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.CategoryType
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject

data class SmartCategorySuggestion(
    val category: Category,
    val confidence: Int,
    val reason: String,
)

class SmartCategorizationUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {

    suspend fun suggest(
        type: TransactionType,
        amount: Long,
        notes: String?,
        hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    ): SmartCategorySuggestion? {
        val categoryType = when (type) {
            TransactionType.EXPENSE -> CategoryType.EXPENSE
            TransactionType.INCOME -> CategoryType.INCOME
            TransactionType.TRANSFER -> return null
        }

        val categories = categoryRepository.getCategoriesByType(categoryType).first()
        if (categories.isEmpty()) return null

        val notesLower = notes?.lowercase() ?: ""
        val amountRounded = roundToNearestThousand(amount)

        val suggestions = mutableListOf<Pair<Category, Pair<Int, String>>>()

        if (type == TransactionType.EXPENSE) {
            if (hour in 5..10) {
                val cat = categories.find { it.name.contains("Makan", ignoreCase = true) }
                if (cat != null) suggestions.add(cat to (60 to "Jam sarapan"))
            }
            if (hour in 11..14) {
                val cat = categories.find { it.name.contains("Makan", ignoreCase = true) }
                if (cat != null) suggestions.add(cat to (70 to "Jam makan siang"))
            }
            if (hour in 17..21) {
                val cat = categories.find { it.name.contains("Makan", ignoreCase = true) }
                if (cat != null) suggestions.add(cat to (65 to "Jam makan malam"))
            }

            if (amountRounded in 5_000..25_000) {
                val cat = categories.find { it.name.contains("Makan", ignoreCase = true) || it.name.contains("Minuman", ignoreCase = true) }
                if (cat != null) suggestions.add(cat to (50 to "Nominal kecil"))
            }
            if (amountRounded in 50_000..150_000) {
                val cat = categories.find { it.name.contains("Belanja", ignoreCase = true) || it.name.contains("Transport", ignoreCase = true) }
                if (cat != null) suggestions.add(cat to (40 to "Nominal sedang"))
            }

            val keywordMap = listOf(
                "makan" to "Makan", "kopi" to "Makan", "nasi" to "Makan", "gojek" to "Transport",
                "grab" to "Transport", "bensin" to "Transport", "taxi" to "Transport",
                "belanja" to "Belanja", "market" to "Belanja", "supermarket" to "Belanja",
                "listrik" to "Tagihan", "air" to "Tagihan", "internet" to "Tagihan", "pdam" to "Tagihan",
                "sewa" to "Rumah", "kos" to "Rumah", "rent" to "Rumah",
                "obat" to "Kesehatan", "dokter" to "Kesehatan", "klinik" to "Kesehatan",
                "buku" to "Edukasi", "kursus" to "Edukasi", "les" to "Edukasi",
                "baju" to "Fashion", "sepatu" to "Fashion",
                "hiburan" to "Hiburan", "nonton" to "Hiburan", "game" to "Hiburan",
            )

            for ((keyword, catName) in keywordMap) {
                if (notesLower.contains(keyword)) {
                    val cat = categories.find { it.name.contains(catName, ignoreCase = true) }
                    if (cat != null) suggestions.add(cat to (85 to "Catatan: '$keyword'"))
                }
            }
        }

        if (type == TransactionType.INCOME) {
            if (amountRounded in 1_000_000..10_000_000) {
                val cat = categories.find { it.name.contains("Gaji", ignoreCase = true) }
                if (cat != null) suggestions.add(cat to (50 to "Nominal gaji"))
            }
            val incomeKeywords = listOf(
                "gaji" to "Gaji", "salary" to "Gaji", "freelance" to "Freelance",
                "project" to "Freelance", "income" to "Freelance",
                "dividen" to "Investasi", "bunga" to "Investasi",
                "hadiah" to "Hadiah", "kado" to "Hadiah",
            )
            for ((keyword, catName) in incomeKeywords) {
                if (notesLower.contains(keyword)) {
                    val cat = categories.find { it.name.contains(catName, ignoreCase = true) }
                    if (cat != null) suggestions.add(cat to (85 to "Catatan: '$keyword'"))
                }
            }
        }

        return suggestions
            .groupBy { it.first.id }
            .mapValues { (_, entries) ->
                val top = entries.maxByOrNull { it.second.first }!!
                entries.size to top.second
            }
            .mapNotNull { (catId, info) ->
                val category = categories.find { it.id == catId }
                category?.let { cat ->
                    val count = info.first
                    val (maxConfidence, maxReason) = info.second
                    SmartCategorySuggestion(
                        category = cat,
                        confidence = (maxConfidence + count * 5).coerceAtMost(99),
                        reason = maxReason,
                    )
                }
            }
            .maxByOrNull { it.confidence }
    }

    private fun roundToNearestThousand(amount: Long): Long {
        return (amount / 1000) * 1000
    }
}
