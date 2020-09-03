import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Roulette {
    public static void main(String[] args) {

        System.out.println(" $$$ Welcome to George's Casino $$$ ");
        System.out.println("------------------------------------");
        System.out.println();
        System.out.println("How much money do you want to change in chips?!");
        Scanner s = new Scanner(System.in);
        int money = s.nextInt();

        if (money > 100) {
            System.out.println("☺");
        }

        Random fortuna = new Random();
        boolean wantToLeave = false;
        do {
            System.out.println("Chose a GAME");
            System.out.println("------------");
            System.out.println("1. ROULETTE");
            System.out.println("2. BLACK JACK");
            System.out.println("-1. Leave");
            int menu = s.nextInt();
            switch (menu) {
                case 1 -> money = playRoulette(money, s, fortuna);
                case 2 -> money = playBlackJack(money, s, fortuna);
                case -1 -> wantToLeave = true;
            }
            if (money <= 0) {
                System.out.println("How much money do you want to change in chips?!");
                money = s.nextInt();
            }
        } while (!wantToLeave);
    }

    public static int drawCard(Random fortuna) {
        return fortuna.nextInt(13) + 1;
    }

    public static void printCards(ArrayList<Integer> cards, Random fortuna) {
        for (int i = 0; i < cards.size(); i++) {
            switch (cards.get(i)) {
                case 1 -> System.out.print("|1|");
                case 2 -> System.out.print("|2|");
                case 3 -> System.out.print("|3|");
                case 4 -> System.out.print("|4|");
                case 5 -> System.out.print("|5|");
                case 6 -> System.out.print("|6|");
                case 7 -> System.out.print("|7|");
                case 8 -> System.out.print("|8|");
                case 9 -> System.out.print("|9|");
                case 10 -> System.out.print("|10|");
                case 11 -> System.out.print("|J|");
                case 12 -> System.out.print("|D|");
                case 13 -> System.out.print("|K|");
                case 14 -> {
                    int help = fortuna.nextInt(4);
                    if (help == 0) {
                        System.out.print("|♥|");
                    } else if (help == 1) {
                        System.out.print("|♦|");
                    } else if (help == 2) {
                        System.out.print("|♣|");
                    } else {
                        System.out.print("|♠|");
                    }
                }
            }
            System.out.print("  ");
        }
    }

    public static int cardValue(ArrayList<Integer> cards) {
        int value = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) >= 1 || cards.get(i) >= 10) {
                value += cards.get(i);
            } else if (cards.get(i) > 10 || cards.get(i) > 14) {
                value += 10;
            } else {
                if (value <= 10) {
                    value += 11;
                } else {
                    value += 1;
                }
            }
        }
        return value;
    }

    public static int playBlackJack(int money, Scanner s, Random fortuna) {

        int setCommitment = 0;

        ArrayList<Integer> playerCards = new ArrayList<Integer>();
        ArrayList<Integer> dealerCards = new ArrayList<Integer>();

        BlackJack:
        do {
            do {
                System.out.println("how much you want to set?");
                setCommitment = s.nextInt();
                if (setCommitment == -1) {
                    break BlackJack;
                }
            } while (setCommitment > money || setCommitment <= 0);

            money -= setCommitment;
            playerCards.add(drawCard(fortuna));
            playerCards.add(drawCard(fortuna));
            int playerHandValue = cardValue(playerCards);
            dealerCards.add(drawCard(fortuna));
            dealerCards.add(drawCard(fortuna));
            int dealerHandValue = cardValue(dealerCards);

            System.out.println("Dealer Cards:");
            printCards(dealerCards, fortuna);
            System.out.println();
            System.out.println("Your Cards:");
            printCards(playerCards, fortuna);
            System.out.println("\n-----------");

            int menu = 0;
            int input = 0;
            boolean playerDone = false;

            playerAction:
            do {

                do {
                    System.out.println("1. draw");
                    System.out.println("2. stand");
                    if (playerCards.get(0) == playerCards.get(1)) {
                        System.out.println("3. split");
                        menu = 3;
                    }
                    if (playerCards.size() == 2 && money >= 2 * setCommitment) {
                        System.out.println("4. double");
                        menu = 4;
                    }
                    input = s.nextInt();
                } while (input > menu && input < 1);

                switch (menu) {
                    case 1 -> {
                        playerCards.add(drawCard(fortuna));
                        playerHandValue = cardValue(playerCards);
                        System.out.println("Dealer Cards:");
                        printCards(dealerCards, fortuna);
                        System.out.println();
                        System.out.println("Your Cards:");
                        printCards(playerCards, fortuna);
                        System.out.println("\n-----------");
                    }
                    case 2 -> playerDone = true;
                    case 3 -> System.out.println("TODO");
                    case 4 -> {
                        money -= setCommitment;
                        setCommitment *= 2;
                        playerCards.add(drawCard(fortuna));
                        playerHandValue = cardValue(playerCards);
                        break playerAction;
                    }
                }
                if (playerHandValue > 21) {
                    playerDone = true;
                }

            } while (!playerDone);

            // DealerAction
            while (dealerHandValue < 17) {
                dealerCards.add(drawCard(fortuna));
                dealerHandValue = cardValue(dealerCards);
                System.out.println("Dealer Cards:");
                printCards(dealerCards, fortuna);
                System.out.println();
                System.out.println("-----------");
            }

            System.out.println("Dealer have " + dealerHandValue);
            System.out.println("You have " + playerHandValue);

            if (dealerHandValue > 21) {
                System.out.println("you win");
                money += setCommitment * 2;
            } else if (dealerHandValue == 21 && playerHandValue != 21) {
                System.out.println("you lose");
            } else if (dealerHandValue == 21 && playerHandValue == 21) {
                System.out.println("draw");
                money += setCommitment;
            } else if (playerHandValue > 21) {
                System.out.println("you lose");
            } else if (playerHandValue == 21) {
                System.out.println("you win");
                money += (setCommitment * 3) / 2;
            } else if (playerHandValue > dealerHandValue) {
                System.out.println("you win");
                money += setCommitment * 2;
            }

            System.out.println("Do you want to continue?(Y/N)");
            s.nextLine();
            String str = s.nextLine();
            if (str.contains("n") || str.contains("N")) {
                break BlackJack;
            }
        } while (money > 0);
        System.out.println("You are leaving the black jack table.");
        System.out.println("We hope you had a good time!");
        System.out.println("\nYou have " + money + "$");

        return money;
    }

    public static int playRoulette(int money, Scanner s, Random fortuna) {

        int setCommitment = 0;
        int menu = 0;
        int chosenNumber = 0;

        Roulette:
        do {
            System.out.println("Faites vos jeux!(Make your bets!)");
            do {
                System.out.println("how much you want to set?");
                setCommitment = s.nextInt();
                if (setCommitment == -1) {

                    break Roulette;
                }
            } while (setCommitment > money || setCommitment <= 0);

            money -= setCommitment;
            do {
                System.out.println("1. red numbers");
                System.out.println("2. black numbers");
                System.out.println("3. first third (1-12)");
                System.out.println("4. secound third (13-24)");
                System.out.println("5. third third (25-36)");
                System.out.println("6. number (0-36)");
                menu = s.nextInt();
                if (setCommitment == -1) {
                    break Roulette;
                }
            } while (menu <= 0 || menu > 6);

            if (menu == 6) {
                do {
                    System.out.println("Witch number?");
                    chosenNumber = s.nextInt();
                    if (setCommitment == -1) {
                        break Roulette;
                    }
                } while (chosenNumber < 0 || chosenNumber > 36);
            }
            System.out.println("Rien ne va plus(No more bets)");
            int fortunaChoice = fortuna.nextInt(37);
            System.out.print("\n" + fortunaChoice + " ");
            if (fortunaChoice == 0) {
            } else if (fortunaChoice % 2 == 0) {
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
        System.out.println("You are leaving the roulette table.");
        System.out.println("We hope you had a good time!");
        System.out.println("\nYou have " + money + "$");

        return money;
    }


}