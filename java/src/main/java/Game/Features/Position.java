package Game.Features;

public class Position {
    private int row;
    private int column;


    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {return row;}
    public void setRow(int row) {this.row = row;}
    public int getColumn() {return column;}
    public void setColumn(int column) {this.column = column;}
    public boolean isOnBoard(){
        return row > -1 && row < 8 && column > -1 && column < 8;
    }

    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
