package Calculator;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


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
        reset();
        String currentResult = resultValue.getText();
        char lastChar = currentResult.charAt(currentResult.length() - 1);
        String buttonKey = ((Button) e.getSource()).getText();
        resultValue.setText(numDuplicates(buttonKey, lastChar, currentResult));

    }


    @FXML
    private void handleOperatorButtons(ActionEvent e) {
        reset();

        String currentHistory = historyValue.getText();
        String buttonKey = ((Button) e.getSource()).getText();


        if (!"=".equals(buttonKey)) {
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
            Files.write(Paths.get("./History.txt"), ( localDate.toString() + " >> "+ s + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void initialize() {
        numC.setOnAction(e -> {
            resultValue.setText("0");
            historyValue.setText("");
        });
    }


}
