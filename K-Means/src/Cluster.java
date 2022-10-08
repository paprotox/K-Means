import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Cluster extends Point{
    private int clusterNumber;
    private List<Double> vector;
    private List<Point> assigned;

    public Cluster(int clusterNumber, List<Double> vector) {
        super(clusterNumber, vector);
        this.vector = vector;
        this.assigned = new ArrayList<>();
    }


    public int getClusterNumber() {
        return clusterNumber;
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }

    public List<Point> getAssigned() {
        return assigned;
    }

}
