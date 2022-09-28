package com.pikipin.pkpn_mobile.base.livedata

interface LiveDataMutator {

    var <T> NullableLiveData<T>.latestValue: T?
        get() {
            return this.value
        }
        set(value) {
            this.value = value
        }

    var <T : Any> NonNullLiveData<T>.latestValue: T
        get() {
            return this.value
        }
        set(value) {
            this.value = value
        }

    fun <T : Any> NonNullLiveData<T>.postLatestValue(value: T) {
        this.postValue(value)
    }

    fun <T : Any> NullableLiveData<T>.postLatestValue(value: T) {
        this.postValue(value)
    }

    fun <T : Any> EventLiveData<T>.emit(event: T) {
        this.latestValue = LiveEvent(event)
    }

    fun <T : Any> EventLiveData<T>.postEmit(event: T) {
        this.postValue(LiveEvent(event))
    }

    fun <T> NonNullLiveData<List<T>>.addItemToEndIfNotExistent(itemToAdd: T) {
        val items = value
        if (items.lastOrNull() == itemToAdd) {
            return
        }
        val updatedItems = items.plus(itemToAdd)
        latestValue = updatedItems
    }

    fun <T> NonNullLiveData<List<T>>.removeItemFromEnd(itemToRemove: T) {
        val items = value
        if (items.lastOrNull() != itemToRemove) {
            return
        }
        val updatedItems = items.dropLast(1)
        latestValue = updatedItems
    }
}
