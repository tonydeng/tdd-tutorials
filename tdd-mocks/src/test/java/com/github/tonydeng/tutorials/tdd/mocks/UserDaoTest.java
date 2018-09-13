package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

@Slf4j
public class UserDaoTest {

    private static JdbcConf jdbcConf;

    @BeforeAll
    public static void init() {
        jdbcConf = new JdbcConf("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:name",
                "sa", "");
    }

    @Test
    public void testNewUser() {
        UserDao userDao = new UserDaoImpl(jdbcConf);

        User user = userDao.newUser();
        Assertions.assertNotNull(user);
        user.setUsername("tonydeng");
        user.setPassword("123456");

        userDao.saveOrUpdate(user);

        Assertions.assertNotNull(user.getId());
    }
}
