package inputScene;

import Exceptions.InvalidBoardException;
import Main.Field;
import Main.SudokuSolver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import Exceptions.InvalidRangeException;

import java.util.LinkedList;

public class Controller {

    @FXML
    private Label errorMsg;
    @FXML
    private GridPane board;

    public void handleButton(ActionEvent event){
        // Determine which button was pressed
        String button = ((Button) event.getSource()).getId();
        switch (button) {
            case "clear":
                // Clear board
                for (Node node : board.getChildren()) {
                    TextField textField = new TextField();
                    if (node instanceof TextField) {
                        textField = (TextField) node;
                    }
                    textField.setText("");
                }
                //Remove any error messages
                errorMsg.setText("");
                break;
            case "solve":
                // Remove any error messages
                errorMsg.setText("");
                LinkedList<Field> boardFields = new LinkedList<>();
                try {
                    LinkedList<TextField> textFields = new LinkedList<>();
                    for (Node node : board.getChildren()) {
                        //Cast node to textField element
                        TextField textField = new TextField();
                        if (node instanceof TextField) {
                            textField = (TextField) node;
                            textFields.add(textField);
                        }
                        // Get input string
                        String input = textField.getText();
                        // Get coordinates of current node
                        Integer row = GridPane.getRowIndex(node);
                        Integer col = GridPane.getColumnIndex(node);
                        // If input is blank value = 0;
                        if (input.equals("")) {
                            boardFields.add(new Field(row, col, 0));
                        } else {
                            // Parse to int
                            int value = Integer.parseInt(input);
                            // Check 0 < value <= 9
                            checkRange(value);
                            boardFields.add(new Field(row, col, value));
                        }
                    }
                    //run solve function
                    LinkedList<Field> solvedBoard = SudokuSolver.solve(boardFields);
                    for (int i = 0; i < 81; i++){
                        textFields.get(i).setText(String.valueOf(solvedBoard.get(i).getValue()));
                    }
                }
                catch (NumberFormatException ex){
                    errorMsg.setText("ERROR: NON-NUMERICAL INPUT(S)");
                }
                catch (InvalidRangeException e){
                    errorMsg.setText("ERROR: INPUT(S) OUT OF RANGE");
                }
                catch (InvalidBoardException e) {
                    errorMsg.setText("ERROR: UNSOLVABLE PUZZLE, CHECK INPUT(S)");
                }

        }
    }

    private static boolean checkRange(int n) throws InvalidRangeException{
        if (n < 10 && n > 0) {
            return true;
        } else {
            throw new InvalidRangeException();
        }
    }

}
