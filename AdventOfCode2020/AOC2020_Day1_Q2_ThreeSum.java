import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ThreeSum {

    static Map<Integer,Boolean> readInputs() {
        Map<Integer,Boolean> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            map.put(scanner.nextInt(), true);
        }
        return map;
    }

    static int[] findThreeSum(Map<Integer,Boolean> map, int sum) {
        for (Integer input : map.keySet()) {
            int difference = sum - input;
            int[] twoSum = findTwoSum(map, difference, input);
            if (twoSum != null) {
                return new int[]{input, twoSum[0], twoSum[1]};
            }
        }
        return null;
    }

    private static int[] findTwoSum(Map<Integer,Boolean> map, int sum, int toExclude) {
        for (Integer input : map.keySet()) {
            int difference = sum - input;
            if (difference != toExclude && map.containsKey(difference)) {
                return new int[]{input, difference};
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<Integer,Boolean> map = readInputs();
        int[] result = findThreeSum(map, 2020);
        System.out.println(result[0] * result[1] * result[2]);
    }
}
