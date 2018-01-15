/**
 * Created by Madzia on 20.11.2017.
 */
public class Main {

    public static void main(String[] args) {

        int n= 1;
        for(int i = 0; i< 10; i++) {
            long before = System.nanoTime();
            new Mandelbrot(n).setVisible(true);
            long after = System.nanoTime();
            System.out.println(after - before);
        }

    }

}
