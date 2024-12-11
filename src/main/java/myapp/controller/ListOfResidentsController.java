//package myapp.controller;
//
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.Event;
//import javafx.fxml.FXML;
//import javafx.geometry.Pos;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import myapp.dao.ResidentDAO;
//import myapp.model.entities.entitiesdb.Resident;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//
//public class ListOfResidentsController extends ManagementController<Resident>{
//    @FXML
//    private Button listOfHouseHoldButton;
//    @FXML
//    private TableColumn<Resident, String> nameColumn, genderColumn, birthdayColumn, IDcardColumn, hometownColumn, houseHoldIDColumn;
//    @FXML
//    private TextField nameText, IDcardText, hometownText, phoneText, ethnicityText, nationalityText, occupationText, educationText, houseHoldIDText, statusText;
//    @FXML
//    private TextArea additionalInfoText;
//    @FXML
//    private DatePicker birthdayText;
//    @FXML
//    private ToggleGroup genderGroup;
//    @FXML
//    private RadioButton maleRadioButton, femaleRadioButton;
//    private Resident editEntity;
//
//    @Override
//    public void initialize() {
//        super.initialize();
//        entityList = ResidentDAO.getResidents();
//        filteredList = FXCollections.observableArrayList(entityList);
//
//        // Thiết lập các cột trong bảng Resident
//        nameColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("name"));
//        genderColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("gender"));
//        birthdayColumn.setCellValueFactory(cellData -> {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            String formattedDate = LocalDate.parse((CharSequence) cellData.getValue().getBirthday(), formatter).format(formatter);
//            return new SimpleObjectProperty<>(formattedDate);
//        });
//        IDcardColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("IDcard"));
//        hometownColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("hometown"));
//        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("houseHoldID"));
//        operationsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createViewEditDeleteButtons(param)));
//
//        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterEntities());
//        listOfHouseHoldButton.setOnAction(event -> switchToListOfHouseHold(event));
//    }
//
//    private HBox createViewEditDeleteButtons(TableColumn.CellDataFeatures<Resident, HBox> param) {
//        HBox hbox = new HBox(10);
//        hbox.setAlignment(Pos.CENTER);
//
//        // Thêm nút xem
//        ImageView viewImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/View.png")));
//        viewImageView.setFitWidth(40);
//        viewImageView.setFitHeight(40);
//        viewImageView.setPreserveRatio(false);
//        Button viewButton = new Button();
//        viewButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #0070C0; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
//        viewButton.setGraphic(viewImageView);
//        viewButton.setOnAction(event -> viewResident(param.getValue()));
//
//        // Thêm nút sửa
//        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Edit.png")));
//        editImageView.setFitWidth(40);
//        editImageView.setFitHeight(40);
//        editImageView.setPreserveRatio(false);
//        Button editButton = new Button();
//        editButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #00B050; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
//        editButton.setGraphic(editImageView);
//        editButton.setOnAction(event -> editResident(param.getValue()));
//
//        // Thêm nút xóa
//        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
//        deleteImageView.setFitWidth(40);
//        deleteImageView.setFitHeight(40);
//        deleteImageView.setPreserveRatio(false);
//        Button deleteButton = new Button();
//        deleteButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #FF0000; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
//        deleteButton.setGraphic(deleteImageView);
//        deleteButton.setOnAction(event -> deleteResident(param.getValue()));
//
//        hbox.getChildren().addAll(viewButton, editButton, deleteButton);
//        return hbox;
//    }
//    private void viewResident(Resident resident) {
//        editEntity = resident;
//        nameText.setText(editEntity.getName());
//        if ("Nam".equalsIgnoreCase(editEntity.getGender())) {
//            genderGroup.selectToggle(maleRadioButton);
//        } else {
//            genderGroup.selectToggle(femaleRadioButton);
//        }
//        birthdayText.setStyle("-fx-font-size: 20px");
//        try {
//            Date birthdayStr = editEntity.getBirthday();
//            LocalDate birthday =
//            birthdayText.setValue(birthday);
//        } catch (DateTimeParseException e) {
//            e.printStackTrace();
//            birthdayText.setValue(null);
//        }
//        IDcardText.setText(editEntity.getIDcard());
//        hometownText.setText(editEntity.getHometown());
//        phoneText.setText(editEntity.getPhone());
//        ethnicityText.setText(editEntity.getEthnicity());
//        nationalityText.setText(editEntity.getNationality());
//        occupationText.setText(editEntity.getOccupation());
//        educationText.setText(editEntity.getEducation());
//        houseHoldIDText.setText(editEntity.getHouseHoldID());
//        statusText.setText(editEntity.getStatus());
//        additionalInfoText.setText(editEntity.getAdditionalInfo());
//
//        nameText.setEditable(false);
//        for (Toggle toggle : genderGroup.getToggles()) {
//            ((RadioButton) toggle).setDisable(true);
//        }
//        birthdayText.setMouseTransparent(true);
//        IDcardText.setEditable(false);
//        hometownText.setEditable(false);
//        phoneText.setEditable(false);
//        ethnicityText.setEditable(false);
//        nationalityText.setEditable(false);
//        occupationText.setEditable(false);
//        educationText.setEditable(false);
//        houseHoldIDText.setEditable(false);
//        statusText.setEditable(false);
//        additionalInfoText.setEditable(false);
//
//        nameText.setStyle("-fx-text-fill: #000000;");
//        for (Toggle toggle : genderGroup.getToggles()) {
//            ((RadioButton) toggle).setDisable(true);
//        }
//
//        saveButton.setVisible(false);
//        saveButton.setDisable(true);
//        cancelButton.setStyle("-fx-background-color: #0070C0; -fx-text-fill: #FFFFFF; -fx-font-size: 20; -fx-font-weight: Bold; -fx-pref-width: 420px; -fx-alignment: center;");
//
//        stackPaneInsertUpdate.setVisible(true);
//    }
//
//    private void editResident(Resident resident) {
//        editEntity = resident;
//        nameText.setText(editEntity.getName());
//        if ("Nam".equalsIgnoreCase(editEntity.getGender())) {
//            genderGroup.selectToggle(maleRadioButton);
//        } else {
//            genderGroup.selectToggle(femaleRadioButton);
//        }
//        birthdayText.setStyle("-fx-font-size: 20px");
//        try {
//            String birthdayStr = editEntity.getBirthday();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            LocalDate birthday = LocalDate.parse(birthdayStr, formatter);
//            birthdayText.setValue(birthday);
//        } catch (DateTimeParseException e) {
//            e.printStackTrace();
//            birthdayText.setValue(null);
//        }
//        IDcardText.setText(editEntity.getIDcard());
//        hometownText.setText(editEntity.getHometown());
//        phoneText.setText(editEntity.getPhone());
//        ethnicityText.setText(editEntity.getEthnicity());
//        nationalityText.setText(editEntity.getNationality());
//        occupationText.setText(editEntity.getOccupation());
//        educationText.setText(editEntity.getEducation());
//        houseHoldIDText.setText(editEntity.getHouseHoldID());
//        statusText.setText(editEntity.getStatus());
//        additionalInfoText.setText(editEntity.getAdditionalInfo());
//
//        // Bật lại khả năng chỉnh sửa cho các trường nhập liệu khi ở chế độ sửa
//        nameText.setEditable(true);
//        for (Toggle toggle : genderGroup.getToggles()) {
//            ((RadioButton) toggle).setDisable(false);
//        }
//        birthdayText.setMouseTransparent(false);
//        IDcardText.setEditable(true);
//        hometownText.setEditable(true);
//        phoneText.setEditable(true);
//        ethnicityText.setEditable(true);
//        nationalityText.setEditable(true);
//        occupationText.setEditable(true);
//        educationText.setEditable(true);
//        houseHoldIDText.setEditable(true);
//        statusText.setEditable(true);
//        additionalInfoText.setEditable(true);
//
//        saveButton.setVisible(true);
//        saveButton.setDisable(false);
//        cancelButton.setStyle("-fx-background-color: #F2F2F2; -fx-text-fill: #000000; -fx-font-size: 20; -fx-font-weight: Normal; -fx-pref-width: 200; -fx-alignment: center;");
//
//        stackPaneInsertUpdate.setVisible(true);
//    }
//
//    private void deleteResident(Resident resident) {
//        entityList.remove(resident);
//        tableView.refresh();
//        updatePagination(entityList);
//    }
//    @Override
//    public void filterEntities() {
//        String searchKeyword = searchText.getText().toLowerCase();
//
//        if (searchKeyword.isEmpty()) {
//            tableView.setItems(entityList); // Hiển thị tất cả khi ô tìm kiếm trống
//            updatePagination(entityList); // Cập nhật phân trang
//            return;
//        }
//        // Bộ lọc
//        ObservableList<Resident> filteredList = entityList.filtered(resident -> {
//            if (resident.getName().toLowerCase().contains(searchKeyword)) {
//                return true;
//            }
//            if (resident.getGender().toLowerCase().contains(searchKeyword)) {
//                return true;
//            }
//           Date formattedDate = formatBirthday(resident.getBirthday());
//            if (formattedDate.toLowerCase().contains(searchKeyword)) {
//                return true;
//            }
//            if (resident.getIDcard().toLowerCase().contains(searchKeyword)) {
//                return true;
//            }
//            if (resident.getHometown().toLowerCase().contains(searchKeyword)) {
//                return true;
//            }
//            if (resident.getHouseHoldID().toLowerCase().contains(searchKeyword)) {
//                return true;
//            }
//            return false;
//        });
//
//        tableView.setItems(filteredList);
//        updatePagination(filteredList); // Cập nhật phân trang sau khi lọc
//    }
//
//    private TableView<Resident> createPage(int pageIndex) {
//        int fromIndex = pageIndex * ROWS_PER_PAGE;
//        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
//        ObservableList<Resident> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
//        tableView.setItems(pageData);
//
//        indexColumn.setCellValueFactory(cellData -> {
//            int rowIndex = pageData.indexOf(cellData.getValue());
//            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
//        });
//
//        return tableView;
//    }
//
//
//    public void add() {
//        editEntity = null;
//        clearFields();
//        stackPaneInsertUpdate.setVisible(true);
//    }
//
//    public void cancel() {
//        clearFields();
//        stackPaneInsertUpdate.setVisible(false);
//    }
//
//    public void save() {
//        String name = nameText.getText();
//        String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
//        String birthday = birthdayText.getValue() != null ? birthdayText.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
//        String idcard = IDcardText.getText();
//        String hometown = hometownText.getText();
//        String phone = phoneText.getText();
//        String ethnicity = ethnicityText.getText();
//        String nationality = nationalityText.getText();
//        String occupation = occupationText.getText();
//        String education = educationText.getText();
//        String houseHoldID = houseHoldIDText.getText();
//        String status = statusText.getText();
//        String additionalInfo = additionalInfoText.getText();
//        if (editEntity != null) {
//            editEntity.setName(name);
//            editEntity.setBirthday(birthday);
//            editEntity.setHometown(hometown);
//            tableView.refresh();
//            editEntity = null;
//            stackPaneInsertUpdate.setVisible(false);
//            clearFields();
//        } else {
//            Resident newResident = new Resident(name, gender, birthday, idcard, hometown, phone, occupation, ethnicity, nationality, education, status, additionalInfo, houseHoldID);
//            entityList.add(newResident);
//        }
//            stackPaneInsertUpdate.setVisible(false);
//            clearFields();
//        }
//    @Override
//    protected void clearFields() {
//        nameText.clear();
//        birthdayText.setValue(null);
//        IDcardText.clear();
//        hometownText.clear();
//        phoneText.clear();
//        ethnicityText.clear();
//        nationalityText.clear();
//        occupationText.clear();
//        educationText.clear();
//        houseHoldIDText.clear();
//        statusText.clear();
//        additionalInfoText.clear();
//    }
//    private String formatBirthday(String birthday) {
//        try {
//            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            return LocalDate.parse(birthday, inputFormatter).format(outputFormatter);
//        } catch (DateTimeParseException e) {
//            return birthday;
//        }
//    }
//
//    private void switchToListOfHouseHold(Event event) {
//        try {
//            switcher.goListOfHouseholdPage(event,this); // Gọi phương thức chuyển cảnh
//        } catch (IOException e) {
//            e.printStackTrace(); // Xử lý ngoại lệ
//        }
//    }
//}