package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import myapp.dao.VehicleManagementDAO;
import myapp.model.entities.entitiesdb.Vehicle;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.sql.Date;

public class ListOfVehiclesController extends ManagementController<Vehicle>{
    @FXML
    private TableColumn<Vehicle, String> houseHoldIDColumn, vehicleTypeColumn, licensePlateColumn, noteColumn;
    @FXML
    private TableColumn<Vehicle, Date> startDateColumn, endDateColumn;
    @FXML
    private Button listOfResidentsButton;
    @Override
    public void initialize() {
        super.initialize();
        listOfResidentsButton.setOnAction(
                event -> {
                    Switcher switcher = new Switcher();
                    try {
                        switcher.goListOfResidentsPage(event, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        entityList = VehicleManagementDAO.getVehicles();
        filteredList = FXCollections.observableArrayList(entityList);
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("houseHoldID"));
        vehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleType"));
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("licensePlate"));

        startDateColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Date>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Date>("endDate"));

        noteColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("note"));
        operationsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createDeleteButtons(param)));
        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterVehicles());
    }

    @Override
    protected void filterEntities() {

    }

    @Override
    protected void clearFields() {

    }

    @Override
    protected void save() {

    }

    @Override
    protected void cancel() {

    }

    @Override
    protected void add() {

    }

    private HBox createDeleteButtons(TableColumn.CellDataFeatures<Vehicle, HBox> param) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        // Thêm nút xóa
        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        deleteImageView.setPreserveRatio(false);
        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #FF0000; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteVehicle(param.getValue()));

        hbox.getChildren().addAll(deleteButton);
        return hbox;
    }
    private void deleteVehicle(Vehicle vehicle) {
        entityList.remove(vehicle);
        tableView.refresh();
        updatePagination(entityList);
        VehicleManagementDAO.deleteVehicle(vehicle.getLicensePlate());
    }

    // Phương thức kết hợp tìm kiếm và lọc dữ liệu
    private void filterVehicles() {
        String searchKeyword = searchText.getText().toLowerCase();
        ObservableList<Vehicle> filtered = entityList.filtered(vehicle -> {
            // Kiểm tra từ khóa tìm kiếm chỉ nếu có nhập vào
            return searchKeyword.isEmpty() ||
                    vehicle.getHouseHoldID().toLowerCase().contains(searchKeyword) ||
                    vehicle.getVehicleType().toLowerCase().contains(searchKeyword) ||
                    vehicle.getLicensePlate().toLowerCase().contains(searchKeyword) ||
                    (vehicle.getNote() != null && vehicle.getNote().toLowerCase().contains(searchKeyword));
        });

        tableView.setItems(filtered);
        updatePagination(filtered);
    }

}
