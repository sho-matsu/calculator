package jp.shoma.calculator.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.RemoteViews
import jp.shoma.calculator.R
import jp.shoma.calculator.constant.IntentConst
import jp.shoma.calculator.enums.Operator
import jp.shoma.calculator.util.IntentUtil
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by sho on 2018/03/15.
 */
class CalcWidgetIntentReceiver : BroadcastReceiver() {

    private var preResult = "0"
    // todo メンバで持つと処理終了後に解放されるので永続化する
    private var result = "0"
    private var operator = Operator.NONE
    private val dotFlg = AtomicBoolean(false)
    private var alreadyCalcValue = 0.0
    private var nowInputValue = ""
    private var nowInputValueInt = 0

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == IntentConst.UPDATE_WIDGET) {
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_calc)
            val pressedButton = intent.getStringExtra(IntentConst.PRESSED_BUTTON_LABEL) ?: ""
            var resId = 0
            when (pressedButton) {
                context.getString(R.string.button_0),
                context.getString(R.string.button_1),
                context.getString(R.string.button_2),
                context.getString(R.string.button_3),
                context.getString(R.string.button_4),
                context.getString(R.string.button_5),
                context.getString(R.string.button_6),
                context.getString(R.string.button_7),
                context.getString(R.string.button_8),
                context.getString(R.string.button_9) -> {
                    setNum(context, pressedButton)
                    val num = pressedButton.toInt()
                    resId = context.resources.getIdentifier("button_" + pressedButton, "id", context.packageName)
                }
                context.getString(R.string.button_plus),
                context.getString(R.string.button_minus),
                context.getString(R.string.button_multiply),
                context.getString(R.string.button_divide) -> {
                    setOperator(context, pressedButton)
                    resId =
                    when (operator) {
                        Operator.PLUS -> R.id.button_plus
                        Operator.MINUS -> R.id.button_minus
                        Operator.MULTIPLY -> R.id.button_multiply
                        Operator.DIVIDE -> R.id.button_divide
                        Operator.NONE -> 0
                    }
                }
                context.getString(R.string.button_equal) -> {
                    calculation()
                    operator = Operator.NONE
                    result = alreadyCalcValue.toString()
                    setResultDisplay(context)
                    resId = R.id.button_equal
                }
                context.getString(R.string.button_dot) -> {
                    setDot(context, pressedButton)
                    resId = R.id.button_dot
                }
                context.getString(R.string.button_delete) -> {
                    if (!TextUtils.isEmpty(result)) {
                        // 1文字だけ消す
                        val str = if (result.length == 1) {
                            "0"
                        } else {
                            result.substring(0, result.length - 1)
                        }
                        result = str
                        setResultDisplay(context)
                        resId = R.id.button_delete
                    }
                }
                context.getString(R.string.button_clear) -> {
                    result = "0"
                    setResultDisplay(context)
                    preResult = "0"
                    operator = Operator.NONE
                    dotFlg.set(false)
                    alreadyCalcValue = 0.0
                    nowInputValueInt = 0
                    nowInputValue = ""
                    resId = R.id.button_clear
                }
                else -> {
                    // NOP
                }
            }
            // リスナーの再登録
            remoteViews.setOnClickPendingIntent(resId, IntentUtil.getWidgetButtonClickIntent(context, pressedButton))
        }
    }

    /**
     * 数字の入力を画面に反映
     *
     * @param input 入力文字
     */
    private fun setNum(context: Context, input: String) {
        if (result == "0") {
            nowInputValue = input
            result = input
        } else {
            nowInputValue += input
            result += input
        }
        setResultDisplay(context)
    }

    /**
     * 算術演算子の入力を画面に反映
     *
     * @param input 入力文字
     */
    private fun setOperator(context: Context, input: String) {
        val inputOperator = Operator.valueTypeOf(input)
        if (inputOperator == operator) return
        calculation()
        operator = inputOperator

        val last = result.substring(result.length -1)
        // 直前の入力が演算子だった場合、置き換える
        if (TextUtils.equals(context.getString(R.string.button_plus), last)
                || TextUtils.equals(context.getString(R.string.button_minus), last)
                || TextUtils.equals(context.getString(R.string.button_multiply), last)
                || TextUtils.equals(context.getString(R.string.button_divide), last)) {
            val inputValue = result.substring(0, result.length -1)
            result = inputValue + input
        } else {
            result += input
        }
        setResultDisplay(context)
    }

    /**
     * 浮動小数点の入力を画面に反映
     *
     * @param input 入力文字
     */
    private fun setDot(context: Context, input: String) {
        if (result.contains(input)) return
        dotFlg.set(true)
        nowInputValue += input
        result += input
        setResultDisplay(context)
    }

    /**
     * 計算処理
     */
    private fun calculation() {
        if (TextUtils.isEmpty(nowInputValue)) return
        when (operator) {
            Operator.PLUS -> {
                alreadyCalcValue += nowInputValue.toDouble()
            }
            Operator.MINUS -> {
                alreadyCalcValue -= nowInputValue.toDouble()
            }
            Operator.MULTIPLY -> {
                alreadyCalcValue *= nowInputValue.toDouble()
            }
            Operator.DIVIDE -> {
                alreadyCalcValue /= nowInputValue.toDouble()
            }
            Operator.NONE -> {
                alreadyCalcValue = nowInputValue.toDouble()
            }
        }
        nowInputValue = ""
    }

    private fun setResultDisplay(context: Context) {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_calc)
        remoteViews.setTextViewText(R.id.result, result)
        // todo 最後に入力された文字にフォーカスする方法
//        remoteViews.setScrollPosition(R.id.result, result.length -1)
        val componentName = ComponentName(context, CalcWidget::class.java)
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews)
    }
}