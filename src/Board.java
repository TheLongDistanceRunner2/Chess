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

    public List<List<Chessman>> getChessmen() {
        return chessmen;
    }

    public void setChessmen(List<List<Chessman>> chessmen) {
        this.chessmen = chessmen;
    }

    public void showData() {
        for (int i = 0; i < this.chessmen.size(); i++) {
            for (int j = 0; j < this.chessmen.size(); j++) {
                System.out.print(this.chessmen.get(i).get(j).getPlayer().getName()
                        + "" + this.chessmen.get(i).get(j).getChessman() + " ");
            }
            System.out.println();
        }
    }

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
                    char tmpChar2 = this.getDestinationField().getChessman();

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
                                    // move the chessman to the next position in order to check whether
                                    // your King will be checked:
                                    this.setChessmanToNewField();

                                    boolean flag = this.willKingBeChecked();

                                    // if the King won't be checked:
                                    if (!flag) {
                                        return "POSSIBLE MOVEMENT - THE FIELD IS FREE!";
                                    }
                                    else {
                                        return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                    }
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
                                        // move the chessman to the next position in order to check whether
                                        // your King will be checked:
                                        this.setChessmanToNewField();

                                        boolean flag = this.willKingBeChecked();

                                        // if the King won't be checked:
                                        if (!flag) {
                                            return "POSSIBLE MOVEMENT - THE FIELD IS FREE!";
                                        }
                                        else {
                                            return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                        }
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
                                // move the chessman to the next position in order to check whether
                                // your King will be checked:
                                this.setChessmanToNewField();

                                boolean flag = this.willKingBeChecked();

                                // if the King won't be checked:
                                if (!flag) {
                                    // if it's not the King:
                                    if (tmpChar2 != 'K') {
                                        return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S    "
                                                + this.returnFullName(tmpChar2) + " !";
                                    }
                                    else {
                                        return "POSSIBLE CHECK!";
                                    }
                                }
                                else {
                                    return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                }
                            }
                            // if it's an own chessman:
                            else if (tmpChar == this.currentPlayer.getName()) {
                                return "IMPOSSIBLE MOVEMENT - IT'S OWN CHESSMAN THERE!";
                            }
                        }
                    }
                    // if it's a kNight or a King:
                    else if (currentChessman.getChessman() == 'N' || currentChessman.getChessman() == 'K') {
                        // if destination field is empty:
                        if (tmpChar == '[') {
                            // move the chessman to the next position in order to check whether
                            // your King will be checked:
                            this.setChessmanToNewField();

                            boolean flag = this.willKingBeChecked();

                            // if the King won't be checked:
                            if (!flag) {
                                return "POSSIBLE MOVEMENT - THE FIELD IS FREE!";
                            }
                            else {
                                return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                            }
                        }
                        // if there is an opponent's chessman:
                        else if (tmpChar == this.opponentPlayer.getName()) {
                            boolean flag = this.willKingBeChecked();

                            // if the King won't be checked:
                            if (!flag) {
                                // if it's not the King:
                                if (tmpChar2 != 'K') {
                                    return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S    "
                                            + this.returnFullName(tmpChar2) + " !";
                                }
                                else {
                                    return "POSSIBLE CHECK!";
                                }
                            }
                            else {
                                return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                            }
                        }
                        // if it's an own chessman:
                        else if (tmpChar == this.currentPlayer.getName()) {
                            return "IMPOSSIBLE MOVEMENT - IT'S OWN CHESSMAN THERE!";
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
                                    // move the chessman to the next position in order to check whether
                                    // your King will be checked:
                                    this.setChessmanToNewField();

                                    boolean _flag = this.willKingBeChecked();

                                    // if the King won't be checked:
                                    if (!_flag) {
                                        return "POSSIBLE MOVEMENT - THE FIELD IS FREE!";
                                    }
                                    else {
                                        return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                    }
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
                                    // move the chessman to the next position in order to check whether
                                    // your King will be checked:
                                    this.setChessmanToNewField();

                                    boolean _flag = this.willKingBeChecked();

                                    // if the King won't be checked:
                                    if (!_flag) {
                                        // if it's not the King:
                                        if (tmpChar2 != 'K') {
                                            return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S    "
                                                    + this.returnFullName(tmpChar2) + " !";
                                        }
                                        else {
                                            return "POSSIBLE CHECK!";
                                        }
                                    }
                                    else {
                                        return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                    }
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
                                    // move the chessman to the next position in order to check whether
                                    // your King will be checked:
                                    this.setChessmanToNewField();

                                    boolean _flag = this.willKingBeChecked();

                                    // if the King won't be checked:
                                    if (!_flag) {
                                        return "POSSIBLE MOVEMENT - THE FIELD IS FREE!";
                                    }
                                    else {
                                        return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                    }
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
                                    // move the chessman to the next position in order to check whether
                                    // your King will be checked:
                                    this.setChessmanToNewField();

                                    boolean _flag = this.willKingBeChecked();

                                    // if the King won't be checked:
                                    if (!_flag) {
                                        // if it's not the King:
                                        if (tmpChar2 != 'K') {
                                            return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S    "
                                                    + this.returnFullName(tmpChar2) + " !";
                                        }
                                        else {
                                            return "POSSIBLE CHECK!";
                                        }
                                    }
                                    else {
                                        return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                    }
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
                                    // move the chessman to the next position in order to check whether
                                    // your King will be checked:
                                    this.setChessmanToNewField();

                                    boolean _flag = this.willKingBeChecked();

                                    // if the King won't be checked:
                                    if (!_flag) {
                                        return "POSSIBLE MOVEMENT - THE FIELD IS FREE!";
                                    }
                                    else {
                                        return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                    }
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
                                    // move the chessman to the next position in order to check whether
                                    // your King will be checked:
                                    this.setChessmanToNewField();

                                    boolean _flag = this.willKingBeChecked();

                                    // if the King won't be checked:
                                    if (!_flag) {
                                        // if it's not the King:
                                        if (tmpChar2 != 'K') {
                                            return "POSSIBLE MOVEMENT - POSSIBLE CAPTURE OF OPPONENT'S    "
                                                    + this.returnFullName(tmpChar2) + " !";
                                        }
                                        else {
                                            return "POSSIBLE CHECK!";
                                        }
                                    }
                                    else {
                                        return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                                    }
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
                            // move the chessman to the next position in order to check whether
                            // your King will be checked:
                            this.setChessmanToNewField();

                            boolean _flag = this.willKingBeChecked();

                            // if the King won't be checked:
                            if (!_flag) {
                                return "POSSIBLE MOVEMENT - THE FIELD IS FREE!";
                            }
                            else {
                                return "IMPOSSIBLE MOVEMENT - YOUR KING WILL BE CHECKED!";
                            }
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

        return " ! ";
    }

    //===================================================
    // private:

    private void setChessmanToNewField() {
        // set the chessman to the new positon:
        this.chessmen.get(this.nextMovement.getRow()).set(this.nextMovement.getColumn(),
                this.chessmen.get(this.chessmanToMove.getRow()).get(this.chessmanToMove.getColumn()));
        // and remove it from the previous field:
        this.chessmen.get(this.chessmanToMove.getRow()).set(this.chessmanToMove.getColumn(),
                new Chessman(this.chessmanToMove.getRow(), this.convertColumn(this.chessmanToMove.getColumn()),
                        new Player('['), ']'));

        // print data:
        // System.out.println("=================================");
        // this.showData();
        // System.out.println("=================================");
    }

    private char convertColumn(int j) {
        if (j == 0) {
            return 'A';
        }
        else if (j == 1) {
            return 'B';
        }
        else if (j == 2) {
            return 'C';
        }
        else if (j == 3) {
            return 'D';
        }
        else if (j == 4) {
            return 'E';
        }
        else if (j == 5) {
            return 'F';
        }
        else if (j == 6) {
            return 'G';
        }
        else if (j == 7) {
            return 'H';
        }
        return ' ';
    }

    private String returnFullName(char c) {
        if (c == 'P') {
            return "PAWN";
        }
        else if (c == 'R') {
            return "ROOK";
        }
        else if (c == 'B') {
            return "BISHOP";
        }
        else if (c == 'N') {
            return "KNIGHT";
        }
        else if (c == 'Q') {
            return "QUEEN";
        }
        else {
            return "KING";
        }
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

        // check every path starting from the King's position:

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

        //=======================================================================
        // check if the King will be checked by the opponent's King:

        // check 1 field up:
        _i = kingPosition.getRow() - 1;
        _j = kingPosition.getColumn();

        char tmpChar = ' ';
        char tmpChar2 = ' ';

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's King:
                if(tmpChar2 == 'K') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check 1 field up and right:
        _i = kingPosition.getRow() - 1;
        _j = kingPosition.getColumn() + 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's King:
                if(tmpChar2 == 'K') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check 1 field right:
        _i = kingPosition.getRow();
        _j = kingPosition.getColumn() + 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's King:
                if(tmpChar2 == 'K') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check 1 field down and right:
        _i = kingPosition.getRow() + 1;
        _j = kingPosition.getColumn() + 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's King:
                if(tmpChar2 == 'K') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check 1 field down:
        _i = kingPosition.getRow() + 1;
        _j = kingPosition.getColumn();

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's King:
                if(tmpChar2 == 'K') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check 1 field down and left:
        _i = kingPosition.getRow() + 1;
        _j = kingPosition.getColumn() - 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's King:
                if(tmpChar2 == 'K') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check 1 field left:
        _i = kingPosition.getRow();
        _j = kingPosition.getColumn() - 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's King:
                if(tmpChar2 == 'K') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check 1 field up and left:
        _i = kingPosition.getRow() - 1;
        _j = kingPosition.getColumn() - 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's King:
                if(tmpChar2 == 'K') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        //=======================================================================
        // check if the King will be checked by the opponent's kNights:

        // check up and right corner:

        _i = kingPosition.getRow() - 1;
        _j = kingPosition.getColumn() + 2;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's kNight:
                if(tmpChar2 == 'N') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        _i = kingPosition.getRow() - 2;
        _j = kingPosition.getColumn() + 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's kNight:
                if(tmpChar2 == 'N') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check up and left corner:

        _i = kingPosition.getRow() - 2;
        _j = kingPosition.getColumn() - 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's kNight:
                if(tmpChar2 == 'N') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        _i = kingPosition.getRow() - 1;
        _j = kingPosition.getColumn() - 2;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's kNight:
                if(tmpChar2 == 'N') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check down and left corner:

        _i = kingPosition.getRow() + 1;
        _j = kingPosition.getColumn() - 2;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's kNight:
                if(tmpChar2 == 'N') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        _i = kingPosition.getRow() + 2;
        _j = kingPosition.getColumn() - 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's kNight:
                if(tmpChar2 == 'N') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        // check down and right corner:

        _i = kingPosition.getRow() + 2;
        _j = kingPosition.getColumn() + 1;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's kNight:
                if(tmpChar2 == 'N') {
                    // the King will be checked!
                    flag = true;
                }
            }
        }

        _i = kingPosition.getRow() + 1;
        _j = kingPosition.getColumn() + 2;

        // if we are within board:
        if (_i > -1 && _j > - 1 && _i < 8 && _j < 8) {
            tmpChar = this.chessmen.get(_i).get(_j).getPlayer().getName();
            tmpChar2 = this.chessmen.get(_i).get(_j).getChessman();

            // if it's the opponent:
            if (tmpChar == this.opponentPlayer.getName()) {
                // if it's the opponent's kNight:
                if(tmpChar2 == 'N') {
                    // the King will be checked!
                    flag = true;
                }
            }
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
}

