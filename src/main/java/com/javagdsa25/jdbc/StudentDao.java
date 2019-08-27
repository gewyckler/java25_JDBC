package com.javagdsa25.jdbc;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.javagdsa25.jdbc.IStudentQueries.*;

public class StudentDao { // Data Acces Object
    private MySqlConnection mySqlConnection;

    public StudentDao() throws SQLException, IOException {
        mySqlConnection = new MySqlConnection();
        createTableIfNotExist();
    }

    public void createTableIfNotExist() throws SQLException {
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
                statement.execute();
            }
        }
    }

    public void insertStudent(Student student) throws SQLException {
        try (Connection connection = mySqlConnection.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, student.getName());
                statement.setInt(2, student.getAge());
                statement.setDouble(3, student.getAverage());
                statement.setBoolean(4, student.isAlive());

                // boolean success = statement.execute();
                int affectedResult = statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    Long generatedId = resultSet.getLong(1);
                    System.out.println("Został utworzony rekord o identyfikatorze: " + generatedId);
                }

            }
        }
    }

    public boolean deleteStudent(Long idStudent) throws SQLException {
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement((DELETE_QUERY))) {
                statement.setLong(1, idStudent);

                int affectedResult = statement.executeUpdate();
                if (affectedResult > 0) {
                    // usunelismy rekord
                    return true;
                }
            }
        }
        return false;
    }

    public List<Student> printStudentBetweenAge(int age1, int age2) throws SQLException {
        List<Student> listStudents = new ArrayList<>();
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_AGE_BETWEEN_QUERY)) {
                statement.setInt(1, age1);
                statement.setInt(2, age2);

                ResultSet resultSet = statement.executeQuery();
                loadMultipleStudentsFromResultSet(listStudents, resultSet);
            }
        }
        return listStudents;
    }

    public List<Student> printStudentWithName(String name) throws SQLException {
        List<Student> listStudents = new ArrayList<>();
        try (Connection connection = mySqlConnection.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(SELECT_NAME_QUERY)) {
                statement.setString(1, "%" + name + "%");
                ResultSet resultSet = statement.executeQuery();
                loadMultipleStudentsFromResultSet(listStudents, resultSet);
            }
        }
        return listStudents;
    }

    public Optional<Student> printStudentWithId(Long id) throws SQLException {
        try (Connection connection = mySqlConnection.getConnection()) {
            Student student;
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ID_QUERY)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    student = loadStudentFromResult(resultSet);
                    return Optional.of(student);
                } else {
                    System.out.println("Nie znaleziono");
                }
            }
        }
        return Optional.empty();

    }

    public List<Student> printAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = mySqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
                ResultSet resultSet = statement.executeQuery();

                loadMultipleStudentsFromResultSet(studentList, resultSet);
            }
        }
        return studentList;
    }

    private void loadMultipleStudentsFromResultSet(List<Student> listStudents, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Student student = loadStudentFromResult(resultSet);
            listStudents.add(student);
        }
    }

    private Student loadStudentFromResult(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong(1));
        student.setName(resultSet.getString(2));
        student.setAge(resultSet.getInt(3));
        student.setAverage(resultSet.getDouble(4));
        student.setAlive(resultSet.getBoolean(5));

        return student;
    }
}