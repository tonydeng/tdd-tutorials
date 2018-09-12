package com.github.tonydeng.tutorials.tdd.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
public class GreeterTest {

    private static Greeter greeter;

    @BeforeAll
    public static void init() {
        greeter = new Greeter();
    }

    @Test
    public void testShouldSayHelloToTheWorld() {
        Assertions.assertNotNull(greeter.sayHello());
        Assertions.assertEquals("Hello World", greeter.sayHello());
    }
}
