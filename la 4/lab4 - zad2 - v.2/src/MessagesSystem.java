/**
 * Created by Madzia on 16.10.2017.
 */
class MessagesSystem
{
    public static void main(String[] args) throws Exception
    {
        int m = 1000;
        int nr = 10;
        BoundedBuffer buffer = new BoundedBuffer(m, nr);
        Producer[] producers = new Producer[nr];
        Consumer[] consumers = new Consumer[nr];
        Thread[] producersThreads = new Thread[nr];
        Thread[] consumerThreads = new Thread[nr];


        //create threads
        for(int i = 0;i<nr;i++) {

            producers[i] = new Producer(buffer, i,m);
            consumers[i] = new Consumer(buffer, i, m);
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