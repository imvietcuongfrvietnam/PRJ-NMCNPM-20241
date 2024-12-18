package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import myapp.dao.BillDAO;
import myapp.model.entities.entitiesdb.Bill;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ListOfBillsController extends ManagementController<Bill> {

    @FXML private TableColumn<Bill, String> houseHoldIDColumn, billNameColumn, billIDColumn, amountColumn, statusColumn, noteColumn;
    @FXML private TableColumn<Bill, Date> expDateColumn;

    @FXML private ChoiceBox<String> billNameChoiceBox, statusChoiceBox;
    @Override
    public void initialize() {
        super.initialize();
        entityList = BillDAO.getBills();
        filteredList = FXCollections.observableArrayList(entityList);

        // Cập nhật số thứ tự trong bảng Fee
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = tableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        // Thiết lập các cột trong bảng Fee
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("houseHoldID"));
        billNameColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("billName"));
        billIDColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("billID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("amount"));

        expDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpDate()));

        // Cập nhật cột trạng thái
        statusColumn.setCellFactory(param -> new TableCell<Bill, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    Bill bill = getTableRow().getItem();
                    String status = bill != null ? bill.getStatus() : "";

                    Label statusLabel = new Label();
                    if ("Đã thanh toán".equals(status)) {
                        statusLabel.setText("Đã thanh toán");
                        statusLabel.setStyle("-fx-pref-width: 180; -fx-pref-height: 40.75; -fx-background-color: rgba(0, 255, 0, 0.25); -fx-background-radius: 5; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal; -fx-alignment: center;");
                    } else if ("Chưa thanh toán".equals(status)) {
                        statusLabel.setText("Chưa thanh toán");
                        statusLabel.setStyle("-fx-pref-width: 180; -fx-pref-height: 40.75; -fx-background-color: rgba(255, 0, 0, 0.25); -fx-background-radius: 5; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal; -fx-alignment: center;");
                    }
                    statusLabel.setPadding(new Insets(5));
                    setGraphic(statusLabel);
                } else {
                    setGraphic(null);
                }
            }
        });

        noteColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("note"));

        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterBills());

        billNameChoiceBox.setItems(FXCollections.observableArrayList("Tên hóa đơn", "Hóa đơn điện", "Hóa đơn nước", "Hóa đơn internet"));
        billNameChoiceBox.setValue("Tên hóa đơn");
        billNameChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterBills());
        statusChoiceBox.setItems(FXCollections.observableArrayList("Trạng thái", "Đã thanh toán", "Chưa thanh toán"));
        statusChoiceBox.setValue("Trạng thái");
        statusChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterBills());
    }

    private void filterBills() {
        String searchKeyword = searchText.getText().toLowerCase();
        String billNameFilter = billNameChoiceBox.getValue();
        String statusFilter = statusChoiceBox.getValue();

        ObservableList<Bill> filtered = entityList.filtered(bill -> {
            boolean matchesSearch = searchKeyword.isEmpty() ||
                    bill.getHouseHoldID().toLowerCase().contains(searchKeyword) ||
                    bill.getBillName().toLowerCase().contains(searchKeyword) ||
                    bill.getBillID().toLowerCase().contains(searchKeyword) ||
                    // Chuyển BigDecimal sang String và kiểm tra
                    bill.getAmount().toPlainString().toLowerCase().contains(searchKeyword) ||
                    // Chuyển java.sql.Date sang chuỗi định dạng
                    (bill.getExpDate() != null && formatDate(String.valueOf(bill.getExpDate())).contains(searchKeyword)) ||
                    bill.getStatus().toLowerCase().contains(searchKeyword) ||
                    (bill.getNote() != null && bill.getNote().toLowerCase().contains(searchKeyword));

            boolean matchesBillName = billNameFilter.equals("Tên hóa đơn") || bill.getBillName().equals(billNameFilter);

            boolean matchesStatus = statusFilter.equals("Trạng thái") || bill.getStatus().equals(statusFilter);

            return matchesSearch && matchesBillName && matchesStatus;
        });

        tableView.setItems(filtered);
        updatePagination(filtered);
    }

    @Override
    protected void filterEntities() {
        // Chưa triển khai
    }

    @Override
    protected void clearFields() {
        // Chưa triển khai
    }

    @Override
    protected void save() {
        // Chưa triển khai
    }

    @Override
    protected void cancel() {
        // Chưa triển khai
    }

    @Override
    protected void add() {
        // Chưa triển khai
    }

    private String formatDate(String date) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, inputFormatter).format(outputFormatter);
        } catch (DateTimeParseException e) {
            return date;
        }
    }
}
