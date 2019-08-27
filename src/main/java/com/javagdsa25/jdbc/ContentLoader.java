package com.javagdsa25.jdbc;

import java.util.List;
import java.util.Scanner;

public class ContentLoader {
    private Scanner scanner = new Scanner(System.in);

    public Student createStudent() {
        Student student = new Student();
        student.setName(loadName());
        student.setAge(loadAge());
        student.setAverage(loadAverage());
        student.setAlive(loadIsAlive());
        return student;
    }

    private long loadId() {
        System.out.println("Wpisz id studenta: ");
        Long id = Long.parseLong(scanner.nextLine());
        return id;
    }

    private String loadName() {
        System.out.println("Podaj imie studenta:");
        String name = scanner.nextLine();
        return name;
    }

    private Integer loadAge() {
        System.out.println("Podaj wiek studenta:");
        int age = Integer.parseInt(scanner.nextLine());
        return age;
    }

    private double loadAverage() {
        System.out.println("Podaj średnią studenta:");
        double average = Double.parseDouble(scanner.nextLine());
        return average;
    }

    private boolean loadIsAlive() {
        System.out.println("Wpisz czy student żyje t/n:");
        String chose = scanner.nextLine();
        if (chose.equalsIgnoreCase("t")) {
            return true;
        } else {
            return false;
        }
    }

    public void printList(List<Student> studentList) {
        for (Student s : studentList) {
            System.out.println(s);
        }
    }

    public void clickToContiunue() {
        System.out.println("Click 'ENTER' to continue...");
        scanner.nextLine();
    }
}
