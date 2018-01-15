/**
 * Created by Madzia on 14.11.2017.
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;


class CountPoint implements Callable
{
    //wcześniej 570
    private int MAX_ITER = 570;
    private double ZOOM = 150;
    private double zx, zy, cX, cY, tmp;
    private int x,y;

        public CountPoint(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public int[] call() {
            zx = zy = 0;
            cX = (x - 400) / ZOOM;
            cY = (y - 300) / ZOOM;
            int iter = MAX_ITER;
            while (zx * zx + zy * zy < 4 && iter > 0) {
                tmp = zx * zx - zy * zy + cX;
                zy = 2.0 * zx * zy + cY;
                zx = tmp;
                iter--;
            }
            int[] result = new int[3];
            result[0] = x;
            result[1] = y;
            result[2] = iter;
            return result;
        }
}

public class Mandelbrot extends JFrame {

    private BufferedImage I;

    public Mandelbrot(int n) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(n);
        //ExecutorService pool = Executors.newWorkStealingPool();
        //dla tego ostatniego n = parallelism - poziom równoległości
        //ExecutorService pool = Executors.newWorkStealingPool(n);
        Set<Future<int[]>> set = new HashSet<>();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Callable<int[]> callable =
                        new CountPoint(x, y);
                Future<int[]> future = pool.submit(callable);
                set.add(future);
            }
        }

        for(Future<int[]> future : set)
        {
            int result[] = new int[0];
            try {
                result = future.get();
            } catch (InterruptedException|ExecutionException e) {
                e.printStackTrace();
            }
            I.setRGB(result[0], result[1], result[2] | (result[2] << 8));
        }

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    /*public static void main(String[] args) {

        new Mandelbrot(n).setVisible(true);
    }*/
}