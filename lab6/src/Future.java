/**
 * Created by Madzia on 21.11.2017.
 */
public class Future {

    private int result;
    private boolean available = false;

    public boolean isAvailable() {
        return available;
    }

    public int returnResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;


    }
    public void setAvailability() {
        available = true;
    }

}
