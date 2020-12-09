import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Chessman>> chessmen;
    private Data data1;
    private Player currentPlayer;
    private Player opponentPlayer;
    private Movement chessmanToMove;
    private Movement nextMovement;

    public Board(Player player, Player opponent) {
        this.data1 = new Data("input.txt");
        this.chessmen = new ArrayList<List<Chessman>>(data1.getSize());
        data1.readFile();
        this.chessmen = data1.getChessmen();
        this.currentPlayer = player;
        this.opponentPlayer = opponent;
        this.chessmanToMove = new Movement(0, 0);
        this.nextMovement = new Movement(0, 0);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
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
            if (hasThisMovement()) {
                if (isIWithinBoard()) {
                    Chessman currentChessman = this.chessmen.get(this.chessmanToMove.getRow())
                            .get(this.chessmanToMove.getColumn());
                    char tmpChar = this.getDestinationField().getPlayer().getName();
                    char tmpChar2 = this.getDestinationField().getChessman();  // zrobic legende p -> pawn itd.

                    // if it's a Pawn:
                    if(currentChessman.getChessman() == 'P') {
                        int tmpInt = this.nextMovement.getColumn();

                        // if the Pawn is going forwards:
                        if(this.chessmanToMove.getColumn() == tmpInt) {
                            int steps = this.chessmanToMove.getRow() - this.nextMovement.getRow();

                            // if 1 step forward:
                            if(steps == 1) {
                                // if destination field is empty:
                                if (tmpChar == '[') {
                                    return "POSSIBLE MOVEMENT";
                                }
                                // if there is an opponent's chessman:
                                else if (tmpChar == this.opponentPlayer.getName()) {
                                    return "IMPOSSIBLE MOVEMENT - IT'S OPPONENT'S CHESSMAN THERE!";
                                }
                                // if it's an own chessman:
                                else if (tmpChar == this.currentPlayer.getName()) {
                                        return "IMPOSSIBLE MOVEMENT - IT'S OWN CHESSMAN THERE!";
                                }
                            }
                            // if 2 steps forward:
                            else if (steps == 2) {
                                // if the first field forward is empty:
                                if (this.chessmen.get(this.nextMovement.getRow() + 1)
                                        .get(this.nextMovement.getColumn())
                                        .getPlayer().getName() == '[') {
                                    // if the second field is empty:
                                    if (tmpChar == '[') {
                                        return "POSSIBLE MOVEMENT";
                                    }
                                    // if there is an opponent's chessman:
                                    else if (tmpChar == this.opponentPlayer.getName()) {
                                        return "IMPOSSIBLE MOVEMENT - IT'S OPPONENT'S CHESSMAN THERE!";
                                    }
                                    // if it's an own chessman:
                                    else if (tmpChar == this.currentPlayer.getName()) {
                                        return "IMPOSSIBLE MOVEMENT - IT'S OWN CHESSMAN THERE!";
                                    }
                                }
                                else {
                                    return "IMPOSSIBLE MOVEMENT - FIRST FIELD FORWARD IS NOT EMPTY!";
                                }
                            }
                        }
                        // if the Pawn is trying to capture opponent's chessman:
                        else {
                            // if destination field is empty:
                            if (tmpChar == '[') {
                                return "IMPOSSIBLE MOVEMENT - NO OPPONENT HERE!";
                            }
                            // if there is an opponent's chessman:
                            else if (tmpChar == this.opponentPlayer.getName()) {
                                return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S    " + tmpChar2 + " !";
                            }
                            // if it's an own chessman:
                            else if (tmpChar == this.currentPlayer.getName()) {
                                return "IMPOSSIBLE MOVEMENT - IT'S OWN CHESSMAN!";
                            }
                        }
                    }
                    // if it's a kNight:
                    else if (currentChessman.getChessman() == 'N') {
                        // if destination field is empty:
                        if (tmpChar == '[') {
                            return "POSSIBLE MOVEMENT - DESTINATION FIELD IS FREE";
                        }
                        // if there is an opponent's chessman:
                        else if (tmpChar == this.opponentPlayer.getName()) {
                            return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S    " + tmpChar2 + " !";
                        }
                        // if it's an own chessman:
                        else if (tmpChar == this.currentPlayer.getName()) {
                            return "IMPOSSIBLE MOVEMENT - IT'S OWN CHESSMAN!";
                        }
                    }
                    // if it's the other chessman:
                    else {
                        return "POSSIBLE MOVEMENT";
                    }
                }
                else {
                    return "IMPOSSIBLE MOVEMENT - YOU WILL GET OUT OF THE BOARD !!!";
                }
            }
            else {
                return "IMPOSSIBLE MOVEMENT - THIS CHESSMAN CANNOT MOVE IN THAT WAY !!!";
            }
        }
        else {
            return "IMPOSSIBLE MOVEMENT - IT'S NOT YOUR CHESSMAN !!!";
        }

        return "jklsdfajkladsjkld";
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
        Chessman currentChessman = this.chessmen.get(this.chessmanToMove.getRow())
                .get(this.chessmanToMove.getColumn());

        for (int i = 0; i < currentChessman.getMovements().size(); i++) {
            // if the chessman's movement is equal to the movement we're considering:
            if (currentChessman.getMovements().get(i).getRow() == movement.getRow()
                    && currentChessman.getMovements().get(i).getColumn() == movement.getColumn()) {
                return true;
            }
        }

        return false;
    }

    private boolean hasThisMovement() {
        int tmpRow = this.nextMovement.getRow() - this.chessmanToMove.getRow();
        int tmpColumn = this.nextMovement.getColumn() - this.chessmanToMove.getColumn();

        // if has this movement:
        if (isMovementInList(new Movement(this.chessmanToMove.getRow() + tmpRow,
                this.chessmanToMove.getColumn() + tmpColumn))) {
            return true;
        }
        // if not:
        else {
            return false;
        }
    }

    private Chessman getDestinationField() {
        return this.chessmen.get(this.nextMovement.getRow())
                .get(this.nextMovement.getColumn());
    }

    private boolean isIWithinBoard() {
        // get the indexes of the next movement:
        int tmpRow = this.nextMovement.getRow() - this.chessmanToMove.getRow();
        int tmpColumn = this.nextMovement.getColumn() - this.chessmanToMove.getColumn();
        // add them to the current position:
        int tmpRow2 = tmpRow + this.chessmanToMove.getRow();
        int tmpColumn2 = tmpColumn + this.chessmanToMove.getColumn();

        // if is within board:
        if (tmpRow2 < this.data1.getSize() && tmpRow2 >= 0 &&
                tmpColumn2 < this.data1.getSize() && tmpColumn2 >= 0) {
            return true;
        }
        // if not:
        else {
            return false;
        }
    }

    //==================================================================================================================
    public static void main(String[] args) {
        Player player1 = new Player('1');
        Player player2 = new Player('2');
        Board board1 = new Board(player1, player2);

        // USTAWIAMY INDEXY ZAWSZE LICZĄC OD ZERA !!!!!!!!!!!!!!!!!!
        board1.setChessmanToMove(new Movement(6, 2));
        //System.out.println("-> Czy to figura naszego gracza: " + board1.isPlayersChessman());

        // ustawiamy ruch, których chcemy wykonać:
        board1.setNextMovement(new Movement(4 ,2));
        //System.out.println("-> Czy ta figura ma taki ruch: " + board1.hasThisMovement());

        //System.out.println("-> Czy ta figura jest w obrebie planszy: " + board1.isIWithinBoard());

        System.out.println("-> checkMovement(): " + board1.checkMovement());
    }
}

