package com.github.tonydeng.tutorials.excel.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelListener extends AnalysisEventListener {

    private List<Object> data = Lists.newArrayList();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        log.info("{}", analysisContext.getCurrentSheet());
        data.add(o);
        if (data.size() >= 100) {
            doSomething();
            data = Lists.newArrayList();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        doSomething();
    }

    void doSomething() {
        data.forEach(d -> log.info("{}", d));
    }
}
