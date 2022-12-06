package org.example.forkjoin;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description split demo task
 * @Author: Levi.Ding
 * @Date: 2022/12/5 15:53
 * @Version V1.0
 */
@Slf4j
public class SplitDemoTask extends ForkJoinTask<String> {


    private Integer i ;

    private ForkJoinTask leftFork;

    private ForkJoinTask rightFork;

    private ForkJoinTask parent;

    private volatile static AtomicStampedReference<String> resultSync = new AtomicStampedReference<>("",0);

    public SplitDemoTask(Integer i, ForkJoinTask parent) {
        this.i = i;
        this.parent = parent;
    }

    @Override
    public String getRawResult() {
        if (rightFork == null){
            try {
                return resultSync.getReference();
            }finally {
                resultSync.compareAndSet(resultSync.getReference(),"",0,0);
            }
        }
        return i.toString();
    }

    @Override
    protected void setRawResult(String value) {
        i = Integer.valueOf(value);
    }

    @SneakyThrows
    @Override
    protected boolean exec() {
        int a ;
        if((a = i>>1)>1){
            SplitDemoTask splitDemoTask = new SplitDemoTask(a,this);
            leftFork = splitDemoTask.fork();
        }
        rightFork = parent;
        log.info("逻辑处理,{}",i);
        TimeUnit.SECONDS.sleep(2);
        if (leftFork!=null){
            String expat = leftFork.join().toString();
            String reference = resultSync.getReference();
            expat = reference+ expat + ",";
            if (!resultSync.weakCompareAndSet(reference,expat,0,0)){
                log.info("leftFork 占用,{},{}",reference,i);
            }
        }
        if (rightFork == null){
            String reference = resultSync.getReference();
            if (!resultSync.weakCompareAndSet(reference,reference+ i +",",0,0)){
                log.info("rightFork 占用,{},{}",reference,i);
            }
        }
        return true;
    }
}
