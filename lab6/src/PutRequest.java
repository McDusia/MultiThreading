/**
 * Created by Madzia on 21.11.2017.
 */
public class PutRequest implements MethodRequest{

    private Future future;
    private Buffer aktywnyObiekt;

    private int n;

    PutRequest(int n, Buffer b) {
        this.n = n;
        aktywnyObiekt = b;
    }

    public boolean guard() {
        return(!aktywnyObiekt.isFull());
    }

    public void execute() {

        aktywnyObiekt.put(n);
    }

}
