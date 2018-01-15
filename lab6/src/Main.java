/**
 * Created by Madzia on 10.12.2017.
 */

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception
    {
        int nr = 100;
        int m = 1000;
        Scheduler scheduler = new Scheduler();
        Buffer b = new Buffer(m);
        System.out.println("tu");
        BufferProxy buffer = new BufferProxy(scheduler, b);
        System.out.println("tu");
        Producer[] producers = new Producer[nr];
        Consumer[] consumers = new Consumer[nr];
        Thread[] producersThreads = new Thread[nr];
        Thread[] consumerThreads = new Thread[nr];




        //create threads
        for(int i = 0;i<nr;i++) {

            producers[i] = new Producer(buffer, i);
            consumers[i] = new Consumer(buffer, i);
            producersThreads[i]= new Thread(producers[i]);
            consumerThreads[i] = new Thread(consumers[i]);
        }

        //start threads
        for(int i = 0;i<nr;i++) {
            producersThreads[i].start();
            consumerThreads[i].start();
        }

        //wait for all threads
        for(int i = 0;i<nr;i++) {
            producersThreads[i].join();
            consumerThreads[i].join();
        }



    }

}