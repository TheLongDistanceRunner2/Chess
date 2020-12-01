public class Chessman {
    private int row;
    private char column;
    private char chessman; // kind of Figure
    private Player player;
    /// ruch

    public Chessman() {
        this.row = Integer.parseInt(null);
        this.column = ' ';
        this.player = null;
        this.chessman = ' ';
    }

    public Chessman(int row, char column, Player player, char chessman) {
        this.row = row;
        this.column = column;
        this.player = player;
        this.chessman = chessman;
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
}
