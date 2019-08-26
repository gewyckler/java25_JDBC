package com.javagdsa25.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    public Student(String name, int age, double average, boolean isAlive) {
        this.name = name;
        this.age = age;
        this.average = average;
        this.isAlive = isAlive;
    }

    private Long id;
    private String name;
    private int age;
    private double average;
    private boolean isAlive;
}

