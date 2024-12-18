package myapp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import myapp.dao.*;
import myapp.model.entities.entitiesdb.ContributionFund;
import myapp.model.manager.LogManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainController extends NavigableController{
    @FXML private Label slideshowLabel, sumCostService, sumCostManagement, sumCostVehicle, sumCostContribute;
    @FXML private Button previousButton, nextButton;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private ComboBox<String> quarterComboBox;
    @FXML private ComboBox<String> yearComboBox;
    @FXML private Button filterButton;
    @FXML private Button editFeeService, editFeeManagement, editFeeParkXM, editFeeParkOT, saveFeeService, saveFeeManagement, saveFeeParkXM, saveFeeParkOT, cancelFeeService, cancelFeeManagement, cancelFeeParkXM, cancelFeeParkOT, deleteFundButton, saveFundButton;
    @FXML private TextField feeService, feeManagement, feeXM, feeOT;
    @FXML private TableView<ContributionFund> contributionFundTableView;
    @FXML private TableColumn<ContributionFund, Integer> indexColumn;
    @FXML private TableColumn<ContributionFund, String> fundNameColumn, fundIDColumn, amountColumn, periodOfTimeColumn;
    @FXML private TextField fundNameText, fundIDText, amountText;
    @FXML private DatePicker startDatePicker, endDatePicker;
    @FXML private Label helloText;
    @FXML private Label countTamVang, countTamTru, countResident;
    private  ObservableList<ContributionFund> contributionFundList = FXCollections.observableArrayList();
    private List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    private ImageView imageView;
    private List<String> textFieldValues = new ArrayList<>();

    /**
     * Khởi tạo và thiết lập các thành phần trong giao diện người dùng.
     */
    @Override
    public void initialize() {
        super.initialize();
        helloText.setText("Xin chào, " + LogManager.getUserName());
        // Tải hình ảnh slideshow
        for(int i = 1; i <= 5; i++) {
            images.add(new Image(Objects.requireNonNull(getClass().getResource("/image/Slideshow" + i + ".png")).toExternalForm()));
        }
        contributionFundList = ContributionFundDAO.getContributionFund();
        contributionFundTableView.setItems(contributionFundList);

        sumCostService.setText("Số tiền: " + safeToString(PaymentHistoryDAO.getTotalFeeByType("Phí dịch vụ")));
        sumCostManagement.setText("Số tiền: " + safeToString(PaymentHistoryDAO.getTotalFeeByType("Phí quản lý")));
        sumCostVehicle.setText("Số tiền: " + safeToString(PaymentHistoryDAO.getTotalFeeByType("Phí gửi xe")));
        sumCostContribute.setText("Số tiền: " + safeToString(PaymentHistoryDAO.getTotalFeeByType("Phí đóng góp")));

        countTamTru.setText(String.valueOf(ResidentDAO.countByType("Tạm trú")));

        countTamTru.setText(String.valueOf(ResidentDAO.countByType("Tạm vắng")));
        countResident.setText(String.valueOf(ResidentDAO.getResidents().size()));



        feeService.setText(safeToString(UnitPriceDAO.getByType("Dịch vụ")));
        feeManagement.setText(safeToString(UnitPriceDAO.getByType("Quản lý")));
        feeXM.setText(safeToString(ParkingFeeDAO.getFeeByType("Xe máy")));
        feeOT.setText(safeToString(ParkingFeeDAO.getFeeByType("Xe ô tô")));

        // Thiết lập hình ảnh slideshow ban đầu
        imageView = createImageView(images.get(currentImageIndex), 1470, 530, 30, 30);
        slideshowLabel.setGraphic(imageView);

        // Thiết lập nút điều hướng cho slideshow
        setButtonGraphics(previousButton, "/image/Back.png");
        setButtonGraphics(nextButton, "/image/Next.png");

        previousButton.setOnAction(e -> showPreviousImage());
        nextButton.setOnAction(e -> showNextImage());

        // Tự động chuyển đổi hình ảnh mỗi 3 giây
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> showNextImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Cài đặt các combo box cho quý và năm
        quarterComboBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
        yearComboBox.setItems(FXCollections.observableArrayList("2022", "2023", "2024", "2025"));
        // Thiết lập kiểu cho combo box
        quarterComboBox.setStyle("-fx-font-size: 20px; -fx-text-fill: #002060; -fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color: #BFBFBF; -fx-border-radius: 10;");
        yearComboBox.setStyle("-fx-font-size: 20px; -fx-text-fill: #002060; -fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color: #BFBFBF; -fx-border-radius: 10;");

        // Thiết lập biểu đồ
        xAxis.getCategories().addAll("Phí dịch vụ", "Phí quản lý", "Phí gửi xe", "Các khoản đóng góp");
        yAxis.setLabel("Chi phí (VNĐ)");

        // Cài đặt ngày hiện tại và quý hiện tại
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();
        int currentQuarter = (currentMonth - 1) / 3 + 1;

        quarterComboBox.setValue(String.valueOf(currentQuarter));
        yearComboBox.setValue(String.valueOf(currentYear));

        addDataForQuarter(currentQuarter, currentYear);

        // Thiết lập sự kiện cho nút lọc
        filterButton.setOnAction(e -> filterData());

        // Lưu giá trị các trường nhập liệu
        textFieldValues.add(feeService.getText());
        textFieldValues.add(feeManagement.getText());
        textFieldValues.add(feeXM.getText());
        textFieldValues.add(feeOT.getText());

        // Cài đặt các trường không chỉnh sửa
        feeService.setEditable(false);
        feeManagement.setEditable(false);
        feeXM.setEditable(false);
        feeOT.setEditable(false);

        // Xử lý các sự kiện cho các nút chỉnh sửa, lưu, hủy
        editFeeService.setOnAction(e -> handleEditAction(editFeeService, saveFeeService, cancelFeeService, feeService));
        editFeeManagement.setOnAction(e -> handleEditAction(editFeeManagement, saveFeeManagement, cancelFeeManagement, feeManagement));
        editFeeParkXM.setOnAction(e -> handleEditAction(editFeeParkXM, saveFeeParkXM, cancelFeeParkXM, feeXM));
        editFeeParkOT.setOnAction(e -> handleEditAction(editFeeParkOT, saveFeeParkOT, cancelFeeParkOT, feeOT));

        cancelFeeService.setOnAction(e -> handleCancelAction(editFeeService, saveFeeService, cancelFeeService, feeService, 0));
        cancelFeeManagement.setOnAction(e -> handleCancelAction(editFeeManagement, saveFeeManagement, cancelFeeManagement, feeManagement, 1));
        cancelFeeParkXM.setOnAction(e -> handleCancelAction(editFeeParkXM, saveFeeParkXM, cancelFeeParkXM, feeXM, 2));
        cancelFeeParkOT.setOnAction(e -> handleCancelAction(editFeeParkOT, saveFeeParkOT, cancelFeeParkOT, feeOT, 3));

        saveFeeService.setOnAction(e -> handleSaveAction(editFeeService, saveFeeService, cancelFeeService, feeService, 0));
        saveFeeManagement.setOnAction(e -> handleSaveAction(editFeeManagement, saveFeeManagement, cancelFeeManagement, feeManagement, 1));
        saveFeeParkXM.setOnAction(e -> handleSaveAction(editFeeParkXM, saveFeeParkXM, cancelFeeParkXM, feeXM, 2));
        saveFeeParkOT.setOnAction(e -> handleSaveAction(editFeeParkOT, saveFeeParkOT, cancelFeeParkOT, feeOT, 3));

        // Cài đặt các cột cho bảng dữ liệu
        indexColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(contributionFundList.indexOf(cellData.getValue()) + 1));
        fundNameColumn.setCellValueFactory(new PropertyValueFactory<>("fundName"));
        fundIDColumn.setCellValueFactory(new PropertyValueFactory<>("fundID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        periodOfTimeColumn.setCellValueFactory(new PropertyValueFactory<>("periodOfTime"));
        contributionFundTableView.setItems(contributionFundList);

        // Thiết lập sự kiện cho các nút lưu và xóa quỹ đóng góp
        saveFundButton.setOnAction(event -> saveContributionFund());
        deleteFundButton.setOnAction(event -> deleteSelectedFund());
    }

    /**
     * Tạo đối tượng {@link ImageView} với hình ảnh và kích thước được chỉ định.
     *
     * @param image     Hình ảnh cần hiển thị.
     * @param width     Chiều rộng của hình ảnh.
     * @param height    Chiều cao của hình ảnh.
     * @param arcWidth  Bán kính bo góc theo chiều rộng.
     * @param arcHeight Bán kính bo góc theo chiều cao.
     * @return đối tượng {@link ImageView}.
     */
    private ImageView createImageView(Image image, double width, double height, double arcWidth, double arcHeight) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);

        Rectangle rectangle = new Rectangle();
        rectangle.widthProperty().bind(imageView.fitWidthProperty());
        rectangle.heightProperty().bind(imageView.fitHeightProperty());
        rectangle.setArcWidth(arcWidth);
        rectangle.setArcHeight(arcHeight);
        imageView.setClip(rectangle);
        return imageView;
    }

    /**
     * Thiết lập hình ảnh cho nhãn với chỉ số hình ảnh đã cho.
     *
     * @param imageIndex Chỉ số hình ảnh cần thiết lập.
     */
    private void setImageForLabel(int imageIndex) {
        imageView.setImage(images.get(imageIndex));
        slideshowLabel.setGraphic(imageView);
    }

    /**
     * Thiết lập biểu tượng cho nút với hình ảnh từ đường dẫn chỉ định.
     *
     * @param button    Nút cần thiết lập biểu tượng.
     * @param imagePath Đường dẫn đến hình ảnh biểu tượng.
     */
    private void setButtonGraphics(Button button, String imagePath) {
        ImageView iconView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        iconView.setFitWidth(30);
        iconView.setFitHeight(30);
        button.setGraphic(iconView);
    }

    /**
     * Hiển thị hình ảnh tiếp theo trong danh sách slideshow.
     */
    @FXML
    private void showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % images.size();
        setImageForLabel(currentImageIndex);
    }

    /**
     * Hiển thị hình ảnh trước trong danh sách slideshow.
     */
    @FXML
    private void showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
        setImageForLabel(currentImageIndex);
    }

    /**
     * Thêm dữ liệu vào biểu đồ cho một quý và năm nhất định.
     *
     * @param quarter Quý cần thêm dữ liệu.
     * @param year    Năm cần thêm dữ liệu.
     */
    @FXML
    private void addDataForQuarter(int quarter, int year) {
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Chi phí cho quý " + quarter + " năm " + year);
        dataSeries.getData().add(new XYChart.Data<>("Phí dịch vụ", PaymentHistoryDAO.getTotalFeeByTypeAndQuarter("Phí dịch vụ", quarter, year)));
        dataSeries.getData().add(new XYChart.Data<>("Phí quản lý", PaymentHistoryDAO.getTotalFeeByTypeAndQuarter("Phí quản lý", quarter, year)));
        dataSeries.getData().add(new XYChart.Data<>("Phí gửi xe", PaymentHistoryDAO.getTotalFeeByTypeAndQuarter("Phí gửi xe", quarter, year)));
        dataSeries.getData().add(new XYChart.Data<>("Các khoản đóng góp", PaymentHistoryDAO.getTotalFeeByTypeAndQuarter("Phí đóng góp", quarter, year)));
        barChartData.add(dataSeries);
        barChart.setData(barChartData);
    }

    /**
     * Lọc dữ liệu theo quý và năm đã chọn.
     */
    private void filterData() {
        int quarter = Integer.parseInt(quarterComboBox.getValue());
        int year = Integer.parseInt(yearComboBox.getValue());
        addDataForQuarter(quarter, year);
    }

    /**
     * Xử lý hành động chỉnh sửa cho các khoản phí.
     *
     * @param editButton    Nút chỉnh sửa.
     * @param saveButton    Nút lưu.
     * @param cancelButton  Nút hủy.
     * @param textField     Trường nhập liệu.
     */
    private void handleEditAction(Button editButton, Button saveButton, Button cancelButton, TextField textField) {
        textField.setEditable(true); // Cho phép chỉnh sửa
        editButton.setVisible(false); // Vô hiệu hóa nút chỉnh sửa
        saveButton.setVisible(true); // Kích hoạt nút lưu
        cancelButton.setVisible(true); // Kích hoạt nút hủy
    }

    /**
     * Hủy thao tác chỉnh sửa cho các khoản phí.
     *
     * @param editButton    Nút chỉnh sửa.
     * @param saveButton    Nút lưu.
     * @param cancelButton  Nút hủy.
     * @param textField     Trường nhập liệu.
     * @param index         Chỉ số của trường cần hủy.
     */
    private void handleCancelAction(Button editButton, Button saveButton, Button cancelButton, TextField textField, int index) {
        editButton.setDisable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        textField.setEditable(false);
        textField.setText(textFieldValues.get(index));
    }

    /**
     * Lưu các thay đổi cho các khoản phí.
     *
     * @param editButton    Nút chỉnh sửa.
     * @param saveButton    Nút lưu.
     * @param cancelButton  Nút hủy.
     * @param textField     Trường nhập liệu.
     * @param index         Chỉ số của trường cần lưu.
     */
    private void handleSaveAction(Button editButton, Button saveButton, Button cancelButton, TextField textField, int index) {
        editButton.setVisible(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        textField.setEditable(false);
        if(index == 2) ParkingFeeDAO.updateParkingFee("Xe máy", Integer.parseInt(textField.getText()));
        else if(index == 3) ParkingFeeDAO.updateParkingFee("Ô tô", Integer.parseInt(textField.getText()));
        textFieldValues.set(index, textField.getText());
    }

    /**
     * Lưu thông tin quỹ đóng góp mới.
     */
    private void saveContributionFund() {
        try {
            // Kiểm tra các trường nhập liệu
            if (fundNameText.getText().isEmpty() || fundIDText.getText().isEmpty() ||
                    amountText.getText().isEmpty() || startDatePicker.getValue() == null ||
                    endDatePicker.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Lỗi", "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            // Tạo đối tượng ContributionFund
            String fundID = fundIDText.getText();
            String fundName = fundNameText.getText();
            BigDecimal amount = new BigDecimal(amountText.getText());
            Date startDate = Date.valueOf(startDatePicker.getValue());
            Date endDate = Date.valueOf(endDatePicker.getValue());

            ContributionFund contributionFund = new ContributionFund(fundName, fundID, amount, startDate, endDate);

            // Lưu vào cơ sở dữ liệu
            ContributionFundDAO.insertContributionFund(fundID,fundName,"", startDate, endDate, amount);

            // Thêm vào danh sách và cập nhật TableView
            contributionFundList.add(contributionFund);
            contributionFundTableView.setItems(contributionFundList);

            // Hiển thị thông báo thành công
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Quỹ đóng góp đã được lưu thành công!");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi định dạng", "ID hoặc số tiền phải là số hợp lệ.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra: " + e.getMessage());
        }
        clearInputFields();
    }

    // Phương thức hiển thị Alert
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Xóa quỹ đóng góp đã chọn trong bảng.
     */
    private void deleteSelectedFund() {
        ContributionFund selectedFund = contributionFundTableView.getSelectionModel().getSelectedItem();
        if (selectedFund != null) {
            ContributionFundDAO.deleteByID(selectedFund.getFundID());
            contributionFundList.remove(selectedFund);
        }
    }

    /**
     * Xóa các trường nhập liệu.
     */
    private void clearInputFields() {
        fundNameText.clear();
        fundIDText.clear();
        amountText.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }

    private String safeToString(BigDecimal value) {
        return (value != null) ? value.toString() : "0";
    }


}
