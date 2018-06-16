import logic.MoveGenerator;
import model.*;

import java.util.Arrays;
import java.util.Scanner;

public class CheckersGame {
    public static void main(String[] args) {
        Game game = new Game();
        Player computer = new ComputerPlayer();
        Player human = new HumanPlayer();

        while (!game.isGameOver()) {
            computer.updateGame(game);
            printCheckersByState(game.getGameState(), computer);
            printScore(game.getGameState());
            movePlayer(game, human);
        }

        System.out.println("Game Over");
    }

    public static void movePlayer(Game game, Player player) {
        int [] turns =  getTurns();
        if (game.move(turns[0], turns[1])) {
            printCheckersByState(game.getGameState(), player);
            return;
        } else {
            System.out.println("Wrong indexes. Repeat enter. Tips: ");
            MoveGenerator.getMoves(game.getBoard(), turns[0]).forEach(p -> {System.out.print("end idx:" + Board.toIndex(p) + " ");
                System.out.println();});
            movePlayer(game, player);
        }
    }

    public static int[] getTurns() {
        Scanner scanner = new Scanner(System.in);
        int [] turns = new int[2];

        for (int i = 0; i < turns.length; i ++) {
            if (i%2 == 0) {
                System.out.print("enter start index: ");
                turns[0] = scanner.nextInt();
            } else {
                System.out.print("enter end index: ");
                turns[1] = scanner.nextInt();
            }
        }

        return turns;
    }

    public static void printCheckersByState(String state, Player player) {
        state = state.substring(0, 32);
        char[] stateCharArray = state.toCharArray();
        Integer [] breaksBefore = {0, 8, 16, 24};
        Integer [] breaksAfter = {3, 7, 11, 15, 19, 23, 27, 31};
        String format = " %c ";

        if (player.isHuman()) {
            System.out.println("-----You-----");
        } else {
            System.out.println("---Computer----");
        }

        for (int i = 0; i <= stateCharArray.length-1; i++) {
            if (Arrays.asList(breaksBefore).contains(i)) {
                System.out.printf("  %c ", stateCharArray[i]);
                continue;
            }
            System.out.printf(format, stateCharArray[i]);

            if (Arrays.asList(breaksAfter).contains(i)) {
                System.out.println();
            }
        }
        System.out.println("---------------");
    }

    public static void printScore(String state) {
        state = state.substring(0, 32);
        int countBlackCheckers = (int) state.codePoints().mapToObj(c -> String.valueOf((char) c)).filter(c -> c.equals("6")).count();
        countBlackCheckers += (int) state.codePoints().mapToObj(c -> String.valueOf((char) c)).filter(c -> c.equals("7")).count();

        int countWhiteCheckers = (int) state.codePoints().mapToObj(c -> String.valueOf((char) c)).filter(c -> c.equals("4")).count();
        countWhiteCheckers += (int) state.codePoints().mapToObj(c -> String.valueOf((char) c)).filter(c -> c.equals("5")).count();

        System.out.println("BLACK: " + countBlackCheckers
                           + " WHITE: " + countWhiteCheckers
        );
    }
}
