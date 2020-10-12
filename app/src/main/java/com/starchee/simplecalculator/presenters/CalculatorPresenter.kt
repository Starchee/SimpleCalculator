package com.starchee.simplecalculator.presenters

import android.util.Log
import com.starchee.simplecalculator.R
import com.starchee.simplecalculator.domain.InvalidMathExpressionException
import com.starchee.simplecalculator.domain.MathExpressionCalculator
import com.starchee.simplecalculator.view.CalculatorView
import moxy.InjectViewState
import moxy.MvpPresenter
import java.lang.Exception

@InjectViewState
class CalculatorPresenter constructor(
    private val mathExpressionCalculator: MathExpressionCalculator): MvpPresenter<CalculatorView>() {

    private var expression = StringBuilder()
    lateinit var maxTextLength: String // надо разобраться

    companion object {
        private val TAG = CalculatorPresenter::class.simpleName
    }

    fun calculate(){
        try {
            val answer = mathExpressionCalculator.calculate(expression.toString())
            viewState.setAnswer(answer = answer)
        } catch (e: InvalidMathExpressionException) {
            viewState.showError(R.string.invalid_expression_error)
            Log.d(TAG, e.toString())
        } catch (e: Exception){
            viewState.showError(R.string.invalid_expression_fatal)
        }
    }

    fun set(token: String){
       expression.append(token)
        viewState.setExpression(expression = expression.toString())
    }

    fun delete(){
        if (expression.isNotEmpty()){
            expression.deleteAt(expression.length - 1)
            viewState.setExpression(expression = expression.toString())
        }
    }

    fun clear(){
        expression.clear()
        viewState.clearDisplay()
    }
}