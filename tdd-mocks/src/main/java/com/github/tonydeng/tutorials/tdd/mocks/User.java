package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.Data;

@Data
public abstract class User {
    protected Long id;
    protected String password;
    protected boolean isLocked;
    protected int failures;
    protected String username;
}
