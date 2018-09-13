package com.github.tonydeng.tutorials.tdd.mocks;

public interface UserDao {
    User findUserByUserName(String username);

    void saveOrUpdate(User user);

    User newUser();
}
