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
import myapp.model.entities.entitiessystem.Resident;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ListofResidentController extends BaseController implements Initializable {
    @FXML
    private StackPane mainStackPane;
    @FXML
    private StackPane formPane; // Đổi tên từ stackPaneInsertUpdate để dễ hiểu hơn
    @FXML
    private Button addButton, cancelButton;
    @FXML
    private TableView<Resident> residentTableView;
    @FXML
    private TableColumn<Resident, Integer> indexColumn;
    @FXML
    private TableColumn<Resident, String> nameColumn;
    @FXML
    private TableColumn<Resident, String> birthdayColumn;
    @FXML
    private TableColumn<Resident, String> hometownColumn;
    @FXML
    private TableColumn<Resident, HBox> editColumn;

    private ObservableList<Resident> residentsList;

    @FXML
    private TextField nameText;
    @FXML
    private DatePicker birthdayText;
    @FXML
    private TextField hometownText;

    private Resident editingResident;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        residentsList = FXCollections.observableArrayList(
                new Resident(1, "A", "01/01/2004", "Hà Nội"),
                new Resident(2, "B", "02/02/2004", "Hà Nội"),
                new Resident(3, "C", "03/03/2004", "Hà Nội")
        );

        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        hometownColumn.setCellValueFactory(new PropertyValueFactory<>("hometown"));

        editColumn.setCellValueFactory(param -> {
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER);

            ImageView editImageView = createImageView("/image/Edit.png");
            ImageView deleteImageView = createImageView("/image/Delete.png");

            Button editButton = new Button("", editImageView);
            Button deleteButton = new Button("", deleteImageView);

            editButton.getStyleClass().add("edit-button");
            deleteButton.getStyleClass().add("delete-button");

            hbox.getChildren().addAll(editButton, deleteButton);

            // Xử lý sự kiện nút "Edit"
            editButton.setOnAction(event -> editResident(param.getValue()));

            // Xử lý sự kiện nút "Delete"
            deleteButton.setOnAction(event -> residentsList.remove(param.getValue()));

            return new SimpleObjectProperty<>(hbox);
        });

        residentTableView.setItems(residentsList);
        residentTableView.setStyle("-fx-font-size: 20px;");
    }

    private ImageView createImageView(String path) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(false);
        return imageView;
    }

    private void editResident(Resident resident) {
        editingResident = resident;
        nameText.setText(resident.getName());
        birthdayText.setValue(LocalDate.parse(resident.getBirthday(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hometownText.setText(resident.getHometown());
        formPane.setVisible(true);
    }

    @FXML
    public void save(ActionEvent e) {
        if (editingResident != null) {
            editingResident.setName(nameText.getText());
            editingResident.setBirthday(birthdayText.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            editingResident.setHometown(hometownText.getText());
            residentTableView.refresh();
            editingResident = null;
        } else {
            Resident newResident = new Resident();
            newResident.setIndex(residentsList.size() + 1);
            newResident.setName(nameText.getText());
            newResident.setBirthday(birthdayText.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            newResident.setHometown(hometownText.getText());
            residentsList.add(newResident);
        }
        formPane.setVisible(false);
        clearFields();
    }

    @FXML
    public void delete(ActionEvent e) {
        Resident selectedResident = residentTableView.getSelectionModel().getSelectedItem();
        if (selectedResident != null) {
            residentsList.remove(selectedResident);
            // Cập nhật chỉ số
            for (int i = 0; i < residentsList.size(); i++) {
                residentsList.get(i).setIndex(i + 1);
            }
            residentTableView.refresh();
        }
    }

    @FXML
    public void cancel() {
        clearFields();
        formPane.setVisible(false);
    }

    private void clearFields() {
        nameText.clear();
        birthdayText.setValue(null);
        hometownText.clear();
    }
}
