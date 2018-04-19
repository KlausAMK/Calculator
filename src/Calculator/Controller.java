package Calculator;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 */
public class Controller {
    @FXML
    private MenuItem closeItem;

    @FXML
    private Label historyValue;

    @FXML
    private Label resultValue;

    @FXML
    private Button numC;

    @FXML
    private VBox vBox;

    private String operator = "";
    private String number1 = "";
    private String currentResult = "";
    private char lastChar;
    private boolean clear = false;

    /**
     * throwAlert method will show a simple Error screen using JavaFX Alert class.
     */
    private void throwAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Hello! It's me the simple-minded calculator");
        alert.setContentText("I can't handle more than one operator at a time!");
        alert.showAndWait();
    }

    /**
     * getOperatorCount method count's operators from the given param.
     *
     * @param text String object where the regex pattern is applied.
     * @return amount of operators found
     */
    private int getOperatorCount(String text) {
        Pattern p = Pattern.compile("\\+|\\-|\\/|\\*");
        Matcher m = p.matcher(text);
        int count = 0;
        while (m.find())
            count++;
        return count;


    }

    /**
     * numDuplicates method removes if they are "bad" ones (0 . )
     *
     * @param buttonKey     is the key that is used currently
     * @param currentResult is the value which we will use to find duplicates from
     * @param lastChar      is the last character of the currentResult
     * @return fixed input text
     */
    private String numDuplicates(String buttonKey, char lastChar, String currentResult) {
        if (buttonKey.equals(".") && currentResult.contains("."))
            buttonKey = "";
        if (buttonKey.equals("0") && currentResult.length() == 1 && lastChar == '0') {
            buttonKey = "";
        } else {
            if (!buttonKey.equals("0") && currentResult.length() == 1 && (lastChar == '0')) {
                currentResult = "";
            }
        }
        return currentResult + buttonKey;
    }

    /**
     * numButtons method handles numeric buttons.
     *
     * @param buttonKey is the key that is used currently.
     */
    private void numButtons(String buttonKey) {
        reset();
        currentResult = resultValue.getText();
        lastChar = currentResult.charAt(currentResult.length() - 1);
        resultValue.setText(numDuplicates(buttonKey, lastChar, currentResult));
    }

    /**
     * operatorButtons method handles operator buttons and
     * gives the values to calculate method.
     *
     * @param buttonKey is the key that is used currently.
     */
    private void operatorButtons(String buttonKey) {

        reset();
        String currentHistory = historyValue.getText();


        if (!"=".equals(buttonKey)) {
            if (getOperatorCount(historyValue.getText()) >= 1) {
                throwAlert();
                resultValue.setText(resultValue.getText());
                return;
            }
            operator = buttonKey;
            historyValue.setText(currentHistory + resultValue.getText() + operator);
            number1 = resultValue.getText();
            resultValue.setText("0");
        } else {
            if (!number1.isEmpty()) {
                historyValue.setText(currentHistory + resultValue.getText());
                resultValue.setText(String.valueOf(new Math().calclulate(Double.parseDouble(number1), Double.parseDouble(resultValue.getText()), operator)));
                clear = true;
                saveHistory(historyValue.getText() + " = " + resultValue.getText());
            }
        }
    }


    /**
     * handleButtons method will add Event handler to all numeric buttons.
     *
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @FXML
    private void handleNumButtons(ActionEvent e) {
        numButtons(((Button) e.getSource()).getText());
    }


    /**
     * handleOperatorButtons method will add Event handler to all operator buttons.
     *
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @FXML
    private void handleOperatorButtons(ActionEvent e) {
        operatorButtons(((Button) e.getSource()).getText());
    }

    /**
     * reset method resets values from resultValue and historyValue.
     */
    private void reset() {
        if (clear) {
            resultValue.setText("0");
            historyValue.setText("");
            clear = false;
            number1 = "";
        }
    }

    /**
     * saveHistory method saves the given text into file
     *
     * @param s Text to save
     */
    public void saveHistory(String s) {
        LocalDate localDate = LocalDate.now();
        try {
            Files.write(Paths.get("./History.txt"), (localDate.toString() + " >> " + s + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * initialize method will add event handles for several buttons and
     * setups the logic behind keyboard input
     */

    @FXML
    private void initialize() {
        closeItem.setOnAction(e -> Main.closeMe());

        numC.setOnAction(e -> {
            resultValue.setText("0");
            historyValue.setText("");
        });

        vBox.setOnKeyPressed(e -> {
            Map<Integer, String> operatorMap = Map.of(106, "*", 107, "+", 109, "-", 110, ".",
                    111, "/");


            currentResult = resultValue.getText();
            lastChar = currentResult.charAt(currentResult.length() - 1);

            int keyCode = e.getCode().getCode();

            if (keyCode >= 106 && keyCode <= 111) {
                operator = operatorMap.get(keyCode);
            }

            if (keyCode >= 96 && keyCode <= 105)
                keyCode -= 48;


            if (("1234567890.".indexOf((char) keyCode) != -1)) {
                numButtons(Character.toString((char) keyCode));
            } else {
                if ("/*-+".contains(operator))
                    operatorButtons(operator);
            }
        });
    }


}
