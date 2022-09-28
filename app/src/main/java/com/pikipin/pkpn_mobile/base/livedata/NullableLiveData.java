package com.pikipin.pkpn_mobile.base.livedata;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

// This class is in Java so that the setValue() method can be package scoped so that it would only be accessible
// to LiveDataMutator
public class NullableLiveData<T> extends LiveData<T> {

    public NullableLiveData(@Nullable T initialValue) {
        super(initialValue);
    }

    public NullableLiveData() {
        super();
    }

    @Override
    protected void postValue(@Nullable T value) {
        super.postValue(value);
    }

    @Override
    protected void setValue(@Nullable T value) {
        super.setValue(value);
    }
}
