package printers;

import java.util.HashMap;

/**
 * Created by joanna on 24.10.17.
 */
public class Main {

    public static PrintersMonitor monitor;

    private static HashMap<TaskCreator, Thread> taskCreatorThreadHashMap = new HashMap<>();

    public static void main(String args[]) {

        int printersAmount = 10;
        int threadsAmount = 30;
        monitor = new PrintersMonitor(printersAmount);


        for (int i = 0; i < threadsAmount; i++) {
            TaskCreator taskCreator = new TaskCreator(monitor);
            Thread taskCreatorThread = new Thread(taskCreator);
            taskCreatorThreadHashMap.put(taskCreator, taskCreatorThread);
        }

        taskCreatorThreadHashMap.values().forEach(Thread::start);

        taskCreatorThreadHashMap.values().forEach((thread) -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
