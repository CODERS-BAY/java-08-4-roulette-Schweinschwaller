import java.util.Random;
import java.util.Scanner;

public class Roulette {
    public static void main(String[] args) {

        int money = 100;
        int setCommitment = 0;
        int menu = 0;
        int chosenNumber = 0;
        Scanner s = new Scanner(System.in);
        Random fortuna = new Random();
        boolean wantToLeave = false;

        System.out.println("Hi");

        do {
            System.out.println("Faites vos jeux!(Make your bets!)");
            do {
                System.out.println("how much you want to set?");
                setCommitment = s.nextInt();
                if (setCommitment == -1) {
                    wantToLeave = true;
                    break;
                }
            } while (setCommitment > money || setCommitment <= 0);
            if (wantToLeave) {
                break;
            }
            do {
                System.out.println("1. red numbers");
                System.out.println("2. black numbers");
                System.out.println("3. first third (1-12)");
                System.out.println("4. secound third (13-24)");
                System.out.println("5. third third (25-36)");
                System.out.println("6. number (0-36)");
                menu = s.nextInt();
                if (setCommitment == -1) {
                    wantToLeave = true;
                    break;
                }
            } while (menu <= 0 || menu > 6);
            money -= setCommitment;
            if (wantToLeave) {
                break;
            }
            if (menu == 6) {
                do {
                    System.out.println("Witch number?");
                    chosenNumber = s.nextInt();
                    if (setCommitment == -1) {
                        wantToLeave = true;
                        break;
                    }
                } while (chosenNumber < 0 || chosenNumber > 36);
                if (wantToLeave) {
                    break;
                }
            }
            if (wantToLeave) {
                break;
            }
            System.out.println("Rien ne va plus(No more bets)");

            int fortunaChoice = fortuna.nextInt(37);
            System.out.print("\n"+ fortunaChoice + " ");
            if (fortunaChoice % 2 == 0) {
                System.out.println("black ");
            } else {
                System.out.println("red ");
            }
            System.out.println("is the Winning number!!");

            if (menu == 6 && chosenNumber == fortunaChoice) {
                money += setCommitment * 36;
            } else if (menu == 3 && (fortunaChoice <= 12 && fortunaChoice >= 1)) {
                money += setCommitment * 3;
            } else if (menu == 4 && (fortunaChoice <= 24 && fortunaChoice >= 13)) {
                money += setCommitment * 3;
            } else if (menu == 5 && (fortunaChoice <= 36 && fortunaChoice >= 25)) {
                money += setCommitment * 3;
            } else if (menu == 1 && fortunaChoice % 2 != 0) {
                money += setCommitment * 2;
            } else if (menu == 2 && fortunaChoice % 2 == 0) {
                money += setCommitment * 2;
            }
            System.out.println("---------------------------");
            System.out.println("You have now " + money + "$");
            System.out.println("---------------------------");

        } while (money > 0);
        System.out.println("We hope you had a good time!");
        System.out.println("See you next time!");
        System.out.println("\nYou have " + money + "$");
    }
}