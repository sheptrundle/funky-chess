package GameSetup;

import java.util.Map;

public class Position {
    private int row;
    private char column;
    private Map<Character, Integer> columnInts = Map.of('A', 0, 'B', 1, 'C', 2, 'D', 3, 'E', 4, 'F', 5, 'G', 6, 'H', 7);

    public Position(int row, char column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {return row;}
    public void setRow(int row) {this.row = row;}
    public char getColumn() {return column;}
    public void setColumn(char column) {this.column = column;}
    public int getRowAsIndex() {return row - 1;}
    public int getColumnAsIndex() {return columnInts.get(column);}
}
