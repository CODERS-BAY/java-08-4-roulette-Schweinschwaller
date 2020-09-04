import java.util.Scanner;

public class GScanner {
    private Scanner s;
    private String inputStr;

    public GScanner() {
        s = new Scanner(System.in);
    }

    public int nextInt() {
        int result = 0;
        this.inputStr = this.s.nextLine();
        String help = "";
        boolean positivSign = true;

        for (int i = 0; i < this.inputStr.length(); i++) {
            if (inputStr.charAt(i) >= '0' && inputStr.charAt(i) <= '9') {
                help += inputStr.charAt(i);
            } else if (inputStr.charAt(i) == '-' && help.isEmpty()) {
                positivSign = false;
            }
        }
        for (int i = 0, j = help.length() - 1; i < help.length(); i++, j--) {
            if (help.charAt(i) >= '0' && help.charAt(i) <= '9') {
                result += Math.pow(10, j) * (help.charAt(i) - '0');
            }
        }
        if (positivSign) {
            return result;
        }
        return result * -1;
    }

    public String nextLine() {
        return this.s.nextLine();
    }

    public char nextChar() {
        this.inputStr = this.s.nextLine();

        if (!inputStr.isEmpty()) {
            for (int i = 0; i < inputStr.length(); i++) {
                if (inputStr.charAt(i) >= 'A' && inputStr.charAt(i) <= 'Z') {
                    return inputStr.charAt(i);
                } else if (inputStr.charAt(i) >= 'a' && inputStr.charAt(i) <= 'z') {
                    return inputStr.charAt(i);
                }
            }
        }
        return ' ';
    }

}
