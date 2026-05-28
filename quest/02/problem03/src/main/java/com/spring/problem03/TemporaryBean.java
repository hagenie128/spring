package com.spring.problem03;

public class TemporaryBean {

    private final String id;

    public TemporaryBean() {
        this.id = "T-" + (int) (Math.random() * 10000);
    }

    public String getId() {
        return id;
    }
}
