package jp.shoma.calculator.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.shoma.calculator.R
import jp.shoma.calculator.databinding.ActivityCalcBinding
import jp.shoma.calculator.model.CalcViewModel

class CalcActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityCalcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calc)
        mBinding.viewModel = CalcViewModel()
    }
}
