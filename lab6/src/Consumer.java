import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by Madzia on 10.12.2017.
 */

class Consumer implements Runnable
{
    private Random rand = new Random();
    private BufferProxy buffer;
    private int myNr;

    public Consumer(BufferProxy buffer, int n)
    {
        this.buffer = buffer;
        myNr = n;
    }

    public void run()
    {
        while(true) {
            Future msg = buffer.take();

            while (!msg.isAvailable()) {
                System.out.println("Consumer " + this.myNr + " is waiting.");

                try {
                    sleep((int)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Consumer " + myNr + " took: "+ msg.returnResult());

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}