package myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import myapp.model.Switcher;

public class HomePageController {

    @FXML
    private void handleSignUpButtonAction(ActionEvent event) {
        Switcher.switchTo("SignUp.fxml");
    }

    @FXML
    private void handleHomePageButtonAction(ActionEvent event) {
        Switcher.switchTo("HomePage.fxml");
    }
}
