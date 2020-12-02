import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

class PasswordChecker {

    static List<String> readInputs() {
        List<String> inputs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }
        return inputs;
    }

    static String[] segment(String input) {
        String delimiters = "-| |: ";
        return input.split(delimiters);
    }

    static boolean isValid(String[] segments) {
        int min = Integer.parseInt(segments[0]);
        int max = Integer.parseInt(segments[1]);
        char letter = segments[2].charAt(0);
        String password = segments[3];
        long count = password.chars()
            .mapToObj(c -> (char) c)
            .filter(c -> c == letter)
            .count();
        return count >= min && count <= max;
    }

    public static void main(String[] args) {
        List<String> inputs = readInputs();
        long numValidPasswords = inputs.stream()
            .map(input -> segment(input))
            .filter(segments -> isValid(segments))
            .count();
        System.out.println("Number of valid passwords: " + numValidPasswords);
    }
}
