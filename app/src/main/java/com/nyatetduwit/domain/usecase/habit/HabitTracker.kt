package com.nyatetduwit.domain.usecase.habit

import android.content.Context
import com.nyatetduwit.domain.repository.SettingsRepository
import com.nyatetduwit.domain.usecase.transaction.GetTransactionCountUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

data class StreakData(
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActiveDate: Long = 0L,
    val streakFreezeAvailable: Int = 1,
    val isFrozen: Boolean = false,
)

data class WeeklyCheckInData(
    val weekLabel: String,
    val totalExpense: Long = 0,
    val totalIncome: Long = 0,
    val transactionCount: Int = 0,
    val topCategoryName: String? = null,
    val topCategoryAmount: Long = 0,
    val vsPreviousWeekPercent: Int = 0,
    val daysActive: Int = 0,
)

data class ProgressData(
    val totalTransactions: Int = 0,
    val currentMonthExpense: Long = 0,
    val currentMonthIncome: Long = 0,
    val daysActiveThisMonth: Int = 0,
    val streak: StreakData = StreakData(),
)

data class DailyAllowance(
    val remainingDays: Int,
    val remainingBudget: Long,
    val dailyAllowance: Long,
    val isWarning: Boolean = false,
    val isDanger: Boolean = false,
)

@Singleton
class HabitTracker @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository,
) {

    companion object {
        private const val PREFS_NAME = "habit_prefs"
        private const val KEY_CURRENT_STREAK = "current_streak"
        private const val KEY_LONGEST_STREAK = "longest_streak"
        private const val KEY_LAST_ACTIVE_DATE = "last_active_date"
        private const val KEY_STREAK_FREEZE = "streak_freeze"
        private const val KEY_LAST_CHECKIN_WEEK = "last_checkin_week"
        private const val KEY_CHECKIN_SHOWN = "checkin_shown_"

        private const val MILLIS_IN_DAY = 24 * 60 * 60 * 1000L
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun updateStreak(): StreakData {
        val lastActive = prefs.getLong(KEY_LAST_ACTIVE_DATE, 0L)
        val today = normalizeDate(System.currentTimeMillis())

        if (lastActive == 0L) {
            saveStreak(1, 1, today)
            return StreakData(currentStreak = 1, longestStreak = 1, lastActiveDate = today)
        }

        val lastActiveNormalized = normalizeDate(lastActive)
        val diffDays = (today - lastActiveNormalized) / MILLIS_IN_DAY

        if (diffDays == 0L) {
            return loadStreak()
        } else if (diffDays == 1L) {
            val current = prefs.getInt(KEY_CURRENT_STREAK, 0) + 1
            val longest = maxOf(current, prefs.getInt(KEY_LONGEST_STREAK, 0))
            val freezeAvailable = if (current % 7 == 0) 1 else prefs.getInt(KEY_STREAK_FREEZE, 1)
            saveStreak(current, longest, today, freezeAvailable)
            return StreakData(currentStreak = current, longestStreak = longest, lastActiveDate = today, streakFreezeAvailable = freezeAvailable)
        } else if (diffDays == 2L && prefs.getInt(KEY_STREAK_FREEZE, 1) > 0) {
            val current = prefs.getInt(KEY_CURRENT_STREAK, 0)
            val freeze = prefs.getInt(KEY_STREAK_FREEZE, 1) - 1
            saveStreak(current, prefs.getInt(KEY_LONGEST_STREAK, 0), today, freeze)
            return loadStreak().copy(isFrozen = true)
        } else {
            saveStreak(0, prefs.getInt(KEY_LONGEST_STREAK, 0), today)
            return StreakData(currentStreak = 0, longestStreak = prefs.getInt(KEY_LONGEST_STREAK, 0), lastActiveDate = today)
        }
    }

    suspend fun loadStreak(): StreakData {
        return StreakData(
            currentStreak = prefs.getInt(KEY_CURRENT_STREAK, 0),
            longestStreak = prefs.getInt(KEY_LONGEST_STREAK, 0),
            lastActiveDate = prefs.getLong(KEY_LAST_ACTIVE_DATE, 0L),
            streakFreezeAvailable = prefs.getInt(KEY_STREAK_FREEZE, 1),
        )
    }

    fun shouldShowWeeklyCheckin(): Boolean {
        val today = Calendar.getInstance()
        val lastWeek = prefs.getInt(KEY_LAST_CHECKIN_WEEK, 0)
        val currentWeek = today.get(Calendar.WEEK_OF_YEAR) + today.get(Calendar.YEAR) * 100
        return currentWeek != lastWeek
    }

    fun markWeeklyCheckinShown() {
        val today = Calendar.getInstance()
        val currentWeek = today.get(Calendar.WEEK_OF_YEAR) + today.get(Calendar.YEAR) * 100
        prefs.edit().putInt(KEY_LAST_CHECKIN_WEEK, currentWeek).apply()
    }

    suspend fun getStreak(): StreakData = loadStreak()

    private fun saveStreak(current: Int, longest: Int, lastActive: Long, freezeAvailable: Int = 1) {
        prefs.edit()
            .putInt(KEY_CURRENT_STREAK, current)
            .putInt(KEY_LONGEST_STREAK, longest)
            .putLong(KEY_LAST_ACTIVE_DATE, lastActive)
            .putInt(KEY_STREAK_FREEZE, freezeAvailable)
            .apply()
    }

    private fun normalizeDate(timestamp: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun getAntiDropOffMessage(inactiveDays: Int): String? {
        return when {
            inactiveDays == 3 -> "Kangen nih, belum nyatet 3 hari"
            inactiveDays == 7 -> "Mau lanjutin streak? Kamu udah sampe ${prefs.getInt(KEY_LONGEST_STREAK, 0)} hari lho"
            inactiveDays == 14 -> "Gapapa kok break, mau mulai lagi? Data kamu masih aman"
            inactiveDays == 30 -> "Welcome back! Yuk lihat ringkasan terakhir kamu..."
            else -> null
        }
    }

    suspend fun getDailyAllowance(
        totalBudget: Long,
        currentMonthExpense: Long,
    ): DailyAllowance {
        val calendar = Calendar.getInstance()
        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val remainingDays = lastDayOfMonth - currentDay
        val remainingBudget = (totalBudget - currentMonthExpense).coerceAtLeast(0)
        val daily = if (remainingDays > 0) remainingBudget / remainingDays else remainingBudget

        val ratio = if (totalBudget > 0) currentMonthExpense.toFloat() / totalBudget else 0f
        return DailyAllowance(
            remainingDays = remainingDays,
            remainingBudget = remainingBudget,
            dailyAllowance = daily,
            isWarning = ratio > 0.8f,
            isDanger = ratio > 1f,
        )
    }
}
