package com.javagdsa25.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    // 1. Stworzenie schema: jdbc_students
    // 2. Create table
    // 3. Zapytanie na wstawienie danych do tabeli

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContentLoader contentLoader = new ContentLoader();

        StudentDao studentDao = null;
        try {
            studentDao = new StudentDao();
        } catch (SQLException e) {
            System.err.println("StudentDao cannot be created. Mysql error.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        String komenda = "";
        do {
            try {
                System.out.println("Co chcesz zrobic?\n" +
                        "1. wstaw\n" +
                        "2. usun\n" +
                        "3. select\n" +
                        "4. select by id\n" +
                        "5. select by name\n" +
                        "6. select between age\n" +
                        "0. quit");
                komenda = scanner.nextLine();
                if (komenda.equalsIgnoreCase("1")) {
                    Student student = contentLoader.createStudent();
                    studentDao.insertStudent(student);

                } else if (komenda.equalsIgnoreCase("2")) {
                    System.out.println("Podaj id studenta do usunięcia:");
                    long id = Long.parseLong(scanner.nextLine());
                    System.out.println(studentDao.deleteStudent(id));

                } else if (komenda.equalsIgnoreCase("3")) {
                    contentLoader.printList(studentDao.printAllStudents());
                    contentLoader.clickToContiunue();

                } else if (komenda.equalsIgnoreCase("4")) {
                    System.out.println("Podaj id studenta:");
                    long id = Long.parseLong(scanner.nextLine());
                    studentDao.printStudentWithId(id);
                    contentLoader.clickToContiunue();

                } else if (komenda.equalsIgnoreCase("5")) {
                    System.out.println("Podaj nazwisko studenta:");
                    String name = scanner.nextLine();
                    contentLoader.printList(studentDao.printStudentWithName(name));
                    contentLoader.clickToContiunue();

                } else if (komenda.equalsIgnoreCase("6")) {
                    System.out.println("Podaj pierwsza liczbe:");
                    int age1 = Integer.parseInt(scanner.nextLine());
                    System.out.println("Podaj drugą liczbę:");
                    int age2 = Integer.parseInt(scanner.nextLine());
                    contentLoader.printList(studentDao.printStudentBetweenAge(age1, age2));
                    contentLoader.clickToContiunue();
                }

            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } while (!komenda.equalsIgnoreCase("0"));
    }
}
