package com.starchee.simplecalculator.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CalculatorView: MvpView{

    fun setExpression(expression: String)
    fun setAnswer(answer: String)
    @StateStrategyType(value = SingleStateStrategy::class)
    fun clearDisplay()
    @StateStrategyType(value = SkipStrategy::class)
    fun showError(error : Int)
}