import java.util.ArrayList;
import java.util.List;

public class Point {
    private List<Double> vector;
    private int id;

    public Point(int id, List<Double> vector) {
        this.vector = vector;
        this.id = id;
    }

    public List<Double> getVector() {
        return vector;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
