import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Madzia on 21.11.2017.
 */

//to jest Servant
public class Buffer {

    private Queue buffer = new LinkedList<>();
    private int size = 100;

    Buffer(int size)
    {
        this.size = size;
    }

    public void put(int n) {

        buffer.add(n);
    }

    public int take() {
        int val = (int)buffer.remove();
        return val;
    }

    public boolean isEmpty() {
        return(buffer.isEmpty());
    }

    public boolean isFull() {
        return(buffer.size()==size);
    }

}
