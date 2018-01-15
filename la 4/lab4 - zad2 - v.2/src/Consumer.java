import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Madzia on 16.10.2017.
 */
class Consumer implements Runnable
{
    private Random rand = new Random();
    private BoundedBuffer buffer;
    private int myNr;
    private int m;
    
    public Consumer(BoundedBuffer buffer, int n, int m)
    {
        this.buffer = buffer;
        myNr = n;
        this.m = m;
        
    }
    public void run()
    {
        for (int i = 0; i < 1; i++)
        //    while(true)
        {
            int a = rand.nextInt(m+1);  //tyle elem wstawi do bufora
            if(a== 0) a = 1;
            Object[] msg = null;
            try {
                 msg = buffer.take(a, myNr);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //for(int j = 0;j<m;j++)
            //System.out.println("Consumer " + myNr + ", iter. "+ i +" took: ");

        }
    }


}