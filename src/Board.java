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
                    // whose chessman is in the destination field:
                    char tmpChar = this.getDestinationField().getPlayer().getName();
                    // get this chessman:
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
                    // if it's a kNight or a King:
                    else if (currentChessman.getChessman() == 'N' || currentChessman.getChessman() == 'K') {
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
                        // calculate the difference between destination and current coordinates:
                        int differenceRows = this.nextMovement.getRow() - this.chessmanToMove.getRow();
                        int differenceeColumns = this.nextMovement.getColumn() - this.chessmanToMove.getColumn();
                        Boolean flag = true;

                        // if it's a Rook:
                        if (currentChessman.getChessman() == 'R') {
                            // if destination field is empty:
                            if (tmpChar == '[') {
                                // check every field before reaching the destination one
                                // depending on the difference of the coordinates:

                                // moving up:
                                if (differenceRows < 0 && differenceeColumns == 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn();

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down:
                                else if (differenceRows > 0 && differenceeColumns == 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn();

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving right:
                                else if (differenceRows == 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow();
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving left:
                                else if (differenceRows == 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow();
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }

                                // if the flag remained unchanged:
                                if (flag) {
                                    return "POSSIBLE MOVEMENT - NO OPPONENT HERE!";
                                }
                                // if it was changed:
                                else {
                                    return "IMPOSSIBLE MOVEMENT - THE PATH IS NOT CLEAR!";
                                }
                            }
                            // if there is an opponent's chessman:
                            else if (tmpChar == this.opponentPlayer.getName()) {
                                // check every field before reaching the destination one
                                // depending on the difference of the coordinates:

                                // moving up:
                                if (differenceRows < 0 && differenceeColumns == 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn();

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down:
                                else if (differenceRows > 0 && differenceeColumns == 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn();

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving right:
                                else if (differenceRows == 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow();
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving left:
                                else if (differenceRows == 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow();
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }

                                // if the flag remained unchanged:
                                if (flag) {
                                    return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S     " + tmpChar2 + " !";
                                }
                                // if it was changed:
                                else {
                                    return "IMPOSSIBLE MOVEMENT - THE PATH IS NOT CLEAR!";
                                }
                            }
                            // if it's an own chessman:
                            else if (tmpChar == this.currentPlayer.getName()) {
                                return "IMPOSSIBLE MOVEMENT - OWN CHESSMAN IN DESTINATION FIELD!";
                            }
                        }
                        // if it's a Bishop:
                        else if (currentChessman.getChessman() == 'B') {
                            // if destination field is empty:
                            if (tmpChar == '[') {
                                // check every field before reaching the destination one
                                // depending on the difference of the coordinates:

                                // moving up and left:
                                if (differenceRows < 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving up and right:
                                else if (differenceRows < 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down and left:
                                else if (differenceRows > 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down and right:
                                else if (differenceRows > 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }

                                // if the flag remained unchanged:
                                if (flag) {
                                    return "POSSIBLE MOVEMENT - NO OPPONENT HERE!";
                                }
                                // if it was changed:
                                else {
                                    return "IMPOSSIBLE MOVEMENT - THE PATH IS NOT CLEAR!";
                                }
                            }
                            // if there is an opponent's chessman:
                            else if (tmpChar == this.opponentPlayer.getName()) {
                                // check every field before reaching the destination one
                                // depending on the difference of the coordinates:

                                // moving up and left:
                                if (differenceRows < 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving up and right:
                                else if (differenceRows < 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down and left:
                                else if (differenceRows > 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down and right:
                                else if (differenceRows > 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }

                                // if the flag remained unchanged:
                                if (flag) {
                                    return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S     " + tmpChar2 + " !";
                                }
                                // if it was changed:
                                else {
                                    return "IMPOSSIBLE MOVEMENT - THE PATH IS NOT CLEAR!";
                                }
                            }
                            // if it's an own chessman:
                            else if (tmpChar == this.currentPlayer.getName()) {
                                return "IMPOSSIBLE MOVEMENT - OWN CHESSMAN IN DESTINATION FIELD!";
                            }
                        }
                        // if it's a Queen:
                        else if (currentChessman.getChessman() == 'Q') {
                            // if destination field is empty:
                            if (tmpChar == '[') {
                                // check every field before reaching the destination one
                                // depending on the difference of the coordinates:

                                // moving up:
                                if (differenceRows < 0 && differenceeColumns == 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn();

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down:
                                else if (differenceRows > 0 && differenceeColumns == 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn();

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving right:
                                else if (differenceRows == 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow();
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving left:
                                else if (differenceRows == 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow();
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving up and left:
                                if (differenceRows < 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving up and right:
                                else if (differenceRows < 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down and left:
                                else if (differenceRows > 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down and right:
                                else if (differenceRows > 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }

                                // if the flag remained unchanged:
                                if (flag) {
                                    return "POSSIBLE MOVEMENT - NO OPPONENT HERE!";
                                }
                                // if it was changed:
                                else {
                                    return "IMPOSSIBLE MOVEMENT - THE PATH IS NOT CLEAR!";
                                }
                            }
                            // if there is an opponent's chessman:
                            else if (tmpChar == this.opponentPlayer.getName()) {
                                // check every field before reaching the destination one
                                // depending on the difference of the coordinates:

                                // moving up:
                                if (differenceRows < 0 && differenceeColumns == 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn();

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down:
                                else if (differenceRows > 0 && differenceeColumns == 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn();

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving right:
                                else if (differenceRows == 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow();
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving left:
                                else if (differenceRows == 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow();
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving up and left:
                                if (differenceRows < 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving up and right:
                                else if (differenceRows < 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceRows); i++) {
                                        int tmpR = this.chessmanToMove.getRow() - i;
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down and left:
                                else if (differenceRows > 0 && differenceeColumns < 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn() - i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }
                                // moving down and right:
                                else if (differenceRows > 0 && differenceeColumns > 0) {
                                    // check every field:
                                    for (int i = 1; i < Math.abs(differenceeColumns); i++) {
                                        int tmpR = this.chessmanToMove.getRow() + i;
                                        int tmpC = this.chessmanToMove.getColumn() + i;

                                        char tmp = this.chessmen.get(tmpR).get(tmpC).getChessman();

                                        // if the field is not free, change the flag to false:
                                        if (tmp != ']') {
                                            flag = false;
                                        }
                                    }
                                }

                                // if the flag remained unchanged:
                                if (flag) {
                                    return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S     " + tmpChar2 + " !";
                                }
                                // if it was changed:
                                else {
                                    return "IMPOSSIBLE MOVEMENT - THE PATH IS NOT CLEAR!";
                                }

                            }
                            // if it's an own chessman:
                            else if (tmpChar == this.currentPlayer.getName()) {
                                return "IMPOSSIBLE MOVEMENT - OWN CHESSMAN IN DESTINATION FIELD!";
                            }
                        }
                        // if it's a King:
                        else if (currentChessman.getChessman() == 'K') {
                            return "POSSIBLE MOVEMENT";
                        }
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

    private boolean willKingBeChecked() {
        Movement kingPosition = null;
        boolean flag = false;

        // find the position of the King:
        for (int i = 0; i < this.chessmen.size(); i++) {
            for (int j = 0; j < this.chessmen.size(); j++) {
                // if found the King:
                if (this.chessmen.get(i).get(j).getPlayer().getName() == this.currentPlayer.getName() &&
                        this.chessmen.get(i).get(j).getChessman() == 'K') {
                    // set the King's position:
                    kingPosition = new Movement(i, j);
                }
            }
        }

        // check every path starting from the King's position:                              KONIE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                                                                    //      ODDZIELNE SPRAAWDZENIE SZACHOWANIE KRÓLA PRZEZ KRÓLA, BO TO PRZECIEZ RUCH O JEDNO POLE !!!!!
        // check first field in front of the King:
        int _i = kingPosition.getRow() - 1;
        int _j = kingPosition.getColumn();

        // check up:
        // while within board:
        while (_i != -1) {
            char tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            char tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's not a free field:
            if (tmpChar != '[') {
                // if it's our chessman:
                if(tmpChar == this.currentPlayer.getName()) {
                    break;
                }
                // if it's an opponent:
                else if (tmpChar == this.opponentPlayer.getName()) {
                    // if it's a Queen or a Rook:
                    if (tmpChar2 == 'Q' || tmpChar2 == 'R') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's the other cheessman:
                    else {
                        // the King won't be checked, so break:
                        break;
                    }
                }
            }

            // go up:
            _i--;
        }

        // remember to reset the King's position (check first diagonal field):
        _i = kingPosition.getRow() - 1;
        _j = kingPosition.getColumn() + 1;
        int counter = 0;

        // check up nad right:
        // while within board:
        while (_i != -1 && _j != - 1 && _i != 8 && _j != 8) {
            char tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            char tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's not a free field:
            if (tmpChar != '[') {
                // if it's our chessman:
                if(tmpChar == this.currentPlayer.getName()) {
                    break;
                }
                // if it's an opponent:
                else if (tmpChar == this.opponentPlayer.getName()) {
                    // if it's a Pawn in the first field and it's the bottom Player's King:
                    if(tmpChar2 == 'P' && counter == 0 && this.currentPlayer.getName() == '1') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's a Queen or a Bishop:
                    else if (tmpChar2 == 'Q' || tmpChar2 == 'B') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's the other cheessman:
                    else {
                        // the King won't be checked, so break:
                        break;
                    }
                }
            }

            // go up and right:
            _i--;
            _j++;
            counter++;
        }

        // remember to reset the King's position (check first right field):
        _i = kingPosition.getRow();
        _j = kingPosition.getColumn() + 1;
        counter = 0;

        // check right:
        // while within board:
        while (_i != -1 && _j != - 1 && _i != 8 && _j != 8) {
            char tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            char tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's not a free field:
            if (tmpChar != '[') {
                // if it's our chessman:
                if(tmpChar == this.currentPlayer.getName()) {
                    break;
                }
                // if it's an opponent:
                else if (tmpChar == this.opponentPlayer.getName()) {
                    // if it's a Queen or a Rook:
                    if (tmpChar2 == 'Q' || tmpChar2 == 'R') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's the other cheessman:
                    else {
                        // the King won't be checked, so break:
                        break;
                    }
                }
            }

            // go right:
            _j++;
            counter++;
        }

        // remember to reset the King's position (check first down and right field):
        _i = kingPosition.getRow() + 1;
        _j = kingPosition.getColumn() + 1;
        counter = 0;

        // check down and right:
        // while within board:
        while (_i != -1 && _j != - 1 && _i != 8 && _j != 8) {
            char tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            char tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's not a free field:
            if (tmpChar != '[') {
                // if it's our chessman:
                if(tmpChar == this.currentPlayer.getName()) {
                    break;
                }
                // if it's an opponent:
                else if (tmpChar == this.opponentPlayer.getName()) {
                    // if it's a Pawn in the first field and it's the upper Player's King:
                    if(tmpChar2 == 'P' && counter == 0 && this.currentPlayer.getName() == '2') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's a Queen or a Bishop:
                    else if (tmpChar2 == 'Q' || tmpChar2 == 'B') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's the other cheessman:
                    else {
                        // the King won't be checked, so break:
                        break;
                    }
                }
            }

            // go down and right:
            _i++;
            _j++;
            counter++;
        }

        // remember to reset the King's position (check first down):
        _i = kingPosition.getRow() + 1;
        _j = kingPosition.getColumn();
        counter = 0;

        // check down:
        // while within board:
        while (_i != -1 && _j != - 1 && _i != 8 && _j != 8) {
            char tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            char tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's not a free field:
            if (tmpChar != '[') {
                // if it's our chessman:
                if(tmpChar == this.currentPlayer.getName()) {
                    break;
                }
                // if it's an opponent:
                else if (tmpChar == this.opponentPlayer.getName()) {
                    // if it's a Queen or a Rook:
                    if (tmpChar2 == 'Q' || tmpChar2 == 'R') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's the other cheessman:
                    else {
                        // the King won't be checked, so break:
                        break;
                    }
                }
            }

            // go down:
            _i++;
            counter++;
        }

        // remember to reset the King's position (check first down and left):
        _i = kingPosition.getRow() + 1;
        _j = kingPosition.getColumn() - 1;
        counter = 0;

        // check down and left:
        // while within board:
        while (_i != -1 && _j != - 1 && _i != 8 && _j != 8) {
            char tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            char tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's not a free field:
            if (tmpChar != '[') {
                // if it's our chessman:
                if(tmpChar == this.currentPlayer.getName()) {
                    break;
                }
                // if it's an opponent:
                else if (tmpChar == this.opponentPlayer.getName()) {
                    // if it's a Pawn in the first field and it's the upper Player's King:
                    if(tmpChar2 == 'P' && counter == 0 && this.currentPlayer.getName() == '2') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's a Queen or a Bishop:
                    else if (tmpChar2 == 'Q' || tmpChar2 == 'B') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's the other cheessman:
                    else {
                        // the King won't be checked, so break:
                        break;
                    }
                }
            }

            // go down and left:
            _i++;
            _j--;
            counter++;
        }

        // remember to reset the King's position (check left):
        _i = kingPosition.getRow();
        _j = kingPosition.getColumn() - 1;
        counter = 0;

        // check left:
        // while within board:
        while (_i != -1 && _j != - 1 && _i != 8 && _j != 8) {
            char tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            char tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's not a free field:
            if (tmpChar != '[') {
                // if it's our chessman:
                if(tmpChar == this.currentPlayer.getName()) {
                    break;
                }
                // if it's an opponent:
                else if (tmpChar == this.opponentPlayer.getName()) {
                    // if it's a Queen or a Rook:
                    if (tmpChar2 == 'Q' || tmpChar2 == 'R') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's the other cheessman:
                    else {
                        // the King won't be checked, so break:
                        break;
                    }
                }
            }

            // go left:
            _j--;
            counter++;
        }

        // remember to reset the King's position (check up and left):
        _i = kingPosition.getRow() - 1;
        _j = kingPosition.getColumn() - 1;
        counter = 0;

        // check up and left:
        // while within board:
        while (_i != -1 && _j != - 1 && _i != 8 && _j != 8) {
            char tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            char tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's not a free field:
            if (tmpChar != '[') {
                // if it's our chessman:
                if(tmpChar == this.currentPlayer.getName()) {
                    break;
                }
                // if it's an opponent:
                else if (tmpChar == this.opponentPlayer.getName()) {
                    // if it's a Pawn in the first field and it's the bottom Player's King:
                    if(tmpChar2 == 'P' && counter == 0 && this.currentPlayer.getName() == '1') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's a Queen or a Bishop:
                    else if (tmpChar2 == 'Q' || tmpChar2 == 'B') {
                        // the King will be checked!
                        flag = true;
                        break;
                    }
                    // if it's the other cheessman:
                    else {
                        // the King won't be checked, so break:
                        break;
                    }
                }
            }

            // go up and left:
            _i--;
            _j--;
            counter++;
        }


        return flag;
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
        board1.setChessmanToMove(new Movement(4, 3));
        //System.out.println("-> Czy to figura naszego gracza: " + board1.isPlayersChessman());

        // ustawiamy ruch, których chcemy wykonać:
        board1.setNextMovement(new Movement(3,2));
        //System.out.println("-> Czy ta figura ma taki ruch: " + board1.hasThisMovement());

        //System.out.println("-> Czy ta figura jest w obrebie planszy: " + board1.isIWithinBoard());

        //System.out.println("-> checkMovement(): " + board1.checkMovement());

        System.out.println("-> willKingBeChecked:   " + board1.willKingBeChecked());
    }
}

