package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import myapp.model.communicatedb.select.HouseHoldSelect;
import myapp.model.communicatedb.select.ResidentSelect;
import myapp.model.communicatedb.update.ApartmentUpdate;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Apartment;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ListOfApartmentsController extends BaseController {
    @FXML private StackPane stackPaneInsertUpdate;
    @FXML private Button cancelButton, saveButton, listOfVehiclesButton;
    @FXML private TableView<Apartment> apartmentTableView;
    @FXML private TableColumn<Apartment, Integer> indexColumn, floorColumn, areaColumn;
    @FXML private TableColumn<Apartment, String> apartmentIDColumn, statusColumn, noteColumn;
    @FXML private TableColumn<Apartment, String> houseHoldIDColumn;
    @FXML private TableColumn<Apartment, HBox> operationsColumn;
    @FXML private Pagination pagination;
    @FXML private TextField searchText;
    @FXML private TextField apartmentIDText, floorText, areaText, residentNameText, residentIDText, houseHoldIDText;
    @FXML private TextArea noteText;
    @FXML private DatePicker moveInDate, moveOutDate;
    @FXML private ChoiceBox<String> floorChoiceBox, statusChoiceBox, status;

    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<Apartment> apartmentsList;
    private ObservableList<Apartment> filteredList;
    private Apartment editingApartment;
    private final Switcher switcher = new Switcher();

    @Override
    public void initialize() {
        super.initialize();
        apartmentsList = SQLConnector.getApartments();
        filteredList = FXCollections.observableArrayList(apartmentsList);
        // Cập nhật số thứ tự trong bảng HouseHold
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = apartmentTableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });
        // Thiết lập các cột trong bảng HouseHold
        apartmentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApartmentID()));
        floorColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFloor()));
        areaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getArea()));
        houseHoldIDColumn.setCellValueFactory(cellData -> {
            String apartmentID = cellData.getValue().getApartmentID();
            HouseHold houseHold = HouseHoldSelect.getHouseHoldByApartmentID(apartmentID);
            if (houseHold != null) {
                return new SimpleStringProperty(houseHold.getHouseHoldID());
            } else {
                return new SimpleStringProperty("N/A"); // Hoặc giá trị mặc định
            }
        });
        statusColumn.setCellFactory(param -> new TableCell<Apartment, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Apartment apartment = getTableRow().getItem();
                    String status = apartment != null ? apartment.getStatus() : "";

                    Label statusLabel = new Label();
                    if ("Đang sử dụng".equals(status)) {
                        statusLabel.setText("Đang sử dụng");
                        statusLabel.setStyle("-fx-pref-width: 180; -fx-pref-height: 40.75; -fx-background-color: rgba(0, 255, 0, 0.25); -fx-background-radius: 5; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal; -fx-alignment: center;");
                    } else if ("Chưa sử dụng".equals(status)) {
                        statusLabel.setText("Chưa sử dụng");
                        statusLabel.setStyle("-fx-pref-width: 180; -fx-pref-height: 40.75; -fx-background-color: rgba(255, 0, 0, 0.25); -fx-background-radius: 5; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal; -fx-alignment: center;");
                    }
                    statusLabel.setPadding(new Insets(5));
                    setGraphic(statusLabel);
                } else {
                    setGraphic(null);
                }
            }
        });
        noteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNote()));
        // Cột Edit và Delete trong bảng HouseHold
        operationsColumn.setCellValueFactory(param -> {
            HBox hbox = createViewEditDeleteButtons(param);
            return new SimpleObjectProperty<>(hbox);
        });
        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterApartments());
        floorChoiceBox.setItems(FXCollections.observableArrayList("Tầng", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        floorChoiceBox.setValue("Tầng");
        floorChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterApartments());
        statusChoiceBox.setItems(FXCollections.observableArrayList("Tình trạng", "Đang sử dụng", "Chưa sử dụng"));
        statusChoiceBox.setValue("Tình trạng");
        statusChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterApartments());
        // Cập nhật bảng Apartment
        apartmentTableView.setItems(apartmentsList);
        apartmentTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount(5);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-font-size: 25;");

        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
        listOfVehiclesButton.setOnAction(event -> {
            try {
                switcher.goListOfVehiclePage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Lựa chọn cho ChoiceBox status
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Đang sử dụng", "Chưa sử dụng");
        status.setItems(statusOptions);
        status.setValue("Đang sử dụng");
    }
    // Phương thức kết hợp tìm kiếm và lọc dữ liệu
    private void filterApartments() {
        String searchKeyword = searchText.getText().toLowerCase();
        String floorFilter = floorChoiceBox.getValue();
        String statusFilter = statusChoiceBox.getValue();

        ObservableList<Apartment> filtered = apartmentsList.filtered(apartment -> {
            // Kiểm tra từ khóa tìm kiếm chỉ nếu có nhập vào
            boolean matchesSearch = searchKeyword.isEmpty() ||
                    apartment.getApartmentID().toLowerCase().contains(searchKeyword) ||
                    String.valueOf(apartment.getFloor()).toLowerCase().contains(searchKeyword) ||
                    String.valueOf(apartment.getArea()).toLowerCase().contains(searchKeyword) ||
                    HouseHoldSelect.getHouseHoldByApartmentID(apartment.getApartmentID()).getHouseHoldID().toLowerCase().contains(searchKeyword) ||
                    apartment.getStatus().toLowerCase().contains(searchKeyword) ||
                    (apartment.getNote() != null && apartment.getNote().toLowerCase().contains(searchKeyword));

            boolean matchesFloor = floorFilter.equals("Tầng") || String.valueOf(apartment.getFloor()).equals(floorFilter);

            boolean matchesStatus = statusFilter.equals("Tình trạng") || apartment.getStatus().equals(statusFilter);

            return matchesSearch && matchesFloor && matchesStatus;
        });

        apartmentTableView.setItems(filtered);
        updatePagination(filtered); // Cập nhật phân trang sau khi lọc
    }

    private HBox createViewEditDeleteButtons(TableColumn.CellDataFeatures<Apartment, HBox> param) {
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
        viewButton.setOnAction(event -> viewApartment(param.getValue()));

        // Thêm nút sửa
        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Edit.png")));
        editImageView.setFitWidth(40);
        editImageView.setFitHeight(40);
        editImageView.setPreserveRatio(false);
        Button editButton = new Button();
        editButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #00B050; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> editApartment(param.getValue()));

        // Thêm nút xóa
        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        deleteImageView.setPreserveRatio(false);
        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #FF0000; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteApartment(param.getValue()));

        hbox.getChildren().addAll(viewButton, editButton, deleteButton);
        return hbox;
    }

    private void viewApartment(Apartment apartment) {
        editingApartment = apartment;
        apartmentIDText.setText(apartment.getApartmentID());
        floorText.setText(String.valueOf(apartment.getFloor()));
        areaText.setText(String.valueOf(apartment.getArea()));

        HouseHold houseHold = HouseHoldSelect.getHouseHoldByApartmentID(apartment.getApartmentID());
        if (houseHold != null) {
            // Kiểm tra nếu thông tin ngày tháng không bị null hoặc rỗng
            if (houseHold.getMoveInDate() != null && !houseHold.getMoveInDate().isEmpty()) {
                moveInDate.setValue(LocalDate.parse(houseHold.getMoveInDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if (houseHold.getMoveOutDate() != null && !houseHold.getMoveOutDate().isEmpty()) {
                moveOutDate.setValue(LocalDate.parse(houseHold.getMoveOutDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            houseHoldIDText.setText(houseHold.getHouseHoldID());
        }

        Resident resident = ResidentSelect.getResidentByApartmentID(apartment.getApartmentID());
        if (resident != null) {
            residentNameText.setText(resident.getName());
            residentIDText.setText(resident.getIDcard());
        }
        status.setValue(apartment.getStatus());
        noteText.setText(apartment.getNote());


        // Tắt khả năng chỉnh sửa cho các trường nhập liệu khi ở chế độ xem
        moveInDate.setMouseTransparent(true);
        moveOutDate.setMouseTransparent(true);
        status.setMouseTransparent(true);
        residentNameText.setEditable(false);
        residentIDText.setEditable(false);
        houseHoldIDText.setEditable(false);
        noteText.setEditable(false);

        saveButton.setVisible(false);
        cancelButton.setStyle("-fx-background-color: #0070C0; -fx-font-size: 20; -fx-text-fill: #FFFFFF; -fx-font-weight: Bold;");

        stackPaneInsertUpdate.setVisible(true);
    }
    private void editApartment(Apartment apartment) {
        editingApartment = apartment;
        apartmentIDText.setText(apartment.getApartmentID());
        floorText.setText(String.valueOf(apartment.getFloor()));
        areaText.setText(String.valueOf(apartment.getArea()));

        HouseHold houseHold = HouseHoldSelect.getHouseHoldByApartmentID(apartment.getApartmentID());
        if (houseHold != null) {
            // Kiểm tra nếu thông tin ngày tháng không bị null hoặc rỗng
            if (houseHold.getMoveInDate() != null && !houseHold.getMoveInDate().isEmpty()) {
                moveInDate.setValue(LocalDate.parse(houseHold.getMoveInDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if (houseHold.getMoveOutDate() != null && !houseHold.getMoveOutDate().isEmpty()) {
                moveOutDate.setValue(LocalDate.parse(houseHold.getMoveOutDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            houseHoldIDText.setText(houseHold.getHouseHoldID());
        }

        Resident resident = ResidentSelect.getResidentByApartmentID(apartment.getApartmentID());
        if (resident != null) {
            residentNameText.setText(resident.getName());
            residentIDText.setText(resident.getIDcard());
        }
        status.setValue(apartment.getStatus());
        noteText.setText(apartment.getNote());


        // Bật lại khả năng chỉnh sửa cho các trường nhập liệu khi ở chế độ sửa
        moveInDate.setMouseTransparent(false);
        moveOutDate.setMouseTransparent(false);
        status.setMouseTransparent(false);
        residentNameText.setEditable(true);
        residentIDText.setEditable(true);
        houseHoldIDText.setEditable(true);
        noteText.setEditable(true);

        saveButton.setVisible(true);
        cancelButton.setStyle("-fx-background-color: #F2F2F2; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal;");

        stackPaneInsertUpdate.setVisible(true);
    }

    private void deleteApartment(Apartment apartment) {
        ApartmentUpdate.removeHouseholdFromApartment(apartment.getApartmentID());
        apartmentsList.remove(apartment);
        apartmentTableView.refresh();
        updatePagination(filteredList);
    }

    private TableView<Apartment> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, apartmentsList.size());
        ObservableList<Apartment> pageData = FXCollections.observableArrayList(apartmentsList.subList(fromIndex, toIndex));
        apartmentTableView.setItems(pageData);

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return apartmentTableView;
    }

    private void updatePagination(ObservableList<Apartment> filteredList) {
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
            ObservableList<Apartment> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
            apartmentTableView.setItems(pageData);
            return apartmentTableView;
        });
        pagination.setPageCount((filteredList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }

    public void cancel() {
        clearFields();
        stackPaneInsertUpdate.setVisible(false);
    }

    public void save() {
        String apartmentID = apartmentIDText.getText();
        int floor = Integer.parseInt(floorText.getText());
        int area = Integer.parseInt(areaText.getText());
        String moveInDateValue = moveInDate.getValue() != null ? moveInDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
        String moveOutDateValue = moveOutDate.getValue() != null ? moveOutDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
        String statusValue = status.getValue();
        String residentName = residentNameText.getText();
        String residentID = residentIDText.getText();
        String houseHoldID = houseHoldIDText.getText();
        String note = noteText.getText();

        if (editingApartment != null) {
            editingApartment.setApartmentID(apartmentID);
            editingApartment.setFloor(floor);
            editingApartment.setArea(area);
            editingApartment.setStatus(statusValue);
            editingApartment.setNote(note);
            apartmentTableView.refresh();
            editingApartment = null;
            stackPaneInsertUpdate.setVisible(false);
            clearFields();
        } else {
            Apartment newApartment = new Apartment(apartmentID, floor, area, moveInDateValue, moveOutDateValue, statusValue, residentName, residentID, houseHoldID, note);
            apartmentsList.add(newApartment);
            ApartmentUpdate apartmentUpdate = new ApartmentUpdate();
            apartmentUpdate.update(apartmentID, floor, area, statusValue, note);

        }

        stackPaneInsertUpdate.setVisible(false);
        updatePagination(filteredList);
        //clearFields();
    }

    private void clearFields() {
        //apartmentIDText.clear();
        //floorText.clear();
        //areaText.clear();
        moveInDate.setValue(null);
        moveOutDate.setValue(null);
        status.setValue(null);
        residentNameText.clear();
        residentIDText.clear();
        houseHoldIDText.clear();
    }
}
