import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;
    private Board board1;

    public Game() {
        this.player1 = new Player('1');
        this.player1 = new Player('2');
        this.board1 = new Board(player1, player2);
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Board getBoard1() {
        return board1;
    }

    public void setBoard1(Board board1) {
        this.board1 = board1;
    }

    private int convertColumn(char j) {
        if (j == 'A') {
            return 0;
        }
        else if (j == 'B') {
            return 1;
        }
        else if (j == 'C') {
            return 2;
        }
        else if (j == 'D') {
            return 3;
        }
        else if (j == 'E') {
            return 4;
        }
        else if (j == 'F') {
            return 5;
        }
        else if (j == 'G') {
            return 6;
        }
        else if (j == 'H') {
            return 7;
        }
        return -1;
    }

    //=========================================================================================
    //=========================================================================================
    //                              Start the game here!
    public static void main(String[] args) throws IOException {
        Game game1 = new Game();

        System.out.println("Choose the chessman to move: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String chessmanToMove = reader.readLine();

        // if entered chessman:
        if (chessmanToMove != null) {
            int _i = game1.convertColumn(chessmanToMove.charAt(0));
            int _j = (int) chessmanToMove.charAt(1);

            // if it's your chessman:
            if () {
            }
            else {
                System.out.println("It's not your chessman!");
            }

        }
        else {
            System.out.println("You haven't entered anything!");
        }


        // USTAWIAMY INDEXY ZAWSZE LICZĄC OD ZERA !!!!!!!!!!!!!!!!!!
        board1.setChessmanToMove(new Movement(4, 0));
        //System.out.println("-> Czy to figura naszego gracza: " + board1.isPlayersChessman());

        // ustawiamy ruch, których chcemy wykonać:
        board1.setNextMovement(new Movement(3,2));
        //System.out.println("-> Czy ta figura ma taki ruch: " + board1.hasThisMovement());

        //System.out.println("-> Czy ta figura jest w obrebie planszy: " + board1.isIWithinBoard());

        //System.out.println("-> checkMovement(): " + board1.checkMovement());

        System.out.println("-> willKingBeChecked:   " + board1.willKingBeChecked());
    }
}
