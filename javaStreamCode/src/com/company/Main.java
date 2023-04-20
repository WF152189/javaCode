package com.company;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
	// write your code here
//        count();
//        map();
//        map1();
//        flatmap();
//        mapToInt();
//        mapToDouble();
//        reduce3();
//        reduce4();
//        reduce5();
//        reduce6();
//        collection1();
//        collect1();
//        partitioningBy();
//        join();
//        reducing();
        skipLimit();
    }
    public static void skipLimit() {
        String[] arr1 = { "a", "b", "c", "d" };
        String[] arr2 = { "d", "e", "f", "g" };
        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);

        // concat　合併
        List<String> concat = Stream.concat(stream1, stream2).collect(Collectors.toList());
        // distinct 同じのを取り除く
        List<String> distinct = concat.stream().distinct().collect(Collectors.toList());
        // skip
        List<Integer> skip = Stream.iterate(0, n -> n + 1).skip(20).limit(10).collect(Collectors.toList());
        System.out.println("concat: " + concat);
        System.out.println("distinct: " + distinct);
        System.out.println("skip: " + skip);
    }
    public static void reducing() {
        List<Person> personList = PersonListInit();
        Integer sum = personList.stream()
                .collect(Collectors
                        .reducing(0,
                                Person::getSalary,
                                (result, salary) -> {
                                    return result + salary - 6000;
                                }
                        )
                );
        System.out.println("sum: " + sum);
    }
    public static void join() {
        List<Person> personList = PersonListInit();
        String names = personList.stream()
                .map(Person::getName)
                .collect(Collectors.joining("-"));

        String names2 = personList.stream()
                .map(Person::getName)
                .collect(
                        Collectors.joining("-", "[", "]")
                );
        System.out.println("names: " + names);
        System.out.println("names2: " + names2);

    }

    public static void partitioningBy(){
        List<Person> personList = PersonListInit();
        //給料で分け
        Map<Boolean, List<Person>> listMap = personList.stream().
                collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
        //性別でわけ
        Map<String, List<Person>> groupBySex = personList.stream()
                .collect(Collectors.groupingBy(Person::getSex));
        //性別で分けてから地域で分け
        Map<String, Map<String, List<Person>>> collect = personList.stream()
                .collect(
                     Collectors.groupingBy(
                         Person::getSex,
                         Collectors.groupingBy(Person::getArea)
                     )
                );

        System.out.println("給料 < 8000: " + listMap.get(false));
        System.out.println("給料 > 8000: " + listMap.get(true));
        System.out.println("男性: " + groupBySex.get("male"));
        System.out.println("女性: " + groupBySex.get("female"));
        System.out.println("New Yorkに住み男性: " + collect.get("male").get("New York"));
        System.out.println("Washingtonに住み男性: " + collect.get("male").get("Washington"));
        System.out.println("New Yorkに住み女性: " + collect.get("female").get("New York"));
        System.out.println("Washingtonに住み女性: " + collect.get("female").get("Washington"));

    }
    public static void collect1() {
        List<Person> personList = PersonListInit();
        Long count = personList.stream().collect(Collectors.counting());
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        Optional<Integer> maxSalary = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("员工工资总和：" + sum);
        System.out.println("员工工资所有统计：" + collect);
        Integer[] ints = {2, 5, 8, 4, 6, 9, 5};
        Arrays.sort(ints, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - 02;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });

    }
    public static void collect() {
        List<Person> personList = PersonListInit();
        // 求总数
        Long count = personList.stream().collect(Collectors.counting());
        // 求平均工资
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        // 求最高工资
        Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        // 求工资之和
        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));
        // 一次性统计所有信息
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("员工工资总和：" + sum);
        System.out.println("员工工资所有统计：" + collect);
    }
    public static void collection1() {
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Person> personList = PersonListInit();
        Set<Integer> set = list.stream().filter(num -> num % 2 == 0).collect(Collectors.toSet());
        Map<String, Person> personMap = personList.stream()
                .filter(person -> person.getSalary() > 8000)
                .collect(Collectors.toMap(Person::getName, p -> p));
        System.out.println("toSet:" + set);
        System.out.println("toMap:" + personMap);

    }
    public static void collection(){
                List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
                List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
                Set<Integer> set = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());

                List<Person> personList = new ArrayList<Person>();
                personList.add(new Person("Tom", 8900, 23, "male", "New York"));
                personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
                personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
                personList.add(new Person("Anni", 8200, 24, "female", "New York"));

                Map<?, Person> map = personList.stream().filter(p -> p.getSalary() > 8000)
                        .collect(Collectors.toMap(Person::getName, p -> p));
                System.out.println("toList:" + listNew);
                System.out.println("toSet:" + set);
                System.out.println("toMap:" + map);

    }
    public static void reduce6() {
        List<Person> personList = PersonListInit();
        Integer[] numList = getNumList();
        List<Integer> list = Arrays.asList(numList);
        Integer sum1 = list.stream().reduce(0, (sum, item) -> sum += item);
        Integer sumSalary = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);
        Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), Integer::max);
        Optional<Integer> maxSalary1 = personList.stream().map(Person::getSalary).reduce(Integer::max);



        System.out.println("sum = " + sumSalary);
    }
    public static void reduce5() {
        Integer[] numList = getNumList();
        Long startTIme = System.currentTimeMillis();
        Integer sum = Arrays.stream(numList).reduce(0, Integer::sum);
        Long endTime = System.currentTimeMillis();
        Long time = endTime - startTIme;
        System.out.println("sum = " + sum + "  time = " +  time);
    }
    public static void reduce4() throws ExecutionException, InterruptedException {
        Long startTime = System.currentTimeMillis();
        ExecutorService threadPool = getThreadPool();
        Integer[] numList = getNumList();
        Integer sum = threadPool.submit(() ->
                Arrays.stream(numList)
                        .reduce(
                                0, // 初始值
                                Integer::sum, // 累加器函数
                                Integer::sum // 合并器函数
                        )).get();
        threadPool.shutdown();
        Long endTIme = System.currentTimeMillis();
        Long time = endTIme - startTime;
        System.out.println("sum = " + sum + "  time = " +  time);

    }
    public static void reduce3() {
            ArrayList<Integer> accResult_ = Stream.of(1, 2, 3, 4)
                    //第一个参数，初始值为ArrayList
                    .parallel().reduce(new ArrayList<Integer>(),
                            //第二个参数，实现了BiFunction函数式接口中apply方法，并且打印BiFunction
                            new BiFunction<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
                                @Override
                                public ArrayList<Integer> apply(ArrayList<Integer> acc, Integer item) {

                                    acc.add(item);
                                    System.out.println("item: " + item);
                                    System.out.println("acc+ : " + acc);
                                    System.out.println("BiFunction");
                                    return acc;
                                }
                                //第三个参数---参数的数据类型必须为返回数据类型，改参数主要用于合并多个线程的result值
                                // （Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result）
                            }, new BinaryOperator<ArrayList<Integer>>() {
                                @Override
                                public ArrayList<Integer> apply(ArrayList<Integer> acc, ArrayList<Integer> item) {
                                    System.out.println("BinaryOperator");
                                    acc.addAll(item);
                                    System.out.println("item: " + item);
                                    System.out.println("acc+ : " + acc);
                                    System.out.println("--------");
                                    return acc;
                                }
                            });
            System.out.println("accResult_: " + accResult_);

            System.out.println("------------------lambda优化代码-----------------");

            ArrayList<Integer> newList = new ArrayList<>();

            ArrayList<Integer> accResult_s = Stream.of(1,2,3,4)
                    .reduce(newList,
                            (acc, item) -> {
                                acc.add(item);
                                System.out.println("item: " + item);
                                System.out.println("acc+ : " + acc);
                                System.out.println("BiFunction");
                                return acc;
                            }, (acc, item) -> null);

    }
    public static void reduce2() {
        List<Person> personList = PersonListInit();

        // 求工资之和方式1：
        Optional<Integer> sumSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        // 求工资之和方式2：
        Integer sumSalary2 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(),
                (sum1, sum2) -> sum1 + sum2);
        // 求工资之和方式3：
        Integer sumSalary3 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);

        // 求最高工资方式1：
        Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                Integer::max);
        // 求最高工资方式2：
        Integer maxSalary2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                (max1, max2) -> max1 > max2 ? max1 : max2);
        // 求最高工资方式3：
        Integer maxSalary3 = personList.stream().map(Person::getSalary).reduce(Integer::max).get();

        System.out.println("工资之和：" + sumSalary.get() + "," + sumSalary2 + "," + sumSalary3);
        System.out.println("最高工资：" + maxSalary + "," + maxSalary2 + "," + maxSalary3);
    }
    public static void reduce() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        // 足し算1
        Optional<Integer> sum1 = Optional.of(list.stream().reduce((x, y) -> x + y).get());
        // 足し算2
        Optional<Integer> sum2 =  list.stream().reduce((x,y)->x+y);
        // 足し算3
        Optional<Integer> sum3 = list.stream().reduce(Integer::sum);
        // 足し算４
        Integer sum4 = list.stream().reduce(0, Integer::sum);

        //　掛け算
        Optional<Integer> product =  list.stream().reduce((x,y)->x * y);

        // maxnumber1
        Integer max = list.stream().reduce(0,Integer::max);
        // maxnumber2
        Optional<Integer> max2 = list.stream().reduce((x, y) -> x > y ? x : y);
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
    public static Integer[] getNumList() {
        Integer[] numList = new Integer[1000000];
        for(int i = 0; i < 1000000; i++) {
            numList[i] = i;
        }
        return numList;
    }
    public static ExecutorService getThreadPool() {
        return Executors.newFixedThreadPool(10);
    }
}
