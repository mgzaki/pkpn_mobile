package com.pikipin.pkpn_mobile.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ScrollView
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.reflect.KClass


/**
 * Creates a new or gets an existing [ViewModel] of type [T] and returns that ViewModel.
 *
 * If the [ViewModel] of type [T] has not yet been created for this Fragment, the [factory] will be called to
 * create a new one.
 *
 * @return A [ViewModel] of type [T]
 */
inline fun <reified T : ViewModel> Fragment.createViewModel(
    noinline factory: (() -> T)
): T {
    val viewFactory = object : ViewModelProvider.Factory {
        override fun <V : ViewModel> create(modelClass: Class<V>): V {
            @Suppress("UNCHECKED_CAST")
            return factory() as V
        }
    }

    return createViewModel(viewFactory)
}

inline fun <reified T : ViewModel> Fragment.createViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory).get(T::class.java)
}

/**
 * Creates a new or gets an existing [ViewModel] of type [T] and returns that ViewModel.
 *
 * If the [ViewModel] of type [T] has not yet been created for this Activity, the [factory] will be called to
 * create a new one.
 *
 * @return A [ViewModel] of type [T]
 */
inline fun <reified T : ViewModel> FragmentActivity.createViewModel(
    noinline factory: (() -> T)
): T {
    val viewFactory = object : ViewModelProvider.Factory {
        override fun <V : ViewModel> create(modelClass: Class<V>): V {
            @Suppress("UNCHECKED_CAST")
            return factory() as V
        }
    }

    return createViewModel(viewFactory)
}

inline fun <reified T : ViewModel> FragmentActivity.createViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory).get(T::class.java)
}

private var showKeyboardDisposable: Disposable? = null
    set(value) {
        field?.let {
            if (value != it) {
                if (!it.isDisposed) it.dispose()
            }
        }
        field = value
    }

fun Context.showKeyboard(view: View, withDelay: Long = 50) {
    val showKeyboardRunnable: () -> Unit = {
        view.requestFocus()

        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    if (view.isFocused) {
        showKeyboardRunnable()
    } else {
        val scheduler = AndroidSchedulers.mainThread()

        // Sometimes a 'show-keyboard' doesn't work when called immediately.
        // E.g doing `view.enabled = true; showKeyboard(view)` often doesn't work because of the
        // 'disabled' state still being 'somewhat' in effect. Delaying it a bit lets it all settle
        // and allows the focusing and the showing of the keyboard to succeed.

        showKeyboardDisposable = Single.just(showKeyboardRunnable)
            .observeOn(scheduler)
            .delay(withDelay, TimeUnit.MILLISECONDS, scheduler)
            .doAfterTerminate { showKeyboardDisposable = null }
            .subscribe { showKeyboard -> showKeyboard() }
    }
}

fun Fragment.showKeyboard(focusedView: View, withDelay: Long = 50) {
    activity?.showKeyboard(focusedView, withDelay)
}

fun Context.hideKeyboard(focusedView: View? = (this as? Activity)?.currentFocus) {
    showKeyboardDisposable = null
    if (focusedView != null) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}

fun Fragment.hideKeyboard(focusedView: View? = activity?.currentFocus) {
    activity?.hideKeyboard(focusedView)
}

/**
 * Convenience method for setting a listener for the completion of animation.
 */
fun ChangeBounds.endWith(endAction: () -> Unit) {
    addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            removeListener(this)
            endAction()
        }

        override fun onTransitionResume(transition: Transition) {}
        override fun onTransitionPause(transition: Transition) {}
        override fun onTransitionCancel(transition: Transition) {}
        override fun onTransitionStart(transition: Transition) {}
    })
}

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

fun runOnUiThread(runnable: () -> Unit) {
    Completable.fromAction(runnable)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe()
}

/**
 * This function can be used for a List Adapter and it will return a so-called 'view-type' for
 * the given class [T].
 */
inline fun <reified T> viewType() = viewType(T::class)

/**
 * This function can be used for a List Adapter and it will return a so-called 'view-type' for
 * the given class [klass].
 */
fun viewType(klass: KClass<*>) = klass.hashCode()

fun View.setHeight(height: Int) {
    updateLayoutParams<ViewGroup.LayoutParams> { this.height = height }
}

fun View.setWidth(width: Int) {
    updateLayoutParams<ViewGroup.LayoutParams> { this.width = width }
}

fun View.announceWithDelay(text: CharSequence, delay: Long = 1000L) {
    this.postDelayed({ this.announceForAccessibility(text) }, delay)
}

inline fun <reified T> View.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}

fun View.setFocusChangeListener(listener: (Boolean) -> Unit) {
    this.setOnFocusChangeListener { _, hasFocus ->
        listener.invoke(hasFocus)
    }
}

// Suppressing the lint warning here. But the caller should still ensure that the interaction doesn't break for
// users using screen reader
@SuppressLint("ClickableViewAccessibility")
fun View.setTouchListener(listener: () -> Unit) {
    this.setOnTouchListener { _, _ ->
        listener.invoke()
        false
    }
}

fun AdapterView<*>.setItemSelectedListener(listener: (Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // do nothing
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener.invoke(position)
        }
    }
}

/**
 * Scroll down the minimum needed amount to show [descendant] in full. More
 * precisely, reveal its bottom.
 */
fun ScrollView.scrollDownTo(descendant: View) {
    // Could use smoothScrollBy, but it sometimes over-scrolled a lot
    howFarDownIs(descendant)?.let {
        (this as? ScrollView)?.smoothScrollBy(0, it)
    }
}

/**
 * Calculate how many pixels below the visible portion of this [ViewGroup] is the
 * bottom of [descendant].
 *
 * In other words, how much you need to scroll down, to make [descendant]'s bottom
 * visible.
 */
fun ViewGroup.howFarDownIs(descendant: View): Int? {
    val bottom = Rect().also {
        // See https://stackoverflow.com/a/36740277/1916449
        descendant.getDrawingRect(it)
        offsetDescendantRectToMyCoords(descendant, it)
    }.bottom
    return (bottom - height - scrollY).takeIf { it > 0 }
}

