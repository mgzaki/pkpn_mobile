package com.pikipin.pkpn_mobile.base.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

// This class is in Java so that the setValue() method can be package scoped so that it would only be accessible
// to LiveDataMutator
public class NonNullLiveData<T> extends LiveData<T> {

    public NonNullLiveData(@NonNull T initialValue) {
        super(initialValue);
    }

    @Override
    protected void postValue(@NonNull T value) {
        super.postValue(value);
    }

    @Override
    protected void setValue(@NonNull T value) {
        super.setValue(value);
    }

    @NonNull
    @Override
    public T getValue() {
        //noinspection ConstantConditions
        return super.getValue();
    }
}
