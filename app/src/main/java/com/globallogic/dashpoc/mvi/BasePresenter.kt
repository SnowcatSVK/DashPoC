package com.globallogic.dashpoc.mvi

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by snowcat on 8.3.2018.
 */
abstract class BasePresenter<V:MvpView, VS>: MviBasePresenter<V, VS>() {

    override fun attachView(view: V) {
        super.attachView(view)
        inject()
    }

    abstract fun inject()
}