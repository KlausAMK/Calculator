package Calculator;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class Controller {
    @FXML
    private Label historyValue;

    @FXML
    private Label resultValue;
    @FXML
    private Button numC;

    private String operator = "";
    private String number1 = "";
    private boolean clear = false;


    //http://code.makery.ch/blog/javafx-8-event-handling-examples/


    public String numDuplicates(String buttonKey, char lastChar, String currentResult) {
        if (buttonKey.equals("0") && currentResult.length() == 1 && lastChar == '0') {
            buttonKey = "";
        } else {
            if (!buttonKey.equals("0") && currentResult.length() == 1 && (lastChar == '0')) {
                currentResult = "";
            }
        }
        return currentResult + buttonKey;
    }

    @FXML
    private void handleNumButtons(ActionEvent e) {
        if (clear) {
            resultValue.setText("0");
            clear = false;
            historyValue.setText("");
        }
        String currentResult = resultValue.getText();
        char lastChar = currentResult.charAt(currentResult.length() - 1);
        String buttonKey = ((Button) e.getSource()).getText();
        resultValue.setText(numDuplicates(buttonKey, lastChar, currentResult));

    }


    @FXML
    private void handleOperatorButtons(ActionEvent e) {

        String currentHistory = historyValue.getText();
        String buttonKey = ((Button) e.getSource()).getText();


        if (!"=".equals(buttonKey)) {
            operator = buttonKey;
            historyValue.setText(currentHistory + resultValue.getText() + operator);
            number1 = resultValue.getText();
            resultValue.setText("0");
        } else {
            historyValue.setText(currentHistory + resultValue.getText());
            resultValue.setText(String.valueOf(new Math().calclulate(Double.parseDouble(number1), Double.parseDouble(resultValue.getText()), operator)));
            clear = true;
        }



    }


    @FXML
    private void initialize() {
        numC.setOnAction(e -> resultValue.setText("0"));
    }


}
