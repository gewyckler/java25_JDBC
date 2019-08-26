package com.javagdsa25.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // 1. Stworzenie schema: jdbc_students
    // 2. Create table
    // 3. Zapytanie na wstawienie danych do tabeli

    private static final String CREATE_TABLE_QUERY = "create table if not exists `students` (\n" +
            "`id` INT not null auto_increment Primary key,\n" +
            "`name` varchar(255) not null,\n" +
            "`age` int not null,\n" +
            "`average` double not null,\n" +
            "`alive` tinyint not null\n" +
            ");";

    private static final String INSERT_QUERY =
            "insert into students (`name`, `age`, `average`, `alive`)\n" +
                    "values (?, ?, ?, ?);";

    private static final String DELETE_QUERY = "delete from `students` where id = ?;";

    private static final String SELECT_QUERY = "select * from `students`;";

    private static final String SELECT_ID_QUERY = "select * from students where id = ?;";

    private static final String SELECT_NAME_QUERY = "select * from students where `name` like ?;";

    private static final String SELECT_AGE_BETWEEN_QUERY = "select * from student where `age` between ? and ?;";

    private static final String DB_HOST = "Localhost"; //127.0.0.1
    private static final String DB_PORT = "3305";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_NAME = "jdbc_students";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContentLoader contentLoader = new ContentLoader();

        MysqlDataSource dataSource = new MysqlDataSource();
// ustawiamy parametry serwera
        dataSource.setPort(Integer.parseInt(DB_PORT));
        dataSource.setUser(DB_USERNAME);
        dataSource.setServerName(DB_HOST);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDatabaseName(DB_NAME);
// ustawiamy strefe czasowa
        try {
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = dataSource.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
                statement.execute();
            }

            String komenda = "";
            do {
                System.out.println("Co chcesz zrobic? 1. wstaw\n2. usun\n3. select\n4. select by id\n5. select by name" +
                        "6. select between age\nquit");
                komenda = scanner.nextLine();
                if (komenda.equalsIgnoreCase("1")) {

                    Student student = contentLoader.createStudent();
                    insertStudent(connection, student);
                } else if (komenda.equalsIgnoreCase("2")) {
                    System.out.println("Podaj id studenta do usunięcia:");
                    long id = Long.parseLong(scanner.nextLine());
                    deleteStudent(connection, id);
                } else if (komenda.equalsIgnoreCase("3")) {
                    printAllStudents(connection);
                } else if (komenda.equalsIgnoreCase("4")) {
                    System.out.println("Podaj id studenta:");
                    long id = Long.parseLong(scanner.nextLine());
                    printStudentWithId(connection, id);
                } else if (komenda.equalsIgnoreCase("5")) {
                    System.out.println("Podaj nazwisko studenta:");
                    String name = scanner.nextLine();
                    printStudentWithName(connection, name);
                }
            } while (!komenda.equalsIgnoreCase("quit"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void prontStudentBetweenAge(Connection connection, int age1, int age2) throws SQLException {
        List<Student> listStudents = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_AGE_BETWEEN_QUERY)) {
            statement.setString(3, age1, age2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();

                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));
                listStudents.add(student);
            }
        }
    }

    private static void printStudentWithName(Connection connection, String name) throws SQLException {
        List<Student> listStudents = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_NAME_QUERY)) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));
                listStudents.add(student);
            }
        }

        for (Student student : listStudents) {
            System.out.println(student);
        }
    }

    private static void printStudentWithId(Connection connection, Long id) throws SQLException {
        Student student = new Student();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));
                System.out.println(student);
            } else {
                System.out.println("Nie znaleziono");
            }
        }

    }

    private static void insertStudent(Connection connection, Student student) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setDouble(3, student.getAverage());
            statement.setBoolean(4, student.isAlive());

            statement.execute();
        }
    }

    private static void printAllStudents(Connection connection) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();

                student.setId(resultSet.getLong(1));
                student.setName(resultSet.getString(2));
                student.setAge(resultSet.getInt(3));
                student.setAverage(resultSet.getDouble(4));
                student.setAlive(resultSet.getBoolean(5));

                studentList.add(student);
            }
        }
        for (int i = 0; i < studentList.size(); i++) {
            System.out.println(studentList.get(i));
        }
    }

    private static void deleteStudent(Connection connection, Long idStudent) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement((DELETE_QUERY))) {
            statement.setLong(1, idStudent);
            statement.execute();
        }
    }


}
