import java.util.Scanner;

public class GScanner {
    private Scanner s;
    private String inputStr;

    public GScanner() {
        s = new Scanner(System.in);
    }

    // this nextInt() forgives mistakes
    public int nextInt() {
        int result = 0;
        this.inputStr = this.s.nextLine();
        String help = "";
        boolean positivSign = true;

        for (int i = 0; i < this.inputStr.length(); i++) {
            // only by digits the help string get the char
            // --> 12w3 == 123
            if (inputStr.charAt(i) >= '0' && inputStr.charAt(i) <= '9') {
                help += inputStr.charAt(i);
            }// the sign will also read but only when it is befor the number
            // --> -12w3 == -123 | 12-3 == 123
            // positivSign is a flag for the sign
            else if (inputStr.charAt(i) == '-' && help.isEmpty()) {
                positivSign = false;
            }
        }

        // convert the String into an int
        // 123 == 1*10^2 + 2*10^1 + 3*10^0
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

    //get the next Letter A-Z a-z
    // default is  
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

    // get the next yes or no from the console
    // default is no
    public boolean nextYN() {
        this.inputStr = this.s.nextLine();

        if (inputStr.toLowerCase().contains("yes")) {
            return true;
        } else if (inputStr.toLowerCase().contains("y")) {
            return true;
        }
        return false;
    }

    // get the next boolean form console
    // 0,1,t,f also are valid inputs
    // default is false
    public boolean nextBoolean() {
        this.inputStr = this.s.nextLine();

        if (inputStr.toLowerCase().contains("true")) {
            return true;
        } else if (inputStr.toLowerCase().contains("false")) {
            return false;
        } else if (inputStr.toLowerCase().contains("t") || inputStr.toLowerCase().contains("1")) {
            return true;
        } else if (inputStr.toLowerCase().contains("f") || inputStr.toLowerCase().contains("0")) {
            return false;
        }
        return false;
    }
}
