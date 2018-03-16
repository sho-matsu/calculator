package jp.shoma.calculator.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import jp.shoma.calculator.R
import jp.shoma.calculator.util.IntentUtil


/**
 * Implementation of App Widget functionality.
 */
class CalcWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId)
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_calc)
            remoteViews.setOnClickPendingIntent(R.id.button_clear, onButtonPressed(context, context.getString(R.string.button_clear)))
            remoteViews.setOnClickPendingIntent(R.id.button_delete, onButtonPressed(context, context.getString(R.string.button_delete)))
            remoteViews.setOnClickPendingIntent(R.id.button_plus, onButtonPressed(context, context.getString(R.string.button_plus)))
            remoteViews.setOnClickPendingIntent(R.id.button_minus, onButtonPressed(context, context.getString(R.string.button_minus)))
            remoteViews.setOnClickPendingIntent(R.id.button_multiply, onButtonPressed(context, context.getString(R.string.button_multiply)))
            remoteViews.setOnClickPendingIntent(R.id.button_divide, onButtonPressed(context, context.getString(R.string.button_divide)))
            remoteViews.setOnClickPendingIntent(R.id.button_equal, onButtonPressed(context, context.getString(R.string.button_equal)))
            remoteViews.setOnClickPendingIntent(R.id.button_dot, onButtonPressed(context, context.getString(R.string.button_dot)))
            remoteViews.setOnClickPendingIntent(R.id.button_0, onButtonPressed(context, context.getString(R.string.button_0)))
            remoteViews.setOnClickPendingIntent(R.id.button_1, onButtonPressed(context, context.getString(R.string.button_1)))
            remoteViews.setOnClickPendingIntent(R.id.button_2, onButtonPressed(context, context.getString(R.string.button_2)))
            remoteViews.setOnClickPendingIntent(R.id.button_3, onButtonPressed(context, context.getString(R.string.button_3)))
            remoteViews.setOnClickPendingIntent(R.id.button_4, onButtonPressed(context, context.getString(R.string.button_4)))
            remoteViews.setOnClickPendingIntent(R.id.button_5, onButtonPressed(context, context.getString(R.string.button_5)))
            remoteViews.setOnClickPendingIntent(R.id.button_6, onButtonPressed(context, context.getString(R.string.button_6)))
            remoteViews.setOnClickPendingIntent(R.id.button_7, onButtonPressed(context, context.getString(R.string.button_7)))
            remoteViews.setOnClickPendingIntent(R.id.button_8, onButtonPressed(context, context.getString(R.string.button_8)))
            remoteViews.setOnClickPendingIntent(R.id.button_9, onButtonPressed(context, context.getString(R.string.button_9)))
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun onButtonPressed(context: Context, label: String)
            = IntentUtil.getWidgetButtonClickIntent(context, label)

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

//            val widgetText = context.getString(R.string.appwidget_text)
//            // Construct the RemoteViews object
//            val views = RemoteViews(context.packageName, R.layout.widget_calc)
//            views.setTextViewText(R.id.appwidget_text, widgetText)
//
//            // Instruct the widget manager to update the widget
//            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

