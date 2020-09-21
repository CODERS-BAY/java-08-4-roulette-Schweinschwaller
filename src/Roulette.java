import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Roulette {
    public static void main(String[] args) {

        System.out.println("##########################################################");
        System.out.println("#           $$$ Welcome to George's Casino $$$           #");
        System.out.println("##########################################################");
        System.out.println();
        System.out.println("How much money do you want to change in chips?!");

        // User Input --> how many chips
        GScanner gs = new GScanner();
        int money = gs.nextInt();
        if (money > 100) {
            System.out.println("☺");
        }
        System.out.println("Here are your chips in worth of " + money + "$");
        System.out.println();

        Random fortuna = new Random();
        boolean wantToLeave = false;
        //menu --> what do you want to play
        do {
            System.out.println("Chose a GAME");
            System.out.println("------------");
            System.out.println("1. ROULETTE");
            System.out.println("2. BLACK JACK");
            System.out.println("-1. Leave");
            int menu = gs.nextInt();
            switch (menu) {
                case 1 -> money = playRoulette(money, gs, fortuna);
                case 2 -> money = playBlackJack(money, gs, fortuna);
                case -1 -> wantToLeave = true;
            }
            if (money <= 0) {
                System.out.println("How much money do you want to change in chips?!");
                money = gs.nextInt();
            }
        } while (!wantToLeave);
    }

    //####################################//
    //   Black Jack                       //
    //####################################//
    public static int drawCard(Random fortuna) {
        return fortuna.nextInt(13);
    }

    public static void printCards(ArrayList<Integer> cards, Random fortuna) {
        for (int i = 0; i < cards.size(); i++) {
            switch (cards.get(i)) {
                case 0 -> System.out.print("\uD83C\uDCA2"); // 2 card symbol unicode
                case 1 -> System.out.print("\uD83C\uDCA3"); // 3
                case 2 -> System.out.print("\uD83C\uDCA4"); // 4
                case 3 -> System.out.print("\uD83C\uDCA5"); // 5
                case 4 -> System.out.print("\uD83C\uDCA6"); // 6
                case 5 -> System.out.print("\uD83C\uDCA7"); // 7
                case 6 -> System.out.print("\uD83C\uDCA8"); // 8
                case 7 -> System.out.print("\uD83C\uDCA9"); // 9
                case 8 -> System.out.print("\uD83C\uDCAA"); // 10
                case 9 -> System.out.print("\uD83C\uDCAB"); // J
                case 10 -> System.out.print("\uD83C\uDCAD"); // D
                case 11 -> System.out.print("\uD83C\uDCAE"); // K
                case 12 -> {                                 // ace
                    int help = fortuna.nextInt(4);
                    if (help == 0) {
                        System.out.print("\uD83C\uDCB1");
                    } else if (help == 1) {
                        System.out.print("\uD83C\uDCC1");
                    } else if (help == 2) {
                        System.out.print("\uD83C\uDCD1");
                    } else {
                        System.out.print("\uD83C\uDCA1");
                    }
                }
            }
            System.out.print("  ");
        }
    }

    public static int cardValue(ArrayList<Integer> cards) {
        // in Black Jack the Cards from 2 until 10 have the value of it self.
        // J D K have the value of 10
        // the ace have the value of 1 or 11
        // there are 13 cards 2,3,4,5,6,7,8,9,10,J,D,K,♦
        int value = 0;
        // with sort of the cards a ace will be at the and of the cards
        Collections.sort(cards);
        for (int i = 0; i < cards.size(); i++) {
            //draw a Card gives a number [0-12] => 0 == |2| 1 == |3| 2 == |4| ....
            if (cards.get(i) >= 0 && cards.get(i) <= 7) {
                // the random number +2 is the value of the card
                value += cards.get(i) + 2;
                // 10,J,D,K add 10 to the value
            } else if (cards.get(i) > 7 && cards.get(i) <= 11) {
                value += 10;
                // the ace!!! can have the value of 1 or 11
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

    public static int playBlackJack(int money, GScanner gs, Random fortuna) {

        System.out.println("\n".repeat(20));
        System.out.println("#########################################################");
        System.out.println("#               Welcome to Black Jack                   #");
        System.out.println("#########################################################");
        System.out.println();

        BlackJack:
        do {
            int setCommitment = 0;
            ArrayList<Integer> playerCards = new ArrayList<Integer>();
            ArrayList<Integer> dealerCards = new ArrayList<Integer>();
            // Set the commitment
            do {
                System.out.println("how much you want to set?");
                setCommitment = gs.nextInt();
                if (setCommitment == -1) {
                    break BlackJack;
                }
            } while (setCommitment > money || setCommitment <= 0);
            money -= setCommitment;
            // draw the Cards
            playerCards.add(drawCard(fortuna));
            playerCards.add(drawCard(fortuna));
            int playerHandValue = cardValue(playerCards);
            dealerCards.add(drawCard(fortuna));
            printCards(dealerCards, fortuna);
            System.out.println();
            // if the dealer have the change of a Black Jack you can exit with the half of your commitment
            boolean playerDone = false;
            if (dealerCards.get(0) >= 8) {
                System.out.println("The Dealer have the chance of a Black Jack");
                System.out.println("Do you want to surrender and get the half of you commitment back? (Y/N)");
                // nextYN: yes == true, no == false
                playerDone = gs.nextYN();
                if (playerDone) {
                    money += setCommitment / 2;
                }
            }
            dealerCards.add(drawCard(fortuna));
            int dealerHandValue = cardValue(dealerCards);

            // player and dealer have each two cards
            System.out.println("Dealer Cards: ( " + dealerHandValue + " )");
            printCards(dealerCards, fortuna);
            System.out.println();
            System.out.println("Your Cards: ( " + playerHandValue + " )");
            printCards(playerCards, fortuna);
            System.out.println("\n-----------");

            if (playerHandValue == 21 && playerCards.size() == 2) {
                System.out.println("BLACK JACK!");
            } else {
                int menu = 0;
                playerAction:
                while (!playerDone) {
                    // the actions of the players
                    int input = 0;
                    do {
                        System.out.println("1. hit");
                        System.out.println("2. stand");
                        if (playerCards.get(0) == playerCards.get(1)) {
                            System.out.println("3. split");
                            menu = 3;
                        }
                        if (playerCards.size() == 2 && money >= 2 * setCommitment) {
                            System.out.println("4. double down");
                            menu = 4;
                        }
                        input = gs.nextInt();
                    } while (input > menu && input < 1);

                    switch (input) {
                        case 1 -> {
                            playerCards.add(drawCard(fortuna));
                            playerHandValue = cardValue(playerCards);

                            System.out.println("Dealer Cards: ( " + dealerHandValue + " )");
                            printCards(dealerCards, fortuna);
                            System.out.println();
                            System.out.println("Your Cards: ( " + playerHandValue + " )");
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
                    } else if (playerHandValue == 21) {
                        playerDone = true;
                    }
                }
            }
            // output of the result of the players action
            // repeat 20 is my version of a clear console
            System.out.println("\n".repeat(20));
            System.out.println("-----------------------------");
            System.out.println("Your Cards: ( " + playerHandValue + " )");
            printCards(playerCards, fortuna);
            System.out.println();
            System.out.println("-----------------------------");

            System.out.println("Dealer's turn");
            // DealerAction
            while (dealerHandValue < 17) {
                System.out.println("Dealer draw a card");
                dealerCards.add(drawCard(fortuna));
                dealerHandValue = cardValue(dealerCards);
                System.out.println("Dealer Cards: ( " + dealerHandValue + " )");
                printCards(dealerCards, fortuna);
                System.out.println();
                System.out.println("-----------------------------");
            }

            // output after the Dealer is finish with hius action
            System.out.println("-$-$-$-$-$-$-$-$-$-$-$-$-$-$-");
            System.out.println("Dealer have " + dealerHandValue);
            System.out.println("You have " + playerHandValue);
            System.out.print(" --- ");

            // how wons?
            if (playerHandValue > 21) {
                System.out.print("dealer wins");
            } else if (dealerHandValue > 21) {
                System.out.print("you wins " + (setCommitment * 2) + "$");
                money += setCommitment * 2;
            } else if (dealerHandValue == 21 && playerHandValue != 21) {
                System.out.print("dealer wins");
            } else if (dealerHandValue == playerHandValue) {
                System.out.print("draw");
                money += setCommitment;
            } else if (playerHandValue > 21) {
                System.out.print("dealer wins");
            } else if (playerHandValue == 21) {
                System.out.print("you wins " + ((setCommitment * 3) / 2) + "$");
                money += (setCommitment * 3) / 2;
            } else if (playerHandValue > dealerHandValue) {
                System.out.print("you wins " + (setCommitment * 2) + "$");
                money += setCommitment * 2;
            } else {
                System.out.print("dealer wins");
            }
            System.out.println(" --- ");
            System.out.println("You have now " + money + "$");
            System.out.println("Do you want to continue?(Y/N)");
            // nextYN: yes == true, no == false
            if (!gs.nextYN()) {
                break BlackJack;
            }
        } while (money > 0);

        System.out.println("You are leaving the black jack table.");
        System.out.println("We hope you had a good time!");
        System.out.println("\nYou have " + money + "$");

        return money;
    }

    //####################################//
    //   Roulette                         //
    //####################################//
    public static int playRoulette(int money, GScanner gs, Random fortuna) {

        System.out.println("\n".repeat(20));
        System.out.println("#########################################################");
        System.out.println("#                Welcome to ROULETTE                    #");
        System.out.println("#########################################################");
        System.out.println();

        Roulette:
        do {
            // set the value of the Commitment
            int setCommitment = 0;
            System.out.println("Faites vos jeux!(Make your bets!)");
            do {
                System.out.println("how much you want to set?");
                setCommitment = gs.nextInt();
                if (setCommitment == -1) {

                    break Roulette;
                }
            } while (setCommitment > money || setCommitment <= 0);
            money -= setCommitment;
            // menu for the bet
            int menu = 0;
            do {
                System.out.println("1. red numbers");
                System.out.println("2. black numbers");
                System.out.println("3. first third (1-12)");
                System.out.println("4. secound third (13-24)");
                System.out.println("5. third third (25-36)");
                System.out.println("6. number (0-36)");
                menu = gs.nextInt();
                if (menu == -1) {
                    break Roulette;
                }
            } while (menu <= 0 || menu > 6);

            int chosenNumber = 0;
            if (menu == 6) {
                do {
                    System.out.println("Which number?");
                    chosenNumber = gs.nextInt();
                    if (setCommitment == -1) {
                        break Roulette;
                    }
                } while (chosenNumber < 0 || chosenNumber > 36);
            }
            System.out.println("Rien ne va plus(No more bets)");

            // spin the bullet
            System.out.println("##############");
            System.out.print("#  ");
            int fortunaChoice = fortuna.nextInt(37);
            System.out.print(fortunaChoice + " ");
            if (fortunaChoice == 0) {
            } else if (fortunaChoice % 2 == 0) {
                System.out.println("black  #");
            } else {
                System.out.println("red    #");
            }
            System.out.println("##############");
            System.out.println("is the Winning number!!");

            // evaluation of the number and the players bet
            if (menu == 6 && chosenNumber == fortunaChoice) {
                System.out.println("you have won " + (setCommitment * 36) + "$");
                money += setCommitment * 36;
            } else if (menu == 3 && (fortunaChoice <= 12 && fortunaChoice >= 1)) {
                System.out.println("you have won " + (setCommitment * 3) + "$");
                money += setCommitment * 3;
            } else if (menu == 4 && (fortunaChoice <= 24 && fortunaChoice >= 13)) {
                System.out.println("you have won " + (setCommitment * 3) + "$");
                money += setCommitment * 3;
            } else if (menu == 5 && (fortunaChoice <= 36 && fortunaChoice >= 25)) {
                System.out.println("you have won " + (setCommitment * 3) + "$");
                money += setCommitment * 3;
            } else if (menu == 1 && fortunaChoice % 2 != 0 && fortunaChoice != 0) {
                System.out.println("you have won " + (setCommitment * 2) + "$");
                money += setCommitment * 2;
            } else if (menu == 2 && fortunaChoice % 2 == 0 && fortunaChoice != 0) {
                System.out.println("you have won " + (setCommitment * 2) + "$");
                money += setCommitment * 2;
            }
            System.out.println("---------------------------");
            System.out.println("You have now " + money + "$");
            System.out.println("Do you want to continue?(Y/N)");
            // nextYN: yes == true, no == false
            if (!gs.nextYN()) {
                break Roulette;
            }

        } while (money > 0);
        System.out.println("You are leaving the roulette table.");
        System.out.println("We hope you had a good time!");
        System.out.println("\nYou have " + money + "$");

        return money;
    }
}