module myapp {
    requires javafx.controls;   // Cho các control JavaFX như Button, Label, ...
    requires javafx.fxml;       // Cho việc sử dụng FXML
    requires javafx.graphics;   // Cho các lớp đồ họa như Scene, Stage, ImageView
    requires java.sql;
    requires junit;          // Nếu có sử dụng các tính năng liên quan đến database (tùy chọn)

    // Mở các package chứa controller và model để có thể sử dụng với JavaFX và FXML
    opens myapp.controller to javafx.fxml;  // Mở package controller để FXML có thể truy cập
    opens myapp.model to javafx.fxml;       // Nếu cần, mở package model (không bắt buộc)

    exports myapp;                        // Xuất package myapp để JavaFX có thể truy cập
    exports myapp.model;
    exports myapp.model.object;
    opens myapp.model.object to javafx.fxml;                  // Xuất package model nếu cần truy cập bên ngoài module
}
