import java.util.List;

public class Main {
    public static void main(String[] args) {

        Alg alg = new Alg(3);
        alg.readData("src/test3.txt");
        alg.generateRandomCentroids();
        alg.mainLoop();

    }
}
