package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public abstract class SqlSandwich<T> {

    public T execute(JdbcConf jdbcConf) {

        try (Connection conn = DriverManager.getConnection(jdbcConf.getJdbcUrl(),
                jdbcConf.getUsername(), jdbcConf.getPassword())) {
            Class.forName(jdbcConf.getDriverName());
            return withConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("{}", e.getStackTrace());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    abstract T withConnection(Connection connection) throws Exception;
}
