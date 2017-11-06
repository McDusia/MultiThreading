/**
 * Created by Madzia on 16.10.2017.
 */
class Consumer implements Runnable
{
    //private Buffer buffer;
    private BoundedBuffer bbuffer;
    private int myNr;

    //public Consumer(BoundedBuffer bbuffer, Buffer buffer, int n)
    public Consumer(BoundedBuffer bbuffer, int n)
    {
        this.bbuffer = bbuffer;
        //this.buffer = buffer;
        myNr = n;
    }
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            //String msg = buffer.take();
            Object msg = null;
            try {
                 msg = bbuffer.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumer " + myNr + ", iter. "+ i +" took: " + (String)msg);
        }
    }
}