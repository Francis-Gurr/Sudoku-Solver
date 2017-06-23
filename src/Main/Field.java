package Main;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Created by franc on 26/05/2017.
 */
public class Field {

    private int row;
    private int col;
    private int value;
    private int square;
    private HashSet<Field> nodes;
    private LinkedList<Integer> possible;

    public Field(int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
        nodes = new HashSet<>();
        //Get top left position of the square
        int rowBox = row - (row%3);
        int colBox = col - (col%3);
        // Get the id of the square
        square = rowBox + colBox/3;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSquare() {
        return square;
    }

    public void setSquarse(int square) {
        this.square = square;
    }

    public HashSet<Field> getNodes() {
        return nodes;
    }

    public void setNodes(HashSet<Field> nodes) {
        this.nodes = nodes;
    }

    public LinkedList<Integer> getPossible() {
        return possible;
    }

    public void setPossible(LinkedList<Integer> possible) {
        this.possible = possible;
    }
}
