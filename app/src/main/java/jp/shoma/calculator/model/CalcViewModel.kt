package jp.shoma.calculator.model

import android.content.Context
import android.databinding.ObservableField
import android.util.Log
import android.view.View
import android.widget.TextView
import jp.shoma.calculator.R

/**
 * Created by sho on 2018/03/05.
 */
class CalcViewModel(private val context: Context) {

    companion object {
        val TAG: String = this::class.java.name
    }

    var preResult = ObservableField("0")
    var result = ObservableField("0")
    private var operator: Operator = Operator.NONE
    private var alreadyCalcValue = 0.0
    private var nowInputValue = 0.0

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
                setInputNum(pressedButton)
                val num = pressedButton.toInt()
            }
            context.getString(R.string.button_plus),
            context.getString(R.string.button_minus),
            context.getString(R.string.button_multiply),
            context.getString(R.string.button_divide) -> {
                setInputOperator(pressedButton)
            }
            context.getString(R.string.button_equal) -> {
                calculation()
            }
            context.getString(R.string.button_dot) -> {
                setInputDot(pressedButton)
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
                alreadyCalcValue = 0.0
                nowInputValue = 0.0
            }
            else -> {
                // NOP
            }
        }
    }

    private fun calculation() {
        when (operator) {
            Operator.PLUS -> {

            }
            Operator.MINUS -> {

            }
            Operator.MULTIPLY -> {

            }
            Operator.DIVIDE -> {

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
    private fun setInputNum(input: String) {
        if (result.get() == "0") {
            result.set(input)
        } else {
            result.set(result.get() + input)
        }
    }

    /**
     * 算術演算子の入力を画面に反映
     *
     * @param input 入力文字
     */
    private fun setInputOperator(input: String) {
        val last = result.get().substring(result.get().length -1)
        if (input == last) return
        operator = Operator.valueTypeOf(input)
        // fixme 直前の入力が演算子だった場合、置き換える
        result.set(result.get() + input)
    }

    /**
     * 浮動小数点の入力を画面に反映
     *
     * @param input 入力文字
     */
    private fun setInputDot(input: String) {
        if (result.get().contains(input)) return
        result.set(result.get() + input)
    }

    enum class Operator(val symbol: String) {
        PLUS("+"),
        MINUS("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        NONE("");

        companion object {
            fun valueTypeOf(symbol: String) =
                values().firstOrNull { it.symbol == symbol } ?: NONE
        }
    }
}