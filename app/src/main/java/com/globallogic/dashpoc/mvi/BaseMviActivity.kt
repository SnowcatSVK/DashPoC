package com.globallogic.dashpoc.mvi

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by snowcat on 8.3.2018.
 */
abstract class BaseMviActivity<V : MvpView, P : BasePresenter<V, *>> : MviActivity<V, P>() {

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        init(savedInstanceState)

    }

    abstract fun layoutId(): Int
}