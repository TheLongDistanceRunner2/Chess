import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    private Player player1;
    private Player player2;
    private Board board1;
    private Player currentPlayer;

    public Game() {
        this.player1 = new Player('1');
        this.player2 = new Player('2');
        this.board1 = new Board(player1, player2);
        // the current Player is the Player1:
        this.currentPlayer = this.player1;
    }

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board1 = new Board(player1, player2);
        // the current Player is the Player1:
        this.currentPlayer = this.player1;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    //============================================
    // private:

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
        //Game game1 = new Game(new Player('2'), new Player('1'));

        System.out.println("        => Choose the chessman to move: <= \n\n"
                                + "Enter row number:" );
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int row1 = -1;
        row1 = Integer.parseInt(reader.readLine());

        System.out.println("Enter column name: ");
        String tmp1 = reader.readLine();

        // if entered chessman:
        if (row1 != -1) {
            int column1 = game1.convertColumn(tmp1.charAt(0));

            // (row1 - 1) and (column1) in order to use real world notation!
            Chessman chessman = game1.getBoard1().getChessmen().get(row1 - 1).get(column1);

            // if it's your chessman:
            if (chessman.getPlayer().getName() == game1.getCurrentPlayer().getName()) {
                // set which chessman to move:
                // =>  (row1 - 1) and (column1) in order to use real world notation!
                game1.getBoard1().setChessmanToMove(new Movement(row1 - 1, column1));

                // enter where to move it:
                System.out.println("        => Enter where to move: <= \n\n"
                                        + "Enter row number:" );
                int row2 =  Integer.parseInt(reader.readLine());

                System.out.println("Enter column number:" );
                String tmp2 = reader.readLine();

                // extract the index:
                int column2 = game1.convertColumn(tmp2.charAt(0));

                // set the next movement:
                // =>  (row2 - 1) in order to use real world notation!
                game1.getBoard1().setNextMovement(new Movement(row2 - 1, column2));

                // and check the movement of the chosen chessman:
                System.out.println(game1.getBoard1().checkMovement());
            }
            else {
                System.out.println("It's not your chessman!");
            }
        }
        else {
            System.out.println("You haven't entered anything!");
        }
    }
}
