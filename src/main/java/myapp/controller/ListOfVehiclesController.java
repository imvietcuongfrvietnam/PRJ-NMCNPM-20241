package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;
import myapp.model.entities.entitiesdb.Vehicle;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class ListOfVehiclesController extends BaseController {
    @FXML private StackPane stackPaneInsertUpdate;
    @FXML private Button addButton, cancelButton, saveButton, listOfApartmentsButton;
    @FXML private TableView<Vehicle> vehicleTableView;
    @FXML private TableColumn<Vehicle, Integer> indexColumn;
    @FXML private TableColumn<Vehicle, String> houseHoldIDColumn, vehicleTypeColumn, licensePlateColumn, startDateColumn, endDateColumn, noteColumn;
    @FXML private TableColumn<Vehicle, HBox> operationsColumn;
    @FXML private Pagination pagination;
    @FXML private TextField searchText;

    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<Vehicle> vehiclesList;
    private ObservableList<Vehicle> filteredList;
    private Vehicle editingVehicle;
    private final Switcher switcher = new Switcher();

    @Override
    public void initialize() {
        super.initialize();
        vehiclesList = SQLConnector.getVehicles();
        filteredList = FXCollections.observableArrayList(vehiclesList);

        // Cập nhật số thứ tự trong bảng Vehicle
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = vehicleTableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        // Thiết lập các cột trong bảng Vehicle
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("houseHoldID"));
        vehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleType"));
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("licensePlate"));
        startDateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = LocalDate.parse(cellData.getValue().getStartDate(), formatter).format(formatter);
            return new SimpleObjectProperty<>(formattedDate);
        });
        endDateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = LocalDate.parse(cellData.getValue().getEndDate(), formatter).format(formatter);
            return new SimpleObjectProperty<>(formattedDate);
        });
        noteColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("note"));
        operationsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createViewEditDeleteButtons(param)));

        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterVehicles());

        // Cập nhật bảng Vehicle
        vehicleTableView.setItems(vehiclesList);
        vehicleTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((vehiclesList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-backround-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #002060; -fx-font-size: 25;");

        addButton.setOnAction(actionEvent -> add());
        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
        listOfApartmentsButton.setOnAction(event -> {
            try {
                switcher.goListOfApartmentPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
        viewButton.setOnAction(event -> viewVehicle(param.getValue()));

        // Thêm nút sửa
        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Edit.png")));
        editImageView.setFitWidth(40);
        editImageView.setFitHeight(40);
        editImageView.setPreserveRatio(false);
        Button editButton = new Button();
        editButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #00B050; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> editVehicle(param.getValue()));

        // Thêm nút xóa
        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        deleteImageView.setPreserveRatio(false);
        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #FF0000; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteVehicle(param.getValue()));

        hbox.getChildren().addAll(viewButton, editButton, deleteButton);
        return hbox;
    }
    private void viewVehicle(Vehicle vehicle) {
        stackPaneInsertUpdate.setVisible(true);


    }
    private void editVehicle(Vehicle vehicle) {
        stackPaneInsertUpdate.setVisible(true);
    }

    private void deleteVehicle(Vehicle vehicle) {
        vehiclesList.remove(vehicle);
        vehicleTableView.refresh();
        updatePagination(vehiclesList);
    }
    // Phương thức kết hợp tìm kiếm và lọc dữ liệu
    private void filterVehicles() {
        String searchKeyword = searchText.getText().toLowerCase();

        ObservableList<Vehicle> filtered = vehiclesList.filtered(vehicle -> {
            // Kiểm tra từ khóa tìm kiếm chỉ nếu có nhập vào
            boolean matchesSearch = searchKeyword.isEmpty() ||
                    vehicle.getHouseHoldID().toLowerCase().contains(searchKeyword) ||
                    vehicle.getVehicleType().toLowerCase().contains(searchKeyword) ||
                    vehicle.getLicensePlate().toLowerCase().contains(searchKeyword) ||
                    formatDate(vehicle.getStartDate()).toLowerCase().contains(searchKeyword) ||
                    formatDate(vehicle.getEndDate()).toLowerCase().contains(searchKeyword) ||
                    (vehicle.getNote() != null && vehicle.getNote().toLowerCase().contains(searchKeyword));
            return matchesSearch;
        });

        vehicleTableView.setItems(filtered);
        updatePagination(filtered); // Cập nhật phân trang sau khi lọc
    }

    // Phương thức tạo trang mới cho bảng Vehicle
    private TableView<Vehicle> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
        ObservableList<Vehicle> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
        vehicleTableView.setItems(pageData);

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return vehicleTableView;
    }

    // Cập nhật phân trang cho danh sách đã lọc
    private void updatePagination(ObservableList<Vehicle> filteredList) {
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
            ObservableList<Vehicle> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
            vehicleTableView.setItems(pageData);
            return vehicleTableView;
        });
        pagination.setPageCount((filteredList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }
    public void add() {
        editingVehicle = null;
        clearFields();
        stackPaneInsertUpdate.setVisible(true);
    }

    public void cancel() {
        clearFields();
        stackPaneInsertUpdate.setVisible(false);
    }

    public void save() {
        stackPaneInsertUpdate.setVisible(false);
        updatePagination(vehiclesList);
    }
    private void clearFields() {

    }
    // Chuyển đổi ngày thành định dạng dd/MM/yyyy
    private String formatDate(String date) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, inputFormatter).format(outputFormatter);
        } catch (DateTimeParseException e) {
            return date;  // Nếu không chuyển đổi được thì trả về ngày gốc
        }
    }
}
