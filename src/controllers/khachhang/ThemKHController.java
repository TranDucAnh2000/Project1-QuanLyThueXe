package controllers.khachhang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.KhachHangModel;
import service.KhachHangService;

import java.sql.SQLException;

public class ThemKHController {

    @FXML
    private TextField textTenKH;

    @FXML
    private TextField textSoDT;

    @FXML
    private TextField textSoCMT;

    @FXML
    private TextField textDiaChi;

    private KhachHangService khachHangService = new KhachHangService();

    @FXML
    void them(ActionEvent event) throws SQLException {
        KhachHangModel khachHangModel = new KhachHangModel();
        khachHangModel.setTenKH(textTenKH.getText());
        khachHangModel.setSoDT(textSoDT.getText());
        khachHangModel.setSoCMT(textSoCMT.getText());
        khachHangModel.setDiaChi(textDiaChi.getText());

        khachHangService.addListKhachHang(khachHangModel);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Thêm thành công!");
        alert.showAndWait();
    }

    @FXML
    void xoaText(ActionEvent event) {
        textDiaChi.clear();
        textTenKH.clear();
        textSoCMT.clear();
        textSoDT.clear();
    }

}
