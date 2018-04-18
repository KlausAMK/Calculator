package Calculator;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Controller {
    @FXML
    private Label historyText;

    @FXML
    private Label resultValue;

    @FXML
    private Button numC;

    @FXML
    private Button numSubstract;

    @FXML
    private Button num1;

    @FXML
    private Button num2;

    @FXML
    private Button num3;

    @FXML
    private Button num4;

    @FXML
    private Button num5;

    @FXML
    private Button numPlus;

    @FXML
    private Button numMinus;

    @FXML
    private Button numMultiply;

    @FXML
    private Button num7;

    @FXML
    private Button num8;

    @FXML
    private Button num9;

    @FXML
    private Button num6;

    @FXML
    private Button numEquals;

    @FXML
    private Button num0;

    @FXML
    private Button numComma;


    //http://code.makery.ch/blog/javafx-8-event-handling-examples/
    public String operatorDuplicates(String buttonKey, char lastChar, String currentResult) {
        if (currentResult.contains(","))
            buttonKey = "";
        if ("+-/=".indexOf(lastChar) != -1 || currentResult.equals("0"))
            return "";
        return buttonKey;
    }

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
        System.out.println(resultValue.getText() + " buttonKey length is :" + resultValue.getText().length());
        String currentResult = resultValue.getText();
        char lastChar = currentResult.charAt(currentResult.length() - 1);
        String buttonKey = ((Button) e.getSource()).getText();
        System.out.println(lastChar);

        resultValue.setText(numDuplicates(buttonKey, lastChar, currentResult));

    }

    @FXML
    private void handleOperatorButtons(ActionEvent e) {
        String currentResult = resultValue.getText();
        char lastChar = currentResult.charAt(currentResult.length() - 1);
        String buttonKey = ((Button) e.getSource()).getText();
        resultValue.setText(currentResult + operatorDuplicates(buttonKey, lastChar, currentResult));

    }


}
