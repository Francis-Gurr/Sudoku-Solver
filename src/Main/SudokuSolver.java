package Main;

import Exceptions.InvalidBoardException;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by Francis on 15/05/2017.
 */
public class SudokuSolver {

    public static LinkedList<Field> solve(LinkedList<Field> board) throws InvalidBoardException{
        // Create map of board
        createMap(board);
        // Check board is actually solvable
        checkSolvable(board);
        // Create list of empty fields
        LinkedList<Field> emptyFields = new LinkedList<>();
        for (Field field: board) {
            if (field.getValue() == 0){
                emptyFields.add(field);
            }
        }
        // Find possible values for each empty field
        updatePoss(emptyFields);
        // Find solution using recursion
        recursion(emptyFields);
        // Return finished board
        return board;
    }

    private static boolean checkSolvable(LinkedList<Field> board) throws InvalidBoardException {
        for (Field field: board) {
            HashSet<Field> nodes = field.getNodes();
            int value = field.getValue();
            // Ensure nodes all have different values
            if (value != 0) {
                for (Field node : nodes) {
                    if (node.getValue() == value) {
                        throw new InvalidBoardException();
                    }
                }
            }
        }
        return true;
    }

    private static void createMap(LinkedList<Field> board){
        for (Field field: board) {
            int row = field.getRow();
            int col = field.getCol();
            int square = field.getSquare();
            HashSet<Field> nodes = new HashSet<>();
            // Loop through board and add a field as a node if it's on the same row, column or in the same 3x3 square
            for (Field node: board) {
                if (!node.equals(field)) {
                    if (node.getRow() == row) {
                        nodes.add(node);
                    }
                    if (node.getCol() == col) {
                        nodes.add(node);
                    }
                    if (node.getSquare() == square) {
                        nodes.add(node);
                    }
                }
            }
            field.setNodes(nodes);
        }
    }
    
    private static void updatePoss(LinkedList<Field> fields){
        for (Field field: fields) {
            // Create list for values that are not possible
            LinkedList<Integer> notPossible = new LinkedList<>();
            HashSet<Field> nodes = field.getNodes();
            // Loop through the nodes
            for (Field node: nodes) {
                // If node has a value add value to list of not possible values
                if (node.getValue() != 0){
                    notPossible.add(node.getValue());
                }
            }
            // Create list for possible values
            LinkedList<Integer> possible = new LinkedList<>();
            // Loop through all values (1-9)
            for (int i = 1; i < 10; i++){
                // If value is not in list of not possible values add it to list of possible values
                if (!notPossible.contains(i)){
                    possible.add(i);
                }
            }
            field.setPossible(possible);
        }
    }

    private static boolean recursion(LinkedList<Field> emptyFields){
        Field field = emptyFields.getFirst();
        // If there are no possible values return false
        if (field.getPossible().size() == 0){
            return false;
        }
        // If at last field, set value and return true
        if (emptyFields.size() == 1) {
            field.setValue(field.getPossible().getFirst());
            emptyFields.remove(field);
            return true;
        }
        else {
            for (int i = 1; i < 10; i++){
                // If value is possible
                if (field.getPossible().contains(i)){
                    // Set value
                    field.setValue(i);
                    // Update list of empty fields
                    emptyFields.remove(field);
                    // Update list of possible values
                    updatePoss(emptyFields);
                    if (recursion(emptyFields)){
                        return true;
                    }
                    else {
                        // Reset values
                        field.setValue(0);
                        emptyFields.add(field);
                        updatePoss(emptyFields);
                    }
                }
            }
            return false;
        }
    }
}