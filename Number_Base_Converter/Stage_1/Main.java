package converter;

import java.util.Scanner;

public class Main {

    public static String convert(int num, int target) {
        if (num == 0)
            return "0";
        StringBuilder res = new StringBuilder();
        while (num != 0) {
            int rem = num % target;
            char ch;
            if (rem > 9) {
                ch = (char) ('A' + (rem - 10));
            } else {
                ch = (char) ('0' + rem);
            }
            res.append(ch);
            num /= target;
        }
        return res.reverse().toString();
    }

    public static void main(String[] args) {
        int num, target;
        String result;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number in decimal system:");
        num = scanner.nextInt();
        System.out.print("Enter target base:");
        target = scanner.nextInt();
        result = convert(num, target);
        System.out.println("Conversion result: " + result);
    }
}
