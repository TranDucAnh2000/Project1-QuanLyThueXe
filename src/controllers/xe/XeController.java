package controllers.xe;

import controllers.nhanvien.SuaNVController;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.KhachHangModel;
import models.NhanVienModel;
import models.XeModel;
import service.XeService;
import views.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class XeController implements  Initializable{

    @FXML
    private TableView<XeModel> tableXe;

    @FXML
    private TableColumn<XeModel, Integer> col_MaXe;

    @FXML
    private TableColumn<XeModel, String> col_LoaiXe;

    @FXML
    private TableColumn<XeModel, String> col_TenXe;

    @FXML
    private TableColumn<XeModel, Integer> col_GiaThue;

    @FXML
    private TableColumn<XeModel, String> col_MauSac;

    @FXML
    private TableColumn<XeModel, Integer> col_TinhTrang;

    @FXML
    private TableColumn<XeModel, String> col_BienSoXe;

    @FXML
    private TextField textTimKiem;

    private ObservableList<String> comboBoxOblist = FXCollections.observableArrayList("Mã Xe", "Loại Xe", "Biển số xe",
            "Tên xe");
    private ObservableList<XeModel> tableOblist;

    @FXML
    private ComboBox<String> comboBox;
    String colSelected;
    XeService xeService=new XeService();
    private Stage themXeStage = new Stage();
    private Stage suaXeStage = new Stage();
    @FXML
    void comboBoxSelect(ActionEvent event) {
        String temp = comboBox.getSelectionModel().getSelectedItem().toString();
        switch (temp){
            case "Mã Xe": colSelected = "MaXE"; break;
            case "Tên xe": colSelected = "TenXe"; break;
            case "Loại Xe": colSelected = "LoaiXe"; break;
            case "Biển số xe": colSelected = "BienSoXe"; break;

        }
    }

    @FXML
    void sua(ActionEvent event) throws IOException {
        XeModel xeModel = tableXe.getSelectionModel().getSelectedItem();
        //canh bao
        if(xeModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 xe trước !");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("xe/SuaXe.fxml"));
            Parent parent = loader.load();

            SuaXeController suaXeController = loader.getController();
            suaXeController.initializeTextField(xeModel);

            suaXeStage.setScene(new Scene(parent));
            suaXeStage.setTitle("Sửa thông tin xe");
            suaXeStage.show();

            suaXeStage.setOnCloseRequest((e) -> {
                updateTable();
            });
        }
    }

    @FXML
    void them(ActionEvent event) throws IOException {
        themXeStage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("xe/ThemXe.fxml"))));
        themXeStage.setTitle("Thêm xe mới");
        themXeStage.show();

        themXeStage.setOnCloseRequest((e)->{
            updateTable();
        });
    }

    @FXML
    void timKiem(ActionEvent event) {
        String key;
        tableXe.getItems().clear();
//        if(colSelected.equals("Mã Xe")){
//            key=String.valueOf(textTimKiem.getText());
//        }
//        else
            key = textTimKiem.getText();

        List<XeModel> list = xeService.searchListXe(colSelected, key);
        tableOblist = FXCollections.observableArrayList(list);
        tableXe.setItems(tableOblist);
    }

    @FXML
    void xoa(ActionEvent event) {
        XeModel xeModel = tableXe.getSelectionModel().getSelectedItem();
        //canh bao
        if(xeModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 xe trước!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chắc muốn xóa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                int maXe = xeModel.getMaXe();
                xeService.deleteXe(maXe);
                tableXe.getItems().removeAll(tableXe.getSelectionModel().getSelectedItem());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(comboBoxOblist);
        col_MaXe.setCellValueFactory(new PropertyValueFactory<>("MaXe"));
        col_LoaiXe.setCellValueFactory(new PropertyValueFactory<>("LoaiXe"));
        col_TenXe.setCellValueFactory(new PropertyValueFactory<>("tenXe"));
        col_GiaThue.setCellValueFactory(new PropertyValueFactory<>("giaThue"));
        col_MauSac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
        col_TinhTrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        col_BienSoXe.setCellValueFactory(new PropertyValueFactory<>("bienSoXe"));
        List<XeModel> list = xeService.getlistXe();
        tableOblist = FXCollections.observableArrayList(list);
        tableXe.setItems(tableOblist);
    }

    private void updateTable(){
        col_MaXe.setCellValueFactory(new PropertyValueFactory<>("MaXe"));
        col_LoaiXe.setCellValueFactory(new PropertyValueFactory<>("LoaiXe"));
        col_TenXe.setCellValueFactory(new PropertyValueFactory<>("tenXe"));
        col_GiaThue.setCellValueFactory(new PropertyValueFactory<>("giaThue"));
        col_MauSac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
        col_TinhTrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        col_BienSoXe.setCellValueFactory(new PropertyValueFactory<>("bienSoXe"));
        List<XeModel> list = xeService.getlistXe();
        tableOblist = FXCollections.observableArrayList(list);
        tableXe.setItems(tableOblist);
    }
    @FXML
    private void openfile(){

    }
    @FXML
    private void outputfile(){

    }

}
