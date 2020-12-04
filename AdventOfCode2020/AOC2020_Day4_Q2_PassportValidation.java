import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class PassportValidation {

    private static final String PASSPORT_DELIMITER_1 = "\n";
    private static final String PASSPORT_DELIMITER_2 = "  ";
    private static final String FIELD_DELIMITER = " ";
    private static final String KEY_VALUE_DELIMITER = ":";

    private static final String BIRTH_YEAR_KEY = "byr";
    private static final String ISSUE_YEAR_KEY = "iyr";
    private static final String EXPIRIATION_YEAR_KEY = "eyr";
    private static final String HEIGHT_KEY = "hgt";
    private static final String HAIR_COLOUR_KEY = "hcl";
    private static final String EYE_COLOUR_KEY = "ecl";
    private static final String PASSPORT_ID_KEY = "pid";
    private static final String COUNTRY_ID_KEY = "cid";

    private static final Function<String,Boolean> isValidByr = byr -> {
        int birthYear = Integer.valueOf(byr);
        return birthYear >= 1920 && birthYear <= 2002;
    };

    private static final Function<String,Boolean> isValidIyr = iyr -> {
        int issueYear = Integer.valueOf(iyr);
        return issueYear >= 2010 && issueYear <= 2020;
    };

    private static final Function<String,Boolean> isValidEyr = eyr -> {
        int expirationYear = Integer.valueOf(eyr);
        return expirationYear >= 2020 && expirationYear <= 2030;
    };

    private static final Function<String,Boolean> isValidHgt = hgt -> {
        if (hgt.contains("cm")) {
            int heightInCm = Integer.valueOf(hgt.replace("cm", ""));
            return heightInCm >= 150 && heightInCm <= 193;
        } else if (hgt.contains("in")) {
            int heightInInch = Integer.valueOf(hgt.replace("in", ""));
            return heightInInch >= 59 && heightInInch <= 76;
        } else {
            return false;
        }
    };

    private static final Function<String,Boolean> isValidHcl = hcl -> {
        if (hcl.charAt(0) == '#') {
            return hcl.replace("#", "").length() == 6;
        } else {
            return false;
        }
    };

    private static final Function<String,Boolean> isValidEcl = ecl -> {
        return ecl.equals("amb")
            || ecl.equals("blu")
            || ecl.equals("brn")
            || ecl.equals("gry")
            || ecl.equals("grn")
            || ecl.equals("hzl")
            || ecl.equals("oth");
    };

    private static final Function<String,Boolean> isValidPid = pid -> {
        return pid.length() == 9;
    };

    static String readInput() {
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine == PASSPORT_DELIMITER_1) {
                input = input.trim();
                input += PASSPORT_DELIMITER_2;
            } else {
                input += nextLine + FIELD_DELIMITER;
            }
        }
        return input;
    }

    static String[] readPassports(String input) {
        String[] passports = input.split(PASSPORT_DELIMITER_2);
        return passports;
    }

    static Map<String, String> readFields(String passport) {
        String[] fields = passport.split(FIELD_DELIMITER);
        Map<String, String> map = new HashMap<>();
        for (String field : fields) {
            String[] pair = field.split(KEY_VALUE_DELIMITER);
            map.put(pair[0], pair[1]);
        }
        return map;
    }

    static boolean isValid(Map<String, String> map) {
        boolean allFieldsPresent = 
            map.containsKey(BIRTH_YEAR_KEY) 
            && map.containsKey(ISSUE_YEAR_KEY)
            && map.containsKey(EXPIRIATION_YEAR_KEY)
            && map.containsKey(HEIGHT_KEY)
            && map.containsKey(HAIR_COLOUR_KEY)
            && map.containsKey(EYE_COLOUR_KEY)
            && map.containsKey(PASSPORT_ID_KEY);

        return allFieldsPresent
            && isValidByr.apply(map.get(BIRTH_YEAR_KEY))
            && isValidIyr.apply(map.get(ISSUE_YEAR_KEY))
            && isValidEyr.apply(map.get(EXPIRIATION_YEAR_KEY))
            && isValidHgt.apply(map.get(HEIGHT_KEY))
            && isValidHcl.apply(map.get(HAIR_COLOUR_KEY))
            && isValidEcl.apply(map.get(EYE_COLOUR_KEY))
            && isValidPid.apply(map.get(PASSPORT_ID_KEY));
    }

    public static void main(String[] args) {
        String input = readInput();
        String[] passports = readPassports(input);
        long valids = Arrays.stream(passports)
            .map(passport -> readFields(passport))
            .filter(fields -> isValid(fields))
            .count();
        System.out.println("There are " + valids + " valid passports.");
    }
}
