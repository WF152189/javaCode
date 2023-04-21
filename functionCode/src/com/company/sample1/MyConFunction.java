package com.company.sample1;

@FunctionalInterface
public interface MyConFunction<T,R> {
    R get(T t);
}
