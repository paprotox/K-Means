import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Alg {
    private List<Point> points;
    private List<Cluster> centroids;
    private int k;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\033[1;31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String YELLOW_BOLD = "\033[1;33m";

    public Alg(int k) {
        this.k = k;
    }

    public void mainLoop() {
        boolean mapHasChanged = true;
        int counter = 1;
        while (mapHasChanged) {

            System.out.println(ANSI_RED + "Pętla nr: " + counter + ANSI_RESET);
            for (Point p : points) {
                Cluster cluster = findNearestCluster(p);
                cluster.getAssigned().add(p);
            }


            for (Cluster c : centroids) {
                System.out.print(YELLOW_BOLD + "Centroid: " + c.getId() + " Points assigned: "+ ANSI_RESET + ANSI_YELLOW + c.getAssigned().stream().map(Point::getId).collect(Collectors.toList()) + ANSI_RESET + "\t");
                System.out.println("Sum of length: " + sumOfLength(c, c.getAssigned()));
            }

            //VECTOR centroidów przed aktualizacją
            System.out.println(ANSI_CYAN + "VECTOR centroidów przed aktualizacją" + ANSI_RESET);
            List<List<Double>> przed = centroids.stream().map(Cluster::getVector).collect(Collectors.toList());
            przed.stream().forEach(System.out::println);

            moveCentroids();

            //VECTOR centroidów po aktualizacją
            System.out.println(ANSI_CYAN + "VECTOR centroidów po aktualizacji" + ANSI_RESET);
            List<List<Double>> po = centroids.stream().map(Cluster::getVector).collect(Collectors.toList());
            po.stream().forEach(System.out::println);

            if (przed.equals(po)) {
                mapHasChanged = false;
            }
            counter++;

            for(Cluster c : centroids) {
                c.getAssigned().clear();
            }
        }
    }

    public void moveCentroids() {
        for(int i = 0; i < centroids.size(); i++) {
            int mianownik = centroids.get(i).getAssigned().size();
            List<Double> tmp = new ArrayList<>();

            for(int j = 0; j < centroids.get(i).getVector().size(); j++) {
                double result = sumOfLicznik(centroids.get(i), j) / mianownik;
                tmp.add(j, result);
            }
            centroids.get(i).setVector(tmp);
        }
    }

    public double sumOfLength(Cluster c, List<Point> points) {
        double sumDistance = 0;
        for(Point p : points) {
            double dis = calculateDistance(p, c);
            sumDistance += dis;
        }

        return sumDistance;
    }

    public double sumOfLicznik(Cluster c, int index) {
        double sum = 0;
        for(int i = 0; i < c.getAssigned().size(); i++){
            sum += c.getAssigned().get(i).getVector().get(index);
        }
        return sum;
    }

    public Cluster findNearestCluster(Point p) {
        Cluster nearest = centroids.get(0);
        double minDistance = calculateDistance(p, nearest);
        for(int i = 1; i < centroids.size(); i++) {
            if(calculateDistance(p, centroids.get(i)) < minDistance) {
                minDistance = calculateDistance(p, centroids.get(i));
                nearest = centroids.get(i);
            }
        }
        return nearest;
    }

    public double calculateDistance(Point point1, Point point2) {
        double distance = 0;
        for(int i = 0; i < point1.getVector().size(); i++) {
            distance += Math.pow((point1.getVector().get(i) - point2.getVector().get(i)),2);
        }
        return Math.sqrt(distance);
    }

    public List<Cluster> generateRandomCentroids() {
        centroids = new ArrayList<>();
        int id = 1;
        for(int i = 0; i < k; i++) {
            Cluster cluster;
            int index = (int)(Math.random() * points.size());
            cluster = new Cluster(id, points.get(index).getVector());
            centroids.add(cluster);
            id++;
        }
        return centroids;
    }

    public List<Point> readData(String path) {
        this.points = new ArrayList<>();
        try {
            int idPoint = 1;
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNextLine()) {
                String[] split = sc.next().split(",");
                int dim = split.length;
                ArrayList<Double> vector = new ArrayList<>();
                for(int i = 0; i < dim; i++) {
                    vector.add(Double.parseDouble(split[i]));
                }
                points.add(new Point(idPoint,vector));
                idPoint++;
            }
            Collections.shuffle(points);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return points;
    }
}
