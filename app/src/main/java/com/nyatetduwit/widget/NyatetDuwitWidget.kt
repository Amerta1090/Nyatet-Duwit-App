package com.nyatetduwit.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nyatetduwit.MainActivity
import com.nyatetduwit.R
import com.nyatetduwit.data.local.NyatetDuwitDatabase
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.runBlocking

class NyatetDuwitWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_small)

            val openIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val openPendingIntent = PendingIntent.getActivity(
                context, 0, openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            views.setOnClickPendingIntent(R.id.widget_title, openPendingIntent)
            views.setOnClickPendingIntent(R.id.widget_balance, openPendingIntent)

            try {
                val db = NyatetDuwitDatabase.getInstance(context)
                val balance = runBlocking {
                    db.accountDao().getAllAccountsSync().sumOf { it.balance }
                }
                val nf = NumberFormat.getNumberInstance(Locale("id", "ID"))
                views.setTextViewText(R.id.widget_balance, "Rp ${nf.format(balance)}")
                views.setViewVisibility(R.id.widget_empty, android.view.View.GONE)
            } catch (e: Exception) {
                views.setTextViewText(R.id.widget_balance, "Rp 0")
                views.setViewVisibility(R.id.widget_empty, android.view.View.VISIBLE)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
