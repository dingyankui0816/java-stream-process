package org.example.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @Description ForkJoinPool 中 任务队列是一个队列数组 `workQueues`
 * forkjoin 中的工作窃取 即当线程启动后 循环调用 ForkJoinWorkerThread.scan(WorkQueue w, int r) 方法 实现工作窃取
 * 感觉工作窃取所带来当性能提升并没有多少，只是提高普通线程池中一个 `workQueue` 所导致线程 queue.take() 抢占的性能
 * 并且由于 工作窃取的问题 所导致 fork join 无法保证整体的`workQueues`的FIFO顺序，在保证单个`workQueue`顺序的前提，会随机的根据当前顺序抢断其他队列中的任务
 * {@link java.util.concurrent.ForkJoinPool}  Preference rules give
 *      * first priority to processing tasks from their own queues (LIFO
 *      * or FIFO, depending on mode), then to randomized FIFO steals of
 *      * tasks in other queues.
 *
 *
 * 分治
 * {@link AbstractTask#compute()} 将task进行左右切分
 * 切分策略
 * {@link ArrayList.ArrayListSpliterator#trySplit()}
 * 切分后通过 {@link AbstractTask#fork()} 将任务加入到 {@link java.util.concurrent.ForkJoinPool} 中
 * {@see AbstractTask}
 *
 *
 * @Author: Levi.Ding
 * @Date: 2022/11/30 17:45
 * @Version V1.0
 */
@Slf4j
public class Demo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinTask<String> submit = pool.submit(new DemoTask());
        log.info("fork join task is done! result:{}",submit.isDone());
        log.info("fork join result :{}",submit.get());

    }
}
