package com.pikipin.pkpn_mobile.base

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.pikipin.pkpn_mobile.base.livedata.LiveDataMutator
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel: ViewModel(), LifecycleObserver, LiveDataMutator {
    protected val disposables = CompositeDisposable()

    @CallSuper
    open fun observeLifeCycle(lifecycle: Lifecycle){
        lifecycle.addObserver(this)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}