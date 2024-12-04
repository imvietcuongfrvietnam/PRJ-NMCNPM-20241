package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.HouseHold;
import myapp.model.entities.entitiesdb.Resident;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class ListofResidentController implements Initializable {
    @FXML private StackPane stackPaneInsertUpdate;
    @FXML private Button addButton, cancleButton, saveButton;
    @FXML private TableView<Resident> residentTableView;
    @FXML private TableColumn<Resident, Integer> indexColumn;
    @FXML private TableColumn<Resident, String> nameColumn, genderColumn, birthdayColumn, IDcardColumn, hometownColumn, houseHoldIDColumn;
    @FXML private TableColumn<Resident, HBox> editColumn;
    @FXML private Pagination pagination;
    @FXML private TextField nameText, IDcardText, hometownText, phoneText, ethnicityText, nationalityText, occupationText, educationText, houseHoldIDText, statusText;
    @FXML private TextArea additionalInfoText;
    @FXML private DatePicker birthdayText;
    @FXML private ToggleGroup genderGroup;
    @FXML private RadioButton maleRadioButton, femaleRadioButton;

    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<Resident> residentsList;
    private Resident editingResident;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        residentsList = SQLConnector.getResidents();

        // Cập nhật số thứ tự trong bảng Resident
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = residentTableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        // Thiết lập các cột trong bảng Resident
        nameColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("gender"));
        birthdayColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = LocalDate.parse(cellData.getValue().getBirthday(), formatter).format(formatter);
            return new SimpleObjectProperty<>(formattedDate);
        });
        IDcardColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("IDcard"));
        hometownColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("hometown"));
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("houseHoldID"));

        // Cột Edit và Delete trong bảng Resident
        editColumn.setCellValueFactory(param -> {
            HBox hbox = createEditDeleteButtons(param);
            return new SimpleObjectProperty<>(hbox);
        });

        // Cập nhật bảng Resident
        residentTableView.setItems(residentsList);
        residentTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((residentsList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        //pagination.setCurrentPageIndex(0);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-font-size: 25;");

        addButton.setOnAction(actionEvent -> add());
        cancleButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());
    }

    private HBox createEditDeleteButtons(TableColumn.CellDataFeatures<Resident, HBox> param) {
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
        editButton.setOnAction(event -> editResident(param.getValue()));

        // Thêm nút xóa
        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
        deleteImageView.setFitWidth(40);
        deleteImageView.setFitHeight(40);
        deleteImageView.setPreserveRatio(false);
        Button deleteButton = new Button();
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteResident(param.getValue()));

        hbox.getChildren().addAll(editButton, deleteButton);
        return hbox;
    }

    private void editResident(Resident resident) {
        editingResident = resident;
        nameText.setText(editingResident.getName());
        if ("Nam".equalsIgnoreCase(editingResident.getGender())) {
            genderGroup.selectToggle(maleRadioButton);
        } else {
            genderGroup.selectToggle(femaleRadioButton);
        }
        birthdayText.setStyle("-fx-font-size: 20px");
        try {
            String birthdayStr = editingResident.getBirthday();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthday = LocalDate.parse(birthdayStr, formatter);
            birthdayText.setValue(birthday);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            birthdayText.setValue(null);
        }
        IDcardText.setText(editingResident.getIDcard());
        hometownText.setText(editingResident.getHometown());
        //phoneText.setText(editingResident.g);
        ethnicityText.setText(editingResident.getEthnicity());
        nationalityText.setText(editingResident.getNationality());
        occupationText.setText(editingResident.getOccupation());
        educationText.setText(editingResident.getEducation());
        houseHoldIDText.setText(editingResident.getHouseHoldID());
        statusText.setText(editingResident.getStatus());
        additionalInfoText.setText(editingResident.getAdditionalInfo());
        stackPaneInsertUpdate.setVisible(true);
    }

    private void deleteResident(Resident resident) {
        residentsList.remove(resident);
        updatePagination();
    }

    private TableView<Resident> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, residentsList.size());

        ObservableList<Resident> pageData = FXCollections.observableArrayList(residentsList.subList(fromIndex, toIndex));
        residentTableView.setItems(pageData);

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return residentTableView;
    }

    private void updatePagination() {
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((residentsList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        residentTableView.refresh();
    }

    public void add() {
        editingResident = null;
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
        String birthday = birthdayText.getValue() != null ? birthdayText.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
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
        if (editingResident != null) {
            editingResident.setName(name);
            editingResident.setBirthday(birthday);
            editingResident.setHometown(hometown);
            residentTableView.refresh();
            editingResident = null;
            stackPaneInsertUpdate.setVisible(false);
            clearFields();
        } else {
            Resident newResident = new Resident(name, gender, birthday, idcard, hometown, occupation, ethnicity, nationality, education, status, additionalInfo, houseHoldID);
            residentsList.add(newResident);
        }
            stackPaneInsertUpdate.setVisible(false);
            clearFields();
        }

    private void clearFields() {
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
}