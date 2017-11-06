/**
 * Created by Madzia on 10.10.2017.
 */

public class Incrementator extends Thread{

    Var a;
    int counter;

    Incrementator(Var var, int counter)
    {
        this.a = var;
        this.counter = counter;
    }


    public void run(){

        for(int i= 0; i<Main.counter;i++)
            Main.a.increment();
    }

}