package com.github.tonydeng.tutorials.excel;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class MyBeanCopyTest {


    @Data
    private class A {

        private int id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class B {

        private long id;
        private String name;
//        private boolean sex;
    }

    @Test
    void testConvert() {
        int index = 2;
        List<A> aList = Lists.newArrayList();
        for (int i = 0; i < index; i++) {
            A a = new A();
            a.setId(i);
            a.setName("name" + i);
            aList.add(a);
        }
        Assertions.assertEquals(index, aList.size());
        List<B> targets = MyBeanCopy.convert(aList, B.class);

        Assertions.assertEquals(index, targets.size());
        log.info("{}", aList.size());
        log.info("{}", targets.size());
        targets.forEach(b -> log.info("{}", b));
    }
}
