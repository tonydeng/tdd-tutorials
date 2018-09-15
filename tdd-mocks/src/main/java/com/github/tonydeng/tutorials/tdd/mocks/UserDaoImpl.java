package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        final UserImpl u = (UserImpl) user;
        new SqlSandwich<Void>() {

            @Override
            Void withConnection(Connection conn) throws Exception {
                PreparedStatement ps;
                if (user.getId() != null) {
                    ps = conn.prepareStatement("UPDATE TDD_USER SET username=?, password=?,is_locked=? WHERE id=?");
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getPassword());
                    ps.setBoolean(3, user.isLocked());
                    ps.setLong(4, user.getId());
                    ps.executeUpdate();
                } else {
                    ps = conn.prepareStatement("INSERT INTO TDD_USER (username,password,is_locked) VALUES(?, ?, ?)",
                            java.sql.Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getPassword());
                    ps.setBoolean(3, user.isLocked());
                    ps.executeUpdate();
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    generatedKeys.next();
                    user.setId(generatedKeys.getLong(1));
                }
                return null;
            }
        }.execute(jdbcConf);
    }

    @Override
    public User newUser() {
        return new UserImpl();
    }
}
