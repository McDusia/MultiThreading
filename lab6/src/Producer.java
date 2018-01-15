import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by Madzia on 10.12.2017.
 */

class Producer implements Runnable
{
    private Random rand = new Random();
    private BufferProxy buffer;
    private int myNr;


    public Producer(BufferProxy bbuffer, int n)
    {
        this.buffer = bbuffer;
        myNr = n;
    }
    public void run()
    {

        while(true)
        {
            int a = rand.nextInt(100);  //taki elem wstawi do bufora
            buffer.put( a);
            System.out.println("Producer "+ myNr+ " has put: " + a);
            try
            {
                sleep((int)(Math.random() * 1000)+2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }


}