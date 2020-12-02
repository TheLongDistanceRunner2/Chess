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

    //======================================
    public class Movement {
        int row;
        int column;

        public Movement() {
            this.row = 0;
            this.column = 0;
        }

        public Movement(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
    //=======================================

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

    //============================================
    private void setMovements() {
        // if it's a Pawn:
        if(this.chessman == 'P') {
            this.setMovementsPawn();
        }
        // if it's a Rook:
        else if (this.chessman == 'R') {

        }
        // if it's a kNight:
        else if (this.chessman == 'N') {

        }
        // if it's a Bishop:
        else if (this.chessman == 'B') {

        }
        // if it's a King:
        else if (this.chessman == 'K') {

        }
        // if it's a Queen:
        else if (this.chessman == 'Q') {

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
        this.movements.add(new Movement(this.row + 1, convertColumn(this.column)));
        // two fields forward:
        this.movements.add(new Movement(this.row + 2, convertColumn(this.column)));
    }
}
