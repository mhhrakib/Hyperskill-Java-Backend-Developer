package converter;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static final String prompt = "Enter two numbers in format: {source base} {target base} (To quit type /exit) ";

    public static void main(String[] args) {
        int src, target;
        String cmd, number;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(prompt);
            cmd = scanner.nextLine();
            if (Objects.equals(cmd, "/exit")) {
                System.exit(0);
            }
            else {
                String[] nums = cmd.split(" ");
                src = Integer.valueOf(nums[0]);
                target = Integer.valueOf(nums[1]);

                while (true) {
                    System.out.print("Enter number in base " + src + " to convert to base " + target + " (To go back type /back) ");
                    number = scanner.nextLine();
                    if (Objects.equals(number, "/back")) {
                        break;
                    }
                    else {
                        BigInteger res = new BigInteger(number, src);
                        System.out.println("Conversion result: " + res.toString(target));
                    }
                }
            }
        }
    }
}
