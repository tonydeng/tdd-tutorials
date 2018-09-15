package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class JdbcConf {
    private String driverName;
    private String jdbcUrl;
    private String username;
    private String password;
}
