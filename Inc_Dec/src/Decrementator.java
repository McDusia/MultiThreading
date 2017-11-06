/**
 * Created by Madzia on 10.10.2017.
 */
public class Decrementator extends Thread{
    Var a;
    int counter;

    Decrementator(Var var, int counter)
    {
        this.a = var;
        this.counter = counter;
    }

    public void run(){
        for(int i= 0; i<Main.counter;i++)
            Main.a.decrement();
    }
}
