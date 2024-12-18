package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import myapp.dao.ApartmentDAO;
import myapp.dao.HouseholdDAO;
import myapp.dao.ResidentDAO;
import myapp.dao.VehicleManagementDAO;
import myapp.model.entities.entitiesdb.Apartment;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;
import myapp.model.entities.entitiesdb.Vehicle;
import myapp.model.manager.FormatDatePresent;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ListOfVehiclesController extends ManagementController<Vehicle>{
    @FXML
    private TableColumn<Vehicle, String> houseHoldIDColumn, vehicleTypeColumn, licensePlateColumn, noteColumn;
    @FXML
    private TableColumn<Vehicle, Date> startDateColumn, endDateColumn;
    @FXML
    private TableColumn<Vehicle, HBox> operationsColumn;
    @FXML
    private TextField houseHoldIDText, apartmentIDText, vehicleTypeText, licensePlateText, addressText;
    @FXML
    private TextArea noteText;

    @FXML
    private DatePicker startDate, endDate;
    @FXML
    private TableView<Vehicle> vehicleTableView;
    @FXML
    private TableColumn<Vehicle, Integer> IndexColumn;

    @FXML
    private TableColumn<Vehicle, String> VehicleTypeColumn, LicensePlateColumn, StartDateColumn, EndDateColumn;
    @FXML
    private Button listOfApartmentsButton;
    private Vehicle editEntity;
    private Vehicle edittingVehicle;
    @Override
    public void initialize() {
        super.initialize();
        listOfApartmentsButton.setOnAction(
                event -> {
                    Switcher switcher = new Switcher();
                    try {
                        switcher.goListOfApartmentPage(event, this);
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
        operationsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createViewEditDeleteButtons(param)));
        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterVehicles());
        pagination.setPageCount((entityList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        tableView.setItems(entityList);

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
        editEntity = null;
        clearFields();
        stackPaneInsertUpdate.setVisible(true);

    }

    private HBox createViewEditDeleteButtons(TableColumn.CellDataFeatures<Vehicle, HBox> param) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);

        // Thêm nút xem
        ImageView viewImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/View.png")));
        viewImageView.setFitWidth(40);
        viewImageView.setFitHeight(40);
        viewImageView.setPreserveRatio(false);
        Button viewButton = new Button();
        viewButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #0070C0; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        viewButton.setGraphic(viewImageView);
        viewButton.setOnAction(event -> viewEntities(param.getValue()));

        // Thêm nút sửa
        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Edit.png")));
        editImageView.setFitWidth(40);
        editImageView.setFitHeight(40);
        editImageView.setPreserveRatio(false);
        Button editButton = new Button();
        editButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #00B050; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> editEntities(param.getValue()));


        // Thêm nút xóa
        ImageView deleteImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Delete.png"))));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        deleteImageView.setPreserveRatio(false);
        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #FF0000; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteEntities(param.getValue()));

        hbox.getChildren().addAll(viewButton, editButton, deleteButton);
        return hbox;
    }

    private void viewEntities(Vehicle value) {
    }

    private void editEntities(Vehicle vehicle) {
        edittingVehicle = vehicle;
        //Set information
        houseHoldIDText.setText(vehicle.getHouseHoldID());
        String apartmentID = ApartmentDAO.getApartmentIDHouseholdID(vehicle.getHouseHoldID());
        apartmentIDText.setText(apartmentID);
        addressText.setText("Chung cư BlueMoon");
        vehicleTypeText.setText(vehicle.getVehicleType());
        licensePlateText.setText(vehicle.getLicensePlate());
        noteText.setText(vehicle.getNote());
        startDate.setValue(vehicle.getStartDate().toLocalDate());
        endDate.setValue(vehicle.getEndDate().toLocalDate());

        //Editable vehicles
        stackPaneInsertUpdate.setVisible(true);
        houseHoldIDText.setEditable(false);
        apartmentIDText.setEditable(false);
        vehicleTypeText.setEditable(true);
        licensePlateText.setEditable(true);
        noteText.setEditable(true);
        startDate.setMouseTransparent(false);
        endDate.setMouseTransparent(false);
        saveButton.setVisible(true);
        cancelButton.setStyle("-fx-background-color: #F2F2F2; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal;");

        updateVehicleTable(vehicle);
        //Show table view about vehicles owned by that household

    }
    private void updateVehicleTable(Vehicle vehicle) {
        // Lấy danh sách Vehicle từ DAO
        ObservableList<Vehicle> members = VehicleManagementDAO.getVehiclesByHouseholdID(vehicle.getHouseHoldID());

        // Cập nhật cột chỉ số (index) của thành viên trong gia đình
        IndexColumn.setCellValueFactory(cellData -> {
            int rowIndex = members.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>(rowIndex + 1);
        });

        // Cập nhật cột loại phương tiện
        VehicleTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVehicleType())
        );

        // Cập nhật cột biển số
        licensePlateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLicensePlate())
        );

        // Cập nhật cột ngày bắt đầu
        StartDateColumn.setCellValueFactory(cellData -> {
            String originalDate = cellData.getValue().getStartDate().toString();
            if (originalDate != null) {
                String formattedDate = FormatDatePresent.formatDate(Date.valueOf(originalDate)); // Định dạng ngày
                return new SimpleStringProperty(formattedDate); // Trả về chuỗi đã định dạng
            }
            return new SimpleStringProperty("31/12/9999"); // Giá trị mặc định nếu ngày null
        });

        // Cập nhật cột ngày kết thúc
        EndDateColumn.setCellValueFactory(cellData -> {
            Date originalDate = cellData.getValue().getEndDate();
            if (originalDate != null) {
                String formattedDate = FormatDatePresent.formatDate(originalDate); // Định dạng ngày
                return new SimpleStringProperty(formattedDate); // Trả về chuỗi đã định dạng
            }
            return new SimpleStringProperty("31/12/9999"); // Giá trị mặc định nếu ngày null
        });

        // Thiết lập dữ liệu cho TableView
        vehicleTableView.setItems(members);
    }



    private void deleteEntities(Vehicle vehicle) {
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
