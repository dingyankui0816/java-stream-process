package org.example.nonparallel;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Description int stream demo
 * @Author: Levi.Ding
 * @Date: 2022/11/22 14:45
 * @Version V1.0
 */
@Slf4j
public class IntStreamDemo {

    /**
     * @Description: 非并行流 stream 执行流程
     * 名词解释  IntArraySpliterator : stream 入参控制器
     *          IntPipeline extend AbstractPipeline       : stream sink 流程创建,stream 执行触发器
     *          Sink               : 类似于责任链中的filter  多个sink 通过 单向队列链接
     *
     *          https://www.processon.com/view/link/637c902c0791290b4baf42a4
     * @author Levi.Ding
     * @date 2022/11/22 17:02
     * @param args :
     * @return : void
     */
    public static void main(String[] args) {
        IntStream intStream = Arrays.stream(new int[]{1, 2, 3}).map(i -> i + 1).skip(2).filter(i -> i > 2);
        int sum = intStream.sum();
        log.info("sum = "+ sum);
    }

}
