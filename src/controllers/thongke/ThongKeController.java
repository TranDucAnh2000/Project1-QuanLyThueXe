package controllers.thongke;

import controllers.khachhang.KhachHangController;
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
import service.KhachHangService;
import service.ThongKeService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {

    @FXML
    private ComboBox<String> tieuChiXe;

    @FXML
    private ComboBox<String> chiTietXe;

    @FXML
    private TextField soLuongXe;

    @FXML
    private TextField soLuongKH;

    @FXML
    private TextField soLuongNV;

    @FXML
    private TableView<KhachHangModel> tableKH;

    @FXML
    private TableColumn<KhachHangModel, Integer> MaKH;

    @FXML
    private TableColumn<KhachHangModel, String> TenKH;

    String stieuChiXe;

    private KhachHangService khachHangService = new KhachHangService();
    private ThongKeService thongKeService = new ThongKeService();

    private ObservableList<String> tieuChiOblist = FXCollections.observableArrayList("LoaiXe", "TenXe", "MauSac");
    private ObservableList<String> chiTietOblist;
    private ObservableList<KhachHangModel> tableKHOblist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tieuChiXe.setItems(tieuChiOblist);

        try {
            soLuongKH.setText(String.valueOf(thongKeService.count("MaKH", "", "KhachHang")));
            soLuongKH.setEditable(false);
            soLuongNV.setText(String.valueOf(thongKeService.count("MaNV", "","NhanVien")));
            soLuongNV.setEditable(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        MaKH.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        TenKH.setCellValueFactory(new PropertyValueFactory<>("TenKH"));
        List<KhachHangModel> list1 = khachHangService.getListKhachHang();
        tableKHOblist = FXCollections.observableArrayList(list1);
        tableKH.setItems(tableKHOblist);
    }

    @FXML
    void chiTietXe(ActionEvent event) throws SQLException {
        String chiTiet;
        if(chiTietXe.getSelectionModel().getSelectedItem() == "Tất cả"){
            chiTiet = "";
        }else {
            chiTiet = chiTietXe.getSelectionModel().getSelectedItem();
        }
        int result = thongKeService.count(stieuChiXe, chiTiet, "Xe");
        soLuongXe.setText(String.valueOf(result));
        soLuongXe.setEditable(false);
    }

    @FXML
    void tieuChiXe(ActionEvent event) throws SQLException {
        stieuChiXe = tieuChiXe.getSelectionModel().getSelectedItem();
        chiTietOblist = FXCollections.observableArrayList(thongKeService.getListComboBox(stieuChiXe));
        chiTietOblist.add("Tất cả");
        chiTietXe.setItems(chiTietOblist);
    }

    @FXML
    private TextField textSoXeDaThue;

    @FXML
    private TextField textTongTienThanhToan;

    @FXML
    void count(ActionEvent event) throws SQLException {
        int maKH = tableKH.getSelectionModel().getSelectedItem().getMaKH();
        textSoXeDaThue.setText(String.valueOf(thongKeService.countSoXeDaThue(maKH)));
        textSoXeDaThue.setEditable(false);
        textTongTienThanhToan.setText(String.valueOf(thongKeService.countTongTienThanhToan(maKH)));
        textTongTienThanhToan.setEditable(false);
    }

}
