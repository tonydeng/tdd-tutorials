package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
    public void saveOrUpdate(User u) {
        final UserImpl user = (UserImpl) u;
        new SqlSandwich<Void>() {

            @Override
            Void withConnection(Connection conn) {
                PreparedStatement ps = null;
                ResultSet resultSet = null;
                try {

                    if (Objects.nonNull(user.getId())) {
                        String sql = "UPDATE TDD_USER SET username=?, `password`=?,is_locked=? WHERE id=?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, user.getUsername());
                        ps.setString(2, getEncryptedPassword(user.getPassword()));
                        ps.setBoolean(3, user.isLocked());
                        ps.setLong(4, user.getId());
                        ps.executeUpdate();
                    } else {
                        String sql = "INSERT INTO TDD_USER (username,password,is_locked) VALUES(?, ?, ?)";

                        ps = conn.prepareStatement(sql,
                                java.sql.Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, user.getUsername());
                        ps.setString(2, getEncryptedPassword(user.getPassword()));
                        ps.setBoolean(3, user.isLocked());
                        ps.executeUpdate();
                        resultSet = ps.getGeneratedKeys();
                        resultSet.next();
                        user.setId(resultSet.getLong(1));
                    }
                } catch (SQLException e) {
                    log.error("execute sql error", e);
                } finally {
                    if (Objects.nonNull(ps)) {
                        try {
                            ps.close();
                        } catch (SQLException e) {
                            log.error("close PreparedStatement error", e);
                        }
                    }
                    if (Objects.nonNull(resultSet)) {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return null;
            }
        }.execute(jdbcConf);
    }

    @Override
    public User newUser() {
        return new UserImpl();
    }

    private String getEncryptedPassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
