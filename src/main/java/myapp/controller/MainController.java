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
import myapp.model.communicatedb.delete.ContributionFundDelete;
import myapp.model.communicatedb.insert.ContributionFundInsert;
import myapp.model.communicatedb.select.FeeSelect;
import myapp.model.communicatedb.select.ResidentSelect;
import myapp.model.communicatedb.update.FeeUpdate;
import myapp.model.connectdb.SQLConnector;
import myapp.model.entities.entitiesdb.ContributionFund;
import myapp.model.manager.LogManager;
import myapp.model.manager.Switcher;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainController extends BaseController {
    @FXML
    private Label usernameLabel, slideshowLabel, totalServiceFee, totalManagementFee, totalParkingFee, totalContributionFund, totalResidents, temporaryResidents, absentResidents;
    @FXML
    private Button previousButton, nextButton;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ComboBox<String> quarterComboBox;
    @FXML
    private ComboBox<String> yearComboBox;
    @FXML
    private Button filterButton;
    @FXML
    private Button editFeeService, editFeeManagement, editFeeParkXM, editFeeParkOT, saveFeeService, saveFeeManagement, saveFeeParkXM, saveFeeParkOT, cancelFeeService, cancelFeeManagement, cancelFeeParkXM, cancelFeeParkOT, deleteFundButton, saveFundButton;
    @FXML
    private TextField feeServiceText, feeManagementText, feeMotorbikeText, feeCarText;
    @FXML
    private TableView<ContributionFund> contributionFundTableView;
    @FXML
    private TableColumn<ContributionFund, Integer> indexColumn;
    @FXML
    private TableColumn<ContributionFund, String> fundNameColumn, fundIDColumn, amountColumn, startDateColumn, endDateColumn;
    @FXML
    private TextField fundNameText, fundIDText, amountText;
    @FXML
    private DatePicker startDatePicker, endDatePicker;

    private List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    private ImageView imageView;
    private List<String> textFieldValues = new ArrayList<>();
    private final Switcher switcher = new Switcher();
    private ObservableList<ContributionFund> contributionFundList;

    @Override
    public void initialize() {
        super.initialize();
        contributionFundList = SQLConnector.getContributionFunds();

        usernameLabel.setText("Xin chào, " + LogManager.getUser().getName());
        images.add(new Image(getClass().getResource("/image/Slideshow1.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/image/Slideshow2.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/image/Slideshow3.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/image/Slideshow4.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/image/Slideshow5.png").toExternalForm()));

        imageView = createImageView(images.get(currentImageIndex), 1470, 530, 30, 30);
        slideshowLabel.setGraphic(imageView);

        setButtonGraphics(previousButton, "/image/Back.png");
        setButtonGraphics(nextButton, "/image/Next.png");

        previousButton.setOnAction(e -> showPreviousImage());
        nextButton.setOnAction(e -> showNextImage());

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> showNextImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        totalServiceFee.setText(String.format("%,d", (long) new FeeSelect().getTotalServiceFee()).replace(',', '.'));
        totalManagementFee.setText(String.format("%,d", (long) new FeeSelect().getTotalManagementFee()).replace(',', '.'));
        totalParkingFee.setText(String.format("%,d", (long) new FeeSelect().getTotalParkingFee()).replace(',', '.'));
        totalContributionFund.setText(String.format("%,d", (long) new FeeSelect().getTotalContributionFund()).replace(',', '.'));

        // Thiết lập dữ liệu mặc định cho ComboBox
        quarterComboBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
        quarterComboBox.setStyle("-fx-font-size: 20px; -fx-text-fill: #002060; -fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color: #BFBFBF; -fx-border-radius: 10;");
        yearComboBox.setItems(FXCollections.observableArrayList("2022", "2023", "2024"));
        yearComboBox.setStyle("-fx-font-size: 20px; -fx-text-fill: #002060; -fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color: #BFBFBF; -fx-border-radius: 10;");

        // Thiết lập trục biểu đồ
        xAxis.getCategories().addAll("Phí dịch vụ", "Phí quản lý", "Phí gửi xe", "Các khoản đóng góp");
        yAxis.setLabel("Chi phí (VNĐ)");
        yAxis.setStyle("-fx-font-size: 20px; -fx-text-fill: #002060; ");

        // Xác định quý hiện tại
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();
        int currentQuarter = (currentMonth - 1) / 3 + 1;

        // Đặt giá trị mặc định cho ComboBox
        quarterComboBox.setValue(String.valueOf(currentQuarter));
        yearComboBox.setValue(String.valueOf(currentYear));

        // Hiển thị dữ liệu cho quý hiện tại
        addDataForQuarter(currentQuarter, currentYear);

        // Thêm sự kiện lọc dữ liệu khi nhấn nút
        filterButton.setOnAction(e -> filterData());

        // Khởi tạo giá trị ban đầu cho TextField
        feeServiceText.setText(new FeeSelect().getFee("PDV"));
        feeManagementText.setText(new FeeSelect().getFee("PQL"));
        feeMotorbikeText.setText(new FeeSelect().getFee("PGXM"));
        feeCarText.setText(new FeeSelect().getFee("PGXOT"));

        textFieldValues.add(feeServiceText.getText());
        textFieldValues.add(feeManagementText.getText());
        textFieldValues.add(feeMotorbikeText.getText());
        textFieldValues.add(feeCarText.getText());

        feeServiceText.setEditable(false);
        feeManagementText.setEditable(false);
        feeMotorbikeText.setEditable(false);
        feeCarText.setEditable(false);

        // Gắn hành động cho các nút Edit
        editFeeService.setOnAction(e -> handleEditAction(editFeeService, saveFeeService, cancelFeeService, feeServiceText));
        editFeeManagement.setOnAction(e -> handleEditAction(editFeeManagement, saveFeeManagement, cancelFeeManagement, feeManagementText));
        editFeeParkXM.setOnAction(e -> handleEditAction(editFeeParkXM, saveFeeParkXM, cancelFeeParkXM, feeMotorbikeText));
        editFeeParkOT.setOnAction(e -> handleEditAction(editFeeParkOT, saveFeeParkOT, cancelFeeParkOT, feeCarText));

        // Gắn hành động cho các nút Cancel
        cancelFeeService.setOnAction(e -> handleCancelAction(editFeeService, saveFeeService, cancelFeeService, feeServiceText, "PDV"));
        cancelFeeManagement.setOnAction(e -> handleCancelAction(editFeeManagement, saveFeeManagement, cancelFeeManagement, feeManagementText, "PQL"));
        cancelFeeParkXM.setOnAction(e -> handleCancelAction(editFeeParkXM, saveFeeParkXM, cancelFeeParkXM, feeMotorbikeText, "PGXM"));
        cancelFeeParkOT.setOnAction(e -> handleCancelAction(editFeeParkOT, saveFeeParkOT, cancelFeeParkOT, feeCarText, "PGXOT"));
        // Gắn hành động cho các nút Save
        saveFeeService.setOnAction(e -> handleSaveAction(editFeeService, saveFeeService, cancelFeeService, feeServiceText, "PDV"));
        saveFeeManagement.setOnAction(e -> handleSaveAction(editFeeManagement, saveFeeManagement, cancelFeeManagement, feeManagementText, "PQL"));
        saveFeeParkXM.setOnAction(e -> handleSaveAction(editFeeParkXM, saveFeeParkXM, cancelFeeParkXM, feeMotorbikeText, "PGXM"));
        saveFeeParkOT.setOnAction(e -> handleSaveAction(editFeeParkOT, saveFeeParkOT, cancelFeeParkOT, feeCarText, "PGXOT"));

        // Cập nhật thông tin trong bảng ContributionFund
        indexColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(contributionFundList.indexOf(cellData.getValue()) + 1));
        fundNameColumn.setCellValueFactory(new PropertyValueFactory<>("fundName"));
        fundIDColumn.setCellValueFactory(new PropertyValueFactory<>("fundID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        contributionFundTableView.setItems(contributionFundList);

        saveFundButton.setOnAction(event -> saveContributionFund());
        deleteFundButton.setOnAction(event -> deleteSelectedFund());

        totalResidents.setText(String.format("%d", new ResidentSelect().getTotalResidents()));
        temporaryResidents.setText(String.format("%d", new ResidentSelect().getTemporaryResidents()));
        absentResidents.setText(String.format("%d", new ResidentSelect().getAbsentResidents()));

    }

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

    private void setImageForLabel(int imageIndex) {
        imageView.setImage(images.get(imageIndex));
        slideshowLabel.setGraphic(imageView);
    }

    private void setButtonGraphics(Button button, String imagePath) {
        ImageView iconView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        iconView.setFitWidth(30);
        iconView.setFitHeight(30);
        button.setGraphic(iconView);
    }

    @FXML
    private void showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % images.size();
        setImageForLabel(currentImageIndex);
    }

    @FXML
    private void showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
        setImageForLabel(currentImageIndex);
    }

    private void filterData() {
        String selectedQuarter = quarterComboBox.getValue();
        String selectedYear = yearComboBox.getValue();
        int quarter = Integer.parseInt(selectedQuarter);
        int year = Integer.parseInt(selectedYear);

        barChart.getData().clear();
        addDataForQuarter(quarter, year);
    }

    private void addDataForQuarter(int quarter, int year) {
        String[] months = {
                "Tháng " + ((quarter - 1) * 3 + 1),
                "Tháng " + ((quarter - 1) * 3 + 2),
                "Tháng " + ((quarter - 1) * 3 + 3)
        };
        // Xóa dữ liệu cũ trên biểu đồ
        barChart.getData().clear();
        // Thêm dữ liệu vào biểu đồ
        for (int i = 0; i < months.length; i++) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(months[i]);
            series.getData().add(new XYChart.Data<>("Phí dịch vụ", new FeeSelect().getTotalServiceFee()));
            series.getData().add(new XYChart.Data<>("Phí quản lý", new FeeSelect().getTotalManagementFee()));
            series.getData().add(new XYChart.Data<>("Phí gửi xe", new  FeeSelect().getTotalParkingFee()));
            series.getData().add(new XYChart.Data<>("Các khoản đóng góp", new FeeSelect().getTotalContributionFund()));
            barChart.getData().add(series);
        }
    }

    private void saveContributionFund() {
        String fundName = fundNameText.getText().trim();
        String fundID = fundIDText.getText().trim();
        String amount = amountText.getText().trim();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (fundName.isEmpty() || fundID.isEmpty() || amount.isEmpty() || startDate == null || endDate == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        ContributionFund newFund = new ContributionFund(fundName, fundID, amount, formattedStartDate, formattedEndDate);
        ContributionFundInsert contributionFundInsert = new ContributionFundInsert();
        contributionFundInsert.insert(fundID, fundName, amount, Date.valueOf(startDate), Date.valueOf(endDate));

        contributionFundList.add(newFund);
        clearInputFields();
    }

    private void deleteSelectedFund() {
        ContributionFund selectedFund = contributionFundTableView.getSelectionModel().getSelectedItem();
        ContributionFundDelete contributionFundDelete = new ContributionFundDelete();
        contributionFundDelete.delete(selectedFund);
        if (selectedFund == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn một hàng để xóa!");
            return;
        }
        contributionFundList.remove(selectedFund);
    }

    private void clearInputFields() {
        fundNameText.clear();
        fundIDText.clear();
        amountText.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleEditAction(Button editButton, Button saveButton, Button cancelButton, TextField textField) {
        textField.setEditable(true);
        textField.setStyle("");
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        editButton.setVisible(false);
    }

    @FXML
    private void handleCancelAction(Button editButton, Button saveButton, Button cancelButton, TextField textField, String feeID) {
        int index = getFeeIndex(feeID);
        textField.setText(textFieldValues.get(index));
        textField.setEditable(false);
        textField.setStyle("-fx-background-color: transparent;");
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        editButton.setVisible(true);
    }
    @FXML
    private void handleSaveAction(Button editButton, Button saveButton, Button cancelButton, TextField textField, String feeID) {
        String newAmount = textField.getText();
        int index = getFeeIndex(feeID);
        textFieldValues.set(index, newAmount);
        //System.out.println("Updated value for TextField " + (index + 1) + ": " + newAmount);
        new FeeUpdate().update(feeID, newAmount);
        textField.setEditable(false);
        textField.setStyle("-fx-background-color: transparent;");
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        editButton.setVisible(true);
    }
    private int getFeeIndex(String feeID) {
        switch (feeID) {
            case "PDV":
                return 0;
            case "PQL":
                return 1;
            case "PGXM":
                return 2;
            case "PGXOT":
                return 3;
            default:
                return -1; // hoặc một giá trị mặc định
        }
    }
}
