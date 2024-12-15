package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import myapp.model.manager.Switcher;

/**
 * Lớp cơ sở trừu tượng để quản lý các controller liên quan đến việc hiển thị danh sách dữ liệu có hỗ trợ phân trang và tìm kiếm.
 *
 * @param <T> Kiểu dữ liệu của các đối tượng sẽ được hiển thị trong bảng (TableView).
 */
abstract class ManagementController<T> extends NavigableController {

    /** Số lượng hàng hiển thị trên mỗi trang. */
    protected static final int ROWS_PER_PAGE = 10;

    /** Đối tượng Switcher hỗ trợ điều hướng giữa các trang. */
    protected final Switcher switcher = new Switcher();

    /** Danh sách dữ liệu đã được lọc để hiển thị. */
    protected ObservableList<T> filteredList;

    /** Danh sách dữ liệu chính được hiển thị trong bảng. */
    protected ObservableList<T> entityList;

    /** Thành phần giao diện điều khiển phân trang. */
    @FXML
    protected Pagination pagination;

    /** Thành phần giao diện nhập liệu cho tìm kiếm. */
    @FXML
    protected TextField searchText;

    /** Cột hiển thị chỉ mục của các hàng trong bảng. */
    @FXML
    protected TableColumn<T, Integer> indexColumn;

    /** Nút hủy thao tác. */
    @FXML
    protected Button cancelButton;

    /** Nút thêm dữ liệu mới. */
    @FXML
    protected Button addButton;

    /** Nút lưu dữ liệu. */
    @FXML
    protected Button saveButton;

    /** Bảng hiển thị danh sách dữ liệu. */
    @FXML
    protected TableView<T> tableView;

    /** Cột chứa các thao tác trên từng hàng dữ liệu. */
    @FXML
    protected TableColumn<T, HBox> operationsColumn;

    /** Thành phần giao diện cho chức năng chèn hoặc cập nhật dữ liệu. */
    @FXML
    protected StackPane stackPaneInsertUpdate;

    /**
     * Phương thức khởi tạo. Thiết lập các sự kiện và cài đặt mặc định cho các thành phần giao diện.
     */

    @Override
    public void initialize() {
        super.initialize();

        // Khởi tạo entityList nếu nó chưa được khởi tạo
        if (entityList == null) {
            entityList = FXCollections.observableArrayList();  // Hoặc sử dụng ArrayList nếu không cần tính năng của JavaFX
        }

        addButton.setOnAction(actionEvent -> add());
        cancelButton.setOnAction(actionEvent -> cancel());
        saveButton.setOnAction(actionEvent -> save());

        indexColumn.setCellValueFactory(cellData -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            int rowIndex = tableView.getItems().indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((currentPageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        searchText.textProperty().addListener((observable, oldValue, newValue) -> filterEntities());
        tableView.setItems(entityList);
        tableView.setStyle("-fx-font-size: 20px;");
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount((entityList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
        pagination.setStyle("-fx-page-information-visible: false; -fx-page-button-pref-height: 50px; -fx-backround-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #002060; -fx-font-size: 25;");
    }


    /**
     * Lọc danh sách các đối tượng dựa trên điều kiện tìm kiếm.
     */
    protected abstract void filterEntities();

    /**
     * Tạo một trang dữ liệu cho bảng dựa trên chỉ số trang.
     *
     * @param pageIndex Chỉ số của trang cần tạo.
     * @return Bảng hiển thị với dữ liệu được cập nhật cho trang.
     */
    protected TableView<T> createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
        ObservableList<T> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));

        tableView.setItems(pageData);

        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = pageData.indexOf(cellData.getValue());
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        return tableView;
    }

    /**
     * Cập nhật phân trang cho danh sách đã lọc.
     *
     * @param filteredList Danh sách dữ liệu cần phân trang.
     */
    protected void updatePagination(ObservableList<T> filteredList) {
        this.filteredList = filteredList;
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
            ObservableList<T> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));

            tableView.setItems(pageData);
            return tableView;
        });

        pagination.setPageCount((filteredList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }

    /**
     * Xóa các trường dữ liệu trong giao diện nhập liệu.
     */
    protected abstract void clearFields();

    /**
     * Lưu dữ liệu hiện tại vào danh sách hoặc cơ sở dữ liệu.
     */
    protected abstract void save();

    /**
     * Hủy thao tác hiện tại và khôi phục trạng thái trước đó.
     */
    protected abstract void cancel();

    /**
     * Thêm một đối tượng mới vào danh sách.
     */
    protected abstract void add();

    /**
     * Xóa một hoặc nhiều đối tượng khỏi danh sách và cập nhật giao diện.
     *
     * @param entity Đối tượng cần xóa.
     * @param <T> Kiểu của đối tượng cần xóa.
     */
    protected <T> void deleteEntities(T entity) {
        entityList.remove(entity);
        tableView.refresh();
        updatePagination(filteredList);
    }
}
