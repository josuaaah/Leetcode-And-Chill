import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TobogganTrajectory {

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

    static long getNumTrees(List<String> map, int right, int down) {
        int row = 0;
        int col = 0;
        long trees = 0;
        int depth = map.size();
        int width =  map.get(0).length();
        while (row < depth) {
            String rowLayout = map.get(row);
            if (rowLayout.charAt(col % width) == TREE) {
                trees++;
            }
            row = row + down;
            col = col + right;
        }
        return trees;
    }

    public static void main(String[] args) {
        List<String> map = readMap();
        long trees1 = getNumTrees(map, 1, 1);
        long trees2 = getNumTrees(map, 3, 1);
        long trees3 = getNumTrees(map, 5, 1);
        long trees4 = getNumTrees(map, 7, 1);
        long trees5 = getNumTrees(map, 1, 2);
        long product = trees1 * trees2 * trees3 * trees4 * trees5;
        System.out.println("The product is " + product + ".");
    }
}