package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        count();
//        map();
//        map1();
//        flatmap();
//        mapToInt();
        mapToDouble();
    }
    public static void mapToDouble(){
        List<Double> doubleList = Arrays.asList(1.0, 2.0, 3.0, 4.0, 2.0);
        double average  = doubleList.stream().mapToDouble(Number::doubleValue).average().getAsDouble();
        double sum = doubleList.stream().mapToDouble(Number::doubleValue).sum();
        OptionalDouble max = doubleList.stream().mapToDouble(Number::doubleValue).max();
        System.out.println("平均值：" + average + "，总和：" + sum + "，最大值：" + max);

    }
    public static void mapToInt(){
        List<String> stringList = Arrays.asList("mu", "CSDN", "hello",
                "world", "quickly");
        stringList.stream().mapToInt(String::length).forEach(System.out::println);
        System.out.println("长度最小的为: " +stringList.stream().mapToInt(String::length).min().getAsInt());
        System.out.println("长度最大的为: " +stringList.stream().mapToInt(String::length).max().getAsInt());

    }
    public static void flatmap() {
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(str -> {
            String[] split = str.split(",");
            Stream<String> stream = Arrays.stream(split);
            return stream;
        }).collect(Collectors.toList());
        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);

    }
    public static void map1(){
        List<Person> personList = PersonListInit();
        List<Person> cloneList = personList.stream().
                map(person -> {
                    Person newPerson = null;
                    try {
                        newPerson = (Person) person.clone();
                        newPerson.setSalary(person.getSalary() + 1000);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    return newPerson;
                }).
                collect(Collectors.toList());
        System.out.println("改变之前" + personList);
        System.out.println("改变之后" + cloneList);

    }
    public static void map() {
        String[] strArr = { "abcd", "bcdd", "defde", "fTr" };
        List<String> toUpperStrArr = Arrays.stream(strArr)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        List<Integer> integerList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> integers = integerList.stream()
                .map(num -> num + 3)
                .collect(Collectors.toList());

        System.out.println("每个元素大写：" + toUpperStrArr);
        System.out.println("每个元素+3：" + integers);

    }
    public static void count() {
        List<Integer> integers = Arrays.asList(7, 6, 4, 8, 2, 11, 9);
        long count = integers.stream().filter(item -> item > 6).count();
        System.out.println("大于6的个数为" + count);
    }
    public static List PersonListInit() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 22,"male", "New York"));
        personList.add(new Person("Jack", 7000, 34,"male", "Washington"));
        personList.add(new Person("Lily", 7800, 53,"female", "Washington"));
        personList.add(new Person("Anni", 8200, 18,"female", "New York"));
        personList.add(new Person("Owen", 9500, 21,"male", "New York"));
        personList.add(new Person("Alisa", 7900, 47,"female", "New York"));
        return personList;
    }
}
