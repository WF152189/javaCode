package com.company;

import com.sun.deploy.util.StringUtils;

import java.util.Comparator;
import java.util.function.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        function();
//        biFunction();
//        doubleFunction();
//        ToDoubleBiFunction();
//        consumer();
//        BiConsumer();
//        Predicate();
//        Supplier();
        Operator();
    }
    public static void Operator() {
        UnaryOperator<Integer> unaryOperator;
        Function<Integer,Integer> function;
        BinaryOperator<Integer> binaryOperator = (a,b) -> a + b;
        System.out.println("a + b = " + binaryOperator.apply(10,20));
        System.out.println("max値:  " + BinaryOperator.maxBy(Integer::compare).apply(10,20));
    }
    public static void Supplier() {
        Supplier<Integer> supplier = () -> 1;
        System.out.println(supplier.get());
    }
    public static void Predicate(){
        Predicate<Integer> predicate = a-> a > 2;
        //  return (t) -> test(t) && other.test(t);
        Predicate<Integer> predicate1 = a -> a > 20;

        System.out.println(predicate.and(predicate1).test(10));
    }
    public static void BiConsumer() {
        BiConsumer<StringBuffer,StringBuffer> biConsumer = (x, y) -> {
            x.append("hello ");
            y.append("word  ");
        };
        BiConsumer<StringBuffer,StringBuffer> biConsumer1 = (x, y) -> {
            x.append("1111 ");
            y.append("2222  ");
        };
        StringBuffer bufferX = new StringBuffer("word");
        StringBuffer buffferY = new StringBuffer("hello");
//        biConsumer.accept(bufferX,buffferY);
//        System.out.println(bufferX.toString());
//        System.out.println(buffferY.toString());
        System.out.println("-----------------------------");
        /*
        *  return (l, r) -> {
             accept(l, r);
             after.accept(l, r);
            };
        *
        * */
        biConsumer1.andThen(biConsumer).accept(bufferX,buffferY);
        System.out.println(bufferX.toString());
        System.out.println(buffferY.toString());

    }
    public static void consumer(){
        StringBuffer strBuffer = new StringBuffer("hello");
        Consumer<StringBuffer> consumer = (str) -> str.append("Tom");
        // void accept(T t);
//        consumer.accept(strBuffer);
//        System.out.println(strBuffer.toString());
        Consumer<StringBuffer> consumer1 = (str) -> str.append("Jack");
        consumer.andThen(consumer1).accept(strBuffer);
        // (T t) -> { accept(t); after.accept(t); };
        System.out.println(strBuffer.toString());


    }
    public static void ToDoubleBiFunction(){
        ToDoubleBiFunction<Integer,Integer> toDoubleBiFunction = (a, b)->Integer.valueOf(a + b).doubleValue();
        System.out.println(toDoubleBiFunction.applyAsDouble(11, 11));
    }
    public static void doubleFunction(){
        DoubleFunction<String> doubleFunction = a -> a + "1";
        DoubleToIntFunction doubleToIntFunction = a -> Double.valueOf(a).intValue();
        System.out.println(doubleToIntFunction.applyAsInt(11.00));
        System.out.println(doubleFunction.apply(11.1));
    }
    public static void biFunction() {
        BiFunction<String,String,Integer> biFunction = (a, b) -> Integer.parseInt(a + b);
        Function<Integer,String> function = a -> a + "1";
        System.out.println(biFunction.apply("1", "1"));
        //  (T t, U u) -> after.apply(apply(t, u));
        System.out.println(biFunction.andThen(function).apply("2", "3"));
    }
    public static void function(){
        Function<String,String> function = a -> a + "jack";
        System.out.println(function.apply("hello "));
        Function<String,String> function1 = a -> a + "tom";
        System.out.println(function.andThen(function1).apply("hello "));
        // (V v) -> apply(before.apply(v));
        System.out.println(function.compose(function1).apply("hello"));
    }
}
