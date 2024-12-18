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
import javafx.scene.layout.StackPane;
import myapp.dao.HouseholdDAO;
import myapp.dao.ResidentDAO;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class ListOfHouseHoldsController extends ManagementController<HouseHold> {
    @FXML
    private StackPane stackPaneInsertUpdate;
    @FXML
    private TableColumn<HouseHold, String> houseHoldIDColumn, apartmentIDColumn, moveInDateColumn, moveOutDateColumn, residentIDColumn, statusColumn;
    @FXML
    private TextField houseHoldIDText, apartmentIDText, addressText, residentNameText, residentIDText, residentPhoneText;
    @FXML
    private DatePicker moveInDate, moveOutDate;
    @FXML
    private ChoiceBox<String> status;
    @FXML
    private TableColumn<Resident, String> memberNameColumn, memberGenderColumn, memberBirthdayColumn, memberIDColumn;
    private HouseHold editingHouseHold;
    @Override
    public void initialize() {
        entityList = HouseholdDAO.getHouseholds();
        System.out.println("Fetched households: " + entityList.size());

        // Lựa chọn cho ChoiceBox status
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Đang sinh sống", "Đã rời đi");
        status.setItems(statusOptions);
        status.setValue("Đang sinh sống");


        // Thiết lập các cột
        houseHoldIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHouseHoldID()));
        apartmentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApartmentID()));
        moveInDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(formatDate(cellData.getValue().getMoveInDate())));
        moveOutDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(formatDate(cellData.getValue().getMoveOutDate())));
        residentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResidentID()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        operationsColumn.setCellValueFactory(param -> {
            HBox hbox = createEditDeleteButtons(param);
            return new SimpleObjectProperty<>(hbox);
        });

        addButton.setOnAction(actionEvent -> add());
        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
        tableView.setItems(entityList);
        pagination.setPageCount((entityList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }

    @Override
    protected void filterEntities() {
    }

    private String formatDate(Date date) {
        return date != null ? date.toLocalDate().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "31/12/9999";
    }

    private Date parseDate(LocalDate localDate) {
        return localDate != null ? Date.valueOf(localDate) : null;
    }

    private HBox createEditDeleteButtons(TableColumn.CellDataFeatures<HouseHold, HBox> param) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);

        ImageView editImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Edit.png"))));
        editImageView.setFitWidth(40);
        editImageView.setFitHeight(40);
        Button editButton = new Button();
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> editHouseHold(param.getValue()));

        ImageView deleteImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Delete.png"))));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        Button deleteButton = new Button();
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteHouseHold(param.getValue()));

        hbox.getChildren().addAll(editButton, deleteButton);
        return hbox;
    }

    private void editHouseHold(HouseHold houseHold) {
        editingHouseHold = houseHold;
        houseHoldIDText.setText(houseHold.getHouseHoldID());
        apartmentIDText.setText(houseHold.getApartmentID());
        addressText.setText("Chung cư BlueMoon");
        moveInDate.setValue(houseHold.getMoveInDate() != null ? houseHold.getMoveInDate().toLocalDate() : null);
        moveOutDate.setValue(houseHold.getMoveOutDate() != null ? houseHold.getMoveOutDate().toLocalDate() : null);
        residentIDText.setText(houseHold.getResidentID());
        status.setValue(houseHold.getStatus());
        //updateMemberTable(houseHold.getHouseHoldID());
        stackPaneInsertUpdate.setVisible(true);
    }

    private void deleteHouseHold(HouseHold houseHold) {
        entityList.remove(houseHold);
        updatePagination(filteredList);
        ObservableList<Resident> members = ResidentDAO.getResidentByHouseholdID(houseHold);
        for(Resident member : members){
            ResidentDAO.deleteResident(member);
        }
        HouseholdDAO.delete(houseHold.getHouseHoldID());
    }

    public void add() {
        editingHouseHold = null;
        clearFields();
        stackPaneInsertUpdate.setVisible(true);
    }

    public void cancel() {
        clearFields();
        stackPaneInsertUpdate.setVisible(false);
    }

    public void save() {
        String houseHoldID = houseHoldIDText.getText();
        String apartmentID = apartmentIDText.getText();
        Date moveInDateValue = parseDate(moveInDate.getValue());
        Date moveOutDateValue = parseDate(moveOutDate.getValue());
        String statusValue = status.getValue();
        String residentID = residentIDText.getText();

        if (editingHouseHold != null) {
            editingHouseHold.setHouseHoldID(houseHoldID);
            editingHouseHold.setApartmentID(apartmentID);
            editingHouseHold.setMoveInDate(moveInDateValue);
            editingHouseHold.setMoveOutDate(moveOutDateValue);
            editingHouseHold.setStatus(statusValue);
            editingHouseHold.setResidentID(residentID);
        } else {
            HouseHold newHouseHold = new HouseHold(houseHoldID, apartmentID, moveInDateValue, moveOutDateValue, statusValue, residentID);
            entityList.add(newHouseHold);
        }

        stackPaneInsertUpdate.setVisible(false);
        updatePagination(filteredList);

    }
    @Override
    protected void clearFields() {
        houseHoldIDText.clear();
        apartmentIDText.clear();
        moveInDate.setValue(null);
        moveOutDate.setValue(null);
        residentIDText.clear();
        status.setValue(null);
    }
}
