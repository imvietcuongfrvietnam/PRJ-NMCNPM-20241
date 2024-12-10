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
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;
import myapp.model.manager.Switcher;
import javafx.event.Event;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class ListOfHouseHoldsController extends BaseController implements Initializable {
    @FXML private StackPane stackPaneInsertUpdate;
    @FXML private Button addButton, cancelButton, saveButton, listOfResidentsButton;
    @FXML private TableView<HouseHold> houseHoldTableView;
    @FXML private TableColumn<HouseHold, Integer> indexColumn;
    @FXML private TableColumn<HouseHold, String> houseHoldIDColumn, apartmentIDColumn, moveInDateColumn, moveOutDateColumn, residentIDColumn, statusColumn;
    @FXML private TableColumn<HouseHold, HBox> operationsColumn;
    @FXML private Pagination pagination;
    @FXML private TextField houseHoldIDText, apartmentIDText, addressText, residentNameText, residentIDText, residentPhoneText, searchText;
    @FXML private DatePicker moveInDate, moveOutDate;
    @FXML private ChoiceBox<String> status;
    @FXML private TableView<Resident> memberTableView;
    @FXML private TableColumn<Resident, Integer> memberIndexColumn;
    @FXML private TableColumn<Resident, String> memberNameColumn, memberGenderColumn, memberBirthdayColumn, memberIDColumn;


    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<HouseHold> houseHoldsList;
    private ObservableList<HouseHold> filteredList;
    private HouseHold editingHouseHold;
    private final Switcher switcher = new Switcher();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        houseHoldsList = SQLConnector.getHouseHolds();
        filteredList = FXCollections.observableArrayList(houseHoldsList);

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
        operationsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createViewEditDeleteButtons(param)));
        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterResidents());
        // Cập nhật bảng HouseHold
        houseHoldTableView.setItems(houseHoldsList);
        houseHoldTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount(5);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-font-size: 25;");

        addButton.setOnAction(actionEvent -> add());
        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
        listOfResidentsButton.setOnAction(event -> switchToListOfResidents(event));
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
        viewButton.setOnAction(event -> viewHouseHold(param.getValue()));

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

    private void viewHouseHold(HouseHold houseHold) {
        editingHouseHold = houseHold;
        houseHoldIDText.setText(houseHold.getHouseHoldID());
        apartmentIDText.setText(houseHold.getApartmentID());
        addressText.setText("Chung cư BlueMoon");
        moveInDate.setValue(LocalDate.parse(houseHold.getMoveInDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        moveOutDate.setValue(LocalDate.parse(houseHold.getMoveOutDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        String residentName = DataHandler.getResidentNameByResidentID(houseHold.getResidentID());
        String residentPhone = DataHandler.getResidentPhoneByResidentID(houseHold.getResidentID());
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
        moveInDate.setValue(LocalDate.parse(houseHold.getMoveInDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        moveOutDate.setValue(LocalDate.parse(houseHold.getMoveOutDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        String residentName = DataHandler.getResidentNameByResidentID(houseHold.getResidentID());
        String residentPhone = DataHandler.getResidentPhoneByResidentID(houseHold.getResidentID());
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
        houseHoldsList.remove(houseHold);
        houseHoldTableView.refresh();
        updatePagination(houseHoldsList);
    }

    private void filterResidents() {
        String searchKeyword = searchText.getText().toLowerCase();

        if (searchKeyword.isEmpty()) {
            houseHoldTableView.setItems(houseHoldsList); // Hiển thị tất cả khi ô tìm kiếm trống
            updatePagination(houseHoldsList); // Cập nhật phân trang
            return;
        }
        // Bộ lọc
        ObservableList<HouseHold> filteredList = houseHoldsList.filtered(houseHold -> {
            if (houseHold.getHouseHoldID().toLowerCase().contains(searchKeyword)) {
                return true;
            }
            if (houseHold.getApartmentID().toLowerCase().contains(searchKeyword)) {
                return true;
            }
            if (houseHold.getResidentID().toLowerCase().contains(searchKeyword)) {
                return true;
            }
            String moveInDate = formatDate(houseHold.getMoveInDate());
            if (moveInDate.toLowerCase().contains(searchKeyword)) {
                return true;
            }
            String moveOutDate = formatDate(houseHold.getMoveOutDate());
            if (moveOutDate.toLowerCase().contains(searchKeyword)) {
                return true;
            }
            if (houseHold.getStatus().toLowerCase().contains(searchKeyword)) {
                return true;
            }
            return false;
        });

        houseHoldTableView.setItems(filteredList);
        updatePagination(filteredList); // Cập nhật phân trang sau khi lọc
    }

    private TableView<HouseHold> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
        ObservableList<HouseHold> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
        houseHoldTableView.setItems(pageData);

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return houseHoldTableView;
    }

    private void updatePagination(ObservableList<HouseHold> filteredList) {
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
            ObservableList<HouseHold> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
            houseHoldTableView.setItems(pageData);
            return houseHoldTableView;
        });
        pagination.setPageCount((filteredList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }

    private void updateMemberTable(String houseHoldID) {
        ObservableList<Resident> members = DataHandler.getMembersByHouseHoldID(houseHoldID);

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
        updatePagination(houseHoldsList);
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
    private String formatDate(String formattedDate) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(formattedDate, inputFormatter).format(outputFormatter);
        } catch (DateTimeParseException e) {
            return formattedDate;
        }
    }

    private void switchToListOfResidents(Event event) {
        try {
            switcher.goListOfResidentsPage(event,this); // Gọi phương thức chuyển cảnh
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }
    }

}
