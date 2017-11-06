/**
 * Created by Madzia on 16.10.2017.
 */
class Buffer
{
    private String box;
    private boolean canTake = false;

    public synchronized String take()
    {
        //wait until nobody wants to read
        //czekaj az bedzie dostepne
        while (canTake == false)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e) { }
        }
        canTake = false;
        notifyAll();
        return box;
    }
    public synchronized void put(String wartosc)
    {
        //czekaj az bedzie niedostepne
        while (canTake == true)
        {
            try
            {
                wait();
            } catch (InterruptedException e) { }
        }
        box = wartosc;
        canTake = true;
        notifyAll();
    }
}