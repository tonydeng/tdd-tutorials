package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class UserDaoTest {

    private static JdbcConf jdbcConf;

    @BeforeAll
    public static void init() throws IOException, ClassNotFoundException, SQLException, DatabaseUnitException {
        String createDatabaseSql = IOUtils.toString(UserDaoTest.class.getResource("/database.sql"));

        log.info("{}", createDatabaseSql);

        jdbcConf = new JdbcConf("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:aname", "sa", "");

        Class.forName(jdbcConf.getDriverName());
        Connection connection = DriverManager.getConnection(jdbcConf.getJdbcUrl(), jdbcConf.getUsername(), jdbcConf.getPassword());
        Statement createStatement = connection.createStatement();
        createStatement.addBatch(createDatabaseSql);
        createStatement.executeBatch();

        final IDatabaseConnection conn = new DatabaseConnection(connection);

        String resourcePath = "/" + UserDaoTest.class.getSimpleName() + ".xml";
        log.info("resourcePath:{}",resourcePath);
        InputStream resourceAsStream = UserDaoTest.class
                .getResourceAsStream(resourcePath);
        if (resourceAsStream == null) {
            throw new NullPointerException(resourcePath + " not found");
        }
        final IDataSet data = new XmlDataSet(resourceAsStream);

        try {
            DatabaseOperation.CLEAN_INSERT.execute(conn, data);
        } finally {
            conn.close();
        }
    }

    @Test
    public void testNewUser() {
        Assertions.assertNotNull(jdbcConf);
        UserDao userDao = new UserDaoImpl(jdbcConf);

        User user = userDao.newUser();
        Assertions.assertNotNull(user);
        user.setUsername("tonydeng");
        user.setPassword("123456");

        userDao.saveOrUpdate(user);

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(new Long(2l),user.getId());
    }
}
