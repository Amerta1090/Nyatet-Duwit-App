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
    private val metadataPatterns = mutableMapOf<String, MutableMap<String, Int>>()

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
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val isWeekend = dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY

        val suggestions = mutableListOf<Pair<Category, Pair<Int, String>>>()

        if (type == TransactionType.EXPENSE) {
            when {
                hour in 5..10 -> {
                    categories.find { it.name.contains("Makan", ignoreCase = true) }
                        ?.let { suggestions.add(it to (60 to "Jam sarapan")) }
                }
                hour in 11..14 -> {
                    categories.find { it.name.contains("Makan", ignoreCase = true) }
                        ?.let { suggestions.add(it to (70 to "Jam makan siang")) }
                }
                hour in 17..21 -> {
                    categories.find { it.name.contains("Makan", ignoreCase = true) }
                        ?.let { suggestions.add(it to (65 to "Jam makan malam")) }
                }
            }

            if (isWeekend) {
                categories.find { it.name.contains("Hiburan", ignoreCase = true) }
                    ?.let { suggestions.add(it to (35 to "Akhir pekan")) }
            }

            when {
                amountRounded in 5_000..25_000 -> {
                    categories.find { it.name.contains("Makan", ignoreCase = true) || it.name.contains("Minuman", ignoreCase = true) }
                        ?.let { suggestions.add(it to (50 to "Nominal kecil")) }
                }
                amountRounded in 50_000..150_000 -> {
                    categories.find { it.name.contains("Belanja", ignoreCase = true) || it.name.contains("Transport", ignoreCase = true) }
                        ?.let { suggestions.add(it to (40 to "Nominal sedang")) }
                }
                amountRounded >= 500_000 -> {
                    categories.find { it.name.contains("Tagihan", ignoreCase = true) || it.name.contains("Belanja", ignoreCase = true) }
                        ?.let { suggestions.add(it to (30 to "Nominal besar")) }
                }
            }

            val keywordMap = listOf(
                "makan" to "Makan", "kopi" to "Makan", "nasi" to "Makan", "nasgor" to "Makan",
                "mie" to "Makan", "ayam" to "Makan", "bakso" to "Makan", "soto" to "Makan",
                "gojek" to "Transport", "grab" to "Transport", "go-car" to "Transport",
                "go ride" to "Transport", "bensin" to "Transport", "taxi" to "Transport",
                "transport" to "Transport", "transjakarta" to "Transport", "krl" to "Transport",
                "belanja" to "Belanja", "market" to "Belanja", "supermarket" to "Belanja",
                "indomaret" to "Belanja", "alfamart" to "Belanja", "minimarket" to "Belanja",
                "listrik" to "Tagihan", "air" to "Tagihan", "internet" to "Tagihan", "pdam" to "Tagihan",
                "telkom" to "Tagihan", "pln" to "Tagihan", "bpjs" to "Tagihan",
                "sewa" to "Rumah", "kos" to "Rumah", "rent" to "Rumah", "kontrakan" to "Rumah",
                "obat" to "Kesehatan", "dokter" to "Kesehatan", "klinik" to "Kesehatan",
                "apotek" to "Kesehatan", "rumah sakit" to "Kesehatan", "rs" to "Kesehatan",
                "buku" to "Edukasi", "kursus" to "Edukasi", "les" to "Edukasi", "tutorial" to "Edukasi",
                "baju" to "Fashion", "sepatu" to "Fashion", "pakaian" to "Fashion",
                "hiburan" to "Hiburan", "nonton" to "Hiburan", "game" to "Hiburan",
                "netflix" to "Hiburan", "spotify" to "Hiburan", "bioskop" to "Hiburan",
                "pulsa" to "Tagihan", "kuota" to "Tagihan", "paket data" to "Tagihan",
                "swalayan" to "Belanja", "toko" to "Belanja",
            )

            for ((keyword, catName) in keywordMap) {
                if (notesLower.contains(keyword)) {
                    categories.find { it.name.contains(catName, ignoreCase = true) }
                        ?.let { suggestions.add(it to (88 to "Catatan: '$keyword'")) }
                }
            }
        }

        if (type == TransactionType.INCOME) {
            when {
                amountRounded in 1_000_000..10_000_000 -> {
                    categories.find { it.name.contains("Gaji", ignoreCase = true) }
                        ?.let { suggestions.add(it to (55 to "Nominal gaji")) }
                }
                amountRounded > 10_000_000 -> {
                    categories.find { it.name.contains("Gaji", ignoreCase = true) }
                        ?.let { suggestions.add(it to (40 to "Nominal besar")) }
                }
                amountRounded in 100_000..1_000_000 -> {
                    categories.find { it.name.contains("Freelance", ignoreCase = true) }
                        ?.let { suggestions.add(it to (35 to "Nominal freelance")) }
                }
            }
            val incomeKeywords = listOf(
                "gaji" to "Gaji", "salary" to "Gaji", "payroll" to "Gaji",
                "freelance" to "Freelance", "project" to "Freelance", "income" to "Freelance",
                "dividen" to "Investasi", "bunga" to "Investasi", "capital" to "Investasi",
                "hadiah" to "Hadiah", "kado" to "Hadiah", "bonus" to "Hadiah",
                "jual" to "Freelance", "komisi" to "Freelance",
            )
            for ((keyword, catName) in incomeKeywords) {
                if (notesLower.contains(keyword)) {
                    categories.find { it.name.contains(catName, ignoreCase = true) }
                        ?.let { suggestions.add(it to (88 to "Catatan: '$keyword'")) }
                }
            }
        }

        val metaKey = "$hour|$amountRounded|$dayOfWeek"
        val corrections = metadataPatterns[metaKey]
        if (corrections != null) {
            val total = corrections.values.sum().coerceAtLeast(1)
            for ((catId, count) in corrections) {
                val cat = categories.find { it.id == catId }
                if (cat != null) {
                    val learnedConfidence = (count * 100 / total).coerceIn(40, 95)
                    suggestions.add(cat to (learnedConfidence to "Pola kebiasaan"))
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
                categories.find { it.id == catId }?.let { cat ->
                    val (maxConfidence, maxReason) = info.second
                    SmartCategorySuggestion(
                        category = cat,
                        confidence = (maxConfidence + 5).coerceAtMost(99),
                        reason = maxReason,
                    )
                }
            }
            .maxByOrNull { it.confidence }
    }

    fun registerCorrection(
        type: TransactionType,
        amount: Long,
        notes: String?,
        categoryId: String,
        hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    ) {
        val amountRounded = roundToNearestThousand(amount)
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val metaKey = "$hour|$amountRounded|$dayOfWeek"

        if (notes?.isNotBlank() == true) {
            val notesKey = notes.lowercase().trim()
            if (notesKey.length in 3..30) {
                metadataPatterns
                    .getOrPut("notes:$notesKey") { mutableMapOf() }
                    .merge(categoryId, 1, Int::plus)
            }
        }

        metadataPatterns
            .getOrPut(metaKey) { mutableMapOf() }
            .merge(categoryId, 1, Int::plus)
    }

    private fun roundToNearestThousand(amount: Long): Long {
        return (amount / 1000) * 1000
    }
}

private fun <K> MutableMap<K, Int>.merge(key: K, value: Int, remappingFunction: (Int, Int) -> Int) {
    merge(key, value, remappingFunction)
}
