package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import myapp.db.SQLConnector;
import myapp.model.entities.Fee;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.sql.Date;

public class ListOfFeesController extends ManagementController<Fee> {
    @FXML
    private TableColumn<Fee, String> houseHoldIDColumn, feeNameColumn, feeIDColumn, amountColumn, statusColumn, noteColumn;
    @FXML
    private TableColumn<Fee, Date> expDateColumn;
    @FXML
    private ChoiceBox<String> feeNameChoiceBox, statusChoiceBox;
    @FXML
    private Button billButton;

    @Override
    public void initialize() {
        super.initialize();
        entityList = SQLConnector.getFees();
        filteredList = FXCollections.observableArrayList(entityList);
        // Thiết lập các cột trong bảng Fee
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("houseHoldID"));
        feeNameColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("feeName"));
        feeIDColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("feeID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("amount"));

        expDateColumn.setCellValueFactory(new PropertyValueFactory<Fee, Date>("expDate"));

        // Cập nhật cột trạng thái
        statusColumn.setCellFactory(param -> new TableCell<Fee, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    Fee fee = getTableRow().getItem();  // Lấy đối tượng Fee từ hàng hiện tại
                    String status = fee != null ? fee.getStatus() : "";  // Lấy giá trị trạng thái từ đối tượng Fee

                    Label statusLabel = new Label();
                    if ("Đã thanh toán".equals(status)) {
                        statusLabel.setText("Đã thanh toán");
                        statusLabel.setStyle("-fx-pref-width: 180; -fx-pref-height: 40.75; -fx-background-color: rgba(0, 255, 0, 0.25); -fx-background-radius: 5; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal; -fx-alignment: center;");
                    } else if ("Chưa thanh toán".equals(status)) {
                        statusLabel.setText("Chưa thanh toán");
                        statusLabel.setStyle("-fx-pref-width: 180; -fx-pref-height: 40.75; -fx-background-color: rgba(255, 0, 0, 0.25); -fx-background-radius: 5; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Bold; -fx-alignment: center;");
                    }
                    statusLabel.setPadding(new Insets(5));
                    setGraphic(statusLabel);
                } else {
                    setGraphic(null);
                }
            }
        });

        noteColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("note"));

        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterFees());

        feeNameChoiceBox.setItems(FXCollections.observableArrayList("Tên khoản phí", "Phí dịch vụ chung cư", "Phí gửi xe máy", "Phí gửi ô tô", "Phí quản lý chung cư"));
        feeNameChoiceBox.setValue("Tên khoản phí");
        feeNameChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterFees());
        statusChoiceBox.setItems(FXCollections.observableArrayList("Trạng thái", "Đã thanh toán", "Chưa thanh toán"));
        statusChoiceBox.setValue("Trạng thái");
        statusChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterFees());

        billButton.setOnAction(
                event -> {
                    Switcher switcher = new Switcher();
                    try {
                        switcher.goBillManagementPage(event, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        tableView.setItems(entityList);

        pagination.setPageCount((entityList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }

    @Override
    protected void filterEntities() {

    }

    @Override
    protected void clearFields() {

    }

    @Override
    protected void save() {

    }

    @Override
    protected void cancel() {

    }

    @Override
    protected void add() {

    }

    // Phương thức kết hợp tìm kiếm và lọc dữ liệu
    private void filterFees() {
        String searchKeyword = searchText.getText().toLowerCase();
        String feeNameFilter = feeNameChoiceBox.getValue();
        String statusFilter = statusChoiceBox.getValue();

        ObservableList<Fee> filtered = entityList.filtered(fee -> {
            // Kiểm tra từ khóa tìm kiếm chỉ nếu có nhập vào
            boolean matchesSearch = searchKeyword.isEmpty() ||
                    fee.getHouseHoldID().toLowerCase().contains(searchKeyword) ||
                    fee.getFeeName().toLowerCase().contains(searchKeyword) ||
                    fee.getFeeID().toLowerCase().contains(searchKeyword) ||
                    fee.getAmount().toLowerCase().contains(searchKeyword) ||
                    fee.getExpDate().toString().toLowerCase().contains(searchKeyword) ||
                    fee.getStatus().toLowerCase().contains(searchKeyword) ||
                    (fee.getNote() != null && fee.getNote().toLowerCase().contains(searchKeyword));

            boolean matchesFeeName = feeNameFilter.equals("Tên khoản phí") || fee.getFeeName().equals(feeNameFilter);

            boolean matchesStatus = statusFilter.equals("Trạng thái") || fee.getStatus().equals(statusFilter);

            return matchesSearch && matchesFeeName && matchesStatus;
        });
        tableView.setItems(filtered);
        updatePagination(filtered); // Cập nhật phân trang sau khi lọc
    }

}
