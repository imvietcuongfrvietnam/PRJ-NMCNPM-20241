package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import myapp.dao.ApartmentDAO;
import myapp.dao.HouseholdDAO;
import myapp.dao.ResidentDAO;
import myapp.model.entities.entitiesdb.Apartment;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;

import java.io.IOException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class ListOfApartmentsController extends ManagementController<Apartment> {
    @FXML
    private TableColumn<Apartment, Integer> floorColumn, areaColumn;
    @FXML
    private TableColumn<Apartment, String> apartmentIDColumn, statusColumn, noteColumn;
    @FXML
    private TableColumn<Apartment, String> houseHoldIDColumn;
    @FXML
    private TextField apartmentIDText, floorText, areaText, residentNameText, residentIDText, houseHoldIDText;
    @FXML
    private TextArea noteText;
    @FXML
    private DatePicker moveInDate, moveOutDate;
    @FXML
    private ChoiceBox<String> status;
    private Apartment editingApartment;
    @FXML
    private Button listOfResidentsButton;

    @Override
    public void initialize() {
        super.initialize();
        entityList = ApartmentDAO.getApartments();

        // Lựa chọn cho ChoiceBox status
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Đang sử dụng", "Chưa sử dụng");
        status.setItems(statusOptions);
        status.setValue("Đang sử dụng");


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
        listOfResidentsButton.setOnAction(event -> {
            try {
                switcher.goListOfResidentsPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void filterEntities() {

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

    private void viewEntities(Apartment apartment) {
        editingApartment = apartment;
        apartmentIDText.setText(apartment.getApartmentID());
        floorText.setText(String.valueOf(apartment.getFloor()));
        areaText.setText(String.valueOf(apartment.getArea()));

        HouseHold houseHold = HouseholdDAO.getHouseHoldByApartmentID(apartment.getApartmentID());
        if (houseHold != null) {
            if (houseHold.getMoveInDate() != null) {
                LocalDate moveInLocalDate = houseHold.getMoveInDate().toLocalDate();
                moveInDate.setValue(moveInLocalDate);
            }
            if (houseHold.getMoveOutDate() != null) {
                LocalDate moveOutLocalDate = houseHold.getMoveOutDate().toLocalDate();
                moveOutDate.setValue(moveOutLocalDate);
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
    private void editEntities(Apartment apartment) {
        editingApartment = apartment;
        apartmentIDText.setText(apartment.getApartmentID());
        floorText.setText(String.valueOf(apartment.getFloor()));
        areaText.setText(String.valueOf(apartment.getArea()));

        HouseHold houseHold = HouseholdDAO.getHouseHoldByApartmentID(apartment.getApartmentID());
        if (houseHold != null) {
            // Kiểm tra nếu thông tin ngày tháng không bị null hoặc rỗng
            if (houseHold.getMoveInDate() != null) {
                LocalDate moveInLocalDate = houseHold.getMoveInDate().toLocalDate();
                moveInDate.setValue(moveInLocalDate);
            }
            if (houseHold.getMoveOutDate() != null) {
                LocalDate moveOutLocalDate = houseHold.getMoveOutDate().toLocalDate();
                moveOutDate.setValue(moveOutLocalDate);
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
    private void updatePagination() {
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((entityList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        tableView.refresh();
    }
    @Override
    public void add() {
        editingApartment = null;
        clearFields();
        stackPaneInsertUpdate.setVisible(true);
    }
    @Override
    public void cancel() {
        clearFields();
        stackPaneInsertUpdate.setVisible(false);
    }
    @Override
    public void save() {
        String apartmentID = apartmentIDText.getText();
        Integer floor = Integer.valueOf(floorText.getText());
        Integer area = Integer.valueOf(areaText.getText());
        Date moveInDateValue = Date.valueOf(moveInDate.getValue());
        Date moveOutDateValue = Date.valueOf(moveOutDate.getValue());
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
            entityList.add(newApartment);
        }

        stackPaneInsertUpdate.setVisible(false);
        updatePagination();
    }
    @Override
    protected void clearFields() {
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
}
