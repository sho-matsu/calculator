package jp.shoma.calculator.model

import android.content.Context
import android.databinding.ObservableField
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import jp.shoma.calculator.R
import jp.shoma.calculator.enums.Operator
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by sho on 2018/03/05.
 */
class CalcViewModel(private val context: Context) {

    companion object {
        val TAG: String = this::class.java.name
        private const val MAX_DIGIT = 10
    }

    var preResult = ObservableField("0")
    var result = ObservableField("0")
    private var operator = Operator.NONE
    private val dotFlg = AtomicBoolean(false)
    private var alreadyCalcValue = 0.0
    private var nowInputValue = ""
    private var nowInputValueInt = 0
    private var nowInputValueFloat = 0.0

    fun onButtonPressed(view: View) {
        val textView = view as TextView
        val pressedButton = textView.text.toString()
        Log.d(TAG, "pressed : $pressedButton")

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
                setNum(pressedButton)
                val num = pressedButton.toInt()
            }
            context.getString(R.string.button_plus),
            context.getString(R.string.button_minus),
            context.getString(R.string.button_multiply),
            context.getString(R.string.button_divide) -> {
                setOperator(pressedButton)
            }
            context.getString(R.string.button_equal) -> {
                calculation()
                operator = Operator.NONE
                result.set(alreadyCalcValue.toString())
            }
            context.getString(R.string.button_dot) -> {
                setDot(pressedButton)
            }
            context.getString(R.string.button_delete) -> {
                if (result.get().isNotEmpty()) {
                    // 1文字だけ消す
                    val str = if (result.get().length == 1) {
                        "0"
                    } else {
                        result.get().substring(0, result.get().length - 1)
                    }
                    result.set(str)
                }
            }
            context.getString(R.string.button_clear) -> {
                result.set("0")
                preResult.set("0")
                operator = Operator.NONE
                dotFlg.set(false)
                alreadyCalcValue = 0.0
                nowInputValueInt = 0
                nowInputValue = ""
            }
            else -> {
                // NOP
            }
        }
    }

    /**
     * 数字の入力を画面に反映
     *
     * @param input 入力文字
     */
    private fun setNum(input: String) {
        if (result.get() == "0") {
            nowInputValue = input
            result.set(input)
        } else {
            nowInputValue += input
            result.set(result.get() + input)
        }
    }

    /**
     * 算術演算子の入力を画面に反映
     *
     * @param input 入力文字
     */
    private fun setOperator(input: String) {
        val inputOperator = Operator.valueTypeOf(input)
        if (inputOperator == operator) return
        calculation()
        operator = inputOperator

        val last = result.get().substring(result.get().length -1)
        // 直前の入力が演算子だった場合、置き換える
        if (TextUtils.equals(context.getString(R.string.button_plus), last)
                || TextUtils.equals(context.getString(R.string.button_minus), last)
                || TextUtils.equals(context.getString(R.string.button_multiply), last)
                || TextUtils.equals(context.getString(R.string.button_divide), last)) {
            val inputValue = result.get().substring(0, result.get().length -1)
            result.set(inputValue + input)
        } else {
            result.set(result.get() + input)
        }
    }

    /**
     * 浮動小数点の入力を画面に反映
     *
     * @param input 入力文字
     */
    private fun setDot(input: String) {
        if (result.get().contains(input)) return
        dotFlg.set(true)
        nowInputValue += input
        result.set(result.get() + input)
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
}