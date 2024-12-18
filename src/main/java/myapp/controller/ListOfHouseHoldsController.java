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

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    @FXML private TableView<Resident> memberTableView;
    @FXML
    private TableColumn<Resident, String> memberNameColumn, memberGenderColumn, memberBirthdayColumn, memberIDColumn;
    @FXML private TableColumn<Resident, Integer> memberIndexColumn;
    private HouseHold editingHouseHold;
    @FXML
    private Button listOfResidentsButton;



    @Override
    public void initialize() {
        entityList = HouseholdDAO.getHouseholds();
        System.out.println("Fetched households: " + entityList.size());

        // Lựa chọn cho ChoiceBox status
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Đang sinh sống", "Đã rời đi");
        status.setItems(statusOptions);
        status.setValue("Đang sinh sống");
        if(filteredList == null) filteredList = entityList;

        // Thiết lập các cột
        houseHoldIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHouseHoldID()));
        apartmentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApartmentID()));
        moveInDateColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null || cellData.getValue().getMoveInDate() == null) {
                // Trả về ngày xa nhất (ngày cuối của java.sql.Date)
                return new SimpleStringProperty("9999-12-31");
            } else {
                return new SimpleStringProperty(cellData.getValue().getMoveInDate().toString());
            }
        });

        moveOutDateColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null || cellData.getValue().getMoveOutDate() == null) {
                // Trả về ngày xa nhất (ngày cuối của java.sql.Date)
                return new SimpleStringProperty("9999-12-31");
            } else {
                return new SimpleStringProperty(cellData.getValue().getMoveInDate().toString());
            }
        });

        residentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResidentID()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        operationsColumn.setCellValueFactory(param -> {
            HBox hbox = createViewEditDeleteButtons(param);
            return new SimpleObjectProperty<>(hbox);
        });

        addButton.setOnAction(actionEvent -> add());
        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
        tableView.setItems(entityList);
        pagination.setPageCount((entityList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        listOfResidentsButton.setOnAction(event -> {
            try {
                switcher.goListOfResidentsPage(event, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        pagination.setPageFactory(this::createPage);

    }

    private HBox createViewEditDeleteButtons(TableColumn.CellDataFeatures<HouseHold, HBox> param) {
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
        viewButton.setOnAction(event -> viewHousehold(param.getValue()));

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
    @Override
    protected void filterEntities() {
    }

    private Date parseDate(LocalDate localDate) {
        return localDate != null ? Date.valueOf(localDate) : null;
    }


    private void viewHousehold(HouseHold houseHold) {
        editingHouseHold = houseHold;
        houseHoldIDText.setText(houseHold.getHouseHoldID());
        apartmentIDText.setText(houseHold.getApartmentID());
        addressText.setText("Chung cư BlueMoon");

        // Kiểm tra nếu moveInDate hoặc moveOutDate là null, thì sử dụng LocalDate.MAX
        moveInDate.setValue(houseHold.getMoveInDate() != null ? houseHold.getMoveInDate().toLocalDate() : null);
        moveOutDate.setValue(houseHold.getMoveOutDate() != null ? houseHold.getMoveOutDate().toLocalDate() : null);

        String residentName = ResidentDAO.getResidentNameByResidentID(houseHold.getResidentID());
        String residentPhone = ResidentDAO.getResidentPhoneByResidentID(houseHold.getResidentID());
        residentNameText.setText(residentName);
        residentIDText.setText(houseHold.getResidentID());
        residentPhoneText.setText(residentPhone);
        status.setValue(houseHold.getStatus());
        updateMemberTable(houseHold.getHouseHoldID());

        // Tắt khả năng chỉnh sửa cho các trường nhập liệu khi ở chế độ xem
        houseHoldIDText.setEditable(false);
        apartmentIDText.setEditable(false);
        addressText.setEditable(false);
        moveInDate.setMouseTransparent(true);
        moveOutDate.setMouseTransparent(true);
        status.setMouseTransparent(true);
        residentNameText.setEditable(false);
        residentIDText.setEditable(false);
        residentPhoneText.setEditable(false);
        memberTableView.setEditable(false);

        saveButton.setVisible(false);
        cancelButton.setStyle("-fx-background-color: #0070C0; -fx-font-size: 20; -fx-text-fill: #FFFFFF; -fx-font-weight: Bold;");

        stackPaneInsertUpdate.setVisible(true);
    }

    private void editHouseHold(HouseHold houseHold) {
        editingHouseHold = houseHold;
        houseHoldIDText.setText(houseHold.getHouseHoldID());
        apartmentIDText.setText(houseHold.getApartmentID());
        addressText.setText("Chung cư BlueMoon");
        moveInDate.setValue(houseHold.getMoveInDate() != null ? houseHold.getMoveInDate().toLocalDate() : null);
        moveOutDate.setValue(houseHold.getMoveOutDate() != null ? houseHold.getMoveOutDate().toLocalDate() : null);

        String residentName = ResidentDAO.getResidentNameByResidentID(houseHold.getResidentID());
        String residentPhone = ResidentDAO.getResidentPhoneByResidentID(houseHold.getResidentID());
        residentNameText.setText(residentName);
        residentIDText.setText(houseHold.getResidentID());
        residentPhoneText.setText(residentPhone);
        status.setValue(houseHold.getStatus());

        // Bật lại khả năng chỉnh sửa cho các trường nhập liệu khi ở chế độ sửa
        houseHoldIDText.setEditable(true);
        apartmentIDText.setEditable(true);
        addressText.setEditable(true);
        moveInDate.setMouseTransparent(false);
        moveOutDate.setMouseTransparent(false);
        status.setMouseTransparent(false);
        residentNameText.setEditable(true);
        residentIDText.setEditable(true);
        residentPhoneText.setEditable(true);
        updateMemberTable(houseHold.getHouseHoldID());

        saveButton.setVisible(true);
        cancelButton.setStyle("-fx-background-color: #F2F2F2; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal;");

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
    private void updateMemberTable(String houseHoldID) {
        ObservableList<Resident> members = ResidentDAO.getMembersByHouseHoldID(houseHoldID);

        // Cập nhật cột chỉ số (index) của thành viên trong gia đình
        memberIndexColumn.setCellValueFactory(cellData -> {
            int rowIndex = members.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>(rowIndex + 1);
        });
        // Cập nhật cột tên thành viên
        memberNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        // Cập nhật cột giới tính của thành viên
        memberGenderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        // Cập nhật cột ngày sinh của thành viên
        memberBirthdayColumn.setCellValueFactory(cellData -> {
            String originalDate = String.valueOf(cellData.getValue().getBirthday());
            if (originalDate != null && !originalDate.isEmpty()) {
                try {
                    LocalDate date = LocalDate.parse(originalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    return new SimpleStringProperty(formattedDate);
                } catch (DateTimeParseException e) {
                    return new SimpleStringProperty("31/12/9999"); // Hoặc xử lý khác nếu cần
                }
            } else {
                return new SimpleStringProperty("31/12/9999");
            }
        });
        // Cập nhật cột ID của thành viên
        memberIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIDcard()));

        memberTableView.setItems(members);
    }

}
