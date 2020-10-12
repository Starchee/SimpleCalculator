package com.starchee.simplecalculator.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.forEach
import com.starchee.simplecalculator.R
import com.starchee.simplecalculator.domain.Converter
import com.starchee.simplecalculator.domain.MathExpressionCalculator
import com.starchee.simplecalculator.domain.Validator
import com.starchee.simplecalculator.presenters.CalculatorPresenter
import com.starchee.simplecalculator.view.CalculatorView
import kotlinx.android.synthetic.main.activity_calculator.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CalculatorActivity : MvpAppCompatActivity(), CalculatorView, View.OnClickListener {

    @InjectPresenter
    lateinit var calculatorPresenter: CalculatorPresenter

    @ProvidePresenter
    fun provideCalculatorPresenter()
            = CalculatorPresenter(MathExpressionCalculator(Validator(), Converter()))

    companion object {
        private val TAG = CalculatorActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        init()
    }

    private fun init() {
        calculator_grid_layout.forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.calculator_btn_clr -> calculatorPresenter.clear()
            R.id.calculator_btn_del -> calculatorPresenter.delete()
            R.id.calculator_btn_equals -> calculatorPresenter.calculate()
            else -> calculatorPresenter.set(token = (view as Button).text.toString())
        }
    }

    override fun setExpression(expression: String) {
        Log.d(TAG, "setExpression()")
        calculator_tv_expression.text = expression
    }

    override fun setAnswer(answer: String) {
        Log.d(TAG, "setAnswer()")
        calculator_tv_answer.text = answer
    }

    override fun clearDisplay() {
        Log.d(TAG, "clearDisplay()")
        calculator_tv_expression.text = ""
        calculator_tv_answer.text = ""
    }

    override fun showError(error: Int) {
        Log.d(TAG, "showError()")
        Toast.makeText(applicationContext, getString(error), Toast.LENGTH_SHORT).show()
    }
}