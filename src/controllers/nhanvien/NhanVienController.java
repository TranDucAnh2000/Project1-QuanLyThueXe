package controllers.nhanvien;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.KhachHangModel;
import models.NhanVienModel;
import service.NhanVienService;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class NhanVienController implements Initializable {

    @FXML
    private TableView<NhanVienModel> tableNhanVien;

    @FXML
    private TableColumn<NhanVienModel, Integer> col_MaNV;

    @FXML
    private TableColumn<NhanVienModel, String> col_TenNV;

    @FXML
    private TableColumn<NhanVienModel, String> col_SDT;

    @FXML
    private TableColumn<NhanVienModel, Date> col_NgaySinh;

    @FXML
    private TableColumn<NhanVienModel, Integer> col_DiaChi;

    @FXML
    private TableColumn<NhanVienModel,String> col_SoCMT;

    @FXML
    private TextField textTimKiem;

    @FXML
    private ComboBox<String> comboBox;

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
    private NhanVienService nhanVienService=new NhanVienService();
    private ObservableList<String> comboBoxOblist = FXCollections.observableArrayList("Mã nhân viên", "Tên nhân viên", "Số điện thoại","Ngày sinh","Địa chỉ","Số CMT");
    private ObservableList<NhanVienModel> tableOblist;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(comboBoxOblist);

        col_MaNV.setCellValueFactory(new PropertyValueFactory<>("MaNV"));
        col_TenNV.setCellValueFactory(new PropertyValueFactory<>("TenNV"));
        col_SDT.setCellValueFactory(new PropertyValueFactory<>("SoDT"));
        col_NgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaysinh"));
        col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        col_SoCMT.setCellValueFactory(new PropertyValueFactory<>("SoCMT"));
        List<NhanVienModel> list = nhanVienService.getlistNV();
        tableOblist = FXCollections.observableArrayList(list);
        tableNhanVien.setItems(tableOblist);
    }
}
