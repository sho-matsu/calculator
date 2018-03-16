package jp.shoma.calculator.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import jp.shoma.calculator.constant.IntentConst

/**
 * Created by sho on 2018/03/15.
 */
class IntentUtil {
    companion object {
        fun getWidgetButtonClickIntent(context: Context, label: String): PendingIntent?
                = PendingIntent.getBroadcast(context, 0,
                Intent().apply {
                    action = IntentConst.UPDATE_WIDGET
                    putExtra(IntentConst.PRESSED_BUTTON_LABEL, label)
                }, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}