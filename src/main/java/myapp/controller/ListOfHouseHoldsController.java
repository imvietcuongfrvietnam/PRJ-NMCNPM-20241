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
import myapp.dao.HouseholdDAO;
import myapp.db.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ListOfHouseHoldController extends BaseController {
    @FXML private StackPane stackPaneInsertUpdate;
    @FXML private Button addButton, cancelButton, saveButton;
    @FXML private TableView<HouseHold> houseHoldTableView;
    @FXML private TableColumn<HouseHold, Integer> indexColumn;
    @FXML private TableColumn<HouseHold, String> houseHoldIDColumn, apartmentIDColumn, moveInDateColumn, moveOutDateColumn, residentIDColumn, statusColumn;
    @FXML private TableColumn<HouseHold, HBox> editColumn;
    @FXML private Pagination pagination;
    @FXML private TextField houseHoldIDText, apartmentIDText, addressText, residentNameText, residentIDText, residentPhoneText;
    @FXML private DatePicker moveInDate, moveOutDate;
    @FXML private ChoiceBox<String> status;
    @FXML private TableView<Resident> memberTableView;
    @FXML private TableColumn<Resident, Integer> memberIndexColumn;
    @FXML private TableColumn<Resident, String> memberNameColumn, memberGenderColumn, memberBirthdayColumn, memberIDColumn;

    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<HouseHold> houseHoldsList;
    private HouseHold editingHouseHold;
    @Override
    public void initialize() {
        houseHoldsList = HouseholdDAO.getHouseholds();

        // Lựa chọn cho ChoiceBox status
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Đang sinh sống", "Đã rời đi");
        status.setItems(statusOptions);
        status.setValue("Đang sinh sống");

        // Thiết lập cột chỉ số
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = houseHoldTableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        // Thiết lập các cột
        houseHoldIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHouseHoldID()));
        apartmentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApartmentID()));
        moveInDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(formatDate(cellData.getValue().getMoveInDate())));
        moveOutDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(formatDate(cellData.getValue().getMoveOutDate())));
        residentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResidentID()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        editColumn.setCellValueFactory(param -> {
            HBox hbox = createEditDeleteButtons(param);
            return new SimpleObjectProperty<>(hbox);
        });

        houseHoldTableView.setItems(houseHoldsList);
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((houseHoldsList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);

        addButton.setOnAction(actionEvent -> add());
        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
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

        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Edit.png")));
        editImageView.setFitWidth(40);
        editImageView.setFitHeight(40);
        Button editButton = new Button();
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> editHouseHold(param.getValue()));

        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
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
        updateMemberTable(houseHold.getHouseHoldID());
        stackPaneInsertUpdate.setVisible(true);
    }

    private void deleteHouseHold(HouseHold houseHold) {
        houseHoldsList.remove(houseHold);
        updatePagination();
    }

    private TableView<HouseHold> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, houseHoldsList.size());
        ObservableList<HouseHold> pageData = FXCollections.observableArrayList(houseHoldsList.subList(fromIndex, toIndex));
        houseHoldTableView.setItems(pageData);
        return houseHoldTableView;
    }

    private void updatePagination() {
        pagination.setPageCount((houseHoldsList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        houseHoldTableView.refresh();
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
            houseHoldsList.add(newHouseHold);
        }

        stackPaneInsertUpdate.setVisible(false);
        updatePagination();
    }

    private void clearFields() {
        houseHoldIDText.clear();
        apartmentIDText.clear();
        moveInDate.setValue(null);
        moveOutDate.setValue(null);
        residentIDText.clear();
        status.setValue(null);
    }
}
