public class Movement {
    private int row;
    private int column;

    public Movement() {
        this.row = 0;
        this.column = 0;
    }

    public Movement(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
