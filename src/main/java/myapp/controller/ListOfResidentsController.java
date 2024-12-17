package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import myapp.dao.ResidentDAO;
import myapp.model.entities.entitiesdb.Resident;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ListOfResidentsController extends ManagementController<Resident>{
    @FXML
    private Button listOfHouseHoldButton;
    @FXML
    private TableColumn<Resident, String> nameColumn, genderColumn, birthdayColumn, IDcardColumn, hometownColumn, houseHoldIDColumn;
    @FXML
    private TextField nameText, IDcardText, hometownText, phoneText, ethnicityText, nationalityText, occupationText, educationText, houseHoldIDText, statusText;
    @FXML
    private TextArea additionalInfoText;
    @FXML
    private DatePicker birthdayText;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton maleRadioButton, femaleRadioButton;
    private Resident editEntity;

    @Override
    public void initialize() {
        super.initialize();
        entityList = ResidentDAO.getResidents();
        filteredList = FXCollections.observableArrayList(entityList);

        // Thiết lập các cột trong bảng Resident
        nameColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("gender"));
        birthdayColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthday = LocalDate.parse(cellData.getValue().getBirthday().toString());  // Assuming birthday is a LocalDate
            String formattedDate = birthday.format(formatter);
            return new SimpleObjectProperty<>(formattedDate);
        });

        IDcardColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("IDcard"));
        hometownColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("hometown"));
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("houseHoldID"));
        operationsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(createViewEditDeleteButtons(param)));

        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterEntities());
        listOfHouseHoldButton.setOnAction(this::switchToListOfHouseHold);

        saveButton.setOnAction(event -> {
            save();
        });
    }

    private HBox createViewEditDeleteButtons(TableColumn.CellDataFeatures<Resident, HBox> param) {
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
        viewButton.setOnAction(event -> viewResident(param.getValue()));

        // Thêm nút sửa
        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Edit.png")));
        editImageView.setFitWidth(40);
        editImageView.setFitHeight(40);
        editImageView.setPreserveRatio(false);
        Button editButton = new Button();
        editButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #00B050; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> editResident(param.getValue()));

        // Thêm nút xóa
        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        deleteImageView.setPreserveRatio(false);
        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #FF0000; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteResident(param.getValue()));

        hbox.getChildren().addAll(viewButton, editButton, deleteButton);
        return hbox;
    }
    private void viewResident(Resident resident) {
        editEntity = resident;
        nameText.setText(editEntity.getName());

        // Chọn giới tính
        if ("Male".equalsIgnoreCase(editEntity.getGender())) {
            genderGroup.selectToggle(maleRadioButton);
        } else {
            genderGroup.selectToggle(femaleRadioButton);
        }

        // Đặt kiểu chữ cho ngày sinh
        birthdayText.setStyle("-fx-font-size: 20px");

        // Chuyển đổi từ java.sql.Date sang LocalDate khi hiển thị ngày sinh
        try {
            java.sql.Date sqlDate = editEntity.getBirthday();
            if (sqlDate != null) {
                LocalDate birthday = sqlDate.toLocalDate(); // Chuyển từ java.sql.Date sang LocalDate
                birthdayText.setValue(birthday);
            } else {
                birthdayText.setValue(null); // Xử lý nếu ngày sinh là null
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            birthdayText.setValue(null);
        }

        // Đặt các giá trị khác cho các trường văn bản
        IDcardText.setText(editEntity.getIDcard());
        hometownText.setText(editEntity.getHometown());
        phoneText.setText(editEntity.getPhone());
        ethnicityText.setText(editEntity.getEthnicity());
        nationalityText.setText(editEntity.getNationality());
        occupationText.setText(editEntity.getOccupation());
        educationText.setText(editEntity.getEducation());
        houseHoldIDText.setText(editEntity.getHouseHoldID());
        statusText.setText(editEntity.getStatus());
        additionalInfoText.setText(editEntity.getAdditionalInfo());

        // Làm cho các trường không thể chỉnh sửa
        nameText.setEditable(false);
        for (Toggle toggle : genderGroup.getToggles()) {
            ((RadioButton) toggle).setDisable(true);
        }
        birthdayText.setMouseTransparent(true);
        IDcardText.setEditable(false);
        hometownText.setEditable(false);
        phoneText.setEditable(false);
        ethnicityText.setEditable(false);
        nationalityText.setEditable(false);
        occupationText.setEditable(false);
        educationText.setEditable(false);
        houseHoldIDText.setEditable(false);
        statusText.setEditable(false);
        additionalInfoText.setEditable(false);

        // Đặt lại màu sắc và giao diện của các trường
        nameText.setStyle("-fx-text-fill: #000000;");
        for (Toggle toggle : genderGroup.getToggles()) {
            ((RadioButton) toggle).setDisable(true);
        }

        // Thiết lập các nút hiển thị
        saveButton.setVisible(false);
        saveButton.setDisable(true);
        cancelButton.setStyle("-fx-background-color: #0070C0; -fx-text-fill: #FFFFFF; -fx-font-size: 20; -fx-font-weight: Bold; -fx-pref-width: 420px; -fx-alignment: center;");

        stackPaneInsertUpdate.setVisible(true);
    }

    private void editResident(Resident resident) {
        editEntity = resident;
        nameText.setText(editEntity.getName());

        // Chọn giới tính
        if ("Nam".equalsIgnoreCase(editEntity.getGender())) {
            genderGroup.selectToggle(maleRadioButton);
        } else {
            genderGroup.selectToggle(femaleRadioButton);
        }

        // Đặt kiểu chữ cho ngày sinh
        birthdayText.setStyle("-fx-font-size: 20px");

        // Chuyển đổi từ java.sql.Date sang LocalDate khi chỉnh sửa ngày sinh
        try {
            java.sql.Date sqlDate = editEntity.getBirthday();
            if (sqlDate != null) {
                LocalDate birthday = sqlDate.toLocalDate(); // Chuyển từ java.sql.Date sang LocalDate
                birthdayText.setValue(birthday);
            } else {
                birthdayText.setValue(null); // Xử lý nếu ngày sinh là null
            }
        } catch (Exception e) {
            birthdayText.setValue(null);
        }

        // Đặt các giá trị khác cho các trường văn bản
        IDcardText.setText(editEntity.getIDcard());
        hometownText.setText(editEntity.getHometown());
        phoneText.setText(editEntity.getPhone());
        ethnicityText.setText(editEntity.getEthnicity());
        nationalityText.setText(editEntity.getNationality());
        occupationText.setText(editEntity.getOccupation());
        educationText.setText(editEntity.getEducation());
        houseHoldIDText.setText(editEntity.getHouseHoldID());
        statusText.setText(editEntity.getStatus());
        additionalInfoText.setText(editEntity.getAdditionalInfo());

        // Bật lại khả năng chỉnh sửa cho các trường nhập liệu khi ở chế độ sửa
        nameText.setEditable(true);
        for (Toggle toggle : genderGroup.getToggles()) {
            ((RadioButton) toggle).setDisable(false);
        }
        birthdayText.setMouseTransparent(false);
        IDcardText.setEditable(true);
        hometownText.setEditable(true);
        phoneText.setEditable(true);
        ethnicityText.setEditable(true);
        nationalityText.setEditable(true);
        occupationText.setEditable(true);
        educationText.setEditable(true);
        houseHoldIDText.setEditable(true);
        statusText.setEditable(true);
        additionalInfoText.setEditable(true);

        // Thiết lập các nút hiển thị
        saveButton.setVisible(true);
        saveButton.setDisable(false);
        cancelButton.setStyle("-fx-background-color: #F2F2F2; -fx-text-fill: #000000; -fx-font-size: 20; -fx-font-weight: Normal; -fx-pref-width: 200; -fx-alignment: center;");

        stackPaneInsertUpdate.setVisible(true);
    }


    private void deleteResident(Resident resident) {
        entityList.remove(resident);
        tableView.refresh();
        updatePagination(entityList);
        ResidentDAO.deleteResident(resident);
    }
    @Override
    public void filterEntities() {
        String searchKeyword = searchText.getText().toLowerCase();

        if (searchKeyword.isEmpty()) {
            tableView.setItems(entityList); // Hiển thị tất cả khi ô tìm kiếm trống
            updatePagination(entityList); // Cập nhật phân trang
            return;
        }

        ObservableList<Resident> filteredList = entityList.filtered(resident -> {
            if (resident.getName().toLowerCase().contains(searchKeyword)) {
                return true;
            }
            if (resident.getGender().toLowerCase().contains(searchKeyword)) {
                return true;
            }

            // Chuyển đổi java.sql.Date thành chuỗi theo định dạng để tìm kiếm
            try {
                java.sql.Date sqlDate = resident.getBirthday();
                if (sqlDate != null) {
                    String formattedDate = sqlDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toLowerCase();
                    if (formattedDate.contains(searchKeyword)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // Bắt lỗi nếu có vấn đề trong việc xử lý ngày sinh
            }

            if (resident.getIDcard().toLowerCase().contains(searchKeyword)) {
                return true;
            }
            if (resident.getHometown().toLowerCase().contains(searchKeyword)) {
                return true;
            }
            if (resident.getHouseHoldID().toLowerCase().contains(searchKeyword)) {
                return true;
            }
            return false;
        });

        tableView.setItems(filteredList);
        updatePagination(filteredList); // Cập nhật phân trang sau khi lọc
    }




    public void add() {
        editEntity = null;
        clearFields();
        stackPaneInsertUpdate.setVisible(true);
    }

    public void cancel() {
        clearFields();
        stackPaneInsertUpdate.setVisible(false);
    }

    public void save() {
        String name = nameText.getText();
        String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
        LocalDate localDate = birthdayText.getValue();
        java.sql.Date birthday = (localDate != null) ? java.sql.Date.valueOf(localDate) : null;
        String idcard = IDcardText.getText();
        String hometown = hometownText.getText();
        String phone = phoneText.getText();
        String ethnicity = ethnicityText.getText();
        String nationality = nationalityText.getText();
        String occupation = occupationText.getText();
        String education = educationText.getText();
        String houseHoldID = houseHoldIDText.getText();
        String status = statusText.getText();
        String additionalInfo = additionalInfoText.getText();
        if (editEntity != null) {
            editEntity.setName(name);
            editEntity.setBirthday(birthday);
            editEntity.setHometown(hometown);
            tableView.refresh();
            editEntity = null;
            stackPaneInsertUpdate.setVisible(false);
            clearFields();
        } else {
            Resident newResident = new Resident(name, gender, birthday, idcard, hometown, phone, occupation, ethnicity, nationality, education, status, additionalInfo, houseHoldID);
            entityList.add(newResident);
            ResidentDAO.updateBySoCMND(newResident);
        }
            stackPaneInsertUpdate.setVisible(false);
            clearFields();
        }
    @Override
    protected void clearFields() {
        nameText.clear();
        birthdayText.setValue(null);
        IDcardText.clear();
        hometownText.clear();
        phoneText.clear();
        ethnicityText.clear();
        nationalityText.clear();
        occupationText.clear();
        educationText.clear();
        houseHoldIDText.clear();
        statusText.clear();
        additionalInfoText.clear();
    }

    private void switchToListOfHouseHold(Event event) {
        try {
            switcher.goListOfHouseholdPage(event,this); // Gọi phương thức chuyển cảnh
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }
    }
}