package com.polyglot.demo.project.classes;

import java.util.ArrayList;

import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.Value;

public class JavaArray implements ProxyArray {
    private final ArrayList<Integer> delegate;
 
    public JavaArray(ArrayList<Integer> delegate) {
        this.delegate = delegate;
    }

    public Object get(long index) {
        return delegate.get((int) index);
    }
    public void set(long index, Value value) {
        throw new UnsupportedOperationException();
    }
    public long getSize() {
        return delegate.size();
    }
}