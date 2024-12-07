package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.Fee;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class ListOfFeesController implements Initializable {
    @FXML private TableView<Fee> feeTableView;
    @FXML private TableColumn<Fee, Integer> indexColumn;
    @FXML private TableColumn<Fee, String> houseHoldIDColumn, feeNameColumn, feeIDColumn, amountColumn, expDateColumn, statusColumn, noteColumn;
    @FXML private Pagination pagination;
    @FXML private TextField searchText;
    @FXML private ChoiceBox<String> feeNameChoiceBox, statusChoiceBox;

    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<Fee> feesList;
    private ObservableList<Fee> filteredList;
    private final Switcher switcher = new Switcher();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        feesList = SQLConnector.getFees();
        filteredList = FXCollections.observableArrayList(feesList);

        // Cập nhật số thứ tự trong bảng Fee
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = feeTableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        // Thiết lập các cột trong bảng Fee
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("houseHoldID"));
        feeNameColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("feeName"));
        feeIDColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("feeID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Fee, String>("amount"));

        expDateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = LocalDate.parse(cellData.getValue().getExpDate(), formatter).format(formatter);
            return new SimpleObjectProperty<>(formattedDate);
        });

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

        // Cập nhật bảng Fee
        feeTableView.setItems(feesList);
        feeTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((feesList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-backround-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #002060; -fx-font-size: 25;");
    }

    // Phương thức kết hợp tìm kiếm và lọc dữ liệu
    private void filterFees() {
        String searchKeyword = searchText.getText().toLowerCase();
        String feeNameFilter = feeNameChoiceBox.getValue();
        String statusFilter = statusChoiceBox.getValue();

        ObservableList<Fee> filtered = feesList.filtered(fee -> {
            // Kiểm tra từ khóa tìm kiếm chỉ nếu có nhập vào
            boolean matchesSearch = searchKeyword.isEmpty() ||
                    fee.getHouseHoldID().toLowerCase().contains(searchKeyword) ||
                    fee.getFeeName().toLowerCase().contains(searchKeyword) ||
                    fee.getFeeID().toLowerCase().contains(searchKeyword) ||
                    fee.getAmount().toLowerCase().contains(searchKeyword) ||
                    formatDate(fee.getExpDate()).toLowerCase().contains(searchKeyword) ||
                    fee.getStatus().toLowerCase().contains(searchKeyword) ||
                    (fee.getNote() != null && fee.getNote().toLowerCase().contains(searchKeyword));

            boolean matchesFeeName = feeNameFilter.equals("Tên khoản phí") || fee.getFeeName().equals(feeNameFilter);

            boolean matchesStatus = statusFilter.equals("Trạng thái") || fee.getStatus().equals(statusFilter);

            return matchesSearch && matchesFeeName && matchesStatus;
        });

        feeTableView.setItems(filtered);
        updatePagination(filtered); // Cập nhật phân trang sau khi lọc
    }

    // Phương thức tạo trang mới cho bảng Fee
    private TableView<Fee> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
        ObservableList<Fee> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
        feeTableView.setItems(pageData);

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return feeTableView;
    }

    // Cập nhật phân trang cho danh sách đã lọc
    private void updatePagination(ObservableList<Fee> filteredList) {
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
            ObservableList<Fee> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
            feeTableView.setItems(pageData);
            return feeTableView;
        });
        pagination.setPageCount((filteredList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }

    // Chuyển đổi ngày thành định dạng dd/MM/yyyy
    private String formatDate(String date) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, inputFormatter).format(outputFormatter);
        } catch (DateTimeParseException e) {
            return date;  // Nếu không chuyển đổi được thì trả về ngày gốc
        }
    }

    private void switchToListOfHouseHold(Event event) {
        try {
            switcher.goListOfHouseholdPage(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
