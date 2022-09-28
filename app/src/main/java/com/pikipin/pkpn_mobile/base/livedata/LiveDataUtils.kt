package com.pikipin.pkpn_mobile.base.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A : Any, B : Any, T> combineLatest(sourceA: LiveData<A>, sourceB: LiveData<B>, combine: (A, B) -> T): LiveData<T> {
    return combineLatest(listOf(sourceA, sourceB)) { list: List<Any> ->
        @Suppress("UNCHECKED_CAST")
        combine(list[0] as A, list[1] as B)
    }
}

fun <A : Any, B : Any, C : Any, T> combineLatest(
    sourceA: LiveData<A>,
    sourceB: LiveData<B>,
    sourceC: LiveData<C>,
    combine: (A, B, C) -> T
): LiveData<T> {
    return combineLatest(listOf(sourceA, sourceB, sourceC)) { list: List<Any> ->
        @Suppress("UNCHECKED_CAST")
        combine(list[0] as A, list[1] as B, list[2] as C)
    }
}

fun <A : Any, B : Any, C : Any, D : Any, T> combineLatest(
    sourceA: LiveData<A>,
    sourceB: LiveData<B>,
    sourceC: LiveData<C>,
    sourceD: LiveData<D>,
    combine: (A, B, C, D) -> T
): LiveData<T> {
    return combineLatest(listOf(sourceA, sourceB, sourceC, sourceD)) { list: List<Any> ->
        @Suppress("UNCHECKED_CAST")
        combine(list[0] as A, list[1] as B, list[2] as C, list[3] as D)
    }
}

fun <A : Any, B : Any, C : Any, D : Any, E : Any, T> combineLatest(
    sourceA: LiveData<A>,
    sourceB: LiveData<B>,
    sourceC: LiveData<C>,
    sourceD: LiveData<D>,
    sourceE: LiveData<E>,
    combine: (A, B, C, D, E) -> T
): LiveData<T> {
    return combineLatest(listOf(sourceA, sourceB, sourceC, sourceD, sourceE)) { list: List<Any> ->
        @Suppress("UNCHECKED_CAST")
        combine(list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E)
    }
}

fun <A : Any, B : Any, C : Any, D : Any, E : Any, F : Any, T> combineLatest(
    sourceA: LiveData<A>,
    sourceB: LiveData<B>,
    sourceC: LiveData<C>,
    sourceD: LiveData<D>,
    sourceE: LiveData<E>,
    sourceF: LiveData<F>,
    combine: (A, B, C, D, E, F) -> T
): LiveData<T> {
    return combineLatest(listOf(sourceA, sourceB, sourceC, sourceD, sourceE, sourceF)) { list: List<Any> ->
        @Suppress("UNCHECKED_CAST")
        combine(list[0] as A, list[1] as B, list[2] as C, list[3] as D, list[4] as E, list[5] as F)
    }
}

fun <S : Any, T> combineLatest(
    sources: List<LiveData<out S>>,
    combine: (List<S>) -> T
): LiveData<T> {
    val mediatorLiveData = MediatorLiveData<T>()
    val latestValues = MutableList<S?>(sources.size) { null }
    sources.forEachIndexed { index, source ->
        mediatorLiveData.addSource(source) {
            latestValues[index] = it
            if (it == null) {
                mediatorLiveData.value = null
                return@addSource
            }
            val nonNullLatestValues = latestValues.filterNotNull()
            if (nonNullLatestValues.size == latestValues.size) {
                mediatorLiveData.value = combine(nonNullLatestValues)
            }
        }
    }
    return mediatorLiveData
}

/**
 * Takes in two LiveData and returns a LiveData that emits the latest value of either of
 * the source LiveData
 * NOTE: If both of the source LiveData has emitted values before merge is called, the resulting
 * LiveData's initial value will be the latest value of sourceB
 * see [io.reactivex.rxjava3.core.Observable.merge]
 */
fun <T> merge(sourceA: LiveData<T>, sourceB: LiveData<T>): LiveData<T> {
    return MediatorLiveData<T>().apply {
        addSource(sourceA) { value = it }
        addSource(sourceB) { value = it }
    }
}

/**
 * see [merge]
 */
fun <T> LiveData<T>.mergeWith(sourceB: LiveData<T>): LiveData<T> {
    return merge(this, sourceB)
}

val LiveData<String>.valueOrEmpty: String
    get() = this.value ?: ""
