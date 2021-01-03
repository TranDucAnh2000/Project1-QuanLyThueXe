package controllers.nhanvien;

import controllers.khachhang.SuaKHController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.KhachHangModel;
import models.NhanVienModel;
import service.NhanVienService;
import views.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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

    private Stage themNVStage = new Stage();
    private Stage suaNVStage = new Stage();
    String colSelected;
    //"Mã nhân viên", "Tên nhân viên", "Số điện thoại","Ngày sinh","Địa chỉ","Số CMT"
    @FXML
    void comboBoxSelect(ActionEvent event) {
        String temp = comboBox.getSelectionModel().getSelectedItem().toString();
        switch (temp){
            case "Mã nhân viên": colSelected = "MaNV"; break;
            case "Tên nhân viên": colSelected = "TenNV"; break;
            case "Số điện thoại": colSelected = "SoDT"; break;
            case "Số CMT": colSelected = "SoCMT"; break;
            case "Ngày sinh": colSelected = "NgaySinh"; break;
            case "Địa chỉ": colSelected = "DiaChi"; break;

        }
    }

    @FXML
    void sua(ActionEvent event) throws IOException {
        NhanVienModel nhanVienModel = tableNhanVien.getSelectionModel().getSelectedItem();
        //canh bao
        if(nhanVienModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 nhân viên trước !");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("nhanvien/SuaNV.fxml"));
            Parent parent = loader.load();

            SuaNVController suaNVController = loader.getController();
            suaNVController.initializeTextField(nhanVienModel);

            suaNVStage.setScene(new Scene(parent));
            suaNVStage.setTitle("Sửa thông tin khách hàng");
            suaNVStage.show();

            suaNVStage.setOnCloseRequest((e) -> {
                updateTable();
            });
        }
    }

    @FXML
    void them(ActionEvent event) throws IOException {
        themNVStage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("nhanvien/ThemNV.fxml"))));
        themNVStage.setTitle("Thêm khách hàng");
        themNVStage.show();

        themNVStage.setOnCloseRequest((e)->{
            updateTable();
        });
    }

    @FXML
    void timKiem(ActionEvent event) {
        tableNhanVien.getItems().clear();
        String key = textTimKiem.getText();

        List<NhanVienModel> list = nhanVienService.searchListNV(colSelected, key);
        tableOblist = FXCollections.observableArrayList(list);
        tableNhanVien.setItems(tableOblist);
    }

    @FXML
    void xoa(ActionEvent event) {
        NhanVienModel nhanVienModel = tableNhanVien.getSelectionModel().getSelectedItem();
        //canh bao
        if(nhanVienModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 nhân viên trước ! ");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chắc muốn xóa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                int maNV = nhanVienModel.getMaNV();
                nhanVienService.deleteNV(maNV);
                tableNhanVien.getItems().removeAll(tableNhanVien.getSelectionModel().getSelectedItem());
            }
        }
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
    private void updateTable(){
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
    @FXML
    private void openfile() throws IOException, SQLException {
        nhanVienService.importExcelNV();
    }
    @FXML
    private void outputfile() throws IOException, SQLException {
        nhanVienService.exportExcelNV();
    }
}
