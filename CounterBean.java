package com.spring.day02.singleton;

public class CounterBean {

    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
