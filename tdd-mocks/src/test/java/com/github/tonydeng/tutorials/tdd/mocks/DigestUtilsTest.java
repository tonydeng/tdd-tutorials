package com.github.tonydeng.tutorials.tdd.mocks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class DigestUtilsTest {

    @Test
    public void testMd5() {
        String input = "hello";

        String digest = DigestUtils.md5Hex(input);
        Assertions.assertEquals("5d41402abc4b2a76b9719d911017c592", digest);
        log.info("{}", digest);
    }
}
