module myapp {
    requires javafx.controls;   // Cho các control JavaFX như Button, Label, ...
    requires javafx.fxml;       // Cho việc sử dụng FXML
    requires javafx.graphics;   // Cho các lớp đồ họa như Scene, Stage, ImageView
    requires java.sql;
    requires junit;
    requires com.fasterxml.jackson.databind;
    requires com.microsoft.sqlserver.jdbc;
    // requires com.microsoft.sqlserver.jdbc; // Nếu có sử dụng các tính năng liên quan đến database (tùy chọn)
    requires java.naming;

    // Mở các package chứa controller và model để có thể sử dụng với JavaFX và FXML
    opens myapp.controller to javafx.fxml, javafx.base;  // Mở package controller để FXML có thể truy cập
    opens myapp.model to javafx.fxml;       // Nếu cần, mở package model (không bắt buộc)

    exports myapp;                           // Xuất package myapp để JavaFX có thể truy cập
    exports myapp.model;
    opens myapp.model to javafx.fxml;
    exports myapp.model.entities;
    opens myapp.model.entities to javafx.fxml;
    exports myapp.model.entities.entitiesdb;
    opens myapp.model.entities.entitiesdb to javafx.fxml;
    exports myapp.model.manager;
    opens myapp.model.manager to javafx.fxml; // Xuất package model nếu cần truy cập bên ngoài module
    exports myapp.model.entities.entitiessystem;
}
