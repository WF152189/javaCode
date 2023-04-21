package com.company.sample1;

class Something {

    // constructor methods
    Something() {}

    Something(String something) {
        System.out.println(something);
    }

    // static methods
    static String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }

    // object methods
    String endWith(String s) {
        return String.valueOf(s.charAt(s.length()-1));
    }

    void endWith() {}

    public static void main(String[] args) {
        MyConFunction<String,String> myConFunction = Something::startsWith;
        System.out.println(myConFunction.get("hello word"));
    }
}