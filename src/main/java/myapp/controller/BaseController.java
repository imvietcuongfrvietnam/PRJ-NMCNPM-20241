package myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import myapp.model.manager.Switcher;

import java.io.IOException;

public class BaseController {
    public void initialize() {
        Switcher switcher = new Switcher();
        homePageButton.setOnAction(event -> {
            try {
                switcher.goHomePage(event, this);
            } catch (IOException e) {
            }
        });
        logOutButton.setOnAction(event ->
        {
            try {
                switcher.goLogInPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        residentManagementButton.setOnAction(event ->
        {
            try {
                switcher.goListOfResidentsPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        settingButton.setOnAction(event ->
        {
            try {
                switcher.goSettingPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        generalManagementButton.setOnAction(event ->
        {
            try {
                switcher.goListOfApartmentPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        feeManagementButton.setOnAction(event ->
        {
            try {
                switcher.goFeeManagementPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    protected Button homePageButton;
    @FXML
    protected Button feeManagementButton;
    @FXML
    protected Button residentManagementButton;
    @FXML
    protected Button logOutButton;
    @FXML
    protected Button generalManagementButton;
    @FXML
    protected Button settingButton;
}
