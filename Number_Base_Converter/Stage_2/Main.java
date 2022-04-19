package converter;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    static final String prompt = "Do you want to convert /from decimal or /to decimal? (To quit type /exit) ";

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
        String cmd, result;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(prompt);
            cmd = scanner.nextLine();
//          System.out.println("cmd: " + cmd);
            if (Objects.equals(cmd, "/from")) {
                System.out.print("Enter a number in decimal system: ");
                num = scanner.nextInt();
                System.out.print("Enter target base: ");
                target = scanner.nextInt();
                scanner.nextLine();
                result = convert(num, target);
                System.out.println("Conversion result: " + result);
            }
            else if (Objects.equals(cmd, "/to")) {
                String src;
                System.out.print("Enter source number: ");
                src = scanner.nextLine();
                System.out.print("Enter source base: ");
                target = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Conversion to decimal result: " + Integer.parseInt(src, target));
            }
            else {
                System.exit(0);
            }
        }

    }
}
