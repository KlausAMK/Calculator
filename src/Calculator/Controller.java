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


    private void throwAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Hello! It's me the simple-minded calculator");
        alert.setContentText("I can't handle more than one operator at the same time!");
        alert.showAndWait();
    }


    private int getOperatorCount(String text) {
        Pattern p = Pattern.compile("\\+|\\-|\\/|\\*");
        Matcher m = p.matcher(text);
        int count = 0;
        while (m.find())
            count++;
        return count;


    }

    private String numDuplicates(String buttonKey, char lastChar, String currentResult) {
        if(buttonKey.equals(".") && currentResult.contains("."))
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


    private void numButtons(String buttonKey) {
        reset();
        currentResult = resultValue.getText();
        lastChar = currentResult.charAt(currentResult.length() - 1);
        resultValue.setText(numDuplicates(buttonKey, lastChar, currentResult));
    }


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

    @FXML
    private void handleNumButtons(ActionEvent e) {
        numButtons(((Button) e.getSource()).getText());
    }


    @FXML
    private void handleOperatorButtons(ActionEvent e) {
        operatorButtons(((Button) e.getSource()).getText());
    }

    private void reset() {
        if (clear) {
            resultValue.setText("0");
            historyValue.setText("");
            clear = false;
            number1 = "";
        }
    }


    public void saveHistory(String s) {
        LocalDate localDate = LocalDate.now();
        try {
            Files.write(Paths.get("./History.txt"), (localDate.toString() + " >> " + s + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


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


            if (("1234567890".indexOf((char) keyCode) != -1)) {
                numButtons(Character.toString((char) keyCode));
            } else {
                if ("/*-+.".contains(operator))
                    operatorButtons(operator);
            }
        });
    }


}
