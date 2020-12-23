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
        khachHangModel.setMaKH(textMaKH.getText());
        khachHangModel.setTenKH(textTenKH.getText());
        khachHangModel.setSoDT(Integer.valueOf(textSoDT.getText()));
        khachHangModel.setSoCMT(Integer.valueOf(textSoCMT.getText()));
        khachHangModel.setDiaChi(textDiaChi.getText());

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
        textMaKH.setText(khachHangModel.getMaKH());
        textTenKH.setText(khachHangModel.getTenKH());
        textSoDT.setText(String.valueOf(khachHangModel.getSoDT()));
        textSoCMT.setText(String.valueOf(khachHangModel.getSoCMT()));

        textMaKH.setEditable(false);
    }

}
