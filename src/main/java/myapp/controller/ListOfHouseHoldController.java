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
import myapp.model.dao.select.ResidentDataHandler;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class ListOfHouseHoldController implements Initializable {
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        houseHoldsList = SQLConnector.getHouseHolds();

        // Lựa chọn cho ChoiceBox status
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Đang sinh sống", "Đã rời đi");
        status.setItems(statusOptions);
        status.setValue("Đang sinh sống");

        // Cập nhật số thứ tự trong bảng HouseHold
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = houseHoldTableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        // Thiết lập các cột trong bảng HouseHold
        houseHoldIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHouseHoldID()));
        apartmentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApartmentID()));
        moveInDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMoveInDate()));
        moveOutDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMoveOutDate()));
        residentIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResidentID()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        // Cột Edit và Delete trong bảng HouseHold
        editColumn.setCellValueFactory(param -> {
            HBox hbox = createEditDeleteButtons(param);
            return new SimpleObjectProperty<>(hbox);
        });

        // Cập nhật bảng HouseHold
        houseHoldTableView.setItems(houseHoldsList);
        houseHoldTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount(5);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-font-size: 25;");

        addButton.setOnAction(actionEvent -> add());
        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
    }

    private HBox createEditDeleteButtons(TableColumn.CellDataFeatures<HouseHold, HBox> param) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);

        // Thêm nút sửa
        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Edit.png")));
        editImageView.setFitWidth(40);
        editImageView.setFitHeight(40);
        editImageView.setPreserveRatio(false);
        Button editButton = new Button();
        editButton.getStyleClass().add("edit-button");
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> editHouseHold(param.getValue()));

        // Thêm nút xóa
        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        deleteImageView.setPreserveRatio(false);
        Button deleteButton = new Button();
        deleteButton.getStyleClass().add("delete-button");
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
        moveInDate.setValue(LocalDate.parse(houseHold.getMoveInDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        moveOutDateColumn.setCellValueFactory(cellData -> {
            String moveOutDate = cellData.getValue().getMoveOutDate();
            // Kiểm tra nếu có giá trị và hợp lệ
            if (moveOutDate != null && !moveOutDate.trim().isEmpty()) {
                try {
                    // Nếu có giá trị thì format lại theo định dạng dd/MM/yyyy
                    LocalDate date = LocalDate.parse(moveOutDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    return new SimpleStringProperty(formattedDate);
                } catch (DateTimeParseException e) {
                    return new SimpleStringProperty("31/12/9999"); // Nếu có lỗi khi parse thì trả về giá trị mặc định
                }
            } else {
                return new SimpleStringProperty("31/12/9999"); // Nếu không có giá trị, trả về giá trị mặc định
            }
        });

        String residentName = ResidentDataHandler.getResidentNameByResidentID(houseHold.getResidentID());
        residentNameText.setText(residentName);
        residentIDText.setText(houseHold.getResidentID());
        residentPhoneText.setText(" ");
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

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return houseHoldTableView;
    }

    private void updateMemberTable(String houseHoldID) {
        ObservableList<Resident> members = ResidentDataHandler.getMembersByHouseHoldID(houseHoldID);

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
            String originalDate = cellData.getValue().getBirthday();
            if (originalDate != null && !originalDate.isEmpty()) {
                try {
                    LocalDate date = LocalDate.parse(originalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    private void updatePagination() {
        pagination.setPageFactory(this::createPage);
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
        String address = addressText.getText();
        String moveInDateValue = moveInDate.getValue() != null ? moveInDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
        String moveOutDateValue = moveOutDate.getValue() != null ? moveOutDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
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
            HouseHold newHouseHold = new HouseHold(houseHoldID, apartmentID, address, moveInDateValue, moveOutDateValue, statusValue, residentID);
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
        residentNameText.clear();
        residentIDText.clear();
        residentPhoneText.clear();
        status.setValue(null);
    }
}
