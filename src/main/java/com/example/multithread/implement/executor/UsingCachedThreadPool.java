package com.example.multithread.implement.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsingCachedThreadPool {
    public static void main(String[] args) {
        System.out.println("Main thread starts here...");

        //CachedThreadPool은 FixedThreadPool과 달리 태스크의 숫자에 따라 쓰레드의 숫자가 가변
        ExecutorService execService = Executors.newCachedThreadPool();

        execService.execute(new MyThreadTask());
        execService.execute(new MyThreadTask());
        execService.execute(new MyThreadTask());
        execService.execute(new MyThreadTask());

        execService.shutdown();

        System.out.println("Main thread ends here...");
    }
}
