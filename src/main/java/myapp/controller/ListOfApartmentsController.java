package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import myapp.dao.ApartmentDAO;
import myapp.dao.HouseholdDAO;
import myapp.dao.ResidentDAO;
import myapp.model.entities.entitiesdb.Apartment;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;
import myapp.model.manager.Switcher;
import javafx.event.Event;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ListOfApartmentsController extends BaseController implements Initializable {
    @FXML private StackPane stackPaneInsertUpdate;
    @FXML private Button addButton, cancelButton, saveButton, listOfResidentsButton;
    @FXML private TableView<Apartment> apartmentTableView;
    @FXML private TableColumn<Apartment, Integer> indexColumn, floorColumn, areaColumn;
    @FXML private TableColumn<Apartment, String> apartmentIDColumn, statusColumn, noteColumn;
    @FXML private TableColumn<Apartment, String> houseHoldIDColumn;
    @FXML private TableColumn<Apartment, HBox> operationsColumn;
    @FXML private Pagination pagination;
    @FXML private TextField apartmentIDText, floorText, areaText, residentNameText, residentIDText, houseHoldIDText;
    @FXML private TextArea noteText;
    @FXML private DatePicker moveInDate, moveOutDate;
    @FXML private ChoiceBox<String> status;

    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<Apartment> apartmentsList;
    private Apartment editingApartment;
    private final Switcher switcher = new Switcher();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize();
        apartmentsList = ApartmentDAO.getApartments();

        // Lựa chọn cho ChoiceBox status
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Đang sử dụng", "Chưa sử dụng");
        status.setItems(statusOptions);
        status.setValue("Đang sử dụng");

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
            HouseHold houseHold = HouseholdDAO.getHouseHoldByApartmentID(apartmentID);
            if (houseHold != null) {
                return new SimpleStringProperty(houseHold.getHouseHoldID());
            } else {
                return new SimpleStringProperty("N/A"); // Hoặc giá trị mặc định
            }
        });


        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        noteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNote()));

        // Cột Edit và Delete trong bảng HouseHold
        operationsColumn.setCellValueFactory(param -> {
            HBox hbox = createViewEditDeleteButtons(param);
            return new SimpleObjectProperty<>(hbox);
        });

        // Cập nhật bảng Apartment
        apartmentTableView.setItems(apartmentsList);
        apartmentTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount(5);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-font-size: 25;");

        addButton.setOnAction(actionEvent -> add());
        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
        listOfResidentsButton.setOnAction(event -> switchToListOfResidents(event));
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
        editButton.setOnAction(event -> editHouseHold(param.getValue()));

        // Thêm nút xóa
        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        deleteImageView.setPreserveRatio(false);
        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #FF0000; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteHouseHold(param.getValue()));

        hbox.getChildren().addAll(viewButton, editButton, deleteButton);
        return hbox;
    }

    private void viewApartment(Apartment apartment) {
        editingApartment = apartment;
        apartmentIDText.setText(apartment.getApartmentID());
        floorText.setText(String.valueOf(apartment.getFloor()));
        areaText.setText(String.valueOf(apartment.getArea()));

        HouseHold houseHold = HouseholdDAO.getHouseHoldByApartmentID(apartment.getApartmentID());
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

        Resident resident = ResidentDAO.getResidentByApartmentID(apartment.getApartmentID());
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
    private void editHouseHold(Apartment apartment) {
        editingApartment = apartment;
        apartmentIDText.setText(apartment.getApartmentID());
        floorText.setText(String.valueOf(apartment.getFloor()));
        areaText.setText(String.valueOf(apartment.getArea()));

        HouseHold houseHold = HouseholdDAO.getHouseHoldByApartmentID(apartment.getApartmentID());
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

        Resident resident = ResidentDAO.getResidentByApartmentID(apartment.getApartmentID());
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

    private void deleteHouseHold(Apartment apartment) {
        apartmentsList.remove(apartment);
        apartmentTableView.refresh();
        updatePagination();
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

    private void updatePagination() {
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((apartmentsList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        apartmentTableView.refresh();
    }

    public void add() {
        editingApartment = null;
        clearFields();
        stackPaneInsertUpdate.setVisible(true);
    }

    public void cancel() {
        clearFields();
        stackPaneInsertUpdate.setVisible(false);
    }

    public void save() {
        String apartmentID = apartmentIDText.getText();
        Integer floor = Integer.valueOf(floorText.getText());
        Integer area = Integer.valueOf(areaText.getText());
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
        } else {
            Apartment newApartment = new Apartment(apartmentID, floor, area, statusValue, note);
            apartmentsList.add(newApartment);
        }

        stackPaneInsertUpdate.setVisible(false);
        updatePagination();
    }

    private void clearFields() {
        apartmentIDText.clear();
        floorText.clear();
        areaText.clear();
        moveInDate.setValue(null);
        moveOutDate.setValue(null);
        status.setValue(null);
        residentNameText.clear();
        residentIDText.clear();
        houseHoldIDText.clear();
    }

    private void switchToListOfResidents(Event event) {
        try {
            switcher.goListOfResidentsPage(event, this); // Gọi phương thức chuyển cảnh
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }
    }

}
