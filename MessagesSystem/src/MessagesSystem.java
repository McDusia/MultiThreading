/**
 * Created by Madzia on 16.10.2017.
 */
class MessagesSystem
{
    public static void main(String[] args) throws Exception
    {

        BoundedBuffer bbuffer = new BoundedBuffer();
        Producer[] producers = new Producer[10];
        Consumer[] consumers = new Consumer[10];
        Thread[] producersThreads = new Thread[10];
        Thread[] consumerThreads = new Thread[10];
        //Buffer buffer = new Buffer();

        //create threads
        for(int i = 0;i<5;i++) {
            //producers[i] = new Producer(bbuffer, buffer, i);
            //consumers[i] = new Consumer(bbuffer, buffer, i);
            producers[i] = new Producer(bbuffer, i);
            consumers[i] = new Consumer(bbuffer, i);
            producersThreads[i]= new Thread(producers[i]);
            consumerThreads[i] = new Thread(consumers[i]);
        }

        //start threads
        for(int i = 0;i<5;i++) {
            producersThreads[i].start();
            consumerThreads[i].start();
        }

        //wait for all threads
        for(int i = 0;i<5;i++) {
            producersThreads[i].join();
            consumerThreads[i].join();
        }

    }

}