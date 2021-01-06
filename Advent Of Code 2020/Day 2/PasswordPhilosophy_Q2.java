import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

class PasswordPhilosophy {

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
        int posOne = Integer.parseInt(segments[0]);
        int posTwo = Integer.parseInt(segments[1]);
        char letter = segments[2].charAt(0);
        String password = segments[3];

        boolean posOneMatch = password.charAt(posOne - 1) == letter;
        boolean posTwoMatch = password.charAt(posTwo - 1) == letter;

        return (posOneMatch && !posTwoMatch) || (!posOneMatch && posTwoMatch);
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
