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
import myapp.dao.VehicleDAO;
import myapp.model.entities.entitiesdb.Vehicle;
import myapp.model.manager.FormatDatePresent;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.sql.Date;
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
        entityList = VehicleDAO.getVehicles();
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
        pagination.setPageFactory(this::createPage);


    }

    @Override
    protected void filterEntities() {

    }

    @Override
    protected void clearFields() {
        houseHoldIDText.clear();
        vehicleTypeText.clear();
        licensePlateText.clear();
        apartmentIDText.clear();
        endDate.setValue(null);
        startDate.setValue(null);
        vehicleTableView.setItems(null);
    }

    @Override
    protected void save() {
        editEntity = null;
        // Lấy thông tin từ các trường nhập liệu
        String idHousehold = houseHoldIDText.getText();
        String vehicleType = vehicleTypeText.getText();
        Date start = Date.valueOf(startDate.getValue());
        Date end = Date.valueOf(endDate.getValue());
        String bienSo = licensePlateText.getText();
        String note = noteText.getText();  // Ghi chú được lưu vào ThongTinBoSung

        // Kiểm tra tất cả các trường không rỗng
        if (idHousehold != null && !idHousehold.isEmpty() &&
                vehicleType != null && !vehicleType.isEmpty() &&
                bienSo != null && !bienSo.isEmpty() &&
                start != null && end != null) {

            // Tạo đối tượng Vehicle với các giá trị đã nhập
            Vehicle vehicle = new Vehicle(idHousehold, vehicleType, bienSo, start, end, note);

            // Lưu thông tin xe vào cơ sở dữ liệu
            VehicleDAO.insertVehicleManagement(vehicle);
        } else {
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setContentText("Chưa điền đủ thông tin!");
        }
    }


    @Override
    protected void cancel() {
        clearFields();
        stackPaneInsertUpdate.setVisible(false);
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

    private void viewEntities(Vehicle vehicle) {
        // Set information for viewing
        houseHoldIDText.setText(vehicle.getHouseHoldID());
        String apartmentID = ApartmentDAO.getApartmentIDHouseholdID(vehicle.getHouseHoldID());
        apartmentIDText.setText(apartmentID);
        addressText.setText("Chung cư BlueMoon"); // You can modify this if needed
        vehicleTypeText.setText(vehicle.getVehicleType());
        licensePlateText.setText(vehicle.getLicensePlate());
        noteText.setText(vehicle.getNote());
        startDate.setValue(vehicle.getStartDate().toLocalDate());
        endDate.setValue(vehicle.getEndDate().toLocalDate());

        // Make all fields non-editable
        stackPaneInsertUpdate.setVisible(false); // Hide the insert/update stack pane
        houseHoldIDText.setEditable(false);
        apartmentIDText.setEditable(false);
        vehicleTypeText.setEditable(false);
        licensePlateText.setEditable(false);
        noteText.setEditable(false);
        startDate.setMouseTransparent(true); // Disable date editing
        endDate.setMouseTransparent(true); // Disable date editing
        saveButton.setVisible(false); // Hide save button
        cancelButton.setStyle("-fx-background-color: #F2F2F2; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal;");

        // If you want to update the table or perform other actions for viewing, you can add them here
        updateVehicleTable(vehicle); // Show the table with vehicle data, if needed
    }


    private void editEntities(Vehicle vehicle) {
        editEntity = vehicle;

        // Set information
        houseHoldIDText.setText(vehicle.getHouseHoldID());
        String apartmentID = ApartmentDAO.getApartmentIDHouseholdID(vehicle.getHouseHoldID());
        apartmentIDText.setText(apartmentID);
        addressText.setText("Chung cư BlueMoon");
        vehicleTypeText.setText(vehicle.getVehicleType());
        licensePlateText.setText(vehicle.getLicensePlate());
        noteText.setText(vehicle.getNote());
        startDate.setValue(vehicle.getStartDate().toLocalDate());
        endDate.setValue(vehicle.getEndDate().toLocalDate());

        // Make fields editable
        stackPaneInsertUpdate.setVisible(true);
        houseHoldIDText.setEditable(false); // ID fields should not be editable
        apartmentIDText.setEditable(false); // Apartment ID should not be editable
        vehicleTypeText.setEditable(true); // Make vehicle type editable
        licensePlateText.setEditable(true); // Make license plate editable
        noteText.setEditable(true); // Make note editable
        startDate.setMouseTransparent(false); // Enable date picker
        endDate.setMouseTransparent(false); // Enable date picker
        saveButton.setVisible(true); // Show save button
        cancelButton.setStyle("-fx-background-color: #F2F2F2; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal;");

        // Get updated input values from the UI
        String vehicleType = vehicleTypeText.getText();
        String licensePlate = licensePlateText.getText();
        String note = noteText.getText();  // Note is used for ThongTinBoSung
        Date start = Date.valueOf(startDate.getValue());
        Date end = Date.valueOf(endDate.getValue());

        // Validate fields before updating
        if (vehicleType != null && !vehicleType.isEmpty() &&
                licensePlate != null && !licensePlate.isEmpty() &&
                start != null && end != null) {

            // Update the vehicle object with the new values
            vehicle.setVehicleType(vehicleType);
            vehicle.setLicensePlate(licensePlate);
            vehicle.setNote(note);
            vehicle.setStartDate(start);
            vehicle.setEndDate(end);

            // Update the vehicle in the database
            VehicleDAO.updateVehicleManagement(vehicle);
            updateVehicleTable(vehicle); // Refresh the table to show updated data
        } else {
            // Show alert if any required fields are empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Chưa điền đủ thông tin!");
            alert.show(); // Make sure to show the alert
        }
    }

    private void updateVehicleTable(Vehicle vehicle) {
        // Lấy danh sách Vehicle từ DAO
        ObservableList<Vehicle> members = VehicleDAO.getVehiclesByHouseholdID(vehicle.getHouseHoldID());

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
        VehicleDAO.deleteVehicle(vehicle.getLicensePlate());
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
