package converter;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static final String prompt = "Enter two numbers in format: {source base} {target base} (To quit type /exit) ";

    static final String[] possibleValues = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    static double getDecimalValue(String digits, int srcBase) {
        double res = 0.0;
        int val, pow = -1;
        for (char ch: digits.toCharArray()) {
            if(ch >= 'A' && ch<='Z')
                val = (int)ch - 'A' + 10;
            else if(ch>='a' && ch<= 'z')
                val = (int)ch - 'a'+ 10;
            else if(ch>='0' && ch<='9')
                val = (int)ch - '0';
            else {
                //redundant
                val = (int)ch;
            }
            res += (val * Math.pow(srcBase, pow--));
        }
        return res;
    }

    static String convert(double value, int targetBase) {
        int precision = 5;
        StringBuilder res = new StringBuilder();
        double tmp = value;
        while (precision != 0) {
            tmp *= targetBase;
            int integralPart = (int) (tmp);
            res.append(possibleValues[integralPart]);
            tmp -= (double)(integralPart);
            precision--;
        }
        return res.toString();
    }

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
                        String[] parts = number.split("[.]");
                        String result = new BigInteger(parts[0], src).toString(target);
                        if (parts.length == 2) {
                            String fraction = parts[1];
                            result += ("." + convert(getDecimalValue(fraction, src), target));
                        }
                        System.out.println("Conversion result: " + result);
                    }
                }
            }
        }
    }
}
