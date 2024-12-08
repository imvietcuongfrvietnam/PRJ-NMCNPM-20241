module myapp {
    requires javafx.controls;   // Cho các control JavaFX như Button, Label, ...
    requires javafx.fxml;       // Cho việc sử dụng FXML
    requires javafx.graphics;   // Cho các lớp đồ họa như Scene, Stage, ImageView
    requires java.sql;
    requires junit;
    requires com.fasterxml.jackson.databind;
    requires com.microsoft.sqlserver.jdbc;
    requires java.naming;
    requires jakarta.mail;

    // Mở các package chứa controller và model để có thể sử dụng với JavaFX và FXML
    opens myapp.controller to javafx.fxml, javafx.base;  // Mở package controller để FXML có thể truy cập

    // Xuất các package có liên quan đến entities, manager... để JavaFX có thể truy cập
    exports myapp.model.entities;
    opens myapp.model.entities to javafx.fxml;
    exports myapp.model.entities.entitiesdb;
    opens myapp.model.entities.entitiesdb to javafx.fxml;
    exports myapp.model.manager;
    opens myapp.model.manager to javafx.fxml;
    exports myapp.model.entities.entitiessystem;

    // Xuất package myapp và các thành phần khác nếu cần
    exports myapp;   // Nếu bạn muốn xuất package myapp (lớp Main, controller, etc.)
    opens myapp to javafx.fxml;   // Nếu cần mở cho FXML truy cập
}
