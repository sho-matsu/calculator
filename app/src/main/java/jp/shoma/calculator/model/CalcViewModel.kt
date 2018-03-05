package jp.shoma.calculator.model

import android.databinding.ObservableField
import android.util.Log
import android.view.View

/**
 * Created by sho on 2018/03/05.
 */
class CalcViewModel {
    var preResult = ObservableField("0")
    var result = ObservableField("0")

    fun onNumButtonPressed(view: View) {
        // todo タップした数字を別な方法で伝搬したい
        val num = view.tag as? Int
        Log.d("CalcViewModel", "onNumButtonPressed : $num")
    }

    @Suppress("UNUSED_PARAMETER")
    fun onOperatorButtonPressed(view: View) {
        // todo タップした符号を別な方法で伝搬したい
        val operator = view.tag as? String
        Log.d("CalcViewModel", "onOperatorButtonPressed : $operator")
    }

    @Suppress("UNUSED_PARAMETER")
    fun onDeleteButtonPressed(view: View) {
        Log.d("CalcViewModel", "onDeleteButtonPressed")
    }

    fun onCommitButtonPressed() {
        Log.d("CalcViewModel", "onCommitButtonPressed")
    }
}