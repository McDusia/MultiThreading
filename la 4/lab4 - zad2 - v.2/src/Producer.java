import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Created by Madzia on 16.10.2017.
 */
class Producer implements Runnable
{
    private Random rand = new Random();

    private BoundedBuffer bbuffer;
    private int myNr;
    private int m;

    public Producer(BoundedBuffer bbuffer, int n, int m)
    {
        this.bbuffer = bbuffer;
        myNr = n;
        this.m = m;
    }
    public void run()
    {

        for (int i = 0; i < 1; i++)
        //while(true)
        {
            int a = rand.nextInt(m+1);  //tyle elem wstawi do bufora
            if(a== 0) a = 1;
            try {
                bbuffer.put(1, a, myNr);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("Producer "+ myNr+" iter. " + i + " has put "+ a);

        }
    }


}