package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(JdbcConf jdbcConf) {
        super(jdbcConf);
    }

    @Override
    public User findUserByUserName(String username) {
        return null;
    }

    @Override
    public void saveOrUpdate(User user) {

    }

    @Override
    public User newUser() {
        return null;
    }
}
