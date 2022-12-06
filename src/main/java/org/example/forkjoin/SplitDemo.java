package org.example.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @Description Split Demo
 * @Author: Levi.Ding
 * @Date: 2022/12/6 13:54
 * @Version V1.0
 */
@Slf4j
public class SplitDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //  ForkJoinPool
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long startTime = System.currentTimeMillis();
        ForkJoinTask<String> submit = pool.submit(new SplitDemoTask(1 << 5, null));
        log.info("fork join task is done! result:{}",submit.isDone());
        log.info("fork join result :{}",submit.get());
        log.info("end,time:{}",System.currentTimeMillis() - startTime);

        // invoke  主线程执行 但是会等待最终结果
        startTime = System.currentTimeMillis();
        String result = new SplitDemoTask(1 << 5, null).invoke();
        log.info("fork join result :{}",result);
        log.info("end,time:{}",System.currentTimeMillis() - startTime);

    }
}
