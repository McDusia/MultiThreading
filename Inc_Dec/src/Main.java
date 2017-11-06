/**
 * Created by Madzia on 10.10.2017.
 */

public class Main {

    public static int counter = 10000000;
    public static Var a = new Var();

    public static void main(String[] args) throws InterruptedException {

        //Var a = new Var(0);
        Incrementator t1 = new Incrementator(a, counter);
        t1.start();
        Decrementator t2 = new Decrementator(a, counter);
        t2.start();

        try{
            t1.join();
            t2.join();
        } catch(Exception e){System.out.println(e);}

        System.out.println("result "+a.var);
    }

}
