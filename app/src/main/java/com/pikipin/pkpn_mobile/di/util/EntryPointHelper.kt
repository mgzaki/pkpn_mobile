package com.pikipin.pkpn_mobile.di.util

import android.content.Context
import dagger.hilt.android.EntryPointAccessors

inline fun <reified T> Context.getEntryPoint(): T {
    val appContext = this.applicationContext ?: throw IllegalStateException()
    return EntryPointAccessors.fromApplication(appContext, T::class.java)
}