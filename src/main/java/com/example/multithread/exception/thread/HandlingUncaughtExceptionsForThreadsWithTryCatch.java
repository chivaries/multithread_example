package com.example.multithread.exception.thread;

public class HandlingUncaughtExceptionsForThreadsWithTryCatch {
    public static void main(String argc[]) {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("[" + currentThreadName + "]" + " thread starts here...");

        //  Exception을 처리하지 않은 것과 같은 결과를 얻었다.
        // 쓰레드가 던진 Exception을 쓰레드 바깥에서 try/catch로 바로 받을 수 없다!!
        try {
            new Thread(new ExceptionLeakingTask(), "Mythread-1").start();
        } catch (RuntimeException re) {
            System.out.println("!!!!!![" + currentThreadName + "]" + " Caught Exception!!!");
        }

        System.out.println("[" + currentThreadName + "]" + " thread ends here...");
    }
}
