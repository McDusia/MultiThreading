import static java.lang.Thread.sleep;

/**
 * Created by Madzia on 16.10.2017.
 */
class Producer implements Runnable
{
    //private Buffer buffer;
    private BoundedBuffer bbuffer;
    private int myNr;

    //public Producer(BoundedBuffer bbuffer, Buffer buffer, int n)
    public Producer(BoundedBuffer bbuffer, int n)
    {
        this.bbuffer = bbuffer;
        //this.buffer = buffer;
        myNr = n;
    }
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            //buffer.put("message" + i);
            try {
                bbuffer.put("message" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Producer "+ myNr+" iter. " + i + " has put: " + i);
            /*try
            {
                sleep((int)(Math.random() * 100));
            }
            catch (InterruptedException e) {  }*/
        }
    }


}