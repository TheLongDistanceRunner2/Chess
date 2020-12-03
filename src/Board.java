import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Chessman>> chessmen;
    private Data data1;
    private Player currentPlayer;
    private Movement chessmanToMove;
    private Movement nextMovement;

    public Board(Player player) {
        this.data1 = new Data("input.txt");
        this.chessmen = new ArrayList<List<Chessman>>(data1.getSize());
        data1.readFile();
        this.chessmen = data1.getChessmen();
        this.currentPlayer = player;
        this.chessmanToMove = new Movement(0, 0);
        this.nextMovement = new Movement(0, 0);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Movement getChessmanToMove() {
        return chessmanToMove;
    }

    public void setChessmanToMove(Movement chessmanToMove) {
        this.chessmanToMove = chessmanToMove;
    }

    public Movement getNextMovement() {
        return nextMovement;
    }

    public void setNextMovement(Movement nextMovement) {
        this.nextMovement = nextMovement;
    }

    //===================================================
    public String checkMovement() {
        // if it is the chessman of the current player:
        if (isPlayersChessman()) {
            return "POSSIBLE MOVEMENT";
        }
        else {
            return "IMPOSSIBLE MOVEMENT - IT'S NOT YOUR CHESSMAN !!!";
        }
    }

    private boolean isPlayersChessman() {
        // check if the chessman to move is owned by the current player:
        if (this.chessmen.get(this.chessmanToMove.getRow())
                .get(this.chessmanToMove.getColumn())
                .getPlayer().getName() == this.currentPlayer.getName()) {
                    return true;
        }
        else {
            return false;
        }
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

    private boolean isMovementInList(Movement movement) {
        // NAJPIERW ZNAJDŹ TE KONKRETNA FIGURĘ W TABLICY, A POTEM SPRAWDZAJ JEJ RUCHY !!!!!!!!!!


        for (int i = 0; i < this.chessmen.size(); i++) {
            for (int j = 0; j < this.chessmen.size(); j++) {
                // if found this movement:
                if (this.chessmen.get(i).get(j).getRow() == movement.getRow() &&
                    this.convertColumn(this.chessmen.get(i).get(j).getColumn()) == movement.getColumn()) {
                    return true;
                }
            }
        }
        // if not found:
        return false;
    }

    private boolean hasThisMovement() {
        int tmpRow = this.nextMovement.getRow() - this.chessmanToMove.getRow();
        int tmpColumn = this.nextMovement.getColumn() - this.chessmanToMove.getColumn();

        // KONIECZNA KONWERSJA CHAR NA INTA !!!!

        if (isMovementInList(new Movement(this.chessmanToMove.getRow() + tmpRow,
                this.chessmanToMove.getColumn() + tmpColumn))) {
            return true;
        }
        else {
            return false;
        }
    }

    //==================================================================================================================
    public static void main(String[] args) {
        Player player1 = new Player('1');
        Board board1 = new Board(player1);
        board1.setChessmanToMove(new Movement(6, 1));
        System.out.println("-> Czy to figura naszego gracza: " + board1.isPlayersChessman());

        // ustawiamy ruch, których chcemy wykonać:
        board1.setNextMovement(new Movement(5 ,1));

        System.out.println("-> Czy ta figura ma taki ruch: " + board1.hasThisMovement());


    }
}

