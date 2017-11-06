/**
 * Created by Madzia on 10.10.2017.
 */

public final class Var
{
    public int var;
    public Semaphore s;

    public Var() {
        var = 0;
        s =  new Semaphore();
    }

    public void increment() {
        s.opusc();
        this.var ++;
        s.podnies();
    }

    public void decrement() {
        s.opusc();
        this.var --;
        s.podnies();
    }

}