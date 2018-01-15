import java.util.Random;


/**
 * Created by Madzia on 16.10.2017.
 */
class Producer implements Runnable
{
    private Random rand = new Random();

    //private Buffer buffer;
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
        {
            int a = rand.nextInt(m);  //tyle elem wstawi do bufora

            try {
                bbuffer.put(1, a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("Producer "+ myNr+" iter. " + i + " has put: " + i);
            /*try
            {
                sleep((int)(Math.random() * 100));
            }
            catch (InterruptedException e) {  }*/
        }
    }


}