package com.pikipin.pkpn_mobile.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

open class BaseFragment: Fragment() {

    fun <T> LiveData<T>.observe(callback: (T) -> Unit) {
        this.observe(viewLifecycleOwner) { callback.invoke(it) }
    }
}