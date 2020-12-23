package controllers.nhanvien;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class NhanVienController {

    @FXML
    private TableView<?> tableNhanVien;

    @FXML
    private TableColumn<?, ?> col_MaNV;

    @FXML
    private TableColumn<?, ?> col_TenNV;

    @FXML
    private TableColumn<?, ?> col_SDT;

    @FXML
    private TableColumn<?, ?> col_NgaySinh;

    @FXML
    private TableColumn<?, ?> col_DiaChi;

    @FXML
    private TableColumn<?, ?> col_SoCMT;

    @FXML
    private TextField textTimKiem;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    void comboBoxSelect(ActionEvent event) {

    }

    @FXML
    void sua(ActionEvent event) {

    }

    @FXML
    void them(ActionEvent event) {

    }

    @FXML
    void timKiem(ActionEvent event) {

    }

    @FXML
    void xoa(ActionEvent event) {

    }

}
