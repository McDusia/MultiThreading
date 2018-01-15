import java.util.Random;

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
        {
            int a = rand.nextInt(m);  //tyle elem wstawi do bufora

            Object[] msg = null;
            try {
                 msg = buffer.take(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //for(int j = 0;j<m;j++)
            //System.out.println("Consumer " + myNr + ", iter. "+ i +" took: ");
        }
    }
}