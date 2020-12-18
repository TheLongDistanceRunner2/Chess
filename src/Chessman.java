import java.util.ArrayList;
import java.util.List;

public class Chessman {
    private int row;
    private char column;
    private char chessman; // kind of Figure
    private Player player;
    // default movements:
    private List<Movement> movements;

    public Chessman() {
        this.row = Integer.parseInt(null);
        this.column = ' ';
        this.player = null;
        this.chessman = ' ';
        this.movements = null;
    }

    public Chessman(int row, char column, Player player, char chessman) {
        this.row = row;
        this.column = column;
        this.player = player;
        this.chessman = chessman;
        this.movements = new ArrayList<>();

        // set the movements depending on chessman:
        this.setMovements();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public void setColumn(char column) {
        this.column = column;
    }

    public char getChessman() {
        return chessman;
    }

    public void setChessman(char chessman) {
        this.chessman = chessman;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    //============================================
    // private:

    private void setMovements() {
        // if it's a Pawn:
        if(this.chessman == 'P') {
            this.setMovementsPawn();
        }
        // if it's a Rook:
        else if (this.chessman == 'R') {
            this.setMovementsRook();
        }
        // if it's a kNight:
        else if (this.chessman == 'N') {
            this.setMovementskNight();
        }
        // if it's a Bishop:
        else if (this.chessman == 'B') {
            this.setMovementsBishop();
        }
        // if it's a King:
        else if (this.chessman == 'K') {
            this.setMovementsKing();
        }
        // if it's a Queen:
        else if (this.chessman == 'Q') {
            this.setMovementsQueen();
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

    private void setMovementsPawn() {
        // one field forward:
        this.movements.add(new Movement(this.row - 1, convertColumn(this.column)));
        // two fields forward:
        this.movements.add(new Movement(this.row - 2, convertColumn(this.column)));
        // capture:
        this.movements.add(new Movement(this.row - 1, convertColumn(this.column) + 1));
        this.movements.add(new Movement(this.row - 1, convertColumn(this.column) - 1));
    }

    private void setMovementsKing() {
        // down:
        this.movements.add(new Movement(this.row + 1, convertColumn(this.column)));
        // down and right:
        this.movements.add(new Movement(this.row + 1, convertColumn(this.column) + 1));
        // right:
        this.movements.add(new Movement(this.row, convertColumn(this.column) + 1));
        // up and right:
        this.movements.add(new Movement(this.row - 1, convertColumn(this.column) + 1));
        // up:
        this.movements.add(new Movement(this.row - 1, convertColumn(this.column)));
        // up and left:
        this.movements.add(new Movement(this.row - 1, convertColumn(this.column) - 1));
        // left:
        this.movements.add(new Movement(this.row, convertColumn(this.column) - 1));
        // left and down:
        this.movements.add(new Movement(this.row + 1, convertColumn(this.column) - 1));
    }

    private void setMovementsQueen() {
        // down:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row + i, convertColumn(this.column)));
        }
        // up:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row - i, convertColumn(this.column)));
        }
        // left:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row, convertColumn(this.column) - i));
        }
        // right:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row, convertColumn(this.column) + i));
        }
        // down and right:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row + i, convertColumn(this.column) + i));
        }
        // down and left:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row + i, convertColumn(this.column) - i));
        }
        // up and left:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row - i, convertColumn(this.column) - i));
        }
        // up and right:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row - i, convertColumn(this.column) + i));
        }
    }

    private void setMovementsRook() {
        // down:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row + i, convertColumn(this.column)));
        }
        // up:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row - i, convertColumn(this.column)));
        }
        // left:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row, convertColumn(this.column) - i));
        }
        // right:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row, convertColumn(this.column) + i));
        }
    }

    private void setMovementsBishop() {
        // down and right:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row + i, convertColumn(this.column) + i));
        }
        // down and left:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row + i, convertColumn(this.column) - i));
        }
        // up and left:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row - i, convertColumn(this.column) - i));
        }
        // up and right:
        for (int i = 1; i < 8; i++) {
            this.movements.add(new Movement(this.row - i, convertColumn(this.column) + i));
        }
    }

    private void setMovementskNight() {
        // down and left corner:
        this.movements.add(new Movement(this.row + 1, convertColumn(this.column) - 2));
        this.movements.add(new Movement(this.row + 2, convertColumn(this.column) - 1));
        // down and right corner:
        this.movements.add(new Movement(this.row + 2, convertColumn(this.column) + 1));
        this.movements.add(new Movement(this.row + 1, convertColumn(this.column) + 2));
        // up and right corner:
        this.movements.add(new Movement(this.row - 1, convertColumn(this.column) + 2));
        this.movements.add(new Movement(this.row - 2, convertColumn(this.column) + 1));
        // up and left corner:
        this.movements.add(new Movement(this.row - 2, convertColumn(this.column) - 1));
        this.movements.add(new Movement(this.row - 1, convertColumn(this.column) - 2));
    }
}
