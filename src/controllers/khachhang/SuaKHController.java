package controllers.khachhang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.KhachHangModel;
import service.KhachHangService;

import java.sql.SQLException;

public class SuaKHController {

    @FXML
    private TextField textMaKH;

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
    void sua(ActionEvent event) throws SQLException {
        KhachHangModel khachHangModel = new KhachHangModel();
        khachHangModel.setMaKH(Integer.valueOf(textMaKH.getText()));
        khachHangModel.setTenKH(textTenKH.getText());
        khachHangModel.setSoDT(textSoDT.getText().trim());
        khachHangModel.setSoCMT(textSoCMT.getText().trim());
        khachHangModel.setDiaChi(textDiaChi.getText().trim());

        khachHangService.editListKhachHang(khachHangModel);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Sửa thành công!");
        alert.showAndWait();
    }

    @FXML
    void xoaText(ActionEvent event) {
        textTenKH.clear();
        textSoCMT.clear();
        textSoDT.clear();
        textDiaChi.clear();
    }

    public void initializeTextField(KhachHangModel khachHangModel){
        textMaKH.setText(String.valueOf(khachHangModel.getMaKH()));
        textDiaChi.setText(khachHangModel.getDiaChi());
        textTenKH.setText(khachHangModel.getTenKH());
        textSoDT.setText(khachHangModel.getSoDT());
        textSoCMT.setText(khachHangModel.getSoCMT());
    }

}
