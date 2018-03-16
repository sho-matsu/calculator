package jp.shoma.calculator.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import jp.shoma.calculator.R
import jp.shoma.calculator.databinding.ActivityCalcBinding
import jp.shoma.calculator.model.CalcViewModel

class CalcActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityCalcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calc)
        mBinding.viewModel = CalcViewModel(this)
        mBinding.result.apply {
            // キーボードを無効化
            keyListener = null
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // 文字が入力されたあともフォーカスが一番最後にくるようにする
                    setSelection(length())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // NOP
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // NOP
                }

            })
        }
    }
}
