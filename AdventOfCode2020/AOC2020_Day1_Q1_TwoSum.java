import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TwoSum {

    static Map<Integer,Boolean> readInputs() {
        Map<Integer,Boolean> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            map.put(scanner.nextInt(), true);
        }
        return map;
    }

    static int[] findTwoSum(Map<Integer,Boolean> map, int sum) {
        for (Integer input : map.keySet()) {
            int difference = sum - input;
            if (map.containsKey(difference)) {
                return new int[]{input, difference};
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<Integer,Boolean> map = readInputs();
        int[] result = findTwoSum(map, 2020);
        System.out.println(result[0] * result[1]);
    }
}
