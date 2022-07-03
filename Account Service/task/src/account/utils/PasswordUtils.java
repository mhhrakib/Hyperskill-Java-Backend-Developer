package account.utils;

import java.util.Arrays;

public class PasswordUtils {
    public static final String[] breachedPasswords = {"PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"};

    public static boolean isPasswordBreached(String password) {
        return Arrays.stream(breachedPasswords).anyMatch(password::equals);
    }
}
