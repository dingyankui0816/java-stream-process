package org.example.parallel;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Description todo
 * @Author: Levi.Ding
 * @Date: 2022/11/29 16:02
 * @Version V1.0
 */
@Slf4j
public class IntStreamDemo {


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3));
        Stream<Integer> integerStream = list.parallelStream().map(i -> i + 1).skip(2).filter(i -> i > 2);

        int sum = integerStream.collect(Collectors.summingInt(Integer::intValue));
        log.info("sum = "+ sum);
    }
}
