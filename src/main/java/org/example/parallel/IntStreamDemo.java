package org.example.parallel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

/**
 * @Description parallel stream
 *  并行流使用了 ForkJoinTask的分治算法，
 *  {@link java.util.stream.AbstractTask#compute()} 将task进行左右切分
 *  分治策略
 *      因为 {@link java.util.stream.ReferencePipeline#skip(long)}  的存在导致当前流是有状态的,
 *      所以分治策略取决于 {@link ReferencePipeline.StatefulOp#opEvaluateParallelLazy(PipelineHelper, Spliterator)}
 *      {@link Spliterator#trySplit()} 分治策略
 *
 * 切分后通过 {@link AbstractTask#fork()} 将任务加入到 {@link java.util.concurrent.ForkJoinPool} 中
 *
 *  https://www.processon.com/view/link/638f1f1f1e08536bffaabb0e
 * @Author: Levi.Ding
 * @Date: 2022/11/29 16:02
 * @Version V1.0
 */
@Slf4j
public class IntStreamDemo {


    @SneakyThrows
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            list.add(i);
        }
        Stream<Integer> integerStream = list.parallelStream().map(i -> i + 1).skip(2).filter(i -> i > 2);

        int sum = integerStream.collect(Collectors.summingInt(Integer::intValue));
        log.info("sum = "+ sum);
        System.in.read();

        ForkJoinPool.commonPool().submit(()->{
            log.info("测试");
        });
        TimeUnit.MINUTES.sleep(5);
    }
}
