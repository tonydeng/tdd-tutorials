package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JdbcConf {
    private String driverName;
    private String jdbcUrl;
    private String username;
    private String password;
}
