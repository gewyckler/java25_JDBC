package com.javagdsa25.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnection {
    private MysqlConnectionParameters parameters;
    private MysqlDataSource dataSource;

    public MySqlConnection() throws IOException {
        parameters = new MysqlConnectionParameters();
        initialized();
    }

    private void initialized() {
        dataSource = new MysqlDataSource();
// ustawiamy parametry serwera
        dataSource.setPort(Integer.parseInt(parameters.getDbPORT()));
        dataSource.setUser(parameters.getDbUSERNAME());
        dataSource.setServerName(parameters.getDbHOST());
        dataSource.setPassword(parameters.getDbPASSWORD());
        dataSource.setDatabaseName(parameters.getDbNAME());
// ustawiamy strefe czasowa
        try {
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setAllowPublicKeyRetrieval(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
