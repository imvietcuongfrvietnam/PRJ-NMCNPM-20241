package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Bill;
import myapp.model.entities.entitiesdb.Fee;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ListOfBillsController extends BaseController {
    @FXML private Button addButton, cancelButton, saveButton, listOfFeesButton, listOfFundsButton;
    @FXML private TableView<Bill> billTableView;
    @FXML private TableColumn<Bill, Integer> indexColumn;
    @FXML private TableColumn<Bill, String> houseHoldIDColumn, billNameColumn, billIDColumn, amountColumn, expDateColumn, statusColumn, noteColumn;
    @FXML private TableColumn<Bill, Void> updateColumn;
    @FXML private Pagination pagination;
    @FXML private TextField searchText;
    @FXML private ChoiceBox<String> billNameChoiceBox, statusChoiceBox;

    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<Bill> billsList;
    private ObservableList<Bill> filteredList;
    private final Switcher switcher = new Switcher();

    @Override
    public void initialize() {
        super.initialize();
        billsList = SQLConnector.getBills();
        filteredList = FXCollections.observableArrayList(billsList);
        // Cập nhật số thứ tự trong bảng Fee
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = billTableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        // Thiết lập các cột trong bảng Fee
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("houseHoldID"));
        billNameColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("billName"));
        billIDColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("billID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("amount"));

        expDateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = LocalDate.parse(cellData.getValue().getExpDate(), formatter).format(formatter);
            return new SimpleObjectProperty<>(formattedDate);
        });

        // Cập nhật cột trạng thái
        statusColumn.setCellFactory(param -> new TableCell<Bill, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    Bill bill = getTableRow().getItem();
                    if (bill != null) {
                        String status = bill.getStatus();
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
                    }
                } else {
                    setGraphic(null);
                }
            }
        });
        noteColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("note"));
        updateColumn.setCellFactory(column -> new TableCell<>() {
            private final Button updateButton = new Button();
            {
                ImageView updateImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/Update.png")));
                updateImageView.setFitWidth(40);
                updateImageView.setFitHeight(40);
                updateImageView.setPreserveRatio(false);
                updateButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color:  #FFC000; -fx-border-radius: 10; -fx-border-width: 2.5; -fx-pref-width: 50px; -fx-pref-height: 50px; -fx-padding: 0;");
                updateButton.setGraphic(updateImageView);
                updateButton.setOnAction(event -> {
                    Bill bill = getTableView().getItems().get(getIndex()); // Lấy đối tượng Fee từ hàng hiện tại
                    if (bill != null) {
                        if ("Đã thanh toán".equals(bill.getStatus())) {
                            bill.setStatus("Chưa thanh toán");
                        } else {
                            bill.setStatus("Đã thanh toán");
                        }
                        getTableView().refresh(); // Làm mới bảng để cập nhật giao diện
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterBills());

        billNameChoiceBox.setItems(FXCollections.observableArrayList("Tên hóa đơn", "Hóa đơn điện", "Hóa đơn nước", "Hóa đơn internet"));
        billNameChoiceBox.setValue("Tên hóa đơn");
        billNameChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterBills());
        statusChoiceBox.setItems(FXCollections.observableArrayList("Trạng thái", "Đã thanh toán", "Chưa thanh toán"));
        statusChoiceBox.setValue("Trạng thái");
        statusChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterBills());

        // Cập nhật bảng Fee
        billTableView.setItems(billsList);
        billTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((billsList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-backround-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #002060; -fx-font-size: 25;");

        listOfFeesButton.setOnAction(event -> {
            try {
                switcher.goFeeManagementPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        listOfFundsButton.setOnAction(event -> {
            try {
                switcher.goFundManagementPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Phương thức kết hợp tìm kiếm và lọc dữ liệu
    private void filterBills() {
        String searchKeyword = searchText.getText().toLowerCase();
        String billNameFilter = billNameChoiceBox.getValue();
        String statusFilter = statusChoiceBox.getValue();

        ObservableList<Bill> filtered = billsList.filtered(bill -> {
            // Kiểm tra từ khóa tìm kiếm chỉ nếu có nhập vào
            boolean matchesSearch = searchKeyword.isEmpty() ||
                    bill.getHouseHoldID().toLowerCase().contains(searchKeyword) ||
                    bill.getBillName().toLowerCase().contains(searchKeyword) ||
                    bill.getBillID().toLowerCase().contains(searchKeyword) ||
                    bill.getAmount().toLowerCase().contains(searchKeyword) ||
                    formatDate(bill.getExpDate()).toLowerCase().contains(searchKeyword) ||
                    bill.getStatus().toLowerCase().contains(searchKeyword) ||
                    (bill.getNote() != null && bill.getNote().toLowerCase().contains(searchKeyword));

            boolean matchesBillName = billNameFilter.equals("Tên hóa đơn") || bill.getBillName().equals(billNameFilter);

            boolean matchesStatus = statusFilter.equals("Trạng thái") || bill.getStatus().equals(statusFilter);

            return matchesSearch && matchesBillName && matchesStatus;
        });

        billTableView.setItems(filtered);
        updatePagination(filtered); // Cập nhật phân trang sau khi lọc
    }

    // Phương thức tạo trang mới cho bảng Fee
    private TableView<Bill> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
        ObservableList<Bill> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
        billTableView.setItems(pageData);

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return billTableView;
    }

    // Cập nhật phân trang cho danh sách đã lọc
    private void updatePagination(ObservableList<Bill> filteredList) {
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
            ObservableList<Bill> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
            billTableView.setItems(pageData);
            return billTableView;
        });
        pagination.setPageCount((filteredList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }
}
