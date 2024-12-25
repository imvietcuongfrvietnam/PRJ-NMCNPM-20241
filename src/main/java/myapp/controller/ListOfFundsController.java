package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.Bill;
import myapp.model.entities.entitiesdb.ContributionFund;
import myapp.model.entities.entitiesdb.Fee;
import myapp.model.manager.Switcher;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ListOfFundsController extends BaseController {
    @FXML private Button addButton, cancelButton, saveButton, listOfBillsButton, listOfFeesButton;
    @FXML private TableView<ContributionFund> fundTableView;
    @FXML private TableColumn<ContributionFund, Integer> indexColumn;
    @FXML private TableColumn<ContributionFund, String> houseHoldIDColumn, fundNameColumn, fundIDColumn, amountColumn, contributionDateColumn, statusColumn, noteColumn;
    @FXML private TableColumn<ContributionFund, Void> updateColumn;
    @FXML private Pagination pagination;
    @FXML private TextField searchText;
    @FXML private ChoiceBox<String> fundNameChoiceBox, statusChoiceBox;

    private static final int ROWS_PER_PAGE = 10;
    private ObservableList<ContributionFund> contributionFundsList;
    private ObservableList<ContributionFund> filteredList;
    private final Switcher switcher = new Switcher();

    @Override
    public void initialize() {
        super.initialize();
        contributionFundsList = SQLConnector.getFunds();
        filteredList = FXCollections.observableArrayList(contributionFundsList);

        // Cập nhật số thứ tự trong bảng Fee
        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = fundTableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });
        // Thiết lập các cột trong bảng Fee
        houseHoldIDColumn.setCellValueFactory(new PropertyValueFactory<ContributionFund, String>("houseHoldID"));
        fundNameColumn.setCellValueFactory(new PropertyValueFactory<ContributionFund, String>("fundName"));
        fundIDColumn.setCellValueFactory(new PropertyValueFactory<ContributionFund, String>("fundID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<ContributionFund, String>("amount"));
        contributionDateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = LocalDate.parse(cellData.getValue().getContribitonDate(), formatter).format(formatter);
            return new SimpleObjectProperty<>(formattedDate);
        });
        statusColumn.setCellFactory(param -> new TableCell<ContributionFund, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    ContributionFund fund = getTableRow().getItem();
                    if (fund != null) {
                        String status = fund.getStatus();
                        Label statusLabel = new Label();
                        if ("Đã đóng góp".equals(status)) {
                            statusLabel.setText("Đã đóng góp");
                            statusLabel.setStyle("-fx-pref-width: 180; -fx-pref-height: 40.75; -fx-background-color: rgba(0, 255, 0, 0.25); -fx-background-radius: 5; -fx-font-size: 20; -fx-text-fill: #002060; -fx-font-weight: Normal; -fx-alignment: center;");
                        } else if ("Không đóng góp".equals(status)) {
                            statusLabel.setText("Không đóng góp");
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
        noteColumn.setCellValueFactory(new PropertyValueFactory<ContributionFund, String>("note"));
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
                    ContributionFund fund = getTableView().getItems().get(getIndex()); // Lấy đối tượng Fund từ hàng hiện tại
                    if (fund != null) {
                        if ("Không đóng góp".equals(fund.getStatus())) {
                            fund.setStatus("Đã đóng góp");
                        }
                        getTableView().refresh();
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

        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterFunds());
        fundNameChoiceBox.setItems(FXCollections.observableArrayList("Tên quỹ", "Quỹ biển đảo", "Quỹ môi trường", "Quỹ từ thiện", "Quỹ vì người nghèo"));
        fundNameChoiceBox.setValue("Tên quỹ");
        fundNameChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterFunds());
        statusChoiceBox.setItems(FXCollections.observableArrayList("Trạng thái", "Đã đóng góp", "Không đóng góp"));
        statusChoiceBox.setValue("Trạng thái");
        statusChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> filterFunds());

        // Cập nhật bảng Fee
        fundTableView.setItems(contributionFundsList);
        fundTableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((contributionFundsList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-backround-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #002060; -fx-font-size: 25;");

        listOfFeesButton.setOnAction(event -> {
            try {
                switcher.goFeeManagementPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        listOfBillsButton.setOnAction(event -> {
            try {
                switcher.goBillManagementPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Phương thức kết hợp tìm kiếm và lọc dữ liệu
    private void filterFunds() {
        String searchKeyword = searchText.getText().toLowerCase();
        String fundNameFilter = fundNameChoiceBox.getValue();
        String statusFilter = statusChoiceBox.getValue();

        ObservableList<ContributionFund> filtered = contributionFundsList.filtered(fund -> {
            // Kiểm tra từ khóa tìm kiếm chỉ nếu có nhập vào
            boolean matchesSearch = searchKeyword.isEmpty() ||
                    fund.getHouseHoldID().toLowerCase().contains(searchKeyword) ||
                    fund.getFundName().toLowerCase().contains(searchKeyword) ||
                    fund.getFundID().toLowerCase().contains(searchKeyword) ||
                    fund.getAmount().toLowerCase().contains(searchKeyword) ||
                    formatDate(fund.getContribitonDate()).toLowerCase().contains(searchKeyword) ||
                    fund.getStatus().toLowerCase().contains(searchKeyword) ||
                    (fund.getNote() != null && fund.getNote().toLowerCase().contains(searchKeyword));

            boolean matchesFeeName = fundNameFilter.equals("Tên quỹ") || fund.getFundName().equals(fundNameFilter);

            boolean matchesStatus = statusFilter.equals("Trạng thái") || fund.getStatus().equals(statusFilter);

            return matchesSearch && matchesFeeName && matchesStatus;
        });

        fundTableView.setItems(filtered);
        updatePagination(filtered); // Cập nhật phân trang sau khi lọc
    }

    // Phương thức tạo trang mới cho bảng Fee
    private TableView<ContributionFund> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
        ObservableList<ContributionFund> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
        fundTableView.setItems(pageData);

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return fundTableView;
    }

    // Cập nhật phân trang cho danh sách đã lọc
    private void updatePagination(ObservableList<ContributionFund> filteredList) {
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
            ObservableList<ContributionFund> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));
            fundTableView.setItems(pageData);
            return fundTableView;
        });
        pagination.setPageCount((filteredList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }
}
