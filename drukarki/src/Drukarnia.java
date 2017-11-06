/**
 * Created by Madzia on 24.10.2017.
 */
public class Drukarnia {

    private static Consumer[] consumers = new Consumer[30];
    private static Thread[] consumerThreads = new Thread[30];


    public static void main(String[] args) throws Exception
    {
        int printersQnt = 10;

        PrintersMonitor monitor = new PrintersMonitor(printersQnt);

        for(int i = 0;i<30;i++) {
            consumers[i] = new Consumer(monitor, i);
            consumerThreads[i] = new Thread(consumers[i]);
        }

        for(int i = 0;i<30;i++) {
            consumerThreads[i].start();
        }

        for(int i = 0;i<30;i++) {
            try {
                consumerThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
