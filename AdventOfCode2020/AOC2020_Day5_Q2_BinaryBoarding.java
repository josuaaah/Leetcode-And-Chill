import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BinaryBoarding {

    static int MIN_ROW = 0;
    static int MAX_ROW = 127;

    static int MIN_COL = 0;
    static int MAX_COL = 7;

    static char LOWER_HALF_ROW_CHAR = 'F';
    static char UPPER_HALF_ROW_CHAR = 'B';

    static char LOWER_HALF_COL_CHAR = 'L';
    static char UPPER_HALF_COL_CHAR = 'R';

    static int NUM_ROW_CHARS = 7;
    static int NUM_COL_CHARS = 3;

    static List<String> readInput() {
        List<String> passes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            passes.add(scanner.nextLine());
        }
        scanner.close();
        return passes;
    }

    static int getRow(String pass) {
        int lowerRow = MIN_ROW;
        int higherRow = MAX_ROW;
        String rowInfo = pass.substring(0, NUM_ROW_CHARS);

        for (int i = 0; i < NUM_ROW_CHARS - 1; i++) {
            if (rowInfo.charAt(i) == LOWER_HALF_ROW_CHAR) {
                higherRow = (int) Math.floor(((double) (lowerRow + higherRow)) / 2);
            }
            if (rowInfo.charAt(i) == UPPER_HALF_ROW_CHAR) {
                lowerRow = (int) Math.ceil(((double) (lowerRow + higherRow)) / 2);
            }
        }

        int row = Integer.MAX_VALUE;
        if (rowInfo.charAt(NUM_ROW_CHARS - 1) == LOWER_HALF_ROW_CHAR) {
            row = lowerRow;
        }
        if (rowInfo.charAt(NUM_ROW_CHARS - 1) == UPPER_HALF_ROW_CHAR) {
            row = higherRow;
        }

        return row;
    }

    static int getCol(String pass) {
        int lowerCol = MIN_COL;
        int higherCol = MAX_COL;
        String colInfo = pass.substring(NUM_ROW_CHARS);
        for (int i = 0; i < NUM_COL_CHARS - 1; i++) {
            if (colInfo.charAt(i) == LOWER_HALF_COL_CHAR) {
                higherCol = (int) Math.floor(((double) (lowerCol + higherCol)) / 2);
            }
            if (colInfo.charAt(i) == UPPER_HALF_COL_CHAR) {
                lowerCol = (int) Math.ceil(((double) (lowerCol + higherCol)) / 2);
            }
        }

        int col = Integer.MAX_VALUE;
        if (colInfo.charAt(NUM_COL_CHARS - 1) == LOWER_HALF_COL_CHAR) {
            col = lowerCol;
        }
        if (colInfo.charAt(NUM_COL_CHARS - 1) == UPPER_HALF_COL_CHAR) {
            col = higherCol;
        }

        return col;
    }

    static int[] toCoordinates(String pass) {
        return new int[]{getRow(pass), getCol(pass)};
    }

    static int toId(int[] coordinates) {
        return coordinates[0] * 8 + coordinates[1];
    }

    static Set<Integer> getAllIds() {
    	Set<Integer> allIds = new HashSet<>();
    	for (int row = MIN_ROW; row < MAX_ROW; row++) {
    		for (int col = MIN_COL; col < MAX_COL; col++) {
    			allIds.add(toId(new int[]{row, col}));
    		}
    	}
    	return allIds;
    }

    public static void main(String[] args) {
    	Set<Integer> allIds = getAllIds();
        List<String> passes = readInput();
        passes.stream()
            .map(pass -> toCoordinates(pass))
            .map(coordinates -> toId(coordinates))
            .mapToInt(id -> id)
            .forEach(id -> allIds.remove(id));
        System.out.println(allIds);
    }
}
