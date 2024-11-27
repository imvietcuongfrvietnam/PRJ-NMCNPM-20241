module myapp {
    requires javafx.controls;   // Cho các control JavaFX như Button, Label, ...
    requires javafx.fxml;       // Cho việc sử dụng FXML
    requires javafx.graphics;   // Cho các lớp đồ họa như Scene, Stage, ImageView
    requires java.sql;
    requires junit;
    requires com.fasterxml.jackson.databind;

    // Mở các package chứa controller và model để có thể sử dụng với JavaFX và FXML
    opens myapp.mvc.controller to javafx.fxml, javafx.base;  // Mở package controller để FXML có thể truy cập
    opens myapp.mvc.model to javafx.fxml;       // Nếu cần, mở package model (không bắt buộc)

    exports myapp;                           // Xuất package myapp để JavaFX có thể truy cập
    exports myapp.mvc.model;
    exports myapp.mvc.model.entities;
    opens myapp.mvc.model.entities to javafx.fxml;
    exports myapp.mvc.model.entities.entitiesdb;
    opens myapp.mvc.model.entities.entitiesdb to javafx.fxml;
    exports myapp.mvc.model.manager;
    opens myapp.mvc.model.manager to javafx.fxml; // Xuất package model nếu cần truy cập bên ngoài module
    exports myapp.mvc.model.entities.entitiessystem;
}
