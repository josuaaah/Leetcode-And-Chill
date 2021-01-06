import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TobogganTrajectory {

    private static final int DOWN = 1;
    private static final int RIGHT = 3;
    private static final int START = 0;

    private static final char CLEAR = '.';
    private static final char TREE = '#';

    static List<String> readMap() {
        List<String> map = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            map.add(scanner.nextLine());
        }
        return map;
    }

    static int getNumTrees(List<String> map) {
        int col = START;
        int trees = 0;
        int width =  map.get(0).length();
        for (String row : map) {
            if (row.charAt(col % width) == TREE) {
                trees++;
            }
            col = col + RIGHT;
        }
        return trees;
    }

    public static void main(String[] args) {
        List<String> map = readMap();
        int trees = getNumTrees(map);
        System.out.println("You will encounter " + trees + " trees.");
    }
}