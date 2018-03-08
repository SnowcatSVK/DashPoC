package com.globallogic.dashpoc.mvi

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by snowcat on 8.3.2018.
 */
abstract class BaseMviFragment<V : MvpView, out P : BasePresenter<V, *>> : Fragment() {

    abstract fun init(savedInstanceState: Bundle?)

    abstract fun injectArgs()

    abstract val presenter : P

    abstract val view : V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectArgs()
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(view)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(layoutId(), container, false)
    }

    abstract fun layoutId(): Int
}