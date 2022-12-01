package org.example.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 * @Description fork join task demo
 * @Author: Levi.Ding
 * @Date: 2022/11/30 17:40
 * @Version V1.0
 */
@Slf4j
public class DemoTask extends ForkJoinTask<String> {


    private int i = 0;
    private  String result ;

    @Override
    public String getRawResult() {
        return result;
    }

    @Override
    protected void setRawResult(String value) {
        result = value;
    }

    @Override
    protected boolean exec() {
        try {
            i++;
            TimeUnit.SECONDS.sleep(5);
            log.info("执行次数, i:{}",i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setRawResult("levi");
        quietlyComplete();
        return false;
    }
}
