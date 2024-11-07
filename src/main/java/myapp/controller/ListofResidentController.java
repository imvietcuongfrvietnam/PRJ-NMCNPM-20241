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


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ListofResidentController extends BaseController implements Initializable {
    @FXML
    private StackPane mainStackPane;
    @FXML
    private StackPane stackPaneInsertUpdate;
    @FXML
    private Button addButton, cancleButton;
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
    private TextField indexText;
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
        indexColumn.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("index"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("name"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("birthday"));
        hometownColumn.setCellValueFactory(new PropertyValueFactory<Resident, String>("hometown"));

        editColumn.setCellValueFactory(param -> {
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER);
            ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Edit.png")));
            editImageView.setFitWidth(40);
            editImageView.setFitHeight(40);
            editImageView.setPreserveRatio(false);
            ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Delete.png")));
            deleteImageView.setFitWidth(40);
            deleteImageView.setFitHeight(40);
            deleteImageView.setPreserveRatio(false);

            Button editButton = new Button();
            editButton.getStyleClass().add("edit-button");
            editButton.setGraphic(editImageView);

            Button deleteButton = new Button();
            deleteButton.getStyleClass().add("delete-button");
            deleteButton.setGraphic(deleteImageView);

            hbox.getChildren().addAll(editButton, deleteButton);

            deleteButton.setOnAction(event -> {
                residentsList.remove(param.getValue());
            });

            editButton.setOnAction(event -> {
                editingResident = param.getValue();
                nameText.setText(editingResident.getName());
                birthdayText.setValue(LocalDate.parse(editingResident.getBirthday(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                hometownText.setText(editingResident.getHometown());
                stackPaneInsertUpdate.setVisible(true);
            });


            return new SimpleObjectProperty<>(hbox);
        });
        residentTableView.setItems(residentsList);
        residentTableView.setStyle("-fx-font-size: 20px;");

    }
    public void save(ActionEvent e) {
        if (editingResident != null) {
            editingResident.setName(nameText.getText());
            editingResident.setBirthday(birthdayText.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            editingResident.setHometown(hometownText.getText());
            residentTableView.refresh();
            editingResident = null;
            stackPaneInsertUpdate.setVisible(false);
            clearFields();
        } else {
            Resident newResident = new Resident();
            newResident.setIndex(residentsList.size() + 1);
            newResident.setName(nameText.getText());
            newResident.setBirthday(birthdayText.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            newResident.setHometown(hometownText.getText());

            residentsList.add(newResident);
            stackPaneInsertUpdate.setVisible(false);
            clearFields();
        }
    }
    public void delete (ActionEvent e) {
        Resident resident = residentTableView.getSelectionModel().getSelectedItem();
        if(resident != null) {
            int index = residentsList.indexOf(resident);
            residentsList.remove(index);
            if(index < residentsList.size()) {
                for(int i=index; i<residentsList.size(); i++) {
                    residentsList.get(i).setIndex(i-1);
                }
            }
            residentTableView.refresh();
        }
    }
    private void clearFields() {
        //indexText.clear();
        nameText.clear();
        birthdayText.setValue(null);
        hometownText.clear();
    }
    @FXML
    public void handleThemMoi() {
        stackPaneInsertUpdate.setVisible(true);
    }

    @FXML
    public void handleThoat() {
        clearFields();
        stackPaneInsertUpdate.setVisible(false);
    }
}