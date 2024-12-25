package myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class BaseController {
    @FXML
    private Button homePageButton, residentManagementButton, feeManagementButton, generalManagementButton, settingButton, logOutButton;
    private final Switcher switcher = new Switcher();
    public void initialize() {
        homePageButton.setOnAction(event  -> {
            try {
                switcher.goHomePage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        logOutButton.setOnAction(event -> {
            try {
                switcher.goLogInPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        residentManagementButton.setOnAction(event -> {
            try {
                switcher.goListOfResidentsPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        settingButton.setOnAction(event -> {
            try {
                switcher.goSettingPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        generalManagementButton.setOnAction(event -> {
            try {
                switcher.goListOfApartmentPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        feeManagementButton.setOnAction(event -> {
            try {
                switcher.goFeeManagementPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String formatDate(String date) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, inputFormatter).format(outputFormatter);
        } catch (DateTimeParseException e) {
            return date;
        }
    }

}
