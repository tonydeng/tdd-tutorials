package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
public class JdbcConfTest {
    private static JdbcConf jc;

    @BeforeAll
    public static void init() {
        jc = new JdbcConf("driver", "", "", "");
    }

    @Test
    public void testCreateJdbcConfTest() {
        Assertions.assertEquals("driver", jc.getDriverName());
        log.info("{}", jc.getDriverName());
    }
}
