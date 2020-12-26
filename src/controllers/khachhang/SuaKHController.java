package controllers.khachhang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        khachHangModel.setMaKH(Integer.parseInt(textMaKH.getText().trim()));
        khachHangModel.setTenKH(textTenKH.getText());
        khachHangModel.setSoDT(textSoDT.getText().trim());
        khachHangModel.setSoCMT(textSoCMT.getText().trim());
        khachHangModel.setDiaChi(textDiaChi.getText().trim());

        khachHangService.editListKhachHang(khachHangModel);
    }

    @FXML
    void xoaText(ActionEvent event) {
        textTenKH.clear();
        textSoCMT.clear();
        textSoDT.clear();
        textDiaChi.clear();
    }

    public void initializeTextField(KhachHangModel khachHangModel){
        textDiaChi.setText(khachHangModel.getDiaChi());
        textMaKH.setText(String.valueOf(khachHangModel.getMaKH()));
        textTenKH.setText(khachHangModel.getTenKH());
        textSoDT.setText(khachHangModel.getSoDT());
        textSoCMT.setText(khachHangModel.getSoCMT());
        textMaKH.setEditable(false);
    }

}
