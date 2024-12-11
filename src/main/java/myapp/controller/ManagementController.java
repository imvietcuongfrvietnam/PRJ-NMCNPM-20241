package myapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import myapp.model.entities.entitiesdb.Apartment;
import myapp.model.entities.entitiesdb.Vehicle;

/**
 * Lớp cơ sở trừu tượng để quản lý các controller liên quan đến việc hiển thị danh sách dữ liệu có hỗ trợ phân trang và tìm kiếm.
 *
 * @param <T> Kiểu dữ liệu của các đối tượng sẽ được hiển thị trong bảng (TableView).
 */
public abstract class ManagementController<T> extends NavigationableController{
    protected static final int ROWS_PER_PAGE = 10; // Số lượng hàng hiển thị trên mỗi trang

    @Override
    public void initialize() {
        super.initialize();
    }

    /** Thành phần giao diện điều khiển phân trang. */
    @FXML
    protected Pagination pagination;

    /** Thành phần giao diện nhập liệu cho tìm kiếm. */
    @FXML
    protected TextField searchText;
    @FXML
    protected TableColumn<T, Integer> indexColumn;

    /** Bảng hiển thị danh sách dữ liệu. */
    @FXML
    protected TableView<T> tableView;

    /** Danh sách dữ liệu đã được lọc để hiển thị. */
    protected ObservableList<T> filteredList;
    protected ObservableList<T> entityList;
    @FXML
    protected TableColumn<T, HBox> operationsColumn;

    /**
     * Cập nhật phân trang cho danh sách đã lọc.
     *
     * @param filteredList danh sách dữ liệu cần phân trang
     */
    protected void updatePagination(ObservableList<T> filteredList) {
        this.filteredList = filteredList;
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());
            ObservableList<T> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));

            tableView.setItems(pageData); // Gán dữ liệu cho tableView
            return tableView;
        });

        // Tính toán số trang dựa trên kích thước danh sách
        pagination.setPageCount((filteredList.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }

    /**
     * Tạo một trang dữ liệu cho bảng dựa trên chỉ số trang.
     *
     * @param pageIndex Chỉ số của trang cần tạo.
     * @return Bảng hiển thị với dữ liệu được cập nhật cho trang.
     */
    protected TableView<T> createPage(int pageIndex) {
        // Tính toán chỉ số bắt đầu và kết thúc trong danh sách
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredList.size());

        // Tạo danh sách dữ liệu cho trang hiện tại
        ObservableList<T> pageData = FXCollections.observableArrayList(filteredList.subList(fromIndex, toIndex));

        // Gán dữ liệu cho bảng
        tableView.setItems(pageData);

        // Thiết lập cột chỉ mục
        indexColumn.setCellValueFactory(cellData -> {
            // Lấy chỉ số hàng hiện tại trong danh sách trang
            int rowIndex = pageData.indexOf(cellData.getValue());
            // Tính toán chỉ mục dựa trên trang và số lượng hàng
            return new SimpleObjectProperty<>((pageIndex * ROWS_PER_PAGE) + rowIndex + 1);
        });

        // Trả về bảng đã cập nhật
        return tableView;
    }
}
