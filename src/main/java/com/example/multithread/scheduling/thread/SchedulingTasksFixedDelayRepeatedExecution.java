package com.example.multithread.scheduling.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class SchedulingTasksFixedDelayRepeatedExecution {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("Main thread starts here...");

        Timer timer = new Timer("Timer-thread", false);
        Date currentTime = new Date();
        System.out.println("["+currentThreadName+"] Current time" + dateFormatter.format(currentTime));

        Date scheduledTime = TimeUtils.getFutureTime(currentTime, 1000);
        ScheduledTask task0 = new ScheduledTask(100);
        long periodMillis = 1000;
        timer.schedule(task0, scheduledTime, periodMillis); //schedule(TimerTask task, Date firstTime, long period)
        System.out.println("["+currentThreadName+"] Task-0 is scheduled for running at " + dateFormatter.format(currentTime));

        long delayMillis = 5000;
        periodMillis = 5000;
        ScheduledTask task1 = new ScheduledTask(100);
        timer.schedule(task1, delayMillis, periodMillis); //schedule(TimerTask task, long delay, long period)
        System.out.println("["+currentThreadName+"] Task-1 is scheduled for running "+ delayMillis/1000 + " at " + dateFormatter.format(currentTime));

        TimeUnit.MILLISECONDS.sleep(11000);
        timer.cancel();

        System.out.println("Main thread ends here...");
    }
}
