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
import myapp.dao.ContributionFundDAO;
import myapp.model.entities.entitiesdb.ContributionFund;
import myapp.model.manager.LogManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private Label slideshowLabel, totalAmount1, totalAmount2, totalAmount3, totalAmount4;
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
    private Button edit1, edit2, edit3, edit4, save1, save2, save3, save4, cancel1, cancel2, cancel3, cancel4, deleteFundButton, saveFundButton;
    @FXML
    private TextField textField1, textField2, textField3, textField4;
    @FXML
    private TableView<ContributionFund> contributionFundTableView;
    @FXML
    private TableColumn<ContributionFund, Integer> indexColumn;
    @FXML
    private TableColumn<ContributionFund, String> fundNameColumn, fundIDColumn, amountColumn, periodOfTimeColumn;
    @FXML
    private TextField fundNameText, fundIDText, amountText;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    @FXML
    private Label helloText;
    private final ObservableList<ContributionFund> contributionFundList = FXCollections.observableArrayList();
    private List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    private ImageView imageView;
    private List<String> textFieldValues = new ArrayList<>();

    @FXML
    public void initialize() {

        helloText.setText("Xin chào, "+ LogManager.getUserName());
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

        // Thiết lập dữ liệu mặc định cho ComboBox
        quarterComboBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
        quarterComboBox.setStyle("-fx-font-size: 20px; -fx-text-fill: #002060; -fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-border-color: #BFBFBF; -fx-border-radius: 10;");
        yearComboBox.setItems(FXCollections.observableArrayList("2022", "2023", "2024", "2025"));
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
        textFieldValues.add(textField1.getText());
        textFieldValues.add(textField2.getText());
        textFieldValues.add(textField3.getText());
        textFieldValues.add(textField4.getText());

        textField1.setEditable(false);
        textField2.setEditable(false);
        textField3.setEditable(false);
        textField4.setEditable(false);

        // Gắn hành động cho các nút Edit
        edit1.setOnAction(e -> handleEditAction(edit1, save1, cancel1, textField1));
        edit2.setOnAction(e -> handleEditAction(edit2, save2, cancel2, textField2));
        edit3.setOnAction(e -> handleEditAction(edit3, save3, cancel3, textField3));
        edit4.setOnAction(e -> handleEditAction(edit4, save4, cancel4, textField4));

        // Gắn hành động cho các nút Cancel
        cancel1.setOnAction(e -> handleCancelAction(edit1, save1, cancel1, textField1, 0));
        cancel2.setOnAction(e -> handleCancelAction(edit2, save2, cancel2, textField2, 1));
        cancel3.setOnAction(e -> handleCancelAction(edit3, save3, cancel3, textField3, 2));
        cancel4.setOnAction(e -> handleCancelAction(edit4, save4, cancel4, textField4, 3));
        // Gắn hành động cho các nút Save
        save1.setOnAction(e -> handleSaveAction(edit1, save1, cancel1, textField1, 0));
        save2.setOnAction(e -> handleSaveAction(edit2, save2, cancel2, textField2, 1));
        save3.setOnAction(e -> handleSaveAction(edit3, save3, cancel3, textField3, 2));
        save4.setOnAction(e -> handleSaveAction(edit4, save4, cancel4, textField4, 3));


        // Cập nhật thông tin trong bảng ContributionFund
        indexColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(contributionFundList.indexOf(cellData.getValue()) + 1));
        fundNameColumn.setCellValueFactory(new PropertyValueFactory<>("fundName"));
        fundIDColumn.setCellValueFactory(new PropertyValueFactory<>("fundID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        periodOfTimeColumn.setCellValueFactory(new PropertyValueFactory<>("periodOfTime"));
        // Gắn danh sách vào TableView
        contributionFundTableView.setItems(contributionFundList);

        // Xử lý sự kiện nút Save
        saveFundButton.setOnAction(event -> saveContributionFund());

        // Xử lý sự kiện nút Delete
        deleteFundButton.setOnAction(event -> deleteSelectedFund());
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

        if (selectedQuarter == null || selectedYear == null) {
            System.out.println("Hãy chọn đầy đủ quý và năm!");
            return;
        }

        int quarter = Integer.parseInt(selectedQuarter);
        int year = Integer.parseInt(selectedYear);

        barChart.getData().clear();

        addDataForQuarter(quarter, year);
    }

    private void addDataForQuarter(int quarter, int year) {
        String[] categories = {"Phí dịch vụ", "Phí quản lý", "Phí gửi xe", "Các khoản đóng góp"};

        String[] months = {
                "Tháng " + ((quarter - 1) * 3 + 1),
                "Tháng " + ((quarter - 1) * 3 + 2),
                "Tháng " + ((quarter - 1) * 3 + 3)
        };

        for (int i = 0; i < months.length; i++) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(months[i]);

            for (String category : categories) {
                double randomValue = Math.random() * 1_000_000;
                series.getData().add(new XYChart.Data<>(category, randomValue));
            }

            barChart.getData().add(series);
        }
    }

    private void saveContributionFund() {
        // Lấy thông tin từ các trường input
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
        String periodOfTime = startDate.format(formatter) + " - " + endDate.format(formatter);

        ContributionFund newFund = new ContributionFund(fundName, fundID, amount, periodOfTime);
        contributionFundList.add(newFund);
        ContributionFundDAO.insertContributionFund(Integer.parseInt(fundID), fundName, "");
        clearInputFields();
    }

    private void deleteSelectedFund() {
        ContributionFund selectedFund = contributionFundTableView.getSelectionModel().getSelectedItem();

        if (selectedFund == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn một hàng để xóa!");
            return;
        }

        contributionFundList.remove(selectedFund);
        ContributionFundDAO.deleteByID(Integer.parseInt(selectedFund.getFundID()));
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
    private void handleCancelAction(Button editButton, Button saveButton, Button cancelButton, TextField textField, int index) {
        textField.setText(textFieldValues.get(index));
        textField.setEditable(false);
        textField.setStyle("-fx-background-color: transparent;");

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        editButton.setVisible(true);
    }
    @FXML
    private void handleSaveAction(Button editButton, Button saveButton, Button cancelButton, TextField textField, int index) {
        String newValue = textField.getText();
        textFieldValues.set(index, newValue);
        //System.out.println("Updated value for TextField " + (index + 1) + ": " + newValue);

        textField.setEditable(false);
        textField.setStyle("-fx-background-color: transparent;");

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        editButton.setVisible(true);
    }
    public void getName(){

    }
}
