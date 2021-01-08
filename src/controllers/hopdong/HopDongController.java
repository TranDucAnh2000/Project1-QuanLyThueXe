package controllers.hopdong;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.HopDongModel;
import models.KhachHangModel;
import models.NhanVienModel;
import service.CTHopDongService;
import service.HopDongService;
import service.KhachHangService;
import service.NhanVienService;
import views.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HopDongController implements Initializable {

    @FXML
    private TextField maHopDong;

    @FXML
    private TableView<KhachHangModel> tableKhachHang;

    @FXML
    private TableColumn<KhachHangModel, Integer> col_MaKH;

    @FXML
    private TableColumn<KhachHangModel, String> col_TenKH;

    @FXML
    private TableView<NhanVienModel> tableNhanVien;

    @FXML
    private TableColumn<NhanVienModel, Integer> col_MaNV;

    @FXML
    private TableColumn<NhanVienModel, String> col_TenNV;

    @FXML
    private TableView<HopDongModel> tableHopDong;

    @FXML
    private TableColumn<HopDongModel, Integer> col_MaNV_HD;

    @FXML
    private TableColumn<HopDongModel, Integer> col_MaKH_HD;

    @FXML
    private TableColumn<HopDongModel, Integer> col_MaHD;

    @FXML
    private TableColumn<HopDongModel, Date> col_NgayThue;

    @FXML
    private TableColumn<HopDongModel, Date> col_NgayHenTra;

    @FXML
    private TableColumn<HopDongModel, Integer> col_TienCoc;

    @FXML
    private TableColumn<HopDongModel, Integer> col_TienThanhToan;

    private HopDongService hopDongService = new HopDongService();
    private KhachHangService khachHangService = new KhachHangService();
    private NhanVienService nhanVienService = new NhanVienService();
    private CTHopDongService ctHopDongService = new CTHopDongService();

   public static HopDongModel hopDongModel1;
    private ObservableList<KhachHangModel> tableKHOblist;
    private ObservableList<NhanVienModel> tableNVOblist;
    private ObservableList<HopDongModel> tableHDOblist;

    private Stage themHDStage = new Stage();
    private Stage suaHDStage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_MaKH.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        col_TenKH.setCellValueFactory(new PropertyValueFactory<>("TenKH"));
        List<KhachHangModel> list1 = khachHangService.getListKhachHang();
        tableKHOblist = FXCollections.observableArrayList(list1);
        tableKhachHang.setItems(tableKHOblist);

        col_MaNV.setCellValueFactory(new PropertyValueFactory<>("MaNV"));
        col_TenNV.setCellValueFactory(new PropertyValueFactory<>("TenNV"));
        List<NhanVienModel> list2 = nhanVienService.getlistNV();
        tableNVOblist = FXCollections.observableArrayList(list2);
        tableNhanVien.setItems(tableNVOblist);

        col_MaKH_HD.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        col_MaNV_HD.setCellValueFactory(new PropertyValueFactory<>("MaNV"));
        col_MaHD.setCellValueFactory(new PropertyValueFactory<>("MaHD"));
        col_NgayThue.setCellValueFactory(new PropertyValueFactory<>("NgayThue"));
        col_NgayHenTra.setCellValueFactory(new PropertyValueFactory<>("NgayHenTra"));
        col_TienCoc.setCellValueFactory(new PropertyValueFactory<>("TienCoc"));
        col_TienThanhToan.setCellValueFactory(new PropertyValueFactory<>("TienThanhToan"));
        List<HopDongModel> list3 = hopDongService.getListHopDong();
        tableHDOblist = FXCollections.observableArrayList(list3);
        tableHopDong.setItems(tableHDOblist);
    }

    @FXML
    void them(ActionEvent event) throws IOException {
        KhachHangModel khachHangModel = tableKhachHang.getSelectionModel().getSelectedItem();
        NhanVienModel nhanVienModel = tableNhanVien.getSelectionModel().getSelectedItem();
        //canh bao
        if(khachHangModel == null || nhanVienModel == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Chọn khách hàng, nhân viên trước!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else {
            HopDongModel hopDongModel = new HopDongModel();
            hopDongModel.setMaKH(tableKhachHang.getSelectionModel().getSelectedItem().getMaKH());
            hopDongModel.setMaNV(tableNhanVien.getSelectionModel().getSelectedItem().getMaNV());

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("hopdong/ThemHD.fxml"));
            Parent parent = loader.load();

            ThemHopDongController themHopDongController = loader.getController();
            themHopDongController.initializeText(hopDongModel);

            themHDStage.setScene(new Scene(parent));
            themHDStage.setTitle("Thêm hợp đồng");
            themHDStage.show();

            themHDStage.setOnCloseRequest((e) -> {
                updateTable();
            });
        }
    }

    @FXML
    void xoa(ActionEvent event) throws IOException, SQLException {
        HopDongModel hopDongModel = tableHopDong.getSelectionModel().getSelectedItem();
        //canh bao
        if(hopDongModel == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Chọn 1 hop dong truoc!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chắc muốn xóa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                int maHD = hopDongModel.getMaHD();
                hopDongService.deleteListHopDong(hopDongModel);
                tableHopDong.getItems().removeAll(tableHopDong.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    void sua(ActionEvent event) throws IOException {
        HopDongModel hopDongModel = tableHopDong.getSelectionModel().getSelectedItem();
        //canh bao
        if(hopDongModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 hợp đồng trước !");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("hopdong/SuaHD.fxml"));
            Parent parent = loader.load();

            SuaHopDongController suaHopDongController = loader.getController();
            suaHopDongController.initializeTextField(hopDongModel);

            suaHDStage.setScene(new Scene(parent));
            suaHDStage.setTitle("Sửa thông tin hợp đồng");
            suaHDStage.show();

            suaHDStage.setOnCloseRequest((e) -> {
                updateTable();
            });
        }
    }

    @FXML
    void handleRow(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2 && tableHopDong.getSelectionModel().getSelectedItem() != null) {
            hopDongModel1 = tableHopDong.getSelectionModel().getSelectedItem();
            ctHopDongService.maHD = hopDongModel1.getMaHD();
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("hopdong/CTHopDong.fxml"))));
            stage.setTitle("Chi tiết hợp đồng");
            stage.show();
        }
    }

    public void updateTable(){
        col_MaKH_HD.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        col_MaNV_HD.setCellValueFactory(new PropertyValueFactory<>("MaNV"));
        col_MaHD.setCellValueFactory(new PropertyValueFactory<>("MaHD"));
        col_NgayThue.setCellValueFactory(new PropertyValueFactory<>("NgayThue"));
        col_NgayHenTra.setCellValueFactory(new PropertyValueFactory<>("NgayHenTra"));
        col_TienCoc.setCellValueFactory(new PropertyValueFactory<>("TienCoc"));
        col_TienThanhToan.setCellValueFactory(new PropertyValueFactory<>("TienThanhToan"));
        List<HopDongModel> list3 = hopDongService.getListHopDong();
        tableHDOblist = FXCollections.observableArrayList(list3);
        tableHopDong.setItems(tableHDOblist);
    }

}
