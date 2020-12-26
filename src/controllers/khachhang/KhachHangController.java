package controllers.khachhang;

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
import service.KhachHangService;
import views.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class KhachHangController implements Initializable {

    @FXML
    private TableView<KhachHangModel> tableKhachHang;

    @FXML
    private TableColumn<KhachHangModel, String> col_MaKH;

    @FXML
    private TableColumn<KhachHangModel, String> col_TenKH;

    @FXML
    private TableColumn<KhachHangModel, Integer> col_SDT;

    @FXML
    private TableColumn<KhachHangModel, Integer> col_SoCMT;

    @FXML
    private TableColumn<KhachHangModel, Integer> col_DiaChi;

    @FXML
    private TextField textTimKiem;

    @FXML
    private ComboBox<String> comboBox;

    String colSelected;

    private Stage themKHStage = new Stage();
    private Stage suaKHStage = new Stage();

    private KhachHangService khachHangService = new KhachHangService();

    private ObservableList<String> comboBoxOblist = FXCollections.observableArrayList("Mã khách hàng", "Tên khách hàng", "Số điện thoại",
                                                                                      "Số CMT", "Địa chỉ");
    private ObservableList<KhachHangModel> tableOblist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(comboBoxOblist);

        col_MaKH.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        col_TenKH.setCellValueFactory(new PropertyValueFactory<>("TenKH"));
        col_SDT.setCellValueFactory(new PropertyValueFactory<>("SoDT"));
        col_SoCMT.setCellValueFactory(new PropertyValueFactory<>("SoCMT"));
        col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));

        List<KhachHangModel> list = khachHangService.getListKhachHang();
        tableOblist = FXCollections.observableArrayList(list);
        tableKhachHang.setItems(tableOblist);
    }

    @FXML
    void comboBoxSelect(ActionEvent event) {
        String temp = comboBox.getSelectionModel().getSelectedItem().toString();
        switch (temp){
            case "Mã khách hàng": colSelected = "MaKH"; break;
            case "Tên khách hàng": colSelected = "TenKH"; break;
            case "Số điện thoại": colSelected = "SoDT"; break;
            case "Số CMT": colSelected = "SoCMT"; break;
            case "Địa chỉ": colSelected = "DiaChi"; break;
        }
    }

    @FXML
    void sua(ActionEvent event) throws IOException {
        KhachHangModel khachHangModel = tableKhachHang.getSelectionModel().getSelectedItem();
        //canh bao
        if(khachHangModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 khách hàng trước !");
            alert.showAndWait();
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("khachhang/SuaKH.fxml"));
        Parent parent = loader.load();

        SuaKHController suaKHController = loader.getController();
        suaKHController.initializeTextField(khachHangModel);

        suaKHStage.setScene(new Scene(parent));
        suaKHStage.setTitle("Sửa thông tin khách hàng");
        suaKHStage.show();

        suaKHStage.setOnCloseRequest((e)->{
            updateTable();
        });
    }

    @FXML
    void them(ActionEvent event) throws IOException {
        themKHStage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("khachhang/themKH.fxml"))));
        themKHStage.setTitle("Thêm khách hàng");
        themKHStage.show();

        themKHStage.setOnCloseRequest((e)->{
            updateTable();
        });
    }

    @FXML
    void timKiem(ActionEvent event) {
        tableKhachHang.getItems().clear();
        String key = textTimKiem.getText();

        List<KhachHangModel> list = khachHangService.searchListKhachHang(colSelected, key);
        tableOblist = FXCollections.observableArrayList(list);
        tableKhachHang.setItems(tableOblist);
    }

    @FXML
    void xoa(ActionEvent event) throws SQLException {
        KhachHangModel khachHangModel = tableKhachHang.getSelectionModel().getSelectedItem();
        //canh bao
        if(khachHangModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 khách hàng đi bạn!");
            alert.showAndWait();
        }

        int maKH = khachHangModel.getMaKH();
        khachHangService.deleteListKhachHang(maKH);
        tableKhachHang.getItems().removeAll(tableKhachHang.getSelectionModel().getSelectedItem());
    }

    private void updateTable(){
        col_MaKH.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        col_TenKH.setCellValueFactory(new PropertyValueFactory<>("TenKH"));
        col_SDT.setCellValueFactory(new PropertyValueFactory<>("SoDT"));
        col_SoCMT.setCellValueFactory(new PropertyValueFactory<>("SoCMT"));
        col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));

        List<KhachHangModel> list = khachHangService.getListKhachHang();
        tableOblist = FXCollections.observableArrayList(list);
        tableKhachHang.setItems(tableOblist);
    }

}
