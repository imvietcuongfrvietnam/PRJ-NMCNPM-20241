package myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import myapp.model.manager.Switcher;

import java.io.IOException;

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

}
