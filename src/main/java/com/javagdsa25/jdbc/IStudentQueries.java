package com.javagdsa25.jdbc;

public interface IStudentQueries {
    String CREATE_TABLE_QUERY = "create table if not exists `students` (\n" +
            "`id` INT not null auto_increment Primary key,\n" +
            "`name` varchar(255) not null,\n" +
            "`age` int not null,\n" +
            "`average` double not null,\n" +
            "`alive` tinyint not null\n" +
            ");";

    String INSERT_QUERY =
            "insert into students (`name`, `age`, `average`, `alive`)\n" +
                    "values (?, ?, ?, ?);";

    String DELETE_QUERY = "delete from `students` where id = ?;";
    String SELECT_QUERY = "select * from `students`;";
    String SELECT_ID_QUERY = "select * from students where id = ?;";
    String SELECT_NAME_QUERY = "select * from students where `name` like ?;";
    String SELECT_AGE_BETWEEN_QUERY = "select * from students where `age` between ? and ?;";
}
