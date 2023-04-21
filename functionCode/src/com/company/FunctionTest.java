package com.company;

import java.util.function.Function;

public class FunctionTest {

    public static void main(String[] args) {

        FunctionTest functionTest = new FunctionTest();
        String s = functionTest.doFunction(functionTest::hasOneParam, "s");
        Integer integer = functionTest.doFunction(functionTest::increase, 6);
        System.out.println(s);
        System.out.println(integer);
    }

    public <T, R> R doFunction(Function<T, R> function, T param) {
        return function.apply(param);
    }

    public <T> String hasOneParam(T param) {
        return param.toString();
    }

    public Integer increase(Integer i) {
        return i + 1;
    }
}
