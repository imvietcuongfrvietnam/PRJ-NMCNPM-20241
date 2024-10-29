package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Switcher;

public class HomePageController {

    private final Switcher switcher = new Switcher();

    // Hàm xử lý sự kiện khi nhấn vào nút Sign Up
    @FXML
    private void handleSignUpButtonAction(ActionEvent event) {
        switcher.goSignUp(); // Chuyển cảnh đến SignUp.fxml
    }

    // Hàm xử lý sự kiện khi nhấn vào nút Home Page
    @FXML
    private void handleHomePageButtonAction(ActionEvent event) {
        switcher.goHomePage(); // Chuyển cảnh đến HomePage.fxml
    }
}
