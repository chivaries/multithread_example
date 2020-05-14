package com.example.multithread.exception.thread;

public class HandlingUncaughtExceptionsForThreads {
    public static void main(String argc[]) {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("["+ currentThreadName + "]" + " thread starts here...");

        new Thread(new ExceptionLeakingTask(), "Mythread-1").start();

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class ExceptionLeakingTask implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }

}